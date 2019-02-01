package com.mycompany.javafx.electronicrecord.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import com.mycompany.javafx.electronicrecord.dao.impl.GroupDB;
import com.mycompany.javafx.electronicrecord.model.Groupstud;
import com.mycompany.javafx.electronicrecord.utill.AlertMaker;
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
    void handleBookDeleteOption(ActionEvent event) {
        Group selectedForDeletion = tableView.getSelectionModel().getSelectedItem();
        if (selectedForDeletion == null) {
            AlertMaker.showErrorMessage("Студент не выбран", "Пожалуйста, выберите студента.");
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
        } else {
            AlertMaker.showSimpleAlert("Удаление отменено", "Удаление группы было отменено");
        }
    }

    @FXML
    void handleBookEditOption(ActionEvent event) {

    }

    @FXML
    void handleRefresh(ActionEvent event) {
        loadData();
    }
    
    
    @FXML
    void handleGroupAdd(ActionEvent event) {

    }

    @FXML
    void serch(ActionEvent event) {
        sortGroups.clear();

        for (Groupstud groupstud : GroupDB.getInstance().getGroupstudsByNameGroup(txt_serch.getText())) {

            String groupName = groupstud.getGroupname();
            Short year = groupstud.getSetYear();
            Integer id = groupstud.getGroupid();

            sortGroups.add(new Group(groupName, year, id));
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

            groups.add(new Group(groupName, year, id));
        }
        tableView.setItems(groups);
    }
    
    private void initDrawer(){
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

    public static class Group {

        private SimpleStringProperty groupName;
        private SimpleIntegerProperty year;
        private SimpleIntegerProperty id;

        public Group(String groupName, Short year, Integer id) {
            this.groupName = new SimpleStringProperty(groupName);
            this.year = new SimpleIntegerProperty(year);
            this.id = new SimpleIntegerProperty(id);
        }

        public Integer getId() {
            return id.get();
        }

        public String getGroupName() {
            return groupName.get();
        }

        /**
         * @return the year
         */
        public Integer getYear() {
            return year.get();
        }

    }

}
