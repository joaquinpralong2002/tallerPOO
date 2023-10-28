package controllers;

import controllers.Enfermero.EnfermeroController;
import controllers.Singletons.SingletonControladorPrimarioSalud;
import controllers.Singletons.SingletonMedico;
import controllers.Triage.DatosTriage;
import controllers.Triage.TriageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import model.Enfermero;
import model.Enum.ColorTriage;
import model.Enum.LugarAtencion;
import model.Medico;
import model.Paciente;
import model.RegistroEntrada;
import model.Triage;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SaludController {

    @Getter
    private static SaludController controladorPrimario;

    @Setter
    @Getter
    private Paciente paciente;
    @Setter
    @Getter
    private RegistroEntrada registroEntrada;
    @Setter
    @Getter
    private ColorTriage colorTriage;
    @Setter
    @Getter
    private DatosTriage datosTriage;
    private Enfermero enfermero;
    private Medico medico;
    @Setter
    @Getter
    private LugarAtencion lugarAtencion;
    private Stage stage;
    private Scene scene;

    @FXML
    private Pane paneTrasero;
    @FXML
    private Button historialClinicoButton;
    @FXML
    private Button inicioButton;
    @FXML
    private Button cerrarSesionButton;



    private void SwitchToLoginScene(ActionEvent event) throws IOException {
        //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/Login.fxml")));
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controladorPrimario = SingletonControladorPrimarioSalud.getInstance().getController();
    }

    public void cargarEscena(String fxmlResource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlResource));

            // Verificar si el controlador secundario tiene un método setControladorPrincipal
            // y, si es así, asignar la referencia al controlador principal

            Node escenaNode = loader.load();

            paneTrasero.getChildren().clear();
            paneTrasero.getChildren().add(escenaNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarEscena(String fxmlResource, boolean restaurarDatosTriage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlResource));

            // Verificar si el controlador secundario tiene un método setControladorPrincipal
            // y, si es así, asignar la referencia al controlador principal

            Node escenaNode = loader.load();
            TriageController triageController = loader.getController();
            triageController.restaurarEstado();
            paneTrasero.getChildren().clear();
            paneTrasero.getChildren().add(escenaNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void iniciarDatosMedico() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MedicoViews/Medico.fxml"));
        Parent root = loader.load();
        MedicoController controller = loader.getController();
        controller.iniciarMedico();
        cargarEscena("/views/MedicoViews/Medico.fxml");
    }

    public void iniciarDatosEnfermero() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/EnfermeroViews/Enfermero.fxml"));
        Parent root = loader.load();
        EnfermeroController controller = loader.getController();
        controller.iniciarEnfermero();
        cargarEscena("/views/EnfermeroViews/Enfermero.fxml");
    }

    public void Inicio(javafx.event.ActionEvent event) throws IOException {
        if(SingletonMedico.getInstance().getMedico() == null) iniciarDatosEnfermero();
        else iniciarDatosMedico();
    }

    public void HistorialClinico(javafx.event.ActionEvent event) {
        cargarEscena("/views/MedicoViews/BuscarPacienteVisualizarRegistros.fxml");
    }

    public void CerrarSesion(javafx.event.ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setContentText("¿Estás seguro de que deseas cerrar sesión?");
        Optional<ButtonType> resultado = alert.showAndWait();
        if(resultado.get() == ButtonType.OK)SwitchToLoginScene(event);
    }
}
