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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class GroupListController implements Initializable {

    ObservableList<Group> groups = FXCollections.observableArrayList();
    ObservableList<Group> filterGroups = FXCollections.observableArrayList();

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
    private MenuItem con_edit;

    @FXML
    private MenuItem con_deleteGroup;

    @FXML
    private MenuItem con_addGroup;

    @FXML
    private JFXButton btn_addGroup;

    @FXML
    private JFXButton btn_delteGroup;

    @FXML
    private JFXButton btn_edit;

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
        Stage stage = new Stage();
        stage.setResizable(false);
        ElectronicRecordUtill.loadWindowModality(getClass().getResource("/fxml/AddGroup.fxml"), "Добавление группы", stage);
        stage.setOnHidden((WindowEvent event1) -> {
            handleRefresh(new ActionEvent());
        });
    }

    @FXML
    void serch(ActionEvent event) {
        filterGroups.clear();

        for (Groupstud groupstud : GroupDB.getInstance().getSortGroupstudsByNameGroup(txt_serch.getText())) {
            String groupName = groupstud.getGroupname();
            Short year = groupstud.getSetYear();
            Integer id = groupstud.getGroupid();
            String SpecName = groupstud.getSpecialityId().getNameSpeciality();
            filterGroups.add(new Group(groupName, year, SpecName, id));
        }
        tableView.setItems(filterGroups);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadData();
        initDrawer();
        checkTeacher();

    }

    private void initCol() {
        groupCol.setCellValueFactory(new PropertyValueFactory<>("groupName"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));

    }

    private void loadData() {

        groups.clear();
        if (LoginController.getUserType().equals("Admin")) {
            for (Groupstud groupstud : GroupDB.getInstance().getAllGroups()) {
                setDateInTable(groupstud);
            }

        }

        if (LoginController.getUserType().equals("Teacher")) {
            for(Groupstud groupstud : GroupDB.getInstance().getGroupstudsByTeacher(LoginController.getUserId())){
                setDateInTable(groupstud);
            }
        }
        tableView.setItems(groups);
    }

    
    
    private void setDateInTable(Groupstud groupstud) {
        String groupName = groupstud.getGroupname();
        Short year = groupstud.getSetYear();
        Integer id = groupstud.getGroupid();
        String SpecName = groupstud.getSpecialityId().getNameSpeciality();
        groups.add(new Group(groupName, year, SpecName, id));
    }

//<editor-fold defaultstate="collapsed" desc="ToolMenu">
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
//</editor-fold>

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

    private void checkTeacher() {
        if (LoginController.getUserType().equals("Teacher")) {
            btn_addGroup.setVisible(false);
            btn_delteGroup.setVisible(false);
            btn_edit.setVisible(false);
            con_addGroup.setVisible(false);
            con_edit.setVisible(false);
            con_deleteGroup.setVisible(false);
        }
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
