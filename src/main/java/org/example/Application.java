package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import util.GlobalSessionFactory;

public class Application extends javafx.application.Application {
    private static String[] argumentos;

    public static void main(String[] args) {
        argumentos = args;
        Application.launch();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        CreacionDatos.iniciarCarga(argumentos);
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
