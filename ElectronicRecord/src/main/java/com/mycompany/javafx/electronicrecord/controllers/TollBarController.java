package com.mycompany.javafx.electronicrecord.controllers;

import com.mycompany.javafx.electronicrecord.utill.ElectronicRecordUtill;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;


public class TollBarController {
 

    @FXML
    void ShowListStudent(ActionEvent event) {

    }

    @FXML
    void closeGroup(ActionEvent event) {
        ElectronicRecordUtill.loadWindow(getClass().getResource("/fxml/Home.fxml"),"Home", null);
        Node node =  (Node) event.getSource();
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
