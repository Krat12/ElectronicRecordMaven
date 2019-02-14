package com.mycompany.javafx.electronicrecord.controllers;

import static com.mycompany.javafx.electronicrecord.controllers.CreateMarkController.getSelectTypeMark;
import static com.mycompany.javafx.electronicrecord.controllers.CreateMarkController.getStatementId;
import com.mycompany.javafx.electronicrecord.dao.impl.ReatingDB;
import com.mycompany.javafx.electronicrecord.model.Reating;
import com.mycompany.javafx.electronicrecord.utill.AlertMaker;
import com.mycompany.javafx.electronicrecord.utill.ElectronicRecordUtill;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class MarkListController implements Initializable {

    ObservableList<ReatingModel> reatingList = FXCollections.observableArrayList();
    List<ReatingModel> listModels = new ArrayList<>();
    List<Integer> listIndex = new ArrayList<>();

    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableView<ReatingModel> tableView;

    @FXML
    private TableColumn<ReatingModel, Integer> numberStudent;

    @FXML
    private TableColumn<ReatingModel, String> fullName;

    @FXML
    private TableColumn<ReatingModel, String> mark;

    @FXML
    private TableColumn<ReatingModel, String> thesis;

    @FXML
    private TableColumn<ReatingModel, String> placePractice;

    @FXML
    private TableColumn<ReatingModel, String> fullNameBoss;

    @FXML
    void exportAsPDF(ActionEvent event) {

    }

    @FXML
    void exportCSV(ActionEvent event) {

    }

    @FXML
    void handleImportCSV(ActionEvent event) {

    }

    @FXML
    void handleMouseClicked(MouseEvent event) {

    }

    @FXML
    void handleRefresh(ActionEvent event) {

    }

    @FXML
    void handleStudentAddOption(ActionEvent event) {

    }

    @FXML
    void handleIsNullGroup(ActionEvent event) {

    }

    @FXML
    void handleMarkOption(ActionEvent event) {

        if (tableView.getSelectionModel().getSelectedItem() == null) {
            AlertMaker.showMaterialDialog(rootPane, contentPane, null, "Студент не выбран", "Пожалуйста, выберите студента");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Mark.fxml"));
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.APPLICATION_MODAL);
            ElectronicRecordUtill.setStageIcon(stage);
            stage.show();
            stage.setOnHiding((WindowEvent e) -> {

                List<Integer> list = tableView.getSelectionModel().getSelectedIndices();
                List<ReatingModel> models = tableView.getSelectionModel().getSelectedItems();
                listModels.addAll(models);
                listIndex.addAll(list);

                for (int j = 0; j < listModels.size(); j++) {
                    listModels.get(j).setMark(MarkController.getMark());
                    reatingList.set(listIndex.get(j), listModels.get(j));
                }

            });
        } catch (IOException ex) {
            Logger.getLogger(GroupListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleStudentCommit(ActionEvent event) {

    }
    private void initCol(Callback<TableColumn<Object, String>, TableCell<Object, String>> integerCell) {
        numberStudent.setCellValueFactory(new PropertyValueFactory<>("numberStudent"));
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        fullNameBoss.setCellValueFactory(new PropertyValueFactory<>("fullNameBoss"));
        placePractice.setCellValueFactory(new PropertyValueFactory<>("placePractic"));
        thesis.setCellValueFactory(new PropertyValueFactory<>("thesis"));
        mark.setCellValueFactory(new PropertyValueFactory<>("mark"));

    }

    private void loadDataByStetment() {
        int amount = 1;
        for (Reating reating : ReatingDB.getInstance().getReatingByStatement(getStatementId())) {
            ReatingModel model = new ReatingModel();
            model.setFullName(reating.getStudentid().getUser().getSurname() + " "
                    + reating.getStudentid().getUser().getName() + " "
                    + reating.getStudentid().getUser().getMidleName() + " ");
            checkIsNullMark(reating, model);
            checkIsNullThesis(reating, model);
            checkIsNullFullNameBoss(reating, model);
            checkIsNullPlacePractice(reating, model);
            model.setStudentId(reating.getStudentid().getStudentid());
            model.setNumberStudent(amount);
            model.setReatingId(reating.getReatingId());
            amount++;
            reatingList.add(model);

            tableView.setItems(reatingList);
        }
    }

    private void loadDataByDiplom() {

        if (getSelectTypeMark().equals("Дипломная работа")) {
            thesis.setVisible(true);
            ReatingModel model = null;
            int amount = 1;
            for (Reating reating : ReatingDB.getInstance().getReatingByDiplom(getStatementId())) {
                model = new ReatingModel();
                model.setFullName(reating.getStudentid().getUser().getSurname() + " "
                        + reating.getStudentid().getUser().getName() + " "
                        + reating.getStudentid().getUser().getMidleName() + " ");

                checkIsNullMark(reating, model);
                checkIsNullThesis(reating, model);
                model.setStudentId(reating.getStudentid().getStudentid());
                model.setNumberStudent(amount);
                model.setReatingId(reating.getReatingId());
                amount++;
                reatingList.add(model);
            }
            if (model == null) {
                loadDataByStetment();
            } else {
                tableView.setItems(reatingList);
            }

        }

    }

    private void loadDataCourserwork() {

        if (getSelectTypeMark().equals("Курсовая работа")) {
            placePractice.setVisible(true);
            fullNameBoss.setVisible(true);
            ReatingModel model = null;
            int amount = 1;
            for (Reating reating : ReatingDB.getInstance().getReatingByCoursework(getStatementId())) {
                model = new ReatingModel();
                model.setFullName(reating.getStudentid().getUser().getSurname() + " "
                        + reating.getStudentid().getUser().getName() + " "
                        + reating.getStudentid().getUser().getMidleName() + " ");

                checkIsNullMark(reating, model);
                checkIsNullFullNameBoss(reating, model);
                checkIsNullPlacePractice(reating, model);
                model.setStudentId(reating.getStudentid().getStudentid());
                model.setNumberStudent(amount);
                model.setReatingId(reating.getReatingId());
                amount++;
                reatingList.add(model);
            }
            if (model == null) {
                loadDataByStetment();
            } else {
                tableView.setItems(reatingList);
            }

        }

    }

    private void checkIsNullMark(Reating reating, ReatingModel model) {
        if (reating.getMark() == null) {
            model.setMark("");
        } else {
            model.setMark(String.valueOf(reating.getMark()));
        }
    }

    private void checkIsNullFullNameBoss(Reating reating, ReatingModel model) {
        if (reating.getCoursework() == null) {
            model.setFullNameBoss("");
        } else {
            model.setFullNameBoss(reating.getCoursework().getFullNameBoss());
        }
    }

    private void checkIsNullPlacePractice(Reating reating, ReatingModel model) {
        if (reating.getCoursework() == null) {
            model.setPlacePractic("");
        } else {
            model.setPlacePractic(reating.getCoursework().getPlacePracticle());
        }
    }

    private void checkIsNullThesis(Reating reating, ReatingModel model) {

        if (reating.getDiplom() == null) {
            model.setThesis("");
        } else {
            model.setThesis(reating.getDiplom().getThesis());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initCol(integerCell((value) -> value >= 0));
        loadDataByDiplom();
        loadDataCourserwork();

        if (getSelectTypeMark().equals("Зачет") || getSelectTypeMark().equals("Дифференцированный зачет")) {
            loadDataByStetment();
        }
        tableView.setEditable(true);
        settingsTable();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    private void settingsTable() {
        thesis.setCellFactory(TextFieldTableCell.<ReatingModel>forTableColumn());
        thesis.setOnEditCommit((CellEditEvent<ReatingModel, String> event) -> {
            TablePosition<ReatingModel, String> pos = event.getTablePosition();

            String thesis = event.getNewValue();

            int row = pos.getRow();
            ReatingModel reatingModel = event.getTableView().getItems().get(row);

            reatingModel.setFullName(thesis);
        });

        placePractice.setCellFactory(TextFieldTableCell.<ReatingModel>forTableColumn());
        placePractice.setOnEditCommit((CellEditEvent<ReatingModel, String> event) -> {
            TablePosition<ReatingModel, String> pos = event.getTablePosition();

            String place = event.getNewValue();

            int row = pos.getRow();
            ReatingModel reatingModel = event.getTableView().getItems().get(row);

            reatingModel.setFullName(place);
        });

        fullNameBoss.setCellFactory(TextFieldTableCell.<ReatingModel>forTableColumn());
        fullNameBoss.setOnEditCommit((CellEditEvent<ReatingModel, String> event) -> {
            TablePosition<ReatingModel, String> pos = event.getTablePosition();

            String fullNameBoss = event.getNewValue();

            int row = pos.getRow();
            ReatingModel reatingModel = event.getTableView().getItems().get(row);

            reatingModel.setFullName(fullNameBoss);
        });
        
        mark.setCellFactory(integerCell(value -> value >= 0 && value <=5));
        mark.setOnEditCommit((event) -> {
            String value = event.getNewValue();
            if (value != null) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setMark(value);
            } 
        });
    }

    protected <T> Callback<TableColumn<T, String>, TableCell<T, String>> integerCell(
            Predicate<Integer> validator) {
        return TextFieldTableCell.forTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                if (object == null) {
                    return null;
                }
                return object.toString();
            }

            @Override
            public String fromString(String string) {
                try {
                    int value = Integer.parseInt(string);
                    if (validator.test(value)) {
                        if(value == 0){
                            return "н/а";
                        }
                        return String.valueOf(value);
                    } else {
                        AlertMaker.showMaterialDialog(rootPane, contentPane, null, "Неверный формат!", "Количество часов не может быть меньше нуля!");
                        return null;
                    }
                } catch (NumberFormatException e) {
                    AlertMaker.showMaterialDialog(rootPane, contentPane, null, "Неверный формат!", "Введите целое положительное число без разделителей");
                    return null;
                }
            }
        });
    }

    public static class ReatingModel {

        private SimpleIntegerProperty numberStudent;
        private SimpleStringProperty fullName;
        private SimpleStringProperty fullNameBoss;
        private SimpleStringProperty placePractic;
        private SimpleStringProperty thesis;
        private SimpleStringProperty mark;
        private int studentId;
        private int reatingId;
        private String firstName;
        private String secondName;
        private String midleName;

        public Integer getNumberStudent() {
            return numberStudent.get();
        }

        public void setNumberStudent(Integer numberStudent) {
            this.numberStudent = new SimpleIntegerProperty(numberStudent);
        }

        public String getFullName() {
            return fullName.get();
        }

        public void setFullName(String fullName) {
            this.fullName = new SimpleStringProperty(fullName);
        }

        public String getFullNameBoss() {
            return fullNameBoss.get();
        }

        public void setFullNameBoss(String fullNameBoss) {
            this.fullNameBoss = new SimpleStringProperty(fullNameBoss);
        }

        public String getPlacePractic() {
            return placePractic.get();
        }

        public void setPlacePractic(String placePractic) {
            this.placePractic = new SimpleStringProperty(placePractic);
        }

        public String getThesis() {
            return thesis.get();
        }

        public void setThesis(String thesis) {
            this.thesis = new SimpleStringProperty(thesis);
        }

        public String getMark() {
            return mark.get();
        }

        public void setMark(String mark) {
            this.mark = new SimpleStringProperty(mark);

        }

        public int getStudentId() {
            return studentId;
        }

        public void setStudentId(int studentId) {
            this.studentId = studentId;
        }

        public int getReatingId() {
            return reatingId;
        }

        public void setReatingId(int reatingId) {
            this.reatingId = reatingId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getSecondName() {
            return secondName;
        }

        public void setSecondName(String secondName) {
            this.secondName = secondName;
        }

        public String getMidleName() {
            return midleName;
        }

        public void setMidleName(String midleName) {
            this.midleName = midleName;
        }

        @Override
        public String toString() {
            return "ReatingModel{" + "mark=" + mark + '}';
        }

    }
}
