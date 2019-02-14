package com.mycompany.javafx.electronicrecord.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import com.mycompany.javafx.electronicrecord.dao.impl.GroupDB;
import com.mycompany.javafx.electronicrecord.model.Groupstud;
import com.mycompany.javafx.electronicrecord.utill.AlertMaker;
import com.mycompany.javafx.electronicrecord.utill.ElectronicRecordUtill;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GroupListController implements Initializable {

    ObservableList<Group> groups = FXCollections.observableArrayList();
    ObservableList<Group> sortGroups = FXCollections.observableArrayList();

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableView<Group> tableView;

    @FXML
    private TableColumn<Group, String> groupCol;

    @FXML
    private TableColumn<Group, Integer> yearCol;

    @FXML
    private TableColumn<Group, Integer> id;

    @FXML
    private TextField txt_serch;


    @FXML
    void closeStage(ActionEvent event) {

    }

    @FXML
    void exportAsPDF(ActionEvent event) {

    }

    @FXML
    void handleGroupDeleteOption(ActionEvent event) {
        Group selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            AlertMaker.showMaterialDialog(rootPane, contentPane, null, "Группа не выбрана!", "Пожалуйста, выберите группу");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удаление группы");
        alert.setContentText("Вы уверены что хотите удалить группу " + selectedForDeletion.getGroupName() + " ?");

        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            System.out.println(selectedForDeletion.getId());
            Boolean result = GroupDB.getInstance().deleteGroup(selectedForDeletion.getId());
            if (result) {
                AlertMaker.showSimpleAlert("Группа удалена", selectedForDeletion.getGroupName() + " была успешно удалена.");
                groups.remove(selectedForDeletion);
            } else {
                AlertMaker.showSimpleAlert("Failed", selectedForDeletion.getGroupName() + " could not be deleted");
            }
        }
    }

    @FXML
    void handleGroupEditOption(ActionEvent event) {
        Group selectedForEdit = tableView.getSelectionModel().getSelectedItem();

        if (selectedForEdit == null) {
            AlertMaker.showMaterialDialog(rootPane, contentPane, null, "Группа не выбрана!", "Пожалуйста, выберите группу");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddGroup.fxml"));
            Parent parent = loader.load();
            GroupCreateController controller = loader.getController();
            controller.inflateUI(selectedForEdit);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Изменение группы");
            stage.setScene(new Scene(parent));
            stage.show();
            ElectronicRecordUtill.setStageIcon(stage);
            stage.setOnHiding((e) -> {
                handleRefresh(new ActionEvent());
            });

        } catch (IOException ex) {
            Logger.getLogger(GroupListController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void handleRefresh(ActionEvent event) {
        loadData();
    }

    @FXML
    void handleGroupAdd(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/AddGroup.fxml"));
            Stage stage = new Stage();
            ElectronicRecordUtill.setStageIcon(stage);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GroupListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void serch(ActionEvent event) {
        sortGroups.clear();

        for (Groupstud groupstud : GroupDB.getInstance().getSortGroupstudsByNameGroup(txt_serch.getText())) {
            String groupName = groupstud.getGroupname();
            Short year = groupstud.getSetYear();
            Integer id = groupstud.getGroupid();
            String SpecName = groupstud.getSpecialityId().getNameSpeciality();
            sortGroups.add(new Group(groupName, year, SpecName, id));
        }
        tableView.setItems(sortGroups);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadData();
        initDrawer();

    }

    private void initCol() {
        groupCol.setCellValueFactory(new PropertyValueFactory<>("groupName"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));

    }

    private void loadData() {

        groups.clear();

        for (Groupstud groupstud : GroupDB.getInstance().getAllGroups()) {

            String groupName = groupstud.getGroupname();
            Short year = groupstud.getSetYear();
            Integer id = groupstud.getGroupid();
            String SpecName = groupstud.getSpecialityId().getNameSpeciality();
            groups.add(new Group(groupName, year, SpecName, id));
        }
        tableView.setItems(groups);
    }

    private void initDrawer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/toolbar.fxml"));
            VBox toolbar = loader.load();
            drawer.setSidePane(toolbar);

        } catch (IOException ex) {
            Logger.getLogger(GroupListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
            drawer.toggle();
        });
        drawer.setOnDrawerOpening((event) -> {
            task.setRate(task.getRate() * -1);
            task.play();
            drawer.toFront();
        });
        drawer.setOnDrawerClosed((event) -> {
            drawer.toBack();
            task.setRate(task.getRate() * -1);
            task.play();
        });
    }

    @FXML
    void handleStudentList(ActionEvent event) {
        Group selectedForEdit = tableView.getSelectionModel().getSelectedItem();

        if (selectedForEdit == null) {
            AlertMaker.showMaterialDialog(rootPane, contentPane, null, "Группа не выбрана!", "Пожалуйста, выберите группу");
            return;
        }

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/StudenList.fxml"));
            Stage stage = new Stage();
            ElectronicRecordUtill.setStageIcon(stage);
            Scene scene = new Scene(root);
            stage.setTitle(Group.getNameGroup());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GroupListController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void SelectGroup(MouseEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Group group = tableView.getSelectionModel().getSelectedItem();
            Group.setNameGroup(group.getGroupName());
            Group.setIdGroup(group.getId());
            if (event.getClickCount() == 2) {
                handleStudentList(new ActionEvent());
            }
        }

    }

    @FXML
    void handleSubjectList(ActionEvent event) {
        Group selectedForEdit = tableView.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            AlertMaker.showMaterialDialog(rootPane, contentPane, null, "Группа не выбрана!", "Пожалуйста, выберите группу");
            return;
        }

        ElectronicRecordUtill.loadWindow(getClass().getResource("/fxml/SubjectList.fxml"), "Список предметов" + " "
                + GroupListController.Group.getNameGroup(), new Stage());

    }

    public static class Group {

        private SimpleStringProperty groupName;
        private SimpleIntegerProperty year;
        private SimpleIntegerProperty id;
        private SimpleStringProperty speciality;
        private static String nameGroup;
        private static int idGroup;

        public Group(String groupName, Short year, String spec, Integer id) {
            this.groupName = new SimpleStringProperty(groupName);
            this.year = new SimpleIntegerProperty(year);
            this.id = new SimpleIntegerProperty(id);
            speciality = new SimpleStringProperty(spec);
        }

        public Integer getId() {
            return id.get();
        }

        public String getGroupName() {
            return groupName.get();
        }

        public String getSpecName() {
            return speciality.get();
        }

        /**
         * @return the year
         */
        public Integer getYear() {
            return year.get();
        }

        /**
         * @return the nameGroup
         */
        public static String getNameGroup() {
            return nameGroup;
        }

        /**
         * @param aNameGroup the nameGroup to set
         */
        public static void setNameGroup(String aNameGroup) {
            nameGroup = aNameGroup;
        }

        public static int getIdGroup() {
            return idGroup;
        }

        public static void setIdGroup(int aIdGroup) {
            idGroup = aIdGroup;
        }
    }

}
