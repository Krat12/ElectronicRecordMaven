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
    private TableColumn<Student, Integer> numberStudent;

    @FXML
    private TableColumn<Student, String> fullName;

    @FXML
    private TableColumn<Student, Integer> id;

    @FXML
    private TableColumn<Student, Integer> numberRecord;

    @FXML
    private TableColumn<Student, Integer> course;
    @FXML
    private JFXComboBox<String> groupsList;

    private void initCol() {
        numberStudent.setCellValueFactory(new PropertyValueFactory<>("numberStudent"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        numberRecord.setCellValueFactory(new PropertyValueFactory<>("numberRecord"));
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        course.setCellValueFactory(new PropertyValueFactory<>("course"));
        fullName.setMinWidth(300);
        numberStudent.setMinWidth(15);
        numberRecord.setMinWidth(30);
        course.setMinWidth(15);
    }

    private void loadData() {
        listStudents.clear();
        groups.clear();
        int i = 1;
        for (com.mycompany.javafx.electronicrecord.model.Student student : StudentDB.getInstance().getAllStudents()) {
            
            Integer numberStudent = i;   
            String fullName = student.getUser().getName() + " " + student.getUser().getSurname() + " " + student.getUser().getMidleName();
            Integer numberRecord = student.getNumberBook();
            Integer id = student.getStudentid();
            Integer course = student.getCourse();
            listStudents.add(new Student(numberStudent, fullName,numberRecord, id, course));
            i++;
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
        private SimpleIntegerProperty numberStudent; 
        private SimpleStringProperty fullName;
        private SimpleIntegerProperty numberRecord;
        private SimpleIntegerProperty id;
        private SimpleIntegerProperty course;

        public Student(Integer numberStudent, String fullName , Integer numberRecord, Integer id, Integer course) {
            this.fullName = new SimpleStringProperty(fullName);
            this.numberStudent = new SimpleIntegerProperty(numberStudent);
            this.numberRecord = new SimpleIntegerProperty(numberRecord);
            this.id = new SimpleIntegerProperty(id);
            this.course = new SimpleIntegerProperty(course);
        }

        public Integer getNumberStudent() {
            return numberStudent.get();
        }

        public String getFullName() {
            return fullName.get();
        }

        public Integer getNumberRecord() {
            return numberRecord.get();
        }

        public Integer getId() {
            return id.get();
        }

        public Integer getCourse() {
            return course.get();
        }
        
        
        




    }

}
