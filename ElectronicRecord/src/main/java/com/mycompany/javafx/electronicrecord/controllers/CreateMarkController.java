package com.mycompany.javafx.electronicrecord.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.mycompany.javafx.electronicrecord.dao.impl.ReatingDB;
import com.mycompany.javafx.electronicrecord.dao.impl.StatementDB;
import com.mycompany.javafx.electronicrecord.dao.impl.SubjectDB;
import com.mycompany.javafx.electronicrecord.model.Statement;
import com.mycompany.javafx.electronicrecord.model.Subject;
import com.mycompany.javafx.electronicrecord.utill.ElectronicRecordUtill;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateMarkController implements Initializable {

    private static final String[] TYPE_MARK = {"Зачет", "Дифференцированный зачет", "Дипломная работа", "Курсовая работа"};
    ObservableList<String> typeList = FXCollections.observableArrayList(TYPE_MARK);
    ObservableList<Subject> subjects = FXCollections.observableArrayList(SubjectDB.getInstance().
            getSubjectsByGroupAndTeacher(6, 33));
    private static String selectTypeMark;
    private static int statementId;

    private final String numberMatcher = "^-?\\d+$";

    @FXML
    private ComboBox<Subject> cmb_subject;

    @FXML
    private ComboBox<String> cmb_typeMark;

    @FXML
    private TextField txt_hours;

    @FXML
    private JFXDatePicker creationDate;

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void save(ActionEvent event) {
        if (checkData()) {
            try {
                ReatingDB.getInstance().insertBySelect(getStatement().getStatementId(), 6);
                selectTypeMark = cmb_typeMark.getSelectionModel().getSelectedItem();
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                ElectronicRecordUtill.loadWindow(getClass().getResource("/fxml/MarkList.fxml"), "", stage);

            } catch (Exception e) {
                System.out.println(e);
                ElectronicRecordUtill.loadAlertError(getClass().getResource("/fxml/AlertError.fxml"), new Stage(), "Ooops...", "Что то пошло не так ");
            }
        }else{
            ElectronicRecordUtill.loadAlertError(getClass().getResource("/fxml/AlertError.fxml"), null, "Ошибка при создании", "Проверьте поля на правильность ввода");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        creationDate.setValue(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        cmb_typeMark.setItems(typeList);
        cmb_subject.setItems(subjects);
        textFieldIsDigit();

    }

    private void textFieldIsDigit() {
        txt_hours.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                if (!newValue.matches(numberMatcher)) {
                    txt_hours.setText(oldValue);
                } else {
                    try {
                        int value = Integer.parseInt(newValue);
                        txt_hours.setText(String.valueOf(value));
                    } catch (NumberFormatException e) {
                        txt_hours.setText(oldValue);
                    }
                }
            }
        });
    }

    private Statement getStatement() {
        Statement statement = new Statement();
        Date date = Date.from(creationDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        statement.setDate(date);
        statement.setHours(Integer.valueOf(txt_hours.getText()));
        statement.setType(cmb_typeMark.getSelectionModel().getSelectedItem());
        StatementDB.getInstance().insertById(cmb_subject.getSelectionModel().getSelectedItem().getSubjectId(), 33, 6, statement);
        statementId = statement.getStatementId();
        return statement;
    }

    public static String getSelectTypeMark() {
        return selectTypeMark;
    }

    public static int getStatementId() {
        return statementId;
    }

    private boolean checkData() {
        if (txt_hours.getText().equals("")) {
            return false;
        }
        if (cmb_subject.getSelectionModel().getSelectedItem() == null) {
            return false;
        }

        if (cmb_typeMark.getSelectionModel().getSelectedItem().equals("Тип оценки")) {
            return false;
        }
        if (creationDate.getValue() == null) {
            return false;
        }

        return true;
    }

}
