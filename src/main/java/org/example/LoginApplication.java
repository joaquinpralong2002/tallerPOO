package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.GlobalSessionFactory;

public class LoginApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        GlobalSessionFactory init = new GlobalSessionFactory();
        init.InitGlobalSessionFactory();
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
