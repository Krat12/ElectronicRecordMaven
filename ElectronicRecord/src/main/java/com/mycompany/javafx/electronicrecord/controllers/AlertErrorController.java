package com.mycompany.javafx.electronicrecord.controllers;

import com.mycompany.javafx.electronicrecord.utill.ElectronicRecordUtill;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


public class AlertErrorController implements Initializable{
    
    @FXML
    private Label lbl_title;

    @FXML
    private Label lbl_content;

    @FXML
    void handleClose(ActionEvent event) {
        ElectronicRecordUtill.closeStage(event);
    }
     public  void setField(String title, String content ){
       lbl_title.setText(title);
       lbl_content.setText(content);
    }
    
    public  void resetField(){
        lbl_title.setText("");
        lbl_content.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       resetField();
    }

}
