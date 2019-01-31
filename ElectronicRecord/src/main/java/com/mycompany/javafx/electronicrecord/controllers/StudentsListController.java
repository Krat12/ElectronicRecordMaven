package com.mycompany.javafx.electronicrecord.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.mycompany.javafx.electronicrecord.dao.impl.GroupDB;
import com.mycompany.javafx.electronicrecord.dao.impl.StudentDB;
import com.mycompany.javafx.electronicrecord.model.Groupstud;
import com.mycompany.javafx.electronicrecord.utill.AlertMaker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class StudentsListController implements Initializable {

    ObservableList<Student> listStudents = FXCollections.observableArrayList();
    ObservableList<String> groups = FXCollections.observableArrayList();

    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableView<Student> tableView;

    @FXML
    private TableColumn<Student, String> fullName;

    @FXML
    private TableColumn<Student, String> group;

    @FXML
    private TableColumn<Student, Integer> id;

    @FXML
    private TableColumn<Student, String> speciality;

    @FXML
    private TableColumn<Student, Integer> course;
    @FXML
    private JFXComboBox<String> groupsList;

    private void initCol() {
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        speciality.setCellValueFactory(new PropertyValueFactory<>("speciality"));
        group.setCellValueFactory(new PropertyValueFactory<>("group"));
        course.setCellValueFactory(new PropertyValueFactory<>("course"));
    }

    private void loadData() {
        listStudents.clear();
        groups.clear();

        for (com.mycompany.javafx.electronicrecord.model.Student student : StudentDB.getInstance().getAllStudents()) {

            String fullName = student.getUser().getName() + " " + student.getUser().getSurname() + " " + student.getUser().getMidleName();
            String speciality = student.getGroupid().getSpecialityId().getNameSpeciality();
            Integer id = student.getStudentid();
            String group = student.getGroupid().getGroupname();
            Integer course = student.getCourse();
            listStudents.add(new Student(fullName, speciality, id, group, course));
        }
        for (Groupstud groupstud : GroupDB.getInstance().getAllGroups()) {
            groups.add(groupstud.getGroupname());
            
        }
         groupsList.setItems(groups);

        tableView.setItems(listStudents);
    }

    @FXML
    void closeStage(ActionEvent event) {

    }

    @FXML
    void exportAsPDF(ActionEvent event) {

    }

    @FXML
    void handleBookDeleteOption(ActionEvent event) {

    }

    @FXML
    void handleBookEditOption(ActionEvent event) {
     Student selectedForEdit = tableView.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            AlertMaker.showErrorMessage("Студент не выбран", "Пожалуйста, выберите студента.");
            return;
        }

    }

    @FXML
    void handleRefresh(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadData();
          tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
       
    }

    public static class Student {

        private SimpleStringProperty fullName;
        private SimpleStringProperty speciality;
        private SimpleIntegerProperty id;
        private SimpleStringProperty group;
        private SimpleIntegerProperty course;

        public Student(String fullName, String speciality, Integer id, String group, Integer course) {
            this.fullName = new SimpleStringProperty(fullName);
            this.speciality = new SimpleStringProperty(speciality);
            this.id = new SimpleIntegerProperty(id);
            this.group = new SimpleStringProperty(group);
            this.course = new SimpleIntegerProperty(course);
        }

        public String getFullName() {
            return fullName.get();
        }

        public String getSpeciality() {
            return speciality.get();
        }

        public int getId() {
            return id.get();
        }

        public String getGroup() {
            return group.get();
        }

        public int getCourse() {
            return course.get();
        }

    }

}
