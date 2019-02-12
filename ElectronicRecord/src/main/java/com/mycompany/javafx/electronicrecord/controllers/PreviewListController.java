package com.mycompany.javafx.electronicrecord.controllers;

import com.mycompany.javafx.electronicrecord.dao.impl.SubjectTeacherGroupDB;

import com.mycompany.javafx.electronicrecord.model.SubjectTeacherGroup;
import com.mycompany.javafx.electronicrecord.utill.AlertMaker;
import com.mycompany.javafx.electronicrecord.utill.ElectronicRecordUtill;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class PreviewListController implements Initializable {

    ObservableList<SubjectListController.SubjectTeacherModel> listPreview = FXCollections.observableArrayList();

    @FXML
    private StackPane rootPanePriview;

    @FXML
    private AnchorPane panePreview;

    @FXML
    private TableView<SubjectListController.SubjectTeacherModel> tableViewPreview;

    @FXML
    private TableColumn<SubjectListController.SubjectTeacherModel, String> colSubjectPreview;

    @FXML
    private TableColumn<SubjectListController.SubjectTeacherModel, String> colTeacherPreview;

    @FXML
    private TableColumn<SubjectListController.SubjectTeacherModel, Integer> colHoursPreview;

    @FXML
    void handleSaveDB(ActionEvent event) {
        
        try {
            if(listPreview.isEmpty()){
                  AlertMaker.showMaterialDialog(rootPanePriview, panePreview, null, "Нет данных для добавления", "Пожалуйста, выберите какие поля вы хотите добавить");
                  return;
            }
            for (SubjectListController.SubjectTeacherModel subjectTeacherModel : listPreview) {
                SubjectTeacherGroup stg = new SubjectTeacherGroup();
                SubjectTeacherGroupDB.getInstance().insetSubjectTeacherGroup(subjectTeacherModel.getSubjectId(), subjectTeacherModel.getTeacherId(),
                        SubjectListController.getGroupId(), subjectTeacherModel.getHours(), stg);

            }
            ElectronicRecordUtill.loadAlertConfrim(getClass().getResource("/fxml/AlertConfrim.fxml"), null, "Успех!", "Было добавлено " + listPreview.size() + " запесей");
            listPreview.clear();
            CreateSubjectController.listInsertPreview.clear();
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            ElectronicRecordUtill.loadAlertError(getClass().getResource("/fxml/AlertError.fxml"), null, "Ooops...", "Что то пошло не так...");
        }

    }

    @FXML
    void handleSubjectDeleteOption(ActionEvent event) {
        if (tableViewPreview.getSelectionModel().getSelectedItem() != null) {
            int index = tableViewPreview.getSelectionModel().getSelectedIndex();
            listPreview.remove(index);
        }else{
             AlertMaker.showMaterialDialog(rootPanePriview, panePreview, null, "Предмет не выбран", "Пожалуйста, выберите предмет");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadData();
        settingsTable();
    }

    private void initCol() {
        colHoursPreview.setCellValueFactory(new PropertyValueFactory<>("hours"));
        colTeacherPreview.setCellValueFactory(new PropertyValueFactory<>("fullNameTeacher"));
        colSubjectPreview.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
    }

    protected void loadData() {
        listPreview.clear();
        List<SubjectListController.SubjectTeacherModel> listDataModels = CreateSubjectController.getPreviewCollection();
        listPreview.addAll(listDataModels);
        tableViewPreview.setItems(listPreview);
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        loadData();
    }

    private void settingsTable() {
        tableViewPreview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableViewPreview.setEditable(true);

        colHoursPreview.setCellFactory(integerCell(value -> value >= 0));
        colHoursPreview.setOnEditCommit((event) -> {
            Integer value = event.getNewValue();
            if (value != null) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setHours(value);
            }
        });
    }
        protected <T> Callback<TableColumn<T, Integer>, TableCell<T, Integer>> integerCell(
            Predicate<Integer> validator) {
        return TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                if (object == null) {
                    return null;
                }
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                try {
                    int value = Integer.parseInt(string);
                    if (validator.test(value)) {
                        return value;
                    } else {
                        AlertMaker.showMaterialDialog(rootPanePriview, panePreview, null, "Неверный формат!", "Количество часов не может быть меньше нуля!");
                        return null;
                    }
                } catch (NumberFormatException e) {
                    AlertMaker.showMaterialDialog(rootPanePriview, panePreview, null, "Неверный формат!", "Введите целое положительное число без разделителей");
                    return null;
                }
            }
        });
    }

}
