/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javafx.electronicrecord.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.javafx.electronicrecord.dao.impl.GroupDB;
import com.mycompany.javafx.electronicrecord.dao.impl.StudentDB;
import com.mycompany.javafx.electronicrecord.dao.impl.UserDB;
import com.mycompany.javafx.electronicrecord.model.Groupstud;
import com.mycompany.javafx.electronicrecord.model.Student;
import com.mycompany.javafx.electronicrecord.model.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 *
 * @author user07
 */
public class StudentCreateController implements Initializable {

    @FXML
    private JFXTextField txt_surname;

    @FXML
    private JFXTextField txt_firstNmae;

    @FXML
    private JFXTextField txt_midleName;

    @FXML
    private JFXTextField txt_numberRecord;
    
    @FXML
    private JFXTextField txt_login;

    @FXML
    private JFXPasswordField txt_password;

    @FXML
    void cancel(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setResizable(false);
        stage.close();
    }

    @FXML
    void save(ActionEvent event) {
       loadDataUser();
    }
    
    
    private void loadDataUser(){
        User user = new User();
        user.setName(txt_firstNmae.getText());
        user.setSurname(txt_surname.getText());
        user.setMidleName(txt_midleName.getText());
        user.setLogin(txt_login.getText());
        user.setPassword(txt_password.getText());
        UserDB.getInstance().insert(user);
        loadDataStudent(user);
    }
    
    private void loadDataStudent(User user){
        Student student = new Student(user.getUserId());
        student.setNumberBook(Integer.valueOf(txt_numberRecord.getText()));
        Groupstud groupstud = GroupDB.getInstance().getGroupstudsByName(StudentsListController.groupName);
        student.setGroupid(groupstud);
        student.setUser(user);
        StudentDB.getInstance().insert(student);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
