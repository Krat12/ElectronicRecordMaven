/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javafx.electronicrecord.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.javafx.electronicrecord.dao.impl.GroupDB;
import com.mycompany.javafx.electronicrecord.dao.impl.StudentDB;
import com.mycompany.javafx.electronicrecord.dao.impl.UserDB;
import com.mycompany.javafx.electronicrecord.model.Groupstud;
import com.mycompany.javafx.electronicrecord.model.Student;
import com.mycompany.javafx.electronicrecord.model.User;
import com.mycompany.javafx.electronicrecord.utill.ElectronicRecordUtill;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author user07
 */
public class StudentCreateController implements Initializable {

    private static int studentId;
    private static final int EMPTY = -1;
    private ObservableList<String> groupList = FXCollections.observableArrayList();
    private Boolean isInEditMode = Boolean.FALSE;
    private Boolean isEmpty = Boolean.FALSE;
    private static StudentsListController.StudentModelTable modelTable;
    private final String numberMatcher = "^-?\\d+$";

    public static StudentsListController.StudentModelTable getModelTableStudent() {
        return modelTable;
    }

    @FXML
    private ComboBox<String> groupComboBox;

    @FXML
    private JFXTextField txt_surname;

    @FXML
    private JFXTextField txt_firstName;

    @FXML
    private JFXTextField txt_midleName;

    @FXML
    private JFXTextField txt_numberRecord;

    @FXML
    private JFXTextField txt_login;

    @FXML
    private JFXPasswordField txt_password;

    @FXML
    private FontAwesomeIconView lbl_surname;

    @FXML
    private FontAwesomeIconView lbl_name;

    @FXML
    private FontAwesomeIconView lbl_midleName;

    @FXML
    private FontAwesomeIconView lbl_NumberRecord;

    @FXML
    private FontAwesomeIconView lbl_login;

    @FXML
    private FontAwesomeIconView lbl_password;

    @FXML
    private Text txt_error;

    @FXML
    void cancel(ActionEvent event) {
        stageClose(event);
    }

    @FXML
    void handleAutoGenerationLogin(ActionEvent event) {
        txt_login.setText(generateLogin());
    }
    
    private String generateLogin() {
        return new Random().ints(5, 65, 90).mapToObj(i -> String.valueOf((char) i)).collect(Collectors.joining());
    }


    @FXML
    void save(ActionEvent event) {
       
        if (!CheckIsEmptyField()) {
            if (checkModelStudentIsNull() != EMPTY) {
                loadDataUser();
            } else {
                modelTable.setFirstName(txt_firstName.getText());
                modelTable.setSecondName(txt_surname.getText());
                modelTable.setMidleName(txt_midleName.getText());
                modelTable.setNumberRecord(Integer.valueOf(txt_numberRecord.getText()));
                modelTable.setLogin(txt_login.getText());
                modelTable.setPassword(txt_password.getText());
            }
            stageClose(event);
            ElectronicRecordUtill.loadAlertConfrim(getClass().getResource("/fxml/AlertConfrim.fxml"), null, "Успех", "Данные успешно обновились");
        }

    }

    private void loadDataUser() {
        User user = new User();
        user.setName(txt_firstName.getText());
        user.setSurname(txt_surname.getText());
        user.setMidleName(txt_midleName.getText());
        user.setLogin(txt_login.getText());
        user.setPassword(txt_password.getText());
        if (isInEditMode) {
            user.setUserId(studentId);
            UserDB.getInstance().update(user);
        } else {
            UserDB.getInstance().insert(user);
            
        }

        loadDataStudent(user);
    }


    private void loadDataStudent(User user) {
        Student student = new Student(user.getUserId());
        student.setNumberBook(Integer.valueOf(txt_numberRecord.getText()));
        student.setUser(user);
        if (isInEditMode) {
            Groupstud groupstud = GroupDB.getInstance().getGroupstudsByName(groupComboBox.getSelectionModel().getSelectedItem());
            student.setGroupid(groupstud);
            StudentDB.getInstance().update(student);
        } else {
            Groupstud groupstud = GroupDB.getInstance().getGroupstudsByName(StudentsListController.groupName);
            student.setGroupid(groupstud);
            StudentDB.getInstance().insert(student);
            
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadGroupList();
        textFieldIsDigit();
    }

    protected void mappingUI(StudentsListController.StudentModelTable student) {
        txt_firstName.setText(student.getFirstName());
        txt_surname.setText(student.getSecondName());
        txt_midleName.setText(student.getMidleName());
        txt_login.setText(student.getLogin());
        txt_password.setText(student.getPassword());
        txt_numberRecord.setText(String.valueOf(student.getNumberRecord()));
        studentId = student.getId();
        isInEditMode = Boolean.TRUE;

        if (student.getId() != EMPTY) {
            groupComboBox.setVisible(true);
        }
        groupComboBox.getSelectionModel().select(StudentsListController.groupName);
        StudentCreateController.modelTable = student;
    }

    private void loadGroupList() {
        groupList.clear();

        for (Groupstud groupstud : GroupDB.getInstance().getAllGroups()) {
            groupList.add(groupstud.getGroupname());
        }
        groupComboBox.setItems(groupList);
    }

    private void stageClose(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    private int checkModelStudentIsNull() {
        if (modelTable == null) {
            return 0;
        } else {
            return modelTable.getId();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="CheckIsEmptyField">
    private boolean CheckIsEmptyField() {
        if (txt_surname.getText().trim().isEmpty()) {
            txt_error.setVisible(true);
            lbl_surname.setVisible(true);
            txt_surname.setFocusColor(Color.web("#FF515A"));
            txt_surname.setUnFocusColor(Color.web("#FF515A"));
            txt_surname.setStyle("-fx-text-fill:#FF515A");
            isEmpty = Boolean.TRUE;
        }
        if (txt_firstName.getText().trim().isEmpty()) {
            txt_error.setVisible(true);
            lbl_name.setVisible(true);
            txt_firstName.setFocusColor(Color.web("#FF515A"));
            txt_firstName.setUnFocusColor(Color.web("#FF515A"));
            txt_firstName.setStyle("-fx-text-fill:#FF515A");
            isEmpty = Boolean.TRUE;
        }
        if (txt_midleName.getText().trim().isEmpty()) {
            txt_error.setVisible(true);
            lbl_midleName.setVisible(true);
            txt_midleName.setFocusColor(Color.web("#FF515A"));
            txt_midleName.setUnFocusColor(Color.web("#FF515A"));
            txt_midleName.setStyle("-fx-text-fill:#FF515A");
            isEmpty = Boolean.TRUE;
        }
        if (txt_numberRecord.getText().trim().isEmpty()) {
            txt_error.setVisible(true);
            lbl_NumberRecord.setVisible(true);
            txt_numberRecord.setFocusColor(Color.web("#FF515A"));
            txt_numberRecord.setUnFocusColor(Color.web("#FF515A"));
            txt_numberRecord.setStyle("-fx-text-fill:#FF515A");
            isEmpty = Boolean.TRUE;
        }
        if (txt_login.getText().trim().isEmpty()) {
            txt_error.setVisible(true);
            lbl_login.setVisible(true);
            txt_login.setFocusColor(Color.web("#FF515A"));
            txt_login.setUnFocusColor(Color.web("#FF515A"));
            txt_login.setStyle("-fx-text-fill:#FF515A");
            isEmpty = Boolean.TRUE;
        }
        if (txt_password.getText().trim().isEmpty()) {
            txt_error.setVisible(true);
            lbl_password.setVisible(true);
            txt_password.setFocusColor(Color.web("#FF515A"));
            txt_password.setUnFocusColor(Color.web("#FF515A"));
            txt_password.setStyle("-fx-text-fill:#FF515A");
            isEmpty = Boolean.TRUE;
        }
        
        if(isEmpty){
            return true;
        }else{
            return false;
        }
       
    }
//</editor-fold>
    
  

    private void textFieldIsDigit() {
        txt_numberRecord.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                if (!newValue.matches(numberMatcher)) {
                    txt_numberRecord.setText(oldValue);
                } else {
                    try {
                        int value = Integer.parseInt(newValue);
                        txt_numberRecord.setText(String.valueOf(value));
                    } catch (NumberFormatException e) {
                        txt_numberRecord.setText(oldValue);
                    }
                }
            }
        });
    }

}
