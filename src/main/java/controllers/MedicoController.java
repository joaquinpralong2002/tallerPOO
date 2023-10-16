package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Enum.ColorTriage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MedicoController {
    @FXML
    private TableView tblPacientes;
    @FXML
    private TextField txtNombPac;
    @FXML
    private ComboBox cmboxTriage;
    @FXML
    private TextField txtApellPac;
    @FXML
    private TextField txtDNIPac;
    @FXML
    private Button bttmRealTriage;

    @FXML
    private Button bttmAtender;

    @FXML
    private Button bttmCerrarSesion;


    @FXML
    public void initialize(){
        cmboxTriage.getItems().addAll(ColorTriage.values());
    }


    public void RealizarTriage(ActionEvent event) throws Exception {
        // Realiza el triage del paciente
        Parent root = FXMLLoader.load(getClass().getResource("/views/MedicoViews/Triage.fxml"));

        // Cambia a la nueva escena
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void AtenderPaciente(ActionEvent event) throws Exception{
        // Atiende al paciente
        Parent root = FXMLLoader.load(getClass().getResource("/views/MedicoViews/AtenderPaciente.fxml"));

        // Cambia a la nueva escena
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void CerrarSesion(ActionEvent event) throws Exception {
        // Cierra la sesión del médico
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setContentText("¿Estás seguro de que deseas cerrar sesión?");
        Optional<ButtonType> resultado = alert.showAndWait();

        if(resultado.get() == ButtonType.OK){
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

}
