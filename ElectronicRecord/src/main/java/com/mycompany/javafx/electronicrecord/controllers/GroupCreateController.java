package com.mycompany.javafx.electronicrecord.controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


public class GroupCreateController {
    
    @FXML
    private JFXTextField txt_nameGroup;

    @FXML
    private ComboBox<String> specialityComboBox;

    @FXML
    private JFXCheckBox CheckSpeciality;

    @FXML
    private JFXTextField txt_year;
    
    @FXML
    private TextField txt_speciality;

    @FXML
    void CheckComboBox(ActionEvent event) {
        if(CheckSpeciality.isSelected()){
            txt_speciality.setVisible(true);
            specialityComboBox.setVisible(false);
        }else{
            txt_speciality.setVisible(false);
            specialityComboBox.setVisible(true);
        }
    }

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void save(ActionEvent event) {

    }

}
