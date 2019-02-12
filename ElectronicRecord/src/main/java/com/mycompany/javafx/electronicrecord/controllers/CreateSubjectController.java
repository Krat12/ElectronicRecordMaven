package com.mycompany.javafx.electronicrecord.controllers;

import com.mycompany.javafx.electronicrecord.dao.impl.SubjectDB;
import com.mycompany.javafx.electronicrecord.dao.impl.TeacherDB;
import com.mycompany.javafx.electronicrecord.model.Subject;
import com.mycompany.javafx.electronicrecord.model.Teacher;
import com.mycompany.javafx.electronicrecord.utill.AlertMaker;
import com.mycompany.javafx.electronicrecord.utill.ElectronicRecordUtill;
import java.io.IOException;
import java.net.URL;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class CreateSubjectController implements Initializable {

    ObservableList<SubjectModelTable> listSubject = FXCollections.observableArrayList();
    ObservableList<TeacherModelTable> listTeacher = FXCollections.observableArrayList();
    protected static List<SubjectListController.SubjectTeacherModel> listInsertPreview;

    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableView<TeacherModelTable> teacherTable;

    @FXML
    private TableColumn<TeacherModelTable, String> colTeacher;

    @FXML
    private TableColumn<TeacherModelTable, Integer> id;

    @FXML
    private TableView<SubjectModelTable> subjectTable;

    @FXML
    private TableColumn<SubjectModelTable, String> colSubject;

    @FXML
    private TableColumn<SubjectModelTable, Integer> idSubject;

    @FXML
    private TableColumn<SubjectModelTable, Integer> colHours;

    @FXML
    private TextField txt_serchTeacher;

    @FXML
    private TextField txt_serchSubject;

    @FXML
    void handleSubjectDeleteOption(ActionEvent event) {

    }

    @FXML
    void SelectGroup(MouseEvent event) {

    }

    @FXML
    void closeStage(ActionEvent event) {

    }

    @FXML
    void exportAsPDF(ActionEvent event) {

    }

    @FXML
    void handlePreview(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PreviewList.fxml"));
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent)); 
            stage.initModality(Modality.APPLICATION_MODAL); 
            ElectronicRecordUtill.setStageIcon(stage);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(GroupListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleSave(ActionEvent event) throws IOException {
        TeacherModelTable teacherModelTable = teacherTable.getSelectionModel().getSelectedItem();

        if (teacherModelTable == null) {
            AlertMaker.showMaterialDialog(rootPane, contentPane, null, "Преподователь не выбран", "Пожалуйста, выберите преподователя");
            return;
        }
        List<SubjectModelTable> modelTables = subjectTable.getSelectionModel().getSelectedItems();
        if (modelTables.isEmpty()) {
            AlertMaker.showMaterialDialog(rootPane, contentPane, null, "Предмет не выбран", "Пожалуйста, выберите предмет");
            return;
        }
        listInsertPreview = SubjectListController.initCollection();
        for (SubjectModelTable modelTable : modelTables) {
            SubjectListController.SubjectTeacherModel stm = new SubjectListController.SubjectTeacherModel();
            stm.setFullNameTeacher(teacherModelTable.getFullNameTeacher());
            stm.setSurnameTeaher(teacherModelTable.getSurnameTeaher());
            stm.setNameTeaher(teacherModelTable.getNameTeaher());
            stm.setMidleNameTeaher(teacherModelTable.getMidleNameTeaher());
            stm.setTeacherId(teacherModelTable.getId());
            stm.setSubjectName(modelTable.getSubjectName());
            stm.setSubjectId(modelTable.getId());
            stm.setHours(modelTable.getHours());
            listInsertPreview.add(stm);   
        }

    }

    @FXML
    void serchSubject(ActionEvent event) {

    }

    @FXML
    void serchTeacher(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadSubject();
        loadTeacher();
        settingsTable();

    }

    private void initCol() {
        colSubject.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTeacher.setCellValueFactory(new PropertyValueFactory<>("fullNameTeacher"));
        colHours.setCellValueFactory(new PropertyValueFactory<>("hours"));
        idSubject.setCellValueFactory(new PropertyValueFactory<>("id"));

    }

    private void settingsTable() {
        subjectTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        subjectTable.setEditable(true);
       

        colHours.setCellFactory(integerCell(value -> value >= 0));
        colHours.setOnEditCommit((event) -> {
            Integer value = event.getNewValue();
            if (value != null) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setHours(value);
            }
        });
    }

    private void loadSubject() {
        listSubject.clear();

        for (Subject subject : SubjectDB.getInstance().getAllSubjects()) {
            SubjectModelTable modelTable = new SubjectModelTable(0, subject.getSubjectId(), subject.getNameSubject());
            listSubject.add(modelTable);
        }
        subjectTable.setItems(listSubject);
    }

    private void loadTeacher() {
        listTeacher.clear();

        for (Teacher teacher : TeacherDB.getInstance().getAllTeachers()) {
            TeacherModelTable modelTable = new TeacherModelTable();
            modelTable.setFullNameTeacher(teacher.getUser().getSurname() + " " + teacher.getUser().getName() + " " + teacher.getUser().getMidleName());
            modelTable.setId(teacher.getTeacherid());
            modelTable.setNameTeaher(teacher.getUser().getName());
            modelTable.setSurnameTeaher(teacher.getUser().getSurname());
            modelTable.setMidleNameTeaher(teacher.getUser().getMidleName());
            listTeacher.add(modelTable);
        }
        teacherTable.setItems(listTeacher);
    }

    public static List<SubjectListController.SubjectTeacherModel> getPreviewCollection() {
        if (listInsertPreview == null) {
            listInsertPreview = SubjectListController.initCollection();
            return listInsertPreview;
        }
        return listInsertPreview;
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
    

//<editor-fold defaultstate="collapsed" desc="TeacherBeanModelTable">
    public static class TeacherModelTable {

        private SimpleStringProperty fullNameTeacher;
        private SimpleIntegerProperty id;
        private String nameTeaher;
        private String surnameTeaher;
        private String midleNameTeaher;

        public TeacherModelTable() {
        }

        public TeacherModelTable(String fullNameTeacher, Integer id, String nameTeaher, String surnameTeaher, String midleNameTeaher) {
            this.fullNameTeacher = new SimpleStringProperty(fullNameTeacher);
            this.id = new SimpleIntegerProperty(id);
            this.nameTeaher = nameTeaher;
            this.surnameTeaher = surnameTeaher;
            this.midleNameTeaher = midleNameTeaher;
        }

        public String getFullNameTeacher() {
            return fullNameTeacher.get();
        }

        public void setFullNameTeacher(String fullNameTeacher) {
            this.fullNameTeacher = new SimpleStringProperty(fullNameTeacher);
        }

        public Integer getId() {
            return id.get();
        }

        public void setId(Integer id) {
            this.id = new SimpleIntegerProperty(id);
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
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="SubjectBeanModeLTable">
    public static class SubjectModelTable {

        private SimpleIntegerProperty hours;
        private SimpleIntegerProperty id;
        private SimpleStringProperty subjectName;

        public SubjectModelTable(Integer hours, Integer id, String subjectName) {
            this.hours = new SimpleIntegerProperty(hours);
            this.id = new SimpleIntegerProperty(id);
            this.subjectName = new SimpleStringProperty(subjectName);
        }

        public Integer getHours() {
            return hours.get();
        }

        public void setHours(Integer hours) {
            this.hours.set(hours);
        }

        public Integer getId() {
            return id.get();
        }

        public void setId(Integer id) {
            this.id.set(id);
        }

        public String getSubjectName() {
            return subjectName.get();
        }

        public void setSubjectName(String subjectName) {
            this.subjectName.set(subjectName);
        }

    }
//</editor-fold>

}
