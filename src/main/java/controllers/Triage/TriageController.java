package controllers.Triage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Enum.ColorTriage;
import model.EnumeracionesVariablesTriage.*;
import model.Medico;
import model.RegistroEntrada;
import model.Triage;

import java.time.LocalDate;
import java.time.Period;

public class TriageController {
    private ColorTriage colorTriageAsignado = ColorTriage.Ninguno;
    private RegistroEntrada registroEntrada;
    private Medico medico;
    private int pulsoCardiaco;
    private int edadAños;
    private float temperatura;
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
        colorRecomendadoLabel.setText(ColorTriage.Ninguno.toString());
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
        this.pulsoCardiaco = pulsoCardiaco;
        EstadoMental estadoMental = estadoMentalComboBox.getSelectionModel().getSelectedItem();
        Conciencia conciencia = concienciaComboBox.getSelectionModel().getSelectedItem();
        DolorPecho dolorPecho = dolorPechoComboBox.getSelectionModel().getSelectedItem();
        LecionesGraves lecionesGraves = lesionGraveComboBox.getSelectionModel().getSelectedItem();
        Edad edad = calcularEdadEnum();
        int edadAños = calcularEdad();
        this.edadAños = edadAños;
        float temperatura = Float.parseFloat(temperaturaTextField.getText());
        this.temperatura = temperatura;
        Fiebre fiebre = calcularFiebre(temperatura);
        Vomitos vomitos = vomitosComboBox.getSelectionModel().getSelectedItem();
        DolorAbdominal dolorAbdominal = dolorAbdominalComboBox.getSelectionModel().getSelectedItem();
        SignoShock signoShock = signoShockComboBox.getSelectionModel().getSelectedItem();
        LesionLeve lesionLeve = lesionLeveComboBox.getSelectionModel().getSelectedItem();
        Sangrado sangrado = sangradoComboBox.getSelectionModel().getSelectedItem();

        ColorTriage colorRecomendado = Triage.calcularColorTriageRecomendado(respiracion, pulso, pulsoCardiaco, estadoMental, conciencia, dolorPecho, lecionesGraves,
                edad, edadAños, fiebre, temperatura, vomitos, dolorAbdominal, signoShock, lesionLeve, sangrado);
        colorRecomendadoLabel.setText(colorRecomendado.toString());
        this.colorTriageAsignado = colorRecomendado;
    }

    public void handleModificarColorButtonAction(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MedicoViews/Triage/ModificarTriage.fxml"));
        Parent root = loader.load();
        ModificarTriageController controller = loader.getController();
        if(this.colorTriageAsignado != ColorTriage.Ninguno){
            controller.recibirDatos(medico, registroEntrada, this.colorTriageAsignado);
            TriageSingleton triageSingleton = TriageSingleton.getInstance();
            triageSingleton.setRespiracion(respiracionComboBox.getSelectionModel().getSelectedItem());
            triageSingleton.setPulsoCardiaco(Integer.parseInt(pulsoTextField.getText()));
            triageSingleton.setPulso(calcularPulso(pulsoCardiaco));
            triageSingleton.setEstadoMental(estadoMentalComboBox.getSelectionModel().getSelectedItem());
            triageSingleton.setConciencia(concienciaComboBox.getSelectionModel().getSelectedItem());
            triageSingleton.setDolorPecho(dolorPechoComboBox.getSelectionModel().getSelectedItem());
            triageSingleton.setLecionesGraves(lesionGraveComboBox.getSelectionModel().getSelectedItem());
            triageSingleton.setEdad(calcularEdadEnum());
            triageSingleton.setEdadAños(edadAños);
            triageSingleton.setTemperatura(temperatura);
            triageSingleton.setFiebre(calcularFiebre(temperatura));
            triageSingleton.setVomitos(vomitosComboBox.getSelectionModel().getSelectedItem());
            triageSingleton.setDolorAbdominal(dolorAbdominalComboBox.getSelectionModel().getSelectedItem());
            triageSingleton.setSignoShock(signoShockComboBox.getSelectionModel().getSelectedItem());
            triageSingleton.setLesionLeve(lesionLeveComboBox.getSelectionModel().getSelectedItem());
            triageSingleton.setSangrado(sangradoComboBox.getSelectionModel().getSelectedItem());
            triageSingleton.setMedico(medico);
            triageSingleton.setColorTriageAsignado(colorTriageAsignado);
            triageSingleton.setRegistroEntrada(registroEntrada);
            // Cambia a la nueva escena
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("El paciente no tiene ningún color asignado.");
            alert.showAndWait();
            return;
        }
    }

    public void handleConfirmarTriageButtonAction(ActionEvent event) throws Exception {

    }

    public void restaurarEstado(){
        this.colorTriageAsignado = TriageSingleton.getInstance().getColorTriageAsignado();
        this.registroEntrada = TriageSingleton.getInstance().getRegistroEntrada();
        this.medico = TriageSingleton.getInstance().getMedico();
        this.concienciaComboBox.setValue(TriageSingleton.getInstance().getConciencia());
        this.dolorAbdominalComboBox.setValue(TriageSingleton.getInstance().getDolorAbdominal());
        this.dolorPechoComboBox.setValue(TriageSingleton.getInstance().getDolorPecho());
        this.estadoMentalComboBox.setValue(TriageSingleton.getInstance().getEstadoMental());
        this.lesionGraveComboBox.setValue(TriageSingleton.getInstance().getLecionesGraves());
        this.lesionLeveComboBox.setValue(TriageSingleton.getInstance().getLesionLeve());
        this.respiracionComboBox.setValue(TriageSingleton.getInstance().getRespiracion());
        this.sangradoComboBox.setValue(TriageSingleton.getInstance().getSangrado());
        this.signoShockComboBox.setValue(TriageSingleton.getInstance().getSignoShock());
        this.vomitosComboBox.setValue(TriageSingleton.getInstance().getVomitos());
        this.pulsoTextField.setText(Integer.toString(TriageSingleton.getInstance().getPulsoCardiaco()));
        this.temperaturaTextField.setText(Float.toString(TriageSingleton.getInstance().getTemperatura()));
        this.colorRecomendadoLabel.setText(TriageSingleton.getInstance().getColorTriageAsignado().toString());
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
