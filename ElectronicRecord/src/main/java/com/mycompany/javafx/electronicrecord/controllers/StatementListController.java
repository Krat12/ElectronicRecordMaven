package com.mycompany.javafx.electronicrecord.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.mycompany.javafx.electronicrecord.dao.impl.GroupDB;
import com.mycompany.javafx.electronicrecord.dao.impl.StatementDB;
import com.mycompany.javafx.electronicrecord.dao.impl.SubjectDB;
import com.mycompany.javafx.electronicrecord.model.Groupstud;
import com.mycompany.javafx.electronicrecord.model.Statement;
import com.mycompany.javafx.electronicrecord.model.Subject;
import com.mycompany.javafx.electronicrecord.utill.AlertMaker;
import com.mycompany.javafx.electronicrecord.utill.ElectronicRecordUtill;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StatementListController implements Initializable {

    private static final String[] TYPE_MARK = {"Зачет", "Дифференцированный зачет", "Дипломная работа", "Курсовая работа"};
    ObservableList<String> listTypeCertification = FXCollections.observableArrayList(TYPE_MARK);
    ObservableList<Groupstud> listGroups = FXCollections.observableArrayList();
    ObservableList<Subject> listSubjects = FXCollections.observableArrayList();

    ObservableList<StatementModel> statementModels = FXCollections.observableArrayList();
    private Date dateTo = null;
    private Date dateFrom = null;

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableView<StatementModel> tableView;

    @FXML
    private TableColumn<StatementModel, Integer> numberStatement;

    @FXML
    private TableColumn<StatementModel, String> colGrpoup;

    @FXML
    private TableColumn<StatementModel, String> colSubject;

    @FXML
    private TableColumn<StatementModel, String> colTeacher;

    @FXML
    private TableColumn<StatementModel, String> colType;

    @FXML
    private TableColumn<StatementModel, Integer> colHours;

    @FXML
    private TableColumn<StatementModel, String> colDate;

    @FXML
    private ComboBox<Groupstud> cmb_group;

    @FXML
    private ComboBox<Subject> cmb_subject;

    @FXML
    private JFXDatePicker dp_From;

    @FXML
    private JFXDatePicker dp_To;

    @FXML
    private ComboBox<String> cmb_typeСertification;

    @FXML
    void closeStage(ActionEvent event) {

    }

    @FXML
    void exportAsPDF(ActionEvent event) {

    }

    @FXML
    void handleSearch(ActionEvent event) {
        if (!FieldIsEmpty()) {
            Subject subject = cmb_subject.getSelectionModel().getSelectedItem();
            String type = cmb_typeСertification.getSelectionModel().getSelectedItem();
            Groupstud groupstud = cmb_group.getSelectionModel().getSelectedItem();
            List<Statement> statements = null;
            if (LoginController.getUserType().equals("Admin")) {
                statements = StatementDB.getInstance().getStatementsByCriteria(type, subject, groupstud, getStartDate(), getEndDate(), "", 0);
            } else {
                statements = StatementDB.getInstance().getStatementsByCriteria(type, subject, groupstud, getStartDate(), getEndDate(),
                        LoginController.getUserType(), LoginController.getUserId());
            }

            if (!StatementListIsEmpty(statements)) {
                loadDataInTable(statements);
            }

        } else {
            AlertMaker.showMaterialDialog(rootPane, contentPane, null, "Слишком много данных", "Пожалуйста, выберите поля для фильтра");
        }

    }

    @FXML
    void SelectRow(MouseEvent event) {
        StatementModel model = tableView.getSelectionModel().getSelectedItem();
        if (model != null) {
            if (event.getClickCount() == 2) {
                Stage stage = new Stage(StageStyle.DECORATED);
                MarkListController.setStatementMode();
                CreateMarkController.setSelectTypeMark(model.getTypeСertification());
                CreateMarkController.setStatementId(model.getStatementId());
                ElectronicRecordUtill.loadWindow(getClass().getResource("/fxml/MarkList.fxml"), "Список студентов", stage);
                System.out.println(model.getStatementId());

            }
        }

    }

    @FXML
    void handleStatementOneDay(ActionEvent event) {
        betweenDate(1);
    }

    @FXML
    void handleStatementSevenDay(ActionEvent event) {
        betweenDate(7);
    }

    @FXML
    void handleStatementThirtyDay(ActionEvent event) {
        betweenDate(30);
    }

    private void betweenDate(int day) {
        Date currentDate = new Date();
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(day);
        Date confertDate = Date.from(thirtyDaysAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Statement> statements = null;
        if (LoginController.getUserType().equals("Admin")) {
            statements = StatementDB.getInstance().getStatementsByCriteria(null, null, null, confertDate, currentDate, "", 0);
        }else{
            statements = StatementDB.getInstance().getStatementsByCriteria(null, null, null, confertDate, currentDate, LoginController.getUserType(), LoginController.getUserId());
        }

        if (!StatementListIsEmpty(statements)) {
            loadDataInTable(statements);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkTeacherOrAdmin();
        initCol();
        cmb_group.setItems(listGroups);
        cmb_subject.setItems(listSubjects);
        cmb_typeСertification.setItems(listTypeCertification);
    }

    private void checkTeacherOrAdmin() {
        if (LoginController.getUserType().equals("Admin")) {
            listSubjects.addAll(SubjectDB.getInstance().getAllSubjects());
            listGroups.addAll(GroupDB.getInstance().getAllGroups());
        }
        if (LoginController.getUserType().equals("Teacher")) {
            listGroups.addAll(GroupDB.getInstance().getGroupstudsByTeacher(LoginController.getUserId()));
            listSubjects.addAll(SubjectDB.getInstance().getSubjectsByTeacher(LoginController.getUserId()));
        }
    }

    private void initCol() {
        colDate.setCellValueFactory(new PropertyValueFactory<>("data"));
        colGrpoup.setCellValueFactory(new PropertyValueFactory<>("group"));
        colHours.setCellValueFactory(new PropertyValueFactory<>("hours"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colTeacher.setCellValueFactory(new PropertyValueFactory<>("fullNameTeacher"));
        numberStatement.setCellValueFactory(new PropertyValueFactory<>("numberStatement"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeСertification"));
    }

    private Date getStartDate() {
        if (dp_To.getValue() != null) {
            dateTo = Date.from(dp_To.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        return dateTo;
    }

    private Date getEndDate() {
        if (dp_From.getValue() != null) {
            dateFrom = Date.from(dp_From.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        return dateFrom;
    }

    private boolean StatementListIsEmpty(List<Statement> statements) {
        if (statements.isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, contentPane, null, "Ничего не найдено", "Пожалуйста, проверьте, ввели ли вы правильные данные.");
            return true;
        }
        return false;
    }

    private void loadDataInTable(List<Statement> statements) {
        statementModels.clear();
        int amount = 1;
        for (Statement statement : statements) {
            StatementModel statementModel = new StatementModel();
            statementModel.setGroup(statement.getGroupId().getGroupname());
            statementModel.setGroupId(statement.getGroupId().getGroupid());
            statementModel.setSubject(statement.getSubjectId().getNameSubject());
            statementModel.setSubjectId(statement.getSubjectId().getSubjectId());
            statementModel.setData(statement.getDate().toString());
            statementModel.setHours(statement.getHours());
            statementModel.setTypeСertification(statement.getType());
            statementModel.setTeacherId(statement.getTeacherId().getTeacherid());
            statementModel.setStatementId(statement.getStatementId());
            statementModel.setNumberStatement(amount);
            amount++;
            statementModels.add(statementModel);
        }
        tableView.setItems(statementModels);
    }

    private boolean FieldIsEmpty() {
        boolean result = (cmb_group.getValue() == null) && (cmb_subject.getValue() == null)
                && (cmb_typeСertification.getValue() == null) && (dp_From.getValue() == null)
                && (dp_To.getValue() == null);

        return result;
    }

    public static class StatementModel {

        private SimpleStringProperty fullNameTeacher;
        private SimpleStringProperty group;
        private SimpleStringProperty typeСertification;
        private SimpleIntegerProperty hours;
        private SimpleStringProperty data;
        private SimpleStringProperty subject;
        private SimpleIntegerProperty numberStatement;
        private String name;
        private String surname;
        private String midleName;
        private int teacherId;
        private int statementId;
        private int subjectId;
        private int groupId;

        public StatementModel() {
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the surname
         */
        public String getSurname() {
            return surname;
        }

        /**
         * @param surname the surname to set
         */
        public void setSurname(String surname) {
            this.surname = surname;
        }

        /**
         * @return the midleName
         */
        public String getMidleName() {
            return midleName;
        }

        /**
         * @param midleName the midleName to set
         */
        public void setMidleName(String midleName) {
            this.midleName = midleName;
        }

        /**
         * @return the teacherId
         */
        public int getTeacherId() {
            return teacherId;
        }

        /**
         * @param teacherId the teacherId to set
         */
        public void setTeacherId(int teacherId) {
            this.teacherId = teacherId;
        }

        /**
         * @return the statementId
         */
        public int getStatementId() {
            return statementId;
        }

        /**
         * @param statementId the statementId to set
         */
        public void setStatementId(int statementId) {
            this.statementId = statementId;
        }

        /**
         * @return the subjectId
         */
        public int getSubjectId() {
            return subjectId;
        }

        /**
         * @param subjectId the subjectId to set
         */
        public void setSubjectId(int subjectId) {
            this.subjectId = subjectId;
        }

        /**
         * @return the fullNameTeacher
         */
        public String getFullNameTeacher() {
            return fullNameTeacher.get();
        }

        /**
         * @param fullNameTeacher the fullNameTeacher to set
         */
        public void setFullNameTeacher(String fullNameTeacher) {
            this.fullNameTeacher = new SimpleStringProperty(fullNameTeacher);
        }

        /**
         * @return the group
         */
        public String getGroup() {
            return group.get();
        }

        /**
         * @param group the group to set
         */
        public void setGroup(String group) {
            this.group = new SimpleStringProperty(group);
        }

        /**
         * @return the typeСertification
         */
        public String getTypeСertification() {
            return typeСertification.get();
        }

        /**
         * @param typeСertification the typeСertification to set
         */
        public void setTypeСertification(String typeСertification) {
            this.typeСertification = new SimpleStringProperty(typeСertification);

        }

        /**
         * @return the hours
         */
        public Integer getHours() {
            return hours.get();
        }

        /**
         * @param hours the hours to set
         */
        public void setHours(Integer hours) {
            this.hours = new SimpleIntegerProperty(hours);
        }

        /**
         * @return the data
         */
        public String getData() {
            return data.get();
        }

        /**
         * @param data the data to set
         */
        public void setData(String data) {
            this.data = new SimpleStringProperty(data);
        }

        /**
         * @return the subject
         */
        public String getSubject() {
            return subject.get();
        }

        /**
         * @param subject the subject to set
         */
        public void setSubject(String subject) {
            this.subject = new SimpleStringProperty(subject);
        }

        /**
         * @return the numberStatement
         */
        public int getNumberStatement() {
            return numberStatement.get();
        }

        /**
         * @param numberStatement the numberStatement to set
         */
        public void setNumberStatement(int numberStatement) {
            this.numberStatement = new SimpleIntegerProperty(numberStatement);
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

    }

}
