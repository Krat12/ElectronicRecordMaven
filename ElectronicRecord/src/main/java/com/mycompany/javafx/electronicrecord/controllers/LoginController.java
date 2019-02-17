package com.mycompany.javafx.electronicrecord.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.javafx.electronicrecord.dao.impl.UserDB;
import com.mycompany.javafx.electronicrecord.model.Admin;
import com.mycompany.javafx.electronicrecord.model.Student;
import com.mycompany.javafx.electronicrecord.model.Teacher;
import com.mycompany.javafx.electronicrecord.model.User;
import com.mycompany.javafx.electronicrecord.utill.ElectronicRecordUtill;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javafx.stage.Stage;

public class LoginController {

    private static String userType;

    private static int userId;

    private User user;

    @FXML
    private JFXTextField txt_login;

    @FXML
    private JFXPasswordField txt_password;

    @FXML
    private Text UserIsNull;

    @FXML
    void input(ActionEvent event) throws IOException {
        if (checkUser() != null) {
            if (checkStudent() != null) {
                Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setScene(new Scene(parent));
            }
            if (checkTeacher() != null) {
                Teacher teacher = user.getTeacher();
                setUserId(teacher.getTeacherid());
                setUserType("Teacher");             
                ElectronicRecordUtill.loadWindow(getClass().getResource("/fxml/GroupList.fxml"), "Список групп", null);
                ElectronicRecordUtill.closeStage(event);
            }

            if (checkAdmin() != null) {
                Admin admin = user.getAdmin();
                setUserId(admin.getAdminid());
                setUserType("Admin");
                ElectronicRecordUtill.loadWindow(getClass().getResource("/fxml/Home.fxml"), "Home", null, 480, 360);
                ElectronicRecordUtill.closeStage(event);
            }

        }
    }

    private User checkUser() {
        user = UserDB.getInstance().getUserByLoginAndPassword(txt_login.getText(), txt_password.getText());

        if (user == null) {
            userNotFind();
            return null;
        } else {
            return user;
        }

    }

    private void userNotFind() {
        UserIsNull.setVisible(true);
        txt_login.setFocusColor(Color.web("#FF515A"));
        txt_password.setFocusColor(Color.web("#FF515A"));
        txt_password.setUnFocusColor(Color.web("#FF515A"));
        txt_login.setUnFocusColor(Color.web("#FF515A"));
        txt_login.setStyle("-fx-text-fill:#FF515A");
        txt_password.setStyle("-fx-text-fill:#FF515A");
    }

    private Student checkStudent() {

        Student student = user.getStudent();

        if (student != null) {
            return student;
        }
        return null;
    }

    private Teacher checkTeacher() {

        Teacher teacher = user.getTeacher();

        if (teacher != null) {
            return teacher;
        }
        return null;
    }

    private Admin checkAdmin() {

        Admin admin = user.getAdmin();

        if (admin != null) {
            return admin;
        }
        return null;
    }

    public static String getUserType() {
        return userType;
    }

    public static void setUserType(String type) {
        userType = type;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int UserId) {
        userId = UserId;
    }

}
