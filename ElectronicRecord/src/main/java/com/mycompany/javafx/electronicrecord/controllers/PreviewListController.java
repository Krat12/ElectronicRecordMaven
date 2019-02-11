package com.mycompany.javafx.electronicrecord.controllers;

import com.mycompany.javafx.electronicrecord.dao.impl.SubjectTeacherGroupDB;
import com.mycompany.javafx.electronicrecord.model.Groupstud;
import com.mycompany.javafx.electronicrecord.model.Subject;
import com.mycompany.javafx.electronicrecord.model.SubjectTeacherGroup;
import com.mycompany.javafx.electronicrecord.model.Teacher;
import com.mycompany.javafx.electronicrecord.utill.AlertMaker;
import com.mycompany.javafx.electronicrecord.utill.ElectronicRecordUtill;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
                Subject subject = new Subject(subjectTeacherModel.getSubjectId());
                Teacher teacher = new Teacher(subjectTeacherModel.getTeacherId());
                Groupstud groupstud = new Groupstud(SubjectListController.getGroupId());

                stg.setSubjectId(subject);
                stg.setTeacher(teacher);
                stg.setGroupstud(groupstud);
                stg.setHours(subjectTeacherModel.getHours());

                SubjectTeacherGroupDB.getInstance().insert(stg);
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
        SubjectListController controller = new SubjectListController();

        colHoursPreview.setCellFactory(controller.integerCell(value -> value >= 0));
        colHoursPreview.setOnEditCommit((event) -> {
            Integer value = event.getNewValue();
            if (value != null) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setHours(value);
            }
        });
    }

}
