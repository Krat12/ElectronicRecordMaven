package com.mycompany.javafx.electronicrecord.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import static com.mycompany.javafx.electronicrecord.controllers.CreateMarkController.getSelectTypeMark;
import com.mycompany.javafx.electronicrecord.dao.impl.ReatingDB;
import com.mycompany.javafx.electronicrecord.dao.impl.StudentDB;
import com.mycompany.javafx.electronicrecord.model.Reating;
import com.mycompany.javafx.electronicrecord.model.SprReating;
import com.mycompany.javafx.electronicrecord.model.SprStudents;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class StudentsOfTeacher implements Initializable {

    private ObservableList<StudentModel> listStudents = FXCollections.observableArrayList();
    private ObservableList<StudentModel> sortStudents = FXCollections.observableArrayList();
    private ObservableList<ReatingModel> reatingModels = FXCollections.observableArrayList();
    private static String typeCertification;

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private TextField txt_serchStudent;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private TableView<StudentModel> tb_students;

    @FXML
    private TableColumn<StudentModel, Integer> col_NumberStudent;

    @FXML
    private TableColumn<StudentModel, String> col_FullName;

    @FXML
    private TableView<ReatingModel> tb_achievement;

    @FXML
    private TableColumn<ReatingModel, Integer> col_NumberSubject;

    @FXML
    private TableColumn<ReatingModel, String> col_Subject;

    @FXML
    private TableColumn<ReatingModel, String> col_typeCertification;

    @FXML
    private TableColumn<ReatingModel, String> col_Mark;

    @FXML
    private TableView<ReatingModel> tb_achievementDiplom;

    @FXML
    private TableColumn<ReatingModel, Integer> col_NumberSubjectDiplom;

    @FXML
    private TableColumn<ReatingModel, String> col_SubjectDiplom;

    @FXML
    private TableColumn<ReatingModel, String> col_typeCertificationDiplom;

    @FXML
    private TableColumn<ReatingModel, String> col_MarkDiplom;

    @FXML
    private TableColumn<ReatingModel, String> col_thesisDiplom;

    @FXML
    private TableView<ReatingModel> tb_achievementCourseWork;

    @FXML
    private TableColumn<ReatingModel, Integer> col_NumberSubjectCourseWork;

    @FXML
    private TableColumn<ReatingModel, String> col_SubjectCourseWork;

    @FXML
    private TableColumn<ReatingModel, String> col_typeCertificationCourseWork;

    @FXML
    private TableColumn<ReatingModel, String> col_MarkCourseWork;

    @FXML
    private TableColumn<ReatingModel, String> col_plasePractickCourseWork;

    @FXML
    private TableColumn<ReatingModel, String> col_fullNameBossCourseWork;

    @FXML
    void handleCommit(ActionEvent event) {

    }

    @FXML
    void handleRefresh(ActionEvent event) {

    }

    @FXML
    void handleRollBack(ActionEvent event) {

    }

    @FXML
    void serchStudent(ActionEvent event) {

    }

    @FXML
    void handleSelectStudent(MouseEvent event) {
        StudentModel studentModel = tb_students.getSelectionModel().getSelectedItem();
        if (studentModel != null) {
            loadReating(studentModel.getId());
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadStudents();
        initDrawer();
    }

    private void initDrawer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/toolbar.fxml"));
            VBox toolbar = loader.load();
            drawer.setSidePane(toolbar);

        } catch (IOException ex) {
            Logger.getLogger(StudentsOfTeacher.class.getName()).log(Level.SEVERE, null, ex);
        }
        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1.5);
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

    private void initCol() {
        col_NumberStudent.setCellValueFactory(new PropertyValueFactory<>("numberStudent"));
        col_FullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        col_Mark.setCellValueFactory(new PropertyValueFactory<>("mark"));
        col_MarkCourseWork.setCellValueFactory(new PropertyValueFactory<>("mark"));
        col_MarkDiplom.setCellValueFactory(new PropertyValueFactory<>("mark"));
        col_NumberSubject.setCellValueFactory(new PropertyValueFactory<>("numberSubject"));
        col_NumberSubjectCourseWork.setCellValueFactory(new PropertyValueFactory<>("numberSubject"));
        col_NumberSubjectDiplom.setCellValueFactory(new PropertyValueFactory<>("numberSubject"));
        col_Subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        col_SubjectCourseWork.setCellValueFactory(new PropertyValueFactory<>("subject"));
        col_SubjectDiplom.setCellValueFactory(new PropertyValueFactory<>("subject"));
        col_fullNameBossCourseWork.setCellValueFactory(new PropertyValueFactory<>("fullNameBoss"));
        col_plasePractickCourseWork.setCellValueFactory(new PropertyValueFactory<>("placePractic"));
        col_thesisDiplom.setCellValueFactory(new PropertyValueFactory<>("thesis"));
        col_typeCertification.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_typeCertificationCourseWork.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_typeCertificationDiplom.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    private void loadStudents() {
        listStudents.clear();
        int amount = 1;
        for (SprStudents student : StudentDB.getInstance().getStudentsByTeacherId(LoginController.getUserId())) {
            StudentModel studentModel = new StudentModel();
            studentModel.setFullName(student.getSurname() + " " + student.getName() + " " + student.getMidleName());
            studentModel.setNumberStudent(amount);
            studentModel.setId(student.getStudentid());
            amount++;
            listStudents.add(studentModel);
        }
        tb_students.setItems(listStudents);
    }

    private void loadReating(int studentId) {
        reatingModels.clear();
       
        List<SprReating> reatings = ReatingDB.getInstance().getReatingsByStudentId(studentId);
     
        try {
            typeCertification = reatings.get(0).getType();
        } catch (Exception e) {
            reatingModels.clear();
            return;
        }
        int amount = 1;
        for (SprReating reating : reatings) {
            ReatingModel reatingModel = new ReatingModel();
            reatingModel.setNumberSubject(amount);
            checkIsNullMark(reating, reatingModel);
            checkIsNullThesis(reating, reatingModel);
            checkIsNullFullNameBoss(reating, reatingModel);
            checkIsNullPlacePractice(reating, reatingModel);
            reatingModel.setSubject(reating.getNameSubject());
            reatingModel.setType(reating.getType());
            reatingModels.add(reatingModel);
            amount++;
        }
        tb_achievement.setItems(reatingModels);
        tb_achievement.setVisible(true);
    }

    private void checkTypeTable() {
        if (typeCertification.equals("Дипломная работа")) {
            tb_achievementDiplom.setItems(reatingModels);
            tb_achievementDiplom.setVisible(true);
        } else if (typeCertification.equals("Курсовая работа")) {
            tb_achievementCourseWork.setItems(reatingModels);
            tb_achievementCourseWork.setVisible(true);
        } else {
            tb_achievement.setItems(reatingModels);
            tb_achievement.setVisible(true);
        }
    }

    private void checkIsNullMark(SprReating reating, ReatingModel model) {
        if (reating.getMark() == null) {
            model.setMark("");
        } else {
            if (reating.getType().equals("Зачет")) {
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

    @FXML
    void handleTest(KeyEvent event) {
        if (event.getCode() == KeyCode.DOWN) {
            int index = tb_students.getSelectionModel().getSelectedIndex();
            if (index != listStudents.size()) {
                StudentModel model = listStudents.get(index + 1);
                loadReating(model.getId());
            }

        }
        if (event.getCode() == KeyCode.UP) {
            int index = tb_students.getSelectionModel().getSelectedIndex();
            if (index != 0) {
                StudentModel model = listStudents.get(index - 1);
                loadReating(model.getId());
            }
        }
    }

    public static class StudentModel {

        private SimpleIntegerProperty numberStudent;
        private SimpleStringProperty fullName;
        private SimpleIntegerProperty id;

        public StudentModel() {
        }

        public Integer getNumberStudent() {
            return numberStudent.get();
        }

        public String getFullName() {
            return fullName.get();
        }

        public Integer getId() {

            return id.get();

        }

        public void setNumberStudent(Integer numberStudent) {
            this.numberStudent = new SimpleIntegerProperty(numberStudent);
        }

        public void setFullName(String fullName) {
            this.fullName = new SimpleStringProperty(fullName);
        }

        public void setId(Integer id) {
            this.id = new SimpleIntegerProperty(id);
        }

        @Override
        public String toString() {
            return "StudentModelTable{" + "fullName=" + fullName + '}';
        }
    }

    public static class ReatingModel {

        private SimpleIntegerProperty numberSubject;
        private SimpleStringProperty fullNameBoss;
        private SimpleStringProperty placePractic;
        private SimpleStringProperty thesis;
        private SimpleStringProperty mark;
        private SimpleStringProperty type;
        private SimpleStringProperty subject;
        private int studentId;
        private int reatingId;
        private String firstName;
        private String secondName;
        private String midleName;
        private int statementId;

        public ReatingModel() {
        }

        public Integer getNumberSubject() {
            return numberSubject.get();
        }

        public void setNumberSubject(Integer numberSubject) {
            this.numberSubject = new SimpleIntegerProperty(numberSubject);
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

        public void setType(String type) {
            this.type = new SimpleStringProperty(type);

        }

        public String getType() {
            return this.type.get();
        }

        public void setSubject(String subject) {
            this.subject = new SimpleStringProperty(subject);

        }

        public String getSubject() {
            return this.subject.get();
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

    }

}
