package controllers;

import controllers.Enfermero.EnfermeroController;
import controllers.Enfermero.TriageEnfermero.DatosTriageEnfermero;
import controllers.Enfermero.TriageEnfermero.TriageEnfermeroController;
import controllers.Singletons.SingletonControladorPrimarioSalud;
import controllers.Singletons.SingletonEnfermero;
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
    @Setter
    @Getter
    private DatosTriageEnfermero datosTriageEnfermero;
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


    /**
     * Cambia a la escena de inicio de sesión.
     *
     * @param event El evento que desencadenó el cambio de escena.
     * @throws IOException Si ocurre un error al cargar la escena de inicio de sesión.
     */
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

    /**
     * Carga una nueva escena desde un archivo FXML en el panel trasero del controlador actual.
     *
     * @param fxmlResource La ubicación del recurso FXML que define la nueva escena.
     */
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


    /**
     * Carga una nueva escena desde un archivo FXML en el panel trasero del controlador actual.
     *
     * @param fxmlResource        La ubicación del recurso FXML que define la nueva escena.
     * @param restaurarDatosTriage Un indicador booleano que determina si se deben restaurar datos relacionados con el triage.
     *                            Si es verdadero, se intentará restaurar el estado de triage; de lo contrario, se restaurará el estado de triage de enfermero.
     */
    public void cargarEscena(String fxmlResource, boolean restaurarDatosTriage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlResource));

            // Verificar si el controlador secundario tiene un método setControladorPrincipal
            // y, si es así, asignar la referencia al controlador principal

            Node escenaNode = loader.load();
            if(restaurarDatosTriage) {
                TriageController triageController = loader.getController();
                triageController.restaurarEstado();
            } else {
                TriageEnfermeroController triageEnfermeroController = loader.getController();
                triageEnfermeroController.restaurarEstado();
            }
            paneTrasero.getChildren().clear();
            paneTrasero.getChildren().add(escenaNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inicia los datos del controlador Medico y carga la escena principal del médico.
     *
     * @throws IOException Si ocurre un error durante la carga de la escena.
     */
    public void iniciarDatosMedico() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MedicoViews/Medico.fxml"));
        Parent root = loader.load();
        MedicoController controller = loader.getController();
        controller.iniciarMedico();
        cargarEscena("/views/MedicoViews/Medico.fxml");
    }

    /**
     * Inicia los datos del controlador Enfermero y carga la escena principal del enfermero.
     *
     * @throws IOException Si ocurre un error durante la carga de la escena.
     */
    public void iniciarDatosEnfermero() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/EnfermeroViews/Enfermero.fxml"));
        Parent root = loader.load();
        EnfermeroController controller = loader.getController();
        controller.iniciarEnfermero();
        cargarEscena("/views/EnfermeroViews/Enfermero.fxml");
    }

    /**
     * Inicia la sesión, redirigiendo al usuario al panel correspondiente basado en su rol.
     *
     * @param event El evento que desencadenó esta acción.
     * @throws IOException Si ocurre un error durante la carga de la escena.
     */
    public void Inicio(javafx.event.ActionEvent event) throws IOException {
        if(SingletonMedico.getInstance().getMedico() == null) iniciarDatosEnfermero();
        else iniciarDatosMedico();
    }

    /**
     * Abre la vista del historial clínico del paciente según el rol del usuario.
     *
     * @param event El evento que desencadenó esta acción.
     */
    public void HistorialClinico(javafx.event.ActionEvent event) {
        if(SingletonMedico.getInstance().getMedico() != null) cargarEscena("/views/MedicoViews/BuscarPacienteVisualizarRegistros.fxml");
        else cargarEscena("/views/EnfermeroViews/BuscarPacienteVisualizarRegistrosEnfermero.fxml");
    }

    /**
     * Cierra la sesión actual y muestra una confirmación al usuario.
     *
     * @param event El evento que desencadenó esta acción.
     * @throws IOException Si ocurre un error durante la carga de la escena de inicio de sesión.
     */
    public void CerrarSesion(javafx.event.ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setContentText("¿Estás seguro de que deseas cerrar sesión?");
        Optional<ButtonType> resultado = alert.showAndWait();

        SingletonMedico.getInstance().setMedico(null);
        SingletonEnfermero.getInstance().setEnfermero(null);
        if(resultado.get() == ButtonType.OK) {
            SwitchToLoginScene(event);
        }
    }
}
