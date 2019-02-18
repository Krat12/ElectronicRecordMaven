package com.mycompany.javafx.electronicrecord.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class StatementListController implements Initializable{

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableView<?> tableView;

    @FXML
    private TableColumn<?, ?> numberSubject;

    @FXML
    private TableColumn<?, ?> colSubject;

    @FXML
    private TableColumn<?, ?> colHours11;

    @FXML
    private TableColumn<?, ?> colTeacher;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> colHours;

    @FXML
    private TableColumn<?, ?> colHours1;

    @FXML
    private TableColumn<?, ?> colHours12;

    @FXML
    private MenuItem con_edit;

    @FXML
    private MenuItem con_deleteSubject;

    @FXML
    private MenuItem con_addSubject;

    @FXML
    private TextField txt_serch;

    @FXML
    private JFXButton btn_apply;

    @FXML
    private JFXButton btn_addSubject;

    @FXML
    private JFXButton btn_edit;

    @FXML
    private ComboBox<?> groupComboBox;

    @FXML
    private ComboBox<?> groupComboBox1;

    @FXML
    private JFXDatePicker testPicker;

    @FXML
    void SelectGroup(MouseEvent event) {

    }

    @FXML
    void closeStage(ActionEvent event) {

    }

    @FXML
    void exportAsPDF(ActionEvent event) {

    }

    @FXML
    void handleCopySubjectGroup(ActionEvent event) {

    }

    @FXML
    void handleRefresh(ActionEvent event) {

    }

    @FXML
    void handleSubjectAdd(ActionEvent event) {

    }

    @FXML
    void handleSubjectDeleteOption(ActionEvent event) {

    }

    @FXML
    void handleSubjectEditOption(ActionEvent event) {

    }

    @FXML
    void serch(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }

}

