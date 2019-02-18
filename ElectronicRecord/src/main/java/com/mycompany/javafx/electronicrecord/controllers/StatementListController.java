package com.mycompany.javafx.electronicrecord.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.mycompany.javafx.electronicrecord.dao.impl.GroupDB;
import com.mycompany.javafx.electronicrecord.dao.impl.SubjectDB;
import com.mycompany.javafx.electronicrecord.model.Groupstud;
import com.mycompany.javafx.electronicrecord.model.Subject;
import java.net.URL;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class StatementListController implements Initializable {

    private static final String[] TYPE_MARK = {"Зачет", "Дифференцированный зачет", "Дипломная работа", "Курсовая работа"};
    ObservableList<String> listTypeCertification = FXCollections.observableArrayList(TYPE_MARK);
    ObservableList<Groupstud> listGroups = FXCollections.observableArrayList();
    ObservableList<Subject> listSubjects = FXCollections.observableArrayList();
    ObservableList<StatementModel> statementModels = FXCollections.observableArrayList();

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
    private TableColumn<StatementModel,String> colSubject;

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

    }

    @FXML
    void handleStatementOneDay(ActionEvent event) {

    }

    @FXML
    void handleStatementSevenDay(ActionEvent event) {

    }

    @FXML
    void handleStatementThirtyDay(ActionEvent event) {

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

        }
    }
    
    private void initCol(){
        colDate.setCellValueFactory(new PropertyValueFactory<>("data"));
        colGrpoup.setCellValueFactory(new PropertyValueFactory<>("group"));
        colHours.setCellValueFactory(new PropertyValueFactory<>("hours"));
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        colTeacher.setCellValueFactory(new PropertyValueFactory<>("fullNameTeacher"));
        numberStatement.setCellValueFactory(new PropertyValueFactory<>("numberStatement"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeСertification"));
    }
    
    public static class StatementModel{
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
       
    
    }

}
