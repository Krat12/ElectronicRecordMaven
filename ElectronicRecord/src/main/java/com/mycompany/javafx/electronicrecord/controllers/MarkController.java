package com.mycompany.javafx.electronicrecord.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class MarkController implements Initializable{
    private static final  String[] STRING_MARKS = {"Зачет","Незачет"};
    
     ObservableList<String> markList = FXCollections.observableArrayList(STRING_MARKS);
     
     private static String mark;
     
    
    @FXML
    private ComboBox<String> cmb_Mark;

    @FXML
    void handlePutMark(ActionEvent event) {
        mark = cmb_Mark.getSelectionModel().getSelectedItem();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmb_Mark.setItems(markList);
    }

    public static String getMark() {
        return mark;
    }
}
