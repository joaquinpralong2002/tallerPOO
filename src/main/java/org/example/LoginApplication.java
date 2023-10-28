package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.GlobalSessionFactory;

public class LoginApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GlobalSessionFactory init = new GlobalSessionFactory();
        init.InitGlobalSessionFactory();
        Image icono = new Image(getClass().getResourceAsStream("/images/signo-de-hospital.png"));

        primaryStage.setResizable(false);
        primaryStage.getIcons().add(icono);
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add((getClass().getResource("/css/style.css").toExternalForm()));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
