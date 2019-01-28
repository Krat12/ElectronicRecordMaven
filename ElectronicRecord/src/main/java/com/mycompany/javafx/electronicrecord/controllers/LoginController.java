package com.mycompany.javafx.electronicrecord.controllers;


import com.mycompany.javafx.electronicrecord.dao.impl.UserDB;
import com.mycompany.javafx.electronicrecord.model.Student;

import com.mycompany.javafx.electronicrecord.model.User;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    private User user;

    @FXML
    private PasswordField txt_password;

    @FXML
    private Button btn_login;

    @FXML
    private TextField txt_login;
    
    @FXML
    private Label errorUser;

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
            errorUser.setVisible(true);
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
