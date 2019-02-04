package com.mycompany.javafx.electronicrecord.controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.mycompany.javafx.electronicrecord.dao.impl.GroupDB;
import com.mycompany.javafx.electronicrecord.dao.impl.SpecialityDB;
import com.mycompany.javafx.electronicrecord.model.Groupstud;
import com.mycompany.javafx.electronicrecord.model.Speciality;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GroupCreateController implements Initializable {

    ObservableList<String> spesialtityList = FXCollections.observableArrayList();
    private int groupId;
    private Boolean isInEditMode = Boolean.FALSE;
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
        if (CheckSpeciality.isSelected()) {
            txt_speciality.setVisible(true);
            specialityComboBox.setVisible(false);
        } else {
            txt_speciality.setVisible(false);
            specialityComboBox.setVisible(true);
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(ActionEvent event) {
        if (isInEditMode) {
            handleEditOperation();
            closeStage(event);
        } else {
            handleAddOperation();
            closeStage(event);
        }

    }

    private boolean isEmpty() {
        if (txt_nameGroup.getText().equals("")) {
            return false;
        }
        if (txt_year.getText().equals("")) {
            return false;
        }
        return true;
    }

    private void load() {
        spesialtityList.clear();

        for (Speciality speciality : SpecialityDB.getInstance().getAllSpecialitys()) {
            spesialtityList.add(speciality.getNameSpeciality());
        }
        specialityComboBox.setItems(spesialtityList);

    }

    private Groupstud dataGroup(Speciality speciality) {
        Groupstud groupstud = new Groupstud();
        groupstud.setGroupname(txt_nameGroup.getText());
        groupstud.setSetYear(Short.parseShort(txt_year.getText()));
        groupstud.setSpecialityId(speciality);
        return groupstud;
    }

    private Groupstud dataGroupUpdate(Speciality speciality, int id) {
        Groupstud groupstud = new Groupstud(id);
        groupstud.setGroupname(txt_nameGroup.getText());
        groupstud.setSetYear(Short.parseShort(txt_year.getText()));
        groupstud.setSpecialityId(speciality);
        return groupstud;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        load();
    }

    public void inflateUI(GroupListController.Group group) {
        txt_nameGroup.setText(group.getGroupName());
        txt_year.setText(String.valueOf(group.getYear()));
        specialityComboBox.getSelectionModel().select(group.getSpecName());
        groupId = group.getId();
        isInEditMode = Boolean.TRUE;
    }

    private void handleEditOperation() {
        if (isEmpty()) {
            if (CheckSpeciality.isSelected()) {
                if (!txt_speciality.getText().equals("")) {
                    Speciality speciality = new Speciality();
                    speciality.setNameSpeciality(txt_speciality.getText());
                    SpecialityDB.getInstance().insert(speciality);
                    GroupDB.getInstance().update(dataGroup(speciality));
                } else {
                    AlertMaker.showErrorMessage("Ошибка при изменении", "Проверьте все поля");
                }
            } else {
                Speciality speciality = SpecialityDB.getInstance().getSpecialityByName(specialityComboBox.getSelectionModel().getSelectedItem());
                GroupDB.getInstance().update(dataGroupUpdate(speciality, groupId));
            }

        } else {
            AlertMaker.showErrorMessage("Ошибка при изменении", "Проверьте все поля");
        }
    }

    
    
    
    private void handleAddOperation() {
        if (isEmpty()) {
            if (CheckSpeciality.isSelected()) {
                checkValidateAddTextSpeciality();
            } else {
                checkValidateAddSpecialityComboBox();
            }
        } else {
            AlertMaker.showErrorMessage("Ошибка при добовлении", "Проверьте все поля");
        }
    }

    private void checkValidateAddSpecialityComboBox() {
        if (specialityComboBox.getValue() != null) {
            Speciality speciality = SpecialityDB.getInstance().getSpecialityByName(specialityComboBox.getSelectionModel().getSelectedItem());
            GroupDB.getInstance().insert(dataGroup(speciality));
        } else {
            AlertMaker.showErrorMessage("Ошибка при добовлении", "Проверьте все поля");
        }
    }

    private void checkValidateAddTextSpeciality() {
        if (!txt_speciality.getText().equals("")) {
            Speciality speciality = new Speciality();
            speciality.setNameSpeciality(txt_speciality.getText());
            SpecialityDB.getInstance().insert(speciality);
            GroupDB.getInstance().insert(dataGroup(speciality));
        } else {
            AlertMaker.showErrorMessage("Ошибка при добовлении", "Проверьте все поля");
        }
    }

    private void closeStage(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

}
