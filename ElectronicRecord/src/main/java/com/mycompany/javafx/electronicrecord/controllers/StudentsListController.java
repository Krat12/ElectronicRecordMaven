package com.mycompany.javafx.electronicrecord.controllers;

import com.jfoenix.controls.JFXButton;
import com.mycompany.javafx.electronicrecord.controllers.GroupListController.Group;
import com.mycompany.javafx.electronicrecord.dao.impl.GroupDB;
import com.mycompany.javafx.electronicrecord.dao.impl.StudentDB;
import com.mycompany.javafx.electronicrecord.dao.impl.UserDB;
import com.mycompany.javafx.electronicrecord.model.Groupstud;
import com.mycompany.javafx.electronicrecord.model.Student;
import com.mycompany.javafx.electronicrecord.model.User;
import com.mycompany.javafx.electronicrecord.utill.AlertMaker;
import com.mycompany.javafx.electronicrecord.utill.ElectronicRecordUtill;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StudentsListController implements Initializable {

    ObservableList<StudentModelTable> listStudents = FXCollections.observableArrayList();
    ObservableList<StudentModelTable> sortStudents = FXCollections.observableArrayList();
    protected static String groupName;
    private static int groupId;
    private static List<StudentModelTable> studentsOnUpdateOrInsert;
    private List<Student> studentsParseCSV;

    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TableView<StudentModelTable> tableView;

    @FXML
    private JFXButton btn_delete;

    @FXML
    private TableColumn<StudentModelTable, Integer> numberStudent;

    @FXML
    private TableColumn<StudentModelTable, String> fullName;

    @FXML
    private TableColumn<StudentModelTable, Integer> id;

    @FXML
    private TableColumn<StudentModelTable, Integer> numberRecord;

    @FXML
    private TableColumn<StudentModelTable, String> login;

    @FXML
    private TableColumn<StudentModelTable, String> password;

    @FXML
    private FontAwesomeIconView commit;

    @FXML
    private FontAwesomeIconView rollback;

    @FXML
    private TextField txt_serch;

    @FXML
    private JFXButton btn_edit;

    @FXML
    private JFXButton btn_addStudent;

    @FXML
    private JFXButton bnt_NullGroup;

    @FXML
    private JFXButton btn_statement;

    @FXML
    private JFXButton btn_autoGenerateLogin;

    @FXML
    private FontAwesomeIconView iconStatement;

    @FXML
    private MenuItem con_edit;

    @FXML
    private MenuItem con_addStudent;

    @FXML
    private MenuItem con_delete;

    @FXML
    void handleCreateStatement(ActionEvent event) {
        ElectronicRecordUtill.loadWindowModality(getClass().getResource("/fxml/AddStatement.fxml"), "Создание ведомости", null);
    }

    @FXML
    void handleGenerateLogin(ActionEvent event) {
        int amount = 0;
        studentsOnUpdateOrInsert = initCollection();
        for (int i = 0; i < listStudents.size(); i++) {

            StudentModelTable student = listStudents.get(i);

            if (student.getLogin().equals("") || student.getPassword().equals("")) {
                setColor();
                student.setLogin(generateLogin(student));
                student.setPassword(generatePassword());
                listStudents.set(i, student);
                studentsOnUpdateOrInsert.add(student);
                amount++;
            }
        }
        if (amount == 0) {
            JFXButton button = new JFXButton("OK");
            AlertMaker.showMaterialDialog(rootPane, contentPane, Arrays.asList(button),
                    "Логин(-ы) и парол(-ь/-и) не были сгенерированы", "Нет пустых полей для автогенерации");
        }

    }

    @FXML
    void handleCommit(MouseEvent event) {
        if (studentsOnUpdateOrInsert != null) {
            updateOrInsertStudents();
            resetColor();
        }

    }

    @FXML
    void handleRolback(MouseEvent event) {
        studentsOnUpdateOrInsert = initCollection();
        if (!studentsOnUpdateOrInsert.isEmpty()) {
            handleRefresh(new ActionEvent());
            studentsOnUpdateOrInsert.clear();
            resetColor();
        }
    }

    @FXML
    void handleIsNullGroup(ActionEvent event) {

    }

    @FXML
    void handleImportCSV(ActionEvent event) {
        try {
            String URL = ElectronicRecordUtill.initCSVImport(new Stage());
            System.out.println(URL);
            if (URL.equals("")) {
                return;
            }
            studentsParseCSV = ElectronicRecordUtill.parseCSVWithHeader(URL);

            if (studentsParseCSV.isEmpty()) {
                ElectronicRecordUtill.loadAlertError(getClass().getResource("/fxml/AlertError.fxml"), new Stage(), "Ooops...", "Похоже файл пустой");
                return;
            }
            for (Student student : studentsParseCSV) {
                int index = listStudents.size() + 1;
                StudentModelTable smt = new StudentModelTable();
                String fullname = student.getFullName();
                smt.setFullName(fullname);
                splitFullName(smt);
                smt.setNumberRecord(student.getNumberBook());
                smt.setNumberStudent(index);
                smt.setLogin("");
                smt.setPassword("");
                smt.setId(-1);
                listStudents.add(smt);
            }
        } catch (IOException e) {
            System.out.println("ErrorEmpty");
        } catch (Exception e) {
            ElectronicRecordUtill.loadAlertError(getClass().getResource("/fxml/AlertError.fxml"), new Stage(), "Ooops...", "Проверьте споводение типов в Excel ");
        }
    }

    private void splitFullName(StudentModelTable studentModelTable) {
        String delimeter = " ";
        String fullName = studentModelTable.getFullName();
        System.out.println(fullName);
        String[] subString = fullName.split(delimeter);

        studentModelTable.setFirstName(subString[1]);
        studentModelTable.setSecondName(subString[0]);
        studentModelTable.setMidleName(subString[2]);

    }

    private void setColor() {
        commit.setFill(Color.web("#70ff7e"));
        rollback.setFill(Color.web("#ff6161"));
        commit.setOpacity(1);
        rollback.setOpacity(1);
    }

    private void resetColor() {
        commit.setFill(Color.web("#b2b2b2"));
        commit.setOpacity(0.5);
        rollback.setFill(Color.web("#b2b2b2"));
        rollback.setOpacity(0.5);
        btn_edit.setStyle("-fx-border-color:white");
        btn_delete.setStyle("-fx-border-color:white");
    }

    private void updateOrInsertStudents() {
        if (!studentsOnUpdateOrInsert.isEmpty()) {
            Groupstud groupstud = GroupDB.getInstance().getGroupstudsByName(StudentsListController.groupName);

            for (StudentModelTable student : studentsOnUpdateOrInsert) {

                User user = new User();
                user.setLogin(student.getLogin());
                user.setPassword(student.getPassword());
                user.setName(student.getFirstName());
                user.setSurname(student.getSecondName());
                user.setMidleName(student.getMidleName());
                if (student.getId() != -1) {
                    user.setUserId(student.getId());
                    UserDB.getInstance().update(user);
                } else {
                    UserDB.getInstance().insert(user);

                    Student studentInsert = new Student();
                    studentInsert.setStudentid(user.getUserId());
                    studentInsert.setNumberBook(student.getNumberRecord());
                    studentInsert.setGroupid(groupstud);
                    studentInsert.setUser(user);
                    StudentDB.getInstance().insert(studentInsert);

                }
            }
            handleRefresh(new ActionEvent());
            studentsOnUpdateOrInsert.clear();
        }

    }

    private String generateLogin(StudentModelTable student) {
        char[] fisrstName = student.getFirstName().toCharArray();
        char[] secondName = student.getSecondName().toCharArray();
        char[] midleName = student.getMidleName().toCharArray();
        String generateString = "" + fisrstName[0] + fisrstName[1] + secondName[0] + midleName[0];

        return generateString.toUpperCase();
    }

    private String generatePassword() {
        return new Random().ints(5, 48, 57).mapToObj(i -> String.valueOf((char) i)).collect(Collectors.joining());
    }

    private void initCol() {
        numberStudent.setCellValueFactory(new PropertyValueFactory<>("numberStudent"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        numberRecord.setCellValueFactory(new PropertyValueFactory<>("numberRecord"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));

    }

    private void loadData() {
        listStudents.clear();

        int amount = 1;
        for (com.mycompany.javafx.electronicrecord.model.Student student : StudentDB.getInstance().getStudentsByGroup(groupName)) {

            Integer numberStudent = amount;
            String fullName = student.getUser().getSurname() + " " + student.getUser().getName() + " " + student.getUser().getMidleName();
            Integer id = student.getStudentid();
            String login = student.getUser().getLogin();
            String password = student.getUser().getPassword();
            Integer numberRecord = student.getNumberBook();

            listStudents.add(new StudentModelTable(numberStudent, fullName, id, login, password, numberRecord,
                    student.getUser().getName(), student.getUser().getSurname(), student.getUser().getMidleName()));

            amount++;
        }
        tableView.setItems(listStudents);
    }

    @FXML
    void closeStage(ActionEvent event) {

    }

    @FXML
    void exportAsPDF(ActionEvent event) {

    }

    @FXML
    void exportCSV(ActionEvent event) {
        String URL = ElectronicRecordUtill.initCSVExport(new Stage());
        System.out.println(URL);
        if (URL.equals("")) {
            return;
        }
        if (listStudents.isEmpty()) {
            JFXButton fXButton = new JFXButton("OK");
            AlertMaker.showMaterialDialog(rootPane, contentPane, Arrays.asList(fXButton), "Пустая таблица!", "Выгрузить не удалось, заполните таблицу!");
            return;
        }
        try {
            ElectronicRecordUtill.exportStudentCSV(URL, listStudents);
        } catch (IOException e) {
            ElectronicRecordUtill.loadAlertError(getClass().getResource("/fxml/AlertError.fxml"), new Stage(), "Ooops...", "Что то пошло не так ");
        }
    }

    @FXML
    void handleStudentDeleteOption(ActionEvent event) {

    }

    @FXML
    void handleStudentEditOption(ActionEvent event) {
        if (LoginController.getUserType().equals("Admin")) {
            StudentModelTable selectedForEdit = tableView.getSelectionModel().getSelectedItem();
            if (selectedForEdit == null) {
                JFXButton button = new JFXButton("Ok");
                AlertMaker.showMaterialDialog(rootPane, contentPane, Arrays.asList(button), "Студент не выбран!", "Пожалуйста, выберите студента");
                return;
            }
            showEditDiolog(selectedForEdit);
        } else {
            handleCreateStatement(event);
        }

    }

    private void showEditDiolog(StudentModelTable student) {
        int indexSelectRow = tableView.getSelectionModel().getSelectedIndex();
        try {
            Stage stage = new Stage();
            stage.setResizable(false);
            StudentCreateController createController = (StudentCreateController) ElectronicRecordUtill.loadWindowModality(getClass().getResource("/fxml/AddStudent.fxml"), "Редоктирование студента", stage);
            createController.mappingUI(student);
            if (student.getId() != -1) {
                stage.setOnHiding((e) -> {
                    handleRefresh(new ActionEvent());
                    btn_edit.setStyle("-fx-border-color:white");
                    btn_delete.setStyle("-fx-border-color:white");
                });
            } else {
                stage.setOnHiding((e) -> {

                    listStudents.set(indexSelectRow, StudentCreateController.getModelTableStudent());
                    if (!checkLoginAndPasswordIsEmpty()) {
                        setColor();
                        studentsOnUpdateOrInsert = initCollection();
                        studentsOnUpdateOrInsert.add(StudentCreateController.getModelTableStudent());
                    }
                });
            }

        } catch (Exception e) {
            Logger.getLogger(StudentsListController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void handleStudentAddOption(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.setResizable(false);
            ElectronicRecordUtill.loadWindowModality(getClass().getResource("/fxml/AddStudent.fxml"), "Добавление студента", stage);
            stage.setOnHiding((e) -> {
                handleRefresh(new ActionEvent());
                btn_edit.setStyle("-fx-border-color:white");
                btn_delete.setStyle("-fx-border-color:white");
            });

        } catch (Exception e) {
            Logger.getLogger(StudentsListController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void handleRefresh(ActionEvent event) {
        loadData();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        groupName = GroupListController.Group.getNameGroup();
        groupId = Group.getIdGroup();
        initCol();
        loadData();
        checkTeacherOrAmdin();
    }

    @FXML
    void serch(ActionEvent event) {
        sortStudents.clear();
        int amount = 1;
        for (com.mycompany.javafx.electronicrecord.model.Student student : StudentDB.getInstance().getStudentsByNameAndGroup(txt_serch.getText(), groupName)) {

            Integer numberStudent = amount;
            String fullName = student.getUser().getSurname() + " " + student.getUser().getName() + " " + student.getUser().getMidleName();
            Integer id = student.getStudentid();
            String login = student.getUser().getLogin();
            String password = student.getUser().getPassword();
            Integer numberRecord = student.getNumberBook();
            amount++;
            sortStudents.add(new StudentModelTable(numberStudent, fullName, id, login, password, numberRecord,
                    student.getUser().getName(), student.getUser().getSurname(), student.getUser().getMidleName()));
        }
        tableView.setItems(sortStudents);

    }

    @FXML
    void handleMouseClicked(MouseEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            setColorBorderButton();
        }

        if (event.getClickCount() == 2 && tableView.getSelectionModel().getSelectedItem() != null) {

            showEditDiolog(tableView.getSelectionModel().getSelectedItem());
        }
    }

    private void setColorBorderButton() {
        btn_edit.setStyle("-fx-border-color:#70ff7e");
        btn_delete.setStyle("-fx-border-color:#70ff7e");
    }

    private boolean checkLoginAndPasswordIsEmpty() {
        if (StudentCreateController.getModelTableStudent().getLogin().equals("")
                || StudentCreateController.getModelTableStudent().getPassword().equals("")) {
            return true;
        }
        return false;
    }

    private static List<StudentModelTable> initCollection() {
        if (studentsOnUpdateOrInsert == null) {
            studentsOnUpdateOrInsert = new ArrayList<>();
        }
        return studentsOnUpdateOrInsert;
    }

    public static int getGroupIdForStudent() {
        return groupId;
    }

    private void checkTeacherOrAmdin() {
        if (LoginController.getUserType().equals("Teacher")) {
            bnt_NullGroup.setVisible(false);
            btn_addStudent.setVisible(false);
            btn_delete.setVisible(false);
            rollback.setVisible(false);
            commit.setVisible(false);
            con_addStudent.setVisible(false);
            con_delete.setVisible(false);
            con_edit.setVisible(false);
            btn_autoGenerateLogin.setVisible(false);
            iconStatement.setGlyphName("FILE_TEXT");
            btn_edit.setText("Ведомость");
        }
    }

    public static class StudentModelTable {

        private SimpleIntegerProperty numberStudent;
        private SimpleStringProperty fullName;
        private SimpleIntegerProperty numberRecord;
        private SimpleIntegerProperty id;
        private SimpleIntegerProperty course;
        private SimpleStringProperty login;
        private SimpleStringProperty password;
        private SimpleStringProperty firstName;
        private SimpleStringProperty secondName;
        private SimpleStringProperty midleName;

        public StudentModelTable(Integer numberStudent, String fullName, Integer id, String login, String password,
                Integer numberRecord, String firstName, String secondName, String midleName) {

            this.fullName = new SimpleStringProperty(fullName);
            this.numberStudent = new SimpleIntegerProperty(numberStudent);
            this.id = new SimpleIntegerProperty(id);
            this.numberRecord = new SimpleIntegerProperty(numberRecord);
            this.login = new SimpleStringProperty(login);
            this.password = new SimpleStringProperty(password);
            this.firstName = new SimpleStringProperty(firstName);
            this.secondName = new SimpleStringProperty(secondName);
            this.midleName = new SimpleStringProperty(midleName);
        }

        public StudentModelTable() {
        }

        public Integer getNumberStudent() {
            return numberStudent.get();
        }

        public String getFullName() {
            return fullName.get();
        }

        public Integer getNumberRecord() {
            return numberRecord.get();
        }

        public Integer getId() {

            return id.get();

        }

        public Integer getCourse() {
            return course.get();
        }

        /**
         * @return the login
         */
        public String getLogin() {
            return login.get();
        }

        /**
         * @return the password
         */
        public String getPassword() {
            return password.get();
        }

        public String getFirstName() {
            return firstName.get();
        }

        public String getSecondName() {
            return secondName.get();
        }

        public String getMidleName() {
            return midleName.get();
        }

        public void setNumberStudent(Integer numberStudent) {
            this.numberStudent = new SimpleIntegerProperty(numberStudent);
        }

        public void setFullName(String fullName) {
            this.fullName = new SimpleStringProperty(fullName);
        }

        public void setNumberRecord(Integer numberRecord) {
            this.numberRecord = new SimpleIntegerProperty(numberRecord);
        }

        public void setId(Integer id) {
            this.id = new SimpleIntegerProperty(id);
        }

        public void setCourse(Integer course) {
            this.course.set(course);
        }

        public void setLogin(String login) {
            this.login = new SimpleStringProperty(login);
        }

        public void setPassword(String password) {
            this.password = new SimpleStringProperty(password);
        }

        public void setFirstName(String firstName) {
            this.firstName = new SimpleStringProperty(firstName);
        }

        public void setSecondName(String secondName) {
            this.secondName = new SimpleStringProperty(secondName);
        }

        public void setMidleName(String midleName) {
            this.midleName = new SimpleStringProperty(midleName);
        }

        @Override
        public String toString() {
            return "StudentModelTable{" + "fullName=" + fullName + '}';
        }

    }

}
