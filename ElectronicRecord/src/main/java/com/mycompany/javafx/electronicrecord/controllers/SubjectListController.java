package com.mycompany.javafx.electronicrecord.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.mycompany.javafx.electronicrecord.dao.impl.GroupDB;
import com.mycompany.javafx.electronicrecord.dao.impl.SubjectTeacherGroupDB;
import com.mycompany.javafx.electronicrecord.model.Groupstud;
import com.mycompany.javafx.electronicrecord.model.SubjectTeacherGroup;
import com.mycompany.javafx.electronicrecord.utill.AlertMaker;
import com.mycompany.javafx.electronicrecord.utill.ElectronicRecordUtill;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class SubjectListController implements Initializable {

    ObservableList<SubjectTeacherModel> listSubject = FXCollections.observableArrayList();
    ObservableList<Groupstud> groupList = FXCollections.observableArrayList();
    private static int groupIdTarget;
    private static String groupName;
    private static List<SubjectTeacherModel> edtitList;

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableView<SubjectTeacherModel> tableView;

    @FXML
    private TableColumn<SubjectTeacherModel, String> colSubject;

    @FXML
    private TableColumn<SubjectTeacherModel, String> colTeacher;

    @FXML
    private TableColumn<SubjectTeacherModel, Integer> id;

    @FXML
    private TableColumn<SubjectTeacherModel, Integer> colHours;

    @FXML
    private TableColumn<SubjectTeacherModel, Integer> numberSubject;

    @FXML
    private TextField txt_serch;

    @FXML
    private FontAwesomeIconView commit;

    @FXML
    private FontAwesomeIconView rollback;

    @FXML
    private ComboBox<Groupstud> groupComboBox;

    @FXML
    private MenuItem con_edit;

    @FXML
    private MenuItem con_deleteSubject;

    @FXML
    private MenuItem con_addSubject;

    @FXML
    private Label text;

    @FXML
    private JFXButton btn_apply;

    @FXML
    private JFXButton btn_edit;

    @FXML
    private JFXButton btn_addSubject;

    @FXML
    private JFXButton btn_deleteSubject;

    @FXML
    void SelectGroup(MouseEvent event) {

    }

    @FXML
    void handleCopySubjectGroup(ActionEvent event) {

        if (groupComboBox.getSelectionModel().getSelectedItem() == null) {
            AlertMaker.showMaterialDialog(rootPane, contentPane, null, "Группа не выбрана!", "Пожалуйста, выберите группу");
            return;
        }
        try {
            Groupstud groupstud = groupComboBox.getSelectionModel().getSelectedItem();
            SubjectTeacherGroupDB.getInstance().copyByInsert(groupstud.getGroupid(), groupIdTarget);
            handleRefresh(new ActionEvent());
            ElectronicRecordUtill.loadAlertConfrim(getClass().getResource("/fxml/AlertConfrim.fxml"), null, "Успех!", "Данные успешно были скопированы");
        } catch (Exception e) {
            ElectronicRecordUtill.loadAlertError(getClass().getResource("/fxml/AlertError.fxml"), null, "Ooops...", "Что то пошло не так...");
        }

    }

    @FXML
    void closeStage(ActionEvent event) {

    }

    @FXML
    void exportAsPDF(ActionEvent event) {

    }

    @FXML
    void handleRefresh(ActionEvent event) {
        loadData();
    }

    @FXML
    void handleSubjectAdd(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddSubject.fxml"));
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(parent));
            ElectronicRecordUtill.setStageIcon(stage);
            stage.showAndWait();
            stage.setOnHiding((e) -> {
                handleRefresh(new ActionEvent());
            });
        } catch (IOException ex) {
            Logger.getLogger(GroupListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleCommit(MouseEvent event) {
        edtitList = initCollection();

        if (edtitList.isEmpty()) {
            return;
        }

        saveChanges();
        resetColor();
        edtitList.clear();
        ElectronicRecordUtill.loadAlertConfrim(getClass().getResource("/fxml/AlertConfrim.fxml"), null, "Успех!", "Данные успешно были изменены");
    }

    @FXML
    void handleRolback(MouseEvent event) {
        edtitList = initCollection();

        if (edtitList.isEmpty()) {
            return;
        }
        handleRefresh(new ActionEvent());
        edtitList.clear();
        resetColor();
    }

    @FXML
    void handleSubjectDeleteOption(ActionEvent event) {

        List<SubjectTeacherModel> smts = tableView.getSelectionModel().getSelectedItems();
        if (smts.isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, contentPane, null, "Предмет не выбран", "Пожалуйста, выберите предмет");
            return;
        }
        try {

            for (SubjectTeacherModel smt : smts) {
                SubjectTeacherGroupDB.getInstance().deleteSubjectTeacherGroup(smt.getId());
            }
            handleRefresh(new ActionEvent());
            ElectronicRecordUtill.loadAlertConfrim(getClass().getResource("/fxml/AlertConfrim.fxml"), null, "Успех!", "Было удалено " + smts.size() + " запесей");
        } catch (Exception e) {
            ElectronicRecordUtill.loadAlertError(getClass().getResource("/fxml/AlertError.fxml"), null, "Ooops...", "Что то пошло не так...");
        }

    }

    @FXML
    void handleSubjectEditOption(ActionEvent event) {

    }

    @FXML
    void serch(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        groupIdTarget = GroupListController.Group.getIdGroup();
        groupName = GroupListController.Group.getNameGroup();
        initCol();
        loadGroupList();
        loadData();
        settingsTable();
        checkTeacher();

    }

    private void initCol() {
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTeacher.setCellValueFactory(new PropertyValueFactory<>("fullNameTeacher"));
        colHours.setCellValueFactory(new PropertyValueFactory<>("hours"));
        numberSubject.setCellValueFactory(new PropertyValueFactory<>("numberSubject"));
    }

    private void settingsTable() {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setEditable(true);
        colHours.setCellFactory(integerCell(value -> value >= 0));
        colHours.setOnEditCommit((event) -> {
            Integer value = event.getNewValue();
            if (value != null) {

                event.getTableView().getItems().get(event.getTablePosition().getRow()).setHours(value);
                edtitList = initCollection();
                edtitList.add(tableView.getSelectionModel().getSelectedItem());
                setColor();

            }
        });
    }

    private void loadData() {
        listSubject.clear();

        int amount = 1;
        for (SubjectTeacherGroup stg : SubjectTeacherGroupDB.getInstance().getSubjectAndTeacherByGroup(groupName)) {

            SubjectTeacherModel modelTable = new SubjectTeacherModel();

            modelTable.setFullNameTeacher(stg.getTeacher().getUser().getSurname()
                    + " " + stg.getTeacher().getUser().getName() + " " + stg.getTeacher().getUser().getMidleName());
            modelTable.setSubjectName(stg.getSubjectId().getNameSubject());
            modelTable.setHours(stg.getHours());
            modelTable.setNumberSubject(amount);
            modelTable.setId(stg.getIdsubjectTeacherGroup());
            modelTable.setNameTeaher(stg.getTeacher().getUser().getName());
            modelTable.setSurnameTeaher(stg.getTeacher().getUser().getSurname());
            modelTable.setMidleNameTeaher(stg.getTeacher().getUser().getMidleName());
            amount++;

            listSubject.add(modelTable);
        }
        tableView.setItems(listSubject);
    }

    private void loadGroupList() {
        groupList.clear();
        for (Groupstud groupstud : GroupDB.getInstance().getGroupsExceptForTarget(groupIdTarget)) {
            groupList.add(groupstud);
        }
        groupComboBox.setItems(groupList);

    }

    protected <T> Callback<TableColumn<T, Integer>, TableCell<T, Integer>> integerCell(
            Predicate<Integer> validator) {
        return TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                if (object == null) {
                    return null;
                }
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                try {
                    int value = Integer.parseInt(string);
                    if (validator.test(value)) {
                        return value;
                    } else {
                        AlertMaker.showMaterialDialog(rootPane, contentPane, null, "Неверный формат!", "Количество часов не может быть меньше нуля!");
                        return null;
                    }
                } catch (NumberFormatException e) {
                    AlertMaker.showMaterialDialog(rootPane, contentPane, null, "Неверный формат!", "Введите целое положительное число без разделителей");
                    return null;
                }
            }
        });
    }

    private void saveChanges() {
        try {
            for (SubjectTeacherModel subjectModelTable : edtitList) {
                SubjectTeacherGroupDB.getInstance().updateHours(subjectModelTable.getId(), subjectModelTable.getHours());
            }
        } catch (Exception e) {
            ElectronicRecordUtill.loadAlertError(getClass().getResource("/fxml/AlertError.fxml"), null, "Ooops...", "Что то пошло не так...");
        }

    }

    public static List<SubjectTeacherModel> initCollection() {
        if (edtitList == null) {
            edtitList = new ArrayList<>();
        }

        return edtitList;
    }

    private void setColor() {
        commit.setFill(Color.web("#70ff7e"));
        rollback.setFill(Color.web("#ff6161"));
        commit.setOpacity(1);
        rollback.setOpacity(1);
    }

    private void resetColor() {
        commit.setFill(Color.web("#b2b2b2"));
        commit.setOpacity(0.5);
        rollback.setFill(Color.web("#b2b2b2"));
        rollback.setOpacity(0.5);
    }

    public static Integer getGroupId() {
        return groupIdTarget;
    }

    private void checkTeacher() {
        if (LoginController.getUserType().equals("Teacher")) {
            rollback.setVisible(false);
            commit.setVisible(false);
            con_edit.setVisible(false);
            con_addSubject.setVisible(false);
            con_deleteSubject.setVisible(false);
            btn_apply.setVisible(false);
            text.setVisible(false);
            btn_deleteSubject.setVisible(false);
            btn_edit.setVisible(false);
            groupComboBox.setVisible(false);
            btn_addSubject.setVisible(false);
   
        }
    }

    public static class SubjectTeacherModel {

        private SimpleStringProperty subjectName;
        private SimpleStringProperty fullNameTeacher;
        private SimpleIntegerProperty hours;
        private SimpleIntegerProperty id;
        private SimpleIntegerProperty numberSubject;
        private String nameTeaher;
        private String surnameTeaher;
        private String midleNameTeaher;
        private Integer teacherId;
        private Integer subjectId;
        private Integer groupId;

        public SubjectTeacherModel() {
        }

        public SubjectTeacherModel(String subjectName, String teacher, int hours, int id, int numberSubject) {
            this.subjectName = new SimpleStringProperty(subjectName);
            this.fullNameTeacher = new SimpleStringProperty(teacher);
            this.hours = new SimpleIntegerProperty(hours);
            this.id = new SimpleIntegerProperty(id);
            this.numberSubject = new SimpleIntegerProperty(numberSubject);

        }

        public String getSubjectName() {
            return subjectName.get();
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = new SimpleStringProperty(subjectName);
        }

        public String getFullNameTeacher() {
            return fullNameTeacher.get();
        }

        public void setFullNameTeacher(String teacher) {
            this.fullNameTeacher = new SimpleStringProperty(teacher);
        }

        public int getHours() {
            return hours.get();
        }

        public void setHours(int hours) {
            this.hours = new SimpleIntegerProperty(hours);
        }

        public int getId() {
            return id.get();
        }

        public void setId(int id) {
            this.id = new SimpleIntegerProperty(id);
        }

        @Override
        public String toString() {
            return "SubjectModelTable{" + "subjectName=" + subjectName + ", teacher=" + fullNameTeacher + ", hours=" + hours + ", id=" + id + '}';
        }

        public String getNameTeaher() {
            return nameTeaher;
        }

        public void setNameTeaher(String nameTeaher) {
            this.nameTeaher = nameTeaher;
        }

        public String getSurnameTeaher() {
            return surnameTeaher;
        }

        public void setSurnameTeaher(String surnameTeaher) {
            this.surnameTeaher = surnameTeaher;
        }

        public String getMidleNameTeaher() {
            return midleNameTeaher;
        }

        public void setMidleNameTeaher(String midleNameTeaher) {
            this.midleNameTeaher = midleNameTeaher;
        }

        public int getNumberSubject() {
            return numberSubject.get();
        }

        public void setNumberSubject(int numberSubject) {
            this.numberSubject = new SimpleIntegerProperty(numberSubject);
        }

        public Integer getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(Integer teacherId) {
            this.teacherId = teacherId;
        }

        public Integer getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(Integer subjectId) {
            this.subjectId = subjectId;
        }

        public Integer getGroupId() {
            return groupId;
        }

        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }

    }

}
