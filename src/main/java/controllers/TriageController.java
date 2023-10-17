package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.EnumeracionesVariablesTriage.*;
import model.Medico;
import model.Paciente;

import java.net.URL;
import java.util.ResourceBundle;
public class TriageController {
    private Paciente paciente;
    @FXML
    private ComboBox<Conciencia> concienciaComboBox;
    @FXML
    private ComboBox<DolorAbdominal> dolorAbdominalComboBox;
    @FXML
    private ComboBox<DolorPecho> dolorPechoComboBox;
    @FXML
    private ComboBox<Edad> edadComboBox;
    @FXML
    private ComboBox<EstadoMental> estadoMentalComboBox;
    @FXML
    private ComboBox<Fiebre> fiebreComboBox;
    @FXML
    private ComboBox<LecionesGraves> lesionGraveComboBox;
    @FXML
    private ComboBox<LesionLeve> lesionLeveComboBox;
    @FXML
    private ComboBox<Pulso> pulsoComboBox;
    @FXML
    private ComboBox<Respiracion> respiracionComboBox;
    @FXML
    private ComboBox<Sangrado> sangradoComboBox;
    @FXML
    private ComboBox<SignoShock> signoShockComboBox;
    @FXML
    private ComboBox<Vomitos> vomitosComboBox;

    @FXML
    public void initialize() {
        concienciaComboBox.getItems().addAll(Conciencia.values());
        dolorAbdominalComboBox.getItems().addAll(DolorAbdominal.values());
        dolorPechoComboBox.getItems().addAll(DolorPecho.values());
        edadComboBox.getItems().addAll(Edad.values());
        estadoMentalComboBox.getItems().addAll(EstadoMental.values());
        fiebreComboBox.getItems().addAll(Fiebre.values());
        lesionGraveComboBox.getItems().addAll(LecionesGraves.values());
        lesionLeveComboBox.getItems().addAll(LesionLeve.values());
        pulsoComboBox.getItems().addAll(Pulso.values());
        respiracionComboBox.getItems().addAll(Respiracion.values());
        sangradoComboBox.getItems().addAll(Sangrado.values());
        signoShockComboBox.getItems().addAll(SignoShock.values());
        vomitosComboBox.getItems().addAll(Vomitos.values());
    }

    @FXML
    public void recibirDatos(Paciente persona) {
        //Método para recibir el paciente de la escena anterior.
        this.paciente = persona;
    }

    public void handleAtrasButtonAction(ActionEvent event) throws Exception {
        // Obtiene la vista inicial
        Parent root = FXMLLoader.load(getClass().getResource("/views/MedicoViews/Medico.fxml"));
        // Cambia a la escena
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println(paciente.toString());
    }

    public void handleCalcularColorButtonAction(ActionEvent event) throws Exception {
    }

    public void handleModificarColorButtonAction(ActionEvent event) throws Exception {

    }

    public void handleConfirmarTriageButtonAction(ActionEvent event) throws Exception {

    }
}
