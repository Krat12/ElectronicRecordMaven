package com.mycompany.javafx.electronicrecord.controllers;

import com.mycompany.javafx.electronicrecord.dao.impl.StudentDB;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class StudentsListController implements Initializable {

    ObservableList<Student> listStudents = FXCollections.observableArrayList();
    ObservableList<Student> sortStudents = FXCollections.observableArrayList();
    private static String groupName;

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
    private TableColumn<Student, String> login;
    
    @FXML
    private TableColumn<Student, String> password;
    

    @FXML
    private TextField txt_serch;

    private void initCol() {
        numberStudent.setCellValueFactory(new PropertyValueFactory<>("numberStudent"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        numberRecord.setCellValueFactory(new PropertyValueFactory<>("numberRecord"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
     
    }

    private void loadData() {
        listStudents.clear();
      
        int amount = 1;
        for (com.mycompany.javafx.electronicrecord.model.Student student : StudentDB.getInstance().getStudentsByGroup(GroupListController.Group.getNameGroup())) {

            Integer numberStudent = amount;
            String fullName = student.getUser().getSurname() + " " + student.getUser().getName() + " " + student.getUser().getMidleName();
            Integer id = student.getStudentid();
            String login = student.getUser().getLogin();
            String password = student.getUser().getPassword();
            Integer numberRecord = student.getNumberBook();
            
            listStudents.add(new Student(numberStudent, fullName, id,login,password,numberRecord));
            amount++;
        }
        tableView.setItems(listStudents);
    }

    @FXML
    void closeStage(ActionEvent event) {

    }

    @FXML
    void exportAsPDF(ActionEvent event) {

    }

    @FXML
    void handleStudentDeleteOption(ActionEvent event) {

    }

    @FXML
    void handleStudentEditOption(ActionEvent event) {
        Student selectedForEdit = tableView.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            AlertMaker.showErrorMessage("Студент не выбран", "Пожалуйста, выберите студента.");
            return;
        }

    }
    
    @FXML
    void handleStudentAddOption(ActionEvent event) {
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
        groupName = GroupListController.Group.getNameGroup();
    }

    @FXML
    void serch(ActionEvent event) {
        sortStudents.clear();
        int amount = 1;
        for (com.mycompany.javafx.electronicrecord.model.Student student : StudentDB.getInstance().getStudentsByNameAndGroup(txt_serch.getText(), groupName)) {
            
            Integer numberStudent = amount;
            String fullName = student.getUser().getSurname() + " " + student.getUser().getName() + " " + student.getUser().getMidleName();
            Integer id = student.getStudentid();
            String login = student.getUser().getLogin();
            String password = student.getUser().getPassword();
            Integer numberRecord = student.getNumberBook();
            amount++;
            sortStudents.add(new Student(numberStudent, fullName, id,login,password,numberRecord));
        }
        tableView.setItems(sortStudents);
        
    
    }

    public static class Student {

        private SimpleIntegerProperty numberStudent;
        private SimpleStringProperty fullName;
        private SimpleIntegerProperty numberRecord;
        private SimpleIntegerProperty id;
        private SimpleIntegerProperty course;
        private SimpleStringProperty login;
        private SimpleStringProperty password;

        public Student(Integer numberStudent, String fullName, Integer id,String login,String password,Integer numberRecord) {
            this.fullName = new SimpleStringProperty(fullName);
            this.numberStudent = new SimpleIntegerProperty(numberStudent);
            this.id = new SimpleIntegerProperty(id);  
            this.numberRecord = new SimpleIntegerProperty(numberRecord);
            this.login = new SimpleStringProperty(login);
            this.password = new SimpleStringProperty(password);
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

        /**
         * @return the login
         */
        public String getLogin() {
            return login.get();
        }

        /**
         * @return the password
         */
        public String getPassword() {
            return password.get();
        }

    }

}
