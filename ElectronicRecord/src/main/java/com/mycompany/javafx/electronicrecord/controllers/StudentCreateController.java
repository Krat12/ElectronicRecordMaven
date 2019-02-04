/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javafx.electronicrecord.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
