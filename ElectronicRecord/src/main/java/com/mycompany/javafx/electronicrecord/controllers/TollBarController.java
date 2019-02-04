package com.mycompany.javafx.electronicrecord.controllers;

import com.mycompany.javafx.electronicrecord.utill.AlertMaker;
import com.mycompany.javafx.electronicrecord.utill.ElectronicRecordUtill;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TollBarController {

    @FXML
    void ShowListStudent(ActionEvent event) {


    }

    @FXML
    void closeGroup(ActionEvent event) {
        ElectronicRecordUtill.loadWindow(getClass().getResource("/fxml/Home.fxml"), "Home", null);
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

    }

    @FXML
    void loadListGroup(ActionEvent event) {

    }

    @FXML
    void loadAddGroup(ActionEvent event) {

    }

    @FXML
    void loadStudentList(ActionEvent event) {

    }

    @FXML
    void updateGroup(ActionEvent event) {

    }

}
