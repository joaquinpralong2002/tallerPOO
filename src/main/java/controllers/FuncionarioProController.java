package controllers;

import controllers.Administracion.RegistroEntradaController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Setter;
import model.Funcionario;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class FuncionarioProController implements Initializable {


    //Declaraciones

    @Setter
    private Funcionario funcionario;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button bttmBuscarPaciente;

    @FXML
    private Button bttmCerrarSesion;

    @FXML
    private Button bttmEstadistica;

    @FXML
    private Button bttmRegistroEntrada;

    @FXML
    private Button bttnAjustes;

    @FXML
    private Button bttnNomina;

    @FXML
    private Pane paneTrasero;



    //Inicializador
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/FuncionarioViews/RegistroEntrada.fxml"));
        RegistroEntradaController registroEntradaController = loader.getController();
        paneTrasero.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void AjustesAction(ActionEvent event) {

    }


    //ActionEvents
    @FXML
    void BuscarPaciente(ActionEvent event) {

    }

    @FXML
    void CerrarSesion(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setContentText("¿Estás seguro de que deseas cerrar sesión?");
        Optional<ButtonType> resultado = alert.showAndWait();
        if(resultado.get() == ButtonType.OK)SwitchToLoginScene(event);
    }

    @FXML
    void EstadisticasAction(ActionEvent event) {

    }

    @FXML
    void NominaAction(ActionEvent event) {

    }

    @FXML
    void RegistroEntradadaAction(ActionEvent event) {

    }

    //Scene Switches - Cambios de Escena
    public void SwitchToLoginScene(ActionEvent event) throws IOException {
        //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/Login.fxml")));
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
