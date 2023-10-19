package controllers.Triage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Enum.ColorTriage;
import model.EnumeracionesVariablesTriage.*;
import model.Medico;
import model.Paciente;
import model.RegistroEntrada;
import model.Triage;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
public class TriageController {
    private ColorTriage colorTriageSistema;
    private RegistroEntrada registroEntrada;
    private Medico medico;
    @FXML
    private ComboBox<Conciencia> concienciaComboBox;
    @FXML
    private ComboBox<DolorAbdominal> dolorAbdominalComboBox;
    @FXML
    private ComboBox<DolorPecho> dolorPechoComboBox;
    @FXML
    private ComboBox<EstadoMental> estadoMentalComboBox;
    @FXML
    private ComboBox<LecionesGraves> lesionGraveComboBox;
    @FXML
    private ComboBox<LesionLeve> lesionLeveComboBox;
    @FXML
    private ComboBox<Respiracion> respiracionComboBox;
    @FXML
    private ComboBox<Sangrado> sangradoComboBox;
    @FXML
    private ComboBox<SignoShock> signoShockComboBox;
    @FXML
    private ComboBox<Vomitos> vomitosComboBox;
    @FXML
    private TextField pulsoTextField;
    @FXML
    private TextField temperaturaTextField;
    @FXML
    private Label colorRecomendadoLabel;

    public void initialize() {
        concienciaComboBox.getItems().addAll(Conciencia.values());
        dolorAbdominalComboBox.getItems().addAll(DolorAbdominal.values());
        dolorPechoComboBox.getItems().addAll(DolorPecho.values());
        estadoMentalComboBox.getItems().addAll(EstadoMental.values());
        lesionGraveComboBox.getItems().addAll(LecionesGraves.values());
        lesionLeveComboBox.getItems().addAll(LesionLeve.values());
        respiracionComboBox.getItems().addAll(Respiracion.values());
        sangradoComboBox.getItems().addAll(Sangrado.values());
        signoShockComboBox.getItems().addAll(SignoShock.values());
        vomitosComboBox.getItems().addAll(Vomitos.values());
    }

    @FXML
    public void recibirDatos(RegistroEntrada registroEntrada, Medico medico) {
        //Método para recibir el paciente de la escena anterior.
        this.registroEntrada = registroEntrada;
        this.medico = medico;
    }

    public void handleAtrasButtonAction(ActionEvent event) throws Exception {
        // Obtiene la vista inicial
        Parent root = FXMLLoader.load(getClass().getResource("/views/MedicoViews/Medico.fxml"));
        // Cambia a la escena
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleCalcularColorButtonAction(ActionEvent event) throws Exception {
        Respiracion respiracion = respiracionComboBox.getSelectionModel().getSelectedItem();
        int pulsoCardiaco = Integer.parseInt(pulsoTextField.getText());
        Pulso pulso = calcularPulso(pulsoCardiaco);
        EstadoMental estadoMental = estadoMentalComboBox.getSelectionModel().getSelectedItem();
        Conciencia conciencia = concienciaComboBox.getSelectionModel().getSelectedItem();
        DolorPecho dolorPecho = dolorPechoComboBox.getSelectionModel().getSelectedItem();
        LecionesGraves lecionesGraves = lesionGraveComboBox.getSelectionModel().getSelectedItem();
        Edad edad = calcularEdadEnum();
        int edadAños = calcularEdad();
        float temperatura = Float.parseFloat(temperaturaTextField.getText());
        Fiebre fiebre = calcularFiebre(temperatura);
        Vomitos vomitos = vomitosComboBox.getSelectionModel().getSelectedItem();
        DolorAbdominal dolorAbdominal = dolorAbdominalComboBox.getSelectionModel().getSelectedItem();
        SignoShock signoShock = signoShockComboBox.getSelectionModel().getSelectedItem();
        LesionLeve lesionLeve = lesionLeveComboBox.getSelectionModel().getSelectedItem();
        Sangrado sangrado = sangradoComboBox.getSelectionModel().getSelectedItem();

        ColorTriage colorRecomendado = Triage.calcularColorTriageRecomendado(respiracion, pulso, pulsoCardiaco, estadoMental, conciencia, dolorPecho, lecionesGraves,
                edad, edadAños, fiebre, temperatura, vomitos, dolorAbdominal, signoShock, lesionLeve, sangrado);
        colorRecomendadoLabel.setText(colorRecomendado.toString());
        this.colorTriageSistema = colorRecomendado;
    }

    public void handleModificarColorButtonAction(ActionEvent event) throws Exception {

    }

    public void handleConfirmarTriageButtonAction(ActionEvent event) throws Exception {

    }

    private Pulso calcularPulso(int valorPulso) {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaNacimiento = this.registroEntrada.getPaciente().getFechaNacimiento();
        Period periodo = Period.between(fechaActual, fechaNacimiento);
        int mesesEdad = periodo.getMonths();
        Pulso pulso = Pulso.Normal;

        if (mesesEdad <= 1) {
            if (valorPulso < 70 || valorPulso > 190) pulso = Pulso.Anormal;
        } else if (mesesEdad > 1 && mesesEdad <= 12) {
            if (valorPulso < 80 || valorPulso > 160) pulso = Pulso.Anormal;
        } else if (mesesEdad > 12 && mesesEdad <= 36) {
            if (valorPulso < 98 || valorPulso > 140) pulso = Pulso.Anormal;
        } else if (mesesEdad > 36 && mesesEdad <= 60) {
            if (valorPulso < 98 || valorPulso > 140) pulso = Pulso.Anormal;
        } else if (mesesEdad > 60 && mesesEdad <= 144) {
            if (valorPulso < 98 || valorPulso > 140) pulso = Pulso.Anormal;
        } else if (valorPulso < 60 || valorPulso > 100) pulso = Pulso.Anormal;
        return pulso;
    }

    private Edad calcularEdadEnum(){
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaNacimiento = this.registroEntrada.getPaciente().getFechaNacimiento();
        Period periodo = Period.between(fechaActual, fechaNacimiento);
        int añosEdad = periodo.getYears();
        Edad edad = Edad.Adulto;
        if(añosEdad <= 12 || añosEdad >= 60) edad = Edad.NinioAnciano;
        return edad;
    }

    private int calcularEdad(){
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaNacimiento = this.registroEntrada.getPaciente().getFechaNacimiento();
        Period periodo = Period.between(fechaActual, fechaNacimiento);
        int añosEdad = periodo.getYears();
        return añosEdad;
    }

    private Fiebre calcularFiebre(float temperatura){
        Fiebre fiebre = Fiebre.SinFiebre;
        int edad = calcularEdad();
        if(edad >= 0 && edad <= 12){
            if(temperatura > 37.2 && temperatura < 39.4) fiebre = Fiebre.Moderada;
            else if(temperatura >= 39.4) fiebre = Fiebre.Alta;
        } else if(edad > 12 && edad <= 65){
            if(temperatura > 37.2 && temperatura < 39.4) fiebre = Fiebre.Moderada;
            else if(temperatura >= 39.4) fiebre = Fiebre.Alta;
        } else {
            if(temperatura > 36.2 && temperatura < 39.4) fiebre = Fiebre.Moderada;
            else if(temperatura >= 39.4) fiebre = Fiebre.Alta;
        }
        return fiebre;
    }
}
