package com.mycompany.javafx.electronicrecord.controllers;

import static com.mycompany.javafx.electronicrecord.controllers.CreateMarkController.getSelectTypeMark;
import com.mycompany.javafx.electronicrecord.utill.AlertMaker;
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
     
     private static final String[] NUMBER_MARKS = {"5","4","3","2","1","н/a"};
    
     ObservableList<String> markList = FXCollections.observableArrayList();
     
     private static String mark;
     
    
    @FXML
    private ComboBox<String> cmb_Mark;

    @FXML
    void handlePutMark(ActionEvent event) {
     if(!cmb_Mark.getSelectionModel().getSelectedItem().equals("Оценка")){
        mark = cmb_Mark.getSelectionModel().getSelectedItem();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        }else{
            AlertMaker.showErrorMessage("Оценка не выбрана", "При выстовлении оценки выберите оценку");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(getSelectTypeMark().equals("Зачет")){
            markList.addAll(STRING_MARKS);
            cmb_Mark.setItems(markList);
        }
        if(getSelectTypeMark().equals("Дифференцированный зачет")){
            markList.addAll(NUMBER_MARKS); 
            cmb_Mark.setItems(markList);
        }
       
    }

    public static String getMark() {
        return mark;
    }
}
