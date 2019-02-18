package com.mycompany.javafx.electronicrecord.controllers;

import com.mycompany.javafx.electronicrecord.utill.ElectronicRecordUtill;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HomeController {

//    @FXML
//    private Button btnDashboard;
//
//    @FXML
//    private Button btnStudents;
//
//    @FXML
//    private FontAwesomeIconView test;
//
//    @FXML
//    private Button btn_Timetable;
//
//    @FXML
//    private Button btnUpdate;
//
//    @FXML
//    private Button btnClasses;
//
//    @FXML
//    private FontAwesomeIconView Idente;
//
//    @FXML
//    private Button btn_Timetable1;
//    
//    @FXML
//    private FontAwesomeIconView icon_test;
    

    
    
    @FXML
    void logout(MouseEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(parent));
    }

    @FXML
    void handleGroupList(ActionEvent event) {
        ElectronicRecordUtill.loadWindow(getClass().getResource("/fxml/GroupList.fxml"), "Список групп", null);
        ElectronicRecordUtill.closeStage(event);
    }

    @FXML
    void handleOpenStatement(ActionEvent event) {
        ElectronicRecordUtill.loadWindow(getClass().getResource("/fxml/StatementList.fxml"), "Список ведомостей", null);
        ElectronicRecordUtill.closeStage(event);

    }

}
