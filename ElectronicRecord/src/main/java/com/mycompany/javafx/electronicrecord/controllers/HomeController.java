    package com.mycompany.javafx.electronicrecord.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnStudents;

    @FXML
    private FontAwesomeIconView test;

    @FXML
    private Button btn_Timetable;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnClasses;

    @FXML
    private FontAwesomeIconView Idente;

    @FXML
    private Button btn_Timetable1;
    
    @FXML
    private FontAwesomeIconView icon_test;
    
    

    @FXML
    void logout(MouseEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(parent));
    }

  

}
