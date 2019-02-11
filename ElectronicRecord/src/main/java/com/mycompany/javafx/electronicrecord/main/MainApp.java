package com.mycompany.javafx.electronicrecord.main;

import com.mycompany.javafx.electronicrecord.utill.ElectronicRecordUtill;
import com.mycompany.javafx.electronicrecord.utill.HibernateSessionFactoryUtill;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {    
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/GroupList.fxml"));
        ElectronicRecordUtill.setStageIcon(stage);
        HibernateSessionFactoryUtill.getSessionFactory();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinHeight(310);
        stage.setMinWidth(275);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        launch(args);
    }

}
