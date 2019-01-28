package com.mycompany.javafx.electronicrecord.controllers;

import com.mycompany.javafx.electronicrecord.dao.impl.UserDB;
import com.mycompany.javafx.electronicrecord.model.Student;
import com.mycompany.javafx.electronicrecord.model.User;
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
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
    void input(ActionEvent event) throws IOException {
        if (checkUser() != null) {
            if (CheckStudent() != null) {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
                stage.getIcons().add(new Image("/image/round-button-blue-glossy-download-png-93250.png"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setMinHeight(310);
                stage.setMinWidth(275);
                stage.show();
            }
            System.out.println("No");
        }
    }

    private User checkUser() {
        user = UserDB.getInstance().getUserByLoginAndPassword(txt_login.getText(), txt_password.getText());
        if (user == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!");

            alert.showAndWait();
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
