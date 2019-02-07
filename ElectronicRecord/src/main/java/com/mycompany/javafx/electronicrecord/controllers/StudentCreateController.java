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

/**
 *
 * @author user07
 */
public class StudentCreateController implements Initializable {

    private static int studentId;
    private static final int EMPTY = -1;
    ObservableList<String> groupList = FXCollections.observableArrayList();
    private Boolean isInEditMode = Boolean.FALSE;
    private static StudentsListController.StudentModelTable modelTable;

    public static StudentsListController.StudentModelTable getModelTableStudent() {
        return modelTable;
    }

    @FXML
    private ComboBox<String> groupComboBox;

    @FXML
    private JFXTextField txt_surname;

    @FXML
    private JFXTextField txt_firstNmae;

    @FXML
    private JFXTextField txt_midleName;

    @FXML
    private JFXTextField txt_numberRecord;

    @FXML
    private JFXTextField txt_login;

    @FXML
    private JFXPasswordField txt_password;

    @FXML
    void cancel(ActionEvent event) {
        stageClose(event);
    }

    @FXML
    void save(ActionEvent event) {

        if (checkModelStudentIsNull() != EMPTY) {
            loadDataUser();
        } else {
            modelTable.setFirstName(txt_firstNmae.getText());
            modelTable.setSecondName(txt_surname.getText());
            modelTable.setMidleName(txt_midleName.getText());
            modelTable.setNumberRecord(Integer.valueOf(txt_numberRecord.getText()));
            modelTable.setLogin(txt_login.getText());
            modelTable.setPassword(txt_password.getText());
        }
        stageClose(event);
        ElectronicRecordUtill.loadAlertConfrim(getClass().getResource("/fxml/AlertConfrim.fxml"), null, "Успех", "Данные успешно обновились");

    }

    private void loadDataUser() {
        User user = new User();
        user.setName(txt_firstNmae.getText());
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
    }

    protected void mappingUI(StudentsListController.StudentModelTable student) {
        txt_firstNmae.setText(student.getFirstName());
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

}
