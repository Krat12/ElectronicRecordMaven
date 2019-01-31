package com.mycompany.javafx.electronicrecord.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.javafx.electronicrecord.dao.impl.UserDB;
import com.mycompany.javafx.electronicrecord.model.Student;

import com.mycompany.javafx.electronicrecord.model.User;
import com.mycompany.javafx.electronicrecord.utill.AlertMaker;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;


import javafx.stage.Stage;

public class LoginController {
   

    private User user;

    @FXML
    private JFXTextField txt_login;

    @FXML
    private JFXPasswordField txt_password;

    @FXML
    void input(ActionEvent event) throws IOException {
        if (checkUser() != null) {
            if (CheckStudent() != null) {
                Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setScene(new Scene(parent));
            }

        }
    }

    private User checkUser() {
        user = UserDB.getInstance().getUserByLoginAndPassword(txt_login.getText(), txt_password.getText());

        if (user == null) {
               AlertMaker.showErrorMessage(new RuntimeException("Test"));
        } else {
            return user;
        }
        return null;
    }

    private Student CheckStudent() {
        Student student = user.getStudent();
        if (student != null) {
            return student;
        }
        return null;
    }



}
