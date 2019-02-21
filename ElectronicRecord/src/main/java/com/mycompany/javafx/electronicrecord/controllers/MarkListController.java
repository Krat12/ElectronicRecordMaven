package com.mycompany.javafx.electronicrecord.controllers;

import com.jfoenix.controls.JFXButton;
import static com.mycompany.javafx.electronicrecord.controllers.CreateMarkController.getSelectTypeMark;
import static com.mycompany.javafx.electronicrecord.controllers.CreateMarkController.getStatementId;
import com.mycompany.javafx.electronicrecord.dao.impl.CourseWorkDB;
import com.mycompany.javafx.electronicrecord.dao.impl.DiplomDB;
import com.mycompany.javafx.electronicrecord.dao.impl.ReatingDB;
import com.mycompany.javafx.electronicrecord.model.Coursework;
import com.mycompany.javafx.electronicrecord.model.Diplom;
import com.mycompany.javafx.electronicrecord.model.Reating;
import com.mycompany.javafx.electronicrecord.model.SprReating;
import com.mycompany.javafx.electronicrecord.model.Statement;
import com.mycompany.javafx.electronicrecord.model.Student;
import com.mycompany.javafx.electronicrecord.utill.AlertMaker;
import com.mycompany.javafx.electronicrecord.utill.ElectronicRecordUtill;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class MarkListController implements Initializable {

    private static final String[] STRING_MARKS = {"Зачет", "Незачет"};
    private final ObservableList<ReatingModel> reatingList = FXCollections.observableArrayList();
    private static Set<Integer> edtitListIndex;

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
    private TableColumn<ReatingModel, String> colMark;

    @FXML
    private TableColumn<ReatingModel, String> thesis;

    @FXML
    private TableColumn<ReatingModel, String> placePractice;

    @FXML
    private TableColumn<ReatingModel, String> colFullNameBoss;

    @FXML
    private FontAwesomeIconView commit;

    @FXML
    private FontAwesomeIconView rollback;

    @FXML
    private JFXButton btn_commit;

    @FXML
    private JFXButton btn_rollback;

    @FXML
    private ToolBar toolBar;

    @FXML
    private MenuItem con_refresh;

    @FXML
    private MenuItem con_add;

    @FXML
    private MenuItem con_addMark;

    @FXML
    void exportAsPDF(ActionEvent event) {

    }

    @FXML
    void exportCSV(ActionEvent event) {
        String URL = ElectronicRecordUtill.initCSVExport(new Stage());
        if (URL.trim().isEmpty()) {
            return;
        }
        if (reatingList.isEmpty()) {
            JFXButton fXButton = new JFXButton("OK");
            AlertMaker.showMaterialDialog(rootPane, contentPane, Arrays.asList(fXButton), "Пустая таблица!", "Выгрузить не удалось, заполните таблицу!");
            return;
        }
        try {
            ElectronicRecordUtill.exportMarstListCSV(URL,reatingList,getSelectTypeMark());
        } catch (IOException e) {
            ElectronicRecordUtill.loadAlertError(getClass().getResource("/fxml/AlertError.fxml"), new Stage(), "Ooops...", "Что то пошло не так ");
        }
    }


    @FXML
    void handleMouseClicked(MouseEvent event) {

    }

    @FXML
    void handleRefresh(ActionEvent event) {

        if (getSelectTypeMark().equals("Дипломная работа")) {
            loadDataDiplom();
        } else if (getSelectTypeMark().equals("Курсовая работа")) {
            loadDataCourserwork();
        } else {
            loadDataByStetment();
        }
    }

    @FXML
    void handleStudentAddOption(ActionEvent event) {

        Stage stage = new Stage(StageStyle.DECORATED);
        ElectronicRecordUtill.loadWindow(getClass().getResource("/fxml/AddStudent.fxml"), "Добавление студента", stage);
        stage.setOnHiding((e) -> {
            handleRefresh(new ActionEvent());
        });
    }

    @FXML
    void handleStudentRolback(ActionEvent event) {
        edtitListIndex = initColletion();

        if (edtitListIndex.isEmpty()) {
            return;
        }
        handleRefresh(new ActionEvent());
        ElectronicRecordUtill.resetColorCommitAndRollBack(commit, rollback, btn_commit, btn_rollback);
        edtitListIndex.clear();

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
                ElectronicRecordUtill.setColorCommitAndRollBack(commit, rollback, btn_commit, btn_rollback);
                edtitListIndex = initColletion();

                List<ReatingModel> listModels = new ArrayList<>();
                List<Integer> listIndex = new ArrayList<>();

                listModels.addAll(tableView.getSelectionModel().getSelectedItems());

                listIndex.addAll(tableView.getSelectionModel().getSelectedIndices());

                for (int j = 0; j < listModels.size(); j++) {
                    listModels.get(j).setMark(MarkController.getMark());
                    edtitListIndex.add(listIndex.get(j));
                    reatingList.set(listIndex.get(j), listModels.get(j));
                }
            }
            );

        } catch (IOException ex) {
            Logger.getLogger(GroupListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void handleStudentCommit(ActionEvent event) {

        edtitListIndex = initColletion();

        if (edtitListIndex.isEmpty()) {
            return;
        }

        saveChanges();
        edtitListIndex.clear();
        ElectronicRecordUtill.resetColorCommitAndRollBack(commit, rollback, btn_commit, btn_rollback);

    }

    private void initCol() {
        numberStudent.setCellValueFactory(new PropertyValueFactory<>("numberStudent"));
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colFullNameBoss.setCellValueFactory(new PropertyValueFactory<>("fullNameBoss"));
        placePractice.setCellValueFactory(new PropertyValueFactory<>("placePractic"));
        thesis.setCellValueFactory(new PropertyValueFactory<>("thesis"));
        colMark.setCellValueFactory(new PropertyValueFactory<>("mark"));

    }

    private void loadDataByStetment() {
        if (getSelectTypeMark().equals("Зачет") || getSelectTypeMark().equals("Дифференцированный зачет")) {
            reatingList.clear();
            int amount = 1;
            for (SprReating reating : ReatingDB.getInstance().getReatingByStatement(getStatementId())) {
                ReatingModel model = new ReatingModel();
                model.setFullName(reating.getSurname() + " "+ reating.getName() + " "+ reating.getMidleName() + " ");
                checkIsNullMark(reating, model);
                checkIsNullThesis(reating, model);
                checkIsNullFullNameBoss(reating, model);
                checkIsNullPlacePractice(reating, model);
                model.setStudentId(reating.getStudentid());
                model.setNumberStudent(amount);
                model.setReatingId(reating.getReatingId());
                amount++;
                reatingList.add(model);
            }
            tableView.setItems(reatingList);
        }
    }

    private void loadDataDiplom() {

        if (getSelectTypeMark().equals("Дипломная работа")) {

            reatingList.clear();

            thesis.setVisible(true);

            int amount = 1;
            for (SprReating reating : ReatingDB.getInstance().getReatingByStatement(getStatementId())) {
                ReatingModel model = new ReatingModel();
                model.setFullName(reating.getSurname() + " "+ reating.getName() + " "+ reating.getMidleName() + " ");

                checkIsNullMark(reating, model);
                checkIsNullThesis(reating, model);
                model.setStudentId(reating.getStudentid());
                model.setNumberStudent(amount);
                model.setReatingId(reating.getReatingId());
                amount++;
                reatingList.add(model);
            }

            tableView.setItems(reatingList);
        }

    }

    private void loadDataCourserwork() {

        if (getSelectTypeMark().equals("Курсовая работа")) {
            reatingList.clear();
            placePractice.setVisible(true);
            colFullNameBoss.setVisible(true);

            int amount = 1;
            for (SprReating reating : ReatingDB.getInstance().getReatingByStatement(getStatementId())) {
                ReatingModel model = new ReatingModel();
                model.setFullName(reating.getSurname() + " "+ reating.getName() + " "+ reating.getMidleName() + " ");

                checkIsNullMark(reating, model);
                checkIsNullFullNameBoss(reating, model);
                checkIsNullPlacePractice(reating, model);
                model.setStudentId(reating.getStudentid());
                model.setNumberStudent(amount);
                model.setReatingId(reating.getReatingId());
                amount++;
                reatingList.add(model);
            }

            tableView.setItems(reatingList);
        }

    }

    private void saveChanges() {
        if (getSelectTypeMark().equals("Дипломная работа")) {
            saveDiplom();
        } else if (getSelectTypeMark().equals("Курсовая работа")) {
            saveCourseWork();
        } else {
            updateMark();
        }
    }

    private void updateMark() {

        Iterator<Integer> it = edtitListIndex.iterator();

        while (it.hasNext()) {

            updateReating(it.next());
        }
    }

    private void saveDiplom() {

        Iterator<Integer> it = edtitListIndex.iterator();

        while (it.hasNext()) {

            int index = it.next();

            Diplom diplom = new Diplom(reatingList.get(index).getReatingId());
            diplom.setThesis(reatingList.get(index).getThesis());
            diplom.setReating(updateReating(index));

            DiplomDB.getInstance().insertOrUpdate(diplom);
        }

    }

    private void saveCourseWork() {
        Iterator<Integer> it = edtitListIndex.iterator();

        while (it.hasNext()) {

            int index = it.next();

            Coursework coursework = new Coursework(reatingList.get(index).getReatingId());
            coursework.setFullNameBoss(reatingList.get(index).getFullNameBoss());
            coursework.setPlacePracticle(reatingList.get(index).getPlacePractic());
            coursework.setReating(updateReating(index));

            CourseWorkDB.getInstance().insertOrUpdate(coursework);
        }
    }

    private Reating updateReating(Integer index) {

        Reating reating = new Reating(reatingList.get(index).getReatingId());
        reating.setMark(getConvertedMark(reatingList.get(index)));

        Statement statement = new Statement(getStatementId());
        reating.setStatementId(statement);

        Student student = new Student(reatingList.get(index).getStudentId());
        reating.setStudentid(student);

        ReatingDB.getInstance().update(reating);

        return reating;
    }

    private Integer getConvertedMark(ReatingModel model) {

        if (model.getMark().equals("н/а")) {
            return 0;
        } else if (model.getMark().equals("Зачет")) {
            return 1;
        } else if (model.getMark().equals("Незачет")) {
            return 0;
        } else {
            return Integer.valueOf(model.getMark());
        }

    }

    private void checkIsNullMark(SprReating reating, ReatingModel model) {
        if (reating.getMark() == null) {
            model.setMark("");
        } else {
            if (getSelectTypeMark().equals("Зачет")) {
                if (reating.getMark() == 0) {
                    model.setMark("Незачет");
                } else if (reating.getMark() == 1) {
                    model.setMark("Зачет");
                }
            } else {
                model.setMark(String.valueOf(reating.getMark()));
            }

        }
    }

    private void checkIsNullFullNameBoss(SprReating reating, ReatingModel model) {
        if (reating.getFullNameBoss() == null) {
            model.setFullNameBoss("");
        } else {
            model.setFullNameBoss(reating.getFullNameBoss());
        }
    }

    private void checkIsNullPlacePractice(SprReating reating, ReatingModel model) {
        if (reating.getPlacePracticle() == null) {
            model.setPlacePractic("");
        } else {
            model.setPlacePractic(reating.getPlacePracticle());
        }
    }

    private void checkIsNullThesis(SprReating reating, ReatingModel model) {
        if (reating.getThesis() == null) {
            model.setThesis("");
        } else {
            model.setThesis(reating.getThesis());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        if (LoginController.getUserType().equals("Admin")) {
            tableView.setEditable(false);
            toolBar.setVisible(false);
            con_add.setVisible(false);
            con_refresh.setVisible(false);
            con_addMark.setVisible(false);
        }
        if (LoginController.getUserType().equals("Teacher")) {
            settingsEditColoumnTable();
            tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        }

        loadDataDiplom();
        loadDataCourserwork();
        loadDataByStetment();

    }

    private void settingsEditColoumnTable() {
        tableView.setEditable(true);
        settingColoumnThesis();
        settingColoumnFullNameBoss();
        settingColoumnPlacePracticl();
        settingColoumnMark();
        settingColoumnMarkPassed();

    }

    private void settingColoumnThesis() {
        thesis.setCellFactory(TextFieldTableCell.<ReatingModel>forTableColumn());
        thesis.setOnEditCommit((CellEditEvent<ReatingModel, String> event) -> {

            String thesisDiplom = event.getNewValue();

            if (!thesisDiplom.trim().isEmpty()) {

                edtitListIndex = initColletion();
                TablePosition<ReatingModel, String> pos = event.getTablePosition();
                int row = pos.getRow();
                ReatingModel reatingModel = event.getTableView().getItems().get(row);
                reatingModel.setThesis(thesisDiplom);
                edtitListIndex.add(row);
                ElectronicRecordUtill.setColorCommitAndRollBack(commit, rollback, btn_commit, btn_rollback);
            }

        });
    }

    private void settingColoumnFullNameBoss() {
        colFullNameBoss.setCellFactory(TextFieldTableCell.<ReatingModel>forTableColumn());
        colFullNameBoss.setOnEditCommit((CellEditEvent<ReatingModel, String> event) -> {

            String fullNameBoss = event.getNewValue();

            if (!fullNameBoss.trim().isEmpty()) {

                edtitListIndex = initColletion();
                TablePosition<ReatingModel, String> pos = event.getTablePosition();
                int row = pos.getRow();
                ReatingModel reatingModel = event.getTableView().getItems().get(row);
                reatingModel.setFullName(fullNameBoss);
                edtitListIndex.add(row);
                ElectronicRecordUtill.setColorCommitAndRollBack(commit, rollback, btn_commit, btn_rollback);
            }

        });
    }

    private void settingColoumnPlacePracticl() {
        placePractice.setCellFactory(TextFieldTableCell.<ReatingModel>forTableColumn());
        placePractice.setOnEditCommit((CellEditEvent<ReatingModel, String> event) -> {

            String place = event.getNewValue();

            if (!place.trim().isEmpty()) {

                edtitListIndex = initColletion();
                TablePosition<ReatingModel, String> pos = event.getTablePosition();
                int row = pos.getRow();
                ReatingModel reatingModel = event.getTableView().getItems().get(row);
                reatingModel.setPlacePractic(place);
                edtitListIndex.add(row);
                ElectronicRecordUtill.setColorCommitAndRollBack(commit, rollback, btn_commit, btn_rollback);
            }

        });
    }

    private void settingColoumnMark() {
        if (!getSelectTypeMark().equals("Зачет")) {
            colMark.setCellFactory(integerCell(value -> value >= 0 && value <= 5));
            colMark.setOnEditCommit((event) -> {

                String mark = event.getNewValue();

                if (mark != null) {

                    edtitListIndex = initColletion();
                    ReatingModel reatingModel = event.getTableView().getItems().get(event.getTablePosition().getRow());
                    reatingModel.setMark(mark);
                    edtitListIndex.add(event.getTablePosition().getRow());
                    ElectronicRecordUtill.setColorCommitAndRollBack(commit, rollback, btn_commit, btn_rollback);
                }
            });
        }
    }

    private void settingColoumnMarkPassed() {
        if (getSelectTypeMark().equals("Зачет")) {
            ObservableList<String> pass = FXCollections.observableArrayList(STRING_MARKS);

            colMark.setCellValueFactory((CellDataFeatures<ReatingModel, String> param) -> {
                ReatingModel reatingModel = param.getValue();
                String mark = reatingModel.getMark();
                return new SimpleObjectProperty<>(mark);
            });

            colMark.setCellFactory(ComboBoxTableCell.forTableColumn(pass));

            colMark.setOnEditCommit((CellEditEvent<ReatingModel, String> event) -> {

                String mark = event.getNewValue();

                if (!mark.trim().isEmpty()) {

                    edtitListIndex = initColletion();
                    TablePosition<ReatingModel, String> pos = event.getTablePosition();
                    int row = pos.getRow();
                    ReatingModel reatingModel = event.getTableView().getItems().get(row);
                    reatingModel.setMark(mark);
                    edtitListIndex.add(row);
                    ElectronicRecordUtill.setColorCommitAndRollBack(commit, rollback, btn_commit, btn_rollback);
                }

            });
        }

    }

    @FXML
    void handlePressed(KeyEvent event) {
        if (getSelectTypeMark().equals("Зачет")) {
            ReatingModel reatingModel = tableView.getSelectionModel().getSelectedItem();
            edtitListIndex = initColletion();
            if (reatingModel != null) {
                if (event.getCode().getName().equals("1")) {

                    int index = tableView.getSelectionModel().getSelectedIndex();
                    edtitListIndex.add(index);
                    reatingModel.setMark("Зачет");
                    ElectronicRecordUtill.setColorCommitAndRollBack(commit, rollback, btn_commit, btn_rollback);
                    reatingList.set(index, reatingModel);
                }
                if (event.getCode().getName().equals("2")) {

                    int index = tableView.getSelectionModel().getSelectedIndex();
                    edtitListIndex.add(index);
                    reatingModel.setMark("Незачет");
                    ElectronicRecordUtill.setColorCommitAndRollBack(commit, rollback, btn_commit, btn_rollback);
                    reatingList.set(index, reatingModel);
                }
            }
        }

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
                        if (value == 0) {
                            return "н/а";
                        }
                        return String.valueOf(value);
                    } else {
                        AlertMaker.showMaterialDialog(rootPane, contentPane, null, "Неверный формат!", "Оценка не может быть меньше нуля!");
                        return null;
                    }
                } catch (NumberFormatException e) {
                    AlertMaker.showMaterialDialog(rootPane, contentPane, null, "Неверный формат!", "Введите целое положительное число без разделителей");
                    return null;
                }
            }
        });
    }

    private static Set<Integer> initColletion() {
        if (edtitListIndex == null) {
            edtitListIndex = new HashSet<>();
        }
        return edtitListIndex;
    }
    
    @FXML
    void closeStage(ActionEvent event) {
        ElectronicRecordUtill.closeStage(event);
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
        private int statementId;

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

        public int getStatementId() {
            return statementId;
        }

        public void setStatementId(int statementId) {
            this.statementId = statementId;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 29 * hash + Objects.hashCode(this.numberStudent);
            hash = 29 * hash + Objects.hashCode(this.fullName);
            hash = 29 * hash + Objects.hashCode(this.fullNameBoss);
            hash = 29 * hash + Objects.hashCode(this.placePractic);
            hash = 29 * hash + Objects.hashCode(this.thesis);
            hash = 29 * hash + Objects.hashCode(this.mark);
            hash = 29 * hash + this.studentId;
            hash = 29 * hash + this.reatingId;
            hash = 29 * hash + Objects.hashCode(this.firstName);
            hash = 29 * hash + Objects.hashCode(this.secondName);
            hash = 29 * hash + Objects.hashCode(this.midleName);
            hash = 29 * hash + this.statementId;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ReatingModel other = (ReatingModel) obj;
            if (this.studentId != other.studentId) {
                return false;
            }
            if (this.reatingId != other.reatingId) {
                return false;
            }
            if (this.statementId != other.statementId) {
                return false;
            }
            if (!Objects.equals(this.firstName, other.firstName)) {
                return false;
            }
            if (!Objects.equals(this.secondName, other.secondName)) {
                return false;
            }
            if (!Objects.equals(this.midleName, other.midleName)) {
                return false;
            }
            if (!Objects.equals(this.numberStudent, other.numberStudent)) {
                return false;
            }
            if (!Objects.equals(this.fullName, other.fullName)) {
                return false;
            }
            if (!Objects.equals(this.fullNameBoss, other.fullNameBoss)) {
                return false;
            }
            if (!Objects.equals(this.placePractic, other.placePractic)) {
                return false;
            }
            if (!Objects.equals(this.thesis, other.thesis)) {
                return false;
            }
            if (!Objects.equals(this.mark, other.mark)) {
                return false;
            }
            return true;
        }

    }
}
