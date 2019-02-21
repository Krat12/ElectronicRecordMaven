package com.mycompany.javafx.electronicrecord.utill;

import com.jfoenix.controls.JFXButton;
import com.mycompany.javafx.electronicrecord.controllers.AlertConfrimController;
import com.mycompany.javafx.electronicrecord.controllers.AlertErrorController;
import com.mycompany.javafx.electronicrecord.controllers.MarkListController;
import com.mycompany.javafx.electronicrecord.controllers.StudentsListController;
import com.mycompany.javafx.electronicrecord.model.Student;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ElectronicRecordUtill {

    public static final String ICON_IMAGE_LOC = "/image/round-button-blue-glossy-download-png-93250.png";
    private static double xOffset = 0.0;
    private static double yOffset = 0.0;

    public static void setStageIcon(Stage stage) {
        stage.getIcons().add(new Image(ICON_IMAGE_LOC));
    }

    public static Object loadWindow(URL loc, String title, Stage parentStage) {
        Object controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = loader.load();
            controller = loader.getController();
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
            setStageIcon(stage);
        } catch (IOException ex) {
            Logger.getLogger(ElectronicRecordUtill.class.getName()).log(Level.SEVERE, null, ex);
        }
        return controller;
    }

    public static Object loadWindow(URL loc, String title, Stage parentStage, int width, int height) {
        Object controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = loader.load();
            controller = loader.getController();
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setTitle(title);
            stage.setMinWidth(width);
            stage.setMinHeight(height);
            stage.setScene(new Scene(parent));
            stage.show();
            setStageIcon(stage);
        } catch (IOException ex) {
            Logger.getLogger(ElectronicRecordUtill.class.getName()).log(Level.SEVERE, null, ex);
        }
        return controller;
    }

    public static Object loadWindowModality(URL loc, String title, Stage parentStage) {
        Object controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = loader.load();
            controller = loader.getController();
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(parent));
            stage.show();
            setStageIcon(stage);
        } catch (IOException ex) {
            Logger.getLogger(ElectronicRecordUtill.class.getName()).log(Level.SEVERE, null, ex);
        }
        return controller;
    }

    public static Object loadWindow(URL loc, String title, Stage parentStage, boolean resizable) {
        Object controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = loader.load();
            controller = loader.getController();
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setTitle(title);
            if (resizable) {
                stage.setResizable(true);
            }
            stage.setScene(new Scene(parent));
            stage.show();
            setStageIcon(stage);
        } catch (IOException ex) {
            Logger.getLogger(ElectronicRecordUtill.class.getName()).log(Level.SEVERE, null, ex);
        }
        return controller;
    }

    public static List<Student> parseCSVWithHeader(String URL) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(URL), ';');

        HeaderColumnNameMappingStrategy<Student> beanStrategy = new HeaderColumnNameMappingStrategy<Student>();

        beanStrategy.setType(Student.class);

        CsvToBean<Student> csvToBean = new CsvToBean<Student>();
        List<Student> students = csvToBean.parse(beanStrategy, reader);

        reader.close();

        return students;
    }

    public static String initCSVImport(Stage stage) {
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("CSV (разделители - точка с запятой) (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            return file.getAbsolutePath();
        }

        return "";

    }

    public static String initCSVExport(Stage stage) {
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("CSV (разделители - точка с запятой) (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            return file.getAbsolutePath();
        }

        return "";

    }

    public static void openFileWithDesktop(File file) {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(ElectronicRecordUtill.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void loadAlertConfrim(URL loc, Stage parentStage, String title, String content) {
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = loader.load();
            AlertConfrimController acc = loader.getController();
            acc.setField(title, content);
            loadWindowAlert(parent, parentStage);
        } catch (IOException ex) {
            Logger.getLogger(ElectronicRecordUtill.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void loadAlertError(URL loc, Stage parentStage, String title, String content) {
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = loader.load();
            AlertErrorController acc = loader.getController();
            acc.setField(title, content);
            loadWindowAlert(parent, parentStage);
        } catch (IOException ex) {
            Logger.getLogger(ElectronicRecordUtill.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void loadWindowAlert(Parent parent, Stage parentStage) {
        Stage stage = new Stage(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(parent);
        cordinateScene(scene, stage);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();
        // setStageIcon(stage);
    }

    private static void cordinateScene(Scene scene, Stage stage) {
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
    }

    public static void closeStage(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public static void exportStudentCSV(String url, List<StudentsListController.StudentModelTable> students) throws IOException {
        FileWriter writer = new FileWriter(url);
        CSVUtils.writeLine(writer, Arrays.asList("ФИО", "Номер зачетки"), ';');

        for (StudentsListController.StudentModelTable d : students) {

            List<String> list = new ArrayList<>();
            list.add(d.getFullName());
            list.add(String.valueOf(d.getNumberRecord()));
            CSVUtils.writeLine(writer, list, ';');
        }

        writer.flush();
        writer.close();
    }

    public static void exportMarstListCSV(String url, List<MarkListController.ReatingModel> rms, String type) throws IOException {
        FileWriter writer = new FileWriter(url);
        if (type.equals("Зачет") || type.equals("Дифференцированный зачет")) {
            CSVUtils.writeLine(writer, Arrays.asList("№","ФИО", "Оценка"), ';');
        }
        if (type.equals("Дипломная работа")) {
            CSVUtils.writeLine(writer, Arrays.asList("№","ФИО", "Оценка","Тема диплома"), ';');
        }
        if (type.equals("Курсовая работа")) {
            CSVUtils.writeLine(writer, Arrays.asList("№","ФИО", "Оценка","Место прохождение практике","Руководитель"), ';');
        }

        for (MarkListController.ReatingModel reatingModel : rms) {

            List<String> list = new ArrayList<>();
            list.add(String.valueOf(reatingModel.getNumberStudent()));
            list.add(reatingModel.getFullName());
            list.add(reatingModel.getMark());
            if (type.equals("Дипломная работа")) {
                list.add(reatingModel.getThesis());
            }
            if (type.equals("Курсовая работа")) {
                list.add(reatingModel.getPlacePractic());
                list.add(reatingModel.getFullNameBoss());
            }

            CSVUtils.writeLine(writer, list, ';');
        }

        writer.flush();
        writer.close();
    }

    public static void setColorCommitAndRollBack(FontAwesomeIconView commit, FontAwesomeIconView rollback, JFXButton btn_commit, JFXButton btn_rollback) {
        commit.setStyle("-fx-fill:#70ff7e");
        rollback.setStyle("-fx-fill:#ff6161");

        btn_commit.setStyle("-fx-text-fill:#70ff7e");
        btn_rollback.setStyle("-fx-text-fill:#ff6161");

        commit.setOpacity(1);
        rollback.setOpacity(1);
    }

    public static void resetColorCommitAndRollBack(FontAwesomeIconView commit, FontAwesomeIconView rollback, JFXButton btn_commit, JFXButton btn_rollback) {
        commit.setStyle("-fx-fill:#FFFF");
        rollback.setStyle("-fx-fill:#FFFF");
        btn_commit.setStyle("-fx-text-fill:white");
        btn_rollback.setStyle("-fx-text-fill:white");
    }

}
