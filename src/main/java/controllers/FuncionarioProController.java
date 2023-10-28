package controllers;

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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import model.FuncionarioAdministrativo;
import model.Login.AdministradorSistemas;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class FuncionarioProController implements Initializable {


    //Declaraciones
    @Getter
    private static FuncionarioProController controladorPrimario;
    @Getter
    private FuncionarioAdministrativo funcionarioAdministrativo;

    @Getter
    private AdministradorSistemas administradorSistemas;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button bttmRegistroEntrada;
    @FXML
    private Button bttmBuscarPaciente;
    @FXML
    private Button bttmEstadistica;
    @FXML
    private Button bttnNomina;
    @FXML
    private Button bttmCerrarSesion;
    @FXML
    private Pane paneTrasero;


    /**
     * Inicializa el controlador al cargar la vista relacionada con el funcionario.
     *
     * @param url           La URL de la ubicación de la vista.
     * @param resourceBundle El conjunto de recursos asociados con la vista.
     */
    //Inicializador
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controladorPrimario = this;

        bttnNomina.setVisible(false);

        cargarEscena("/views/FuncionarioViews/RegistroEntrada.fxml");

        System.out.println(bttmRegistroEntrada.getBoundsInParent());
        System.out.println(bttmBuscarPaciente.getBoundsInParent());
        System.out.println(bttmEstadistica.getBoundsInParent());
        System.out.println(bttnNomina.getBoundsInParent());
    }


    //ActionEvents
    /**
     * Maneja la acción de carga de la vista de registro de entrada.
     *
     * @param event El evento de acción que desencadena la carga de la vista.
     */
    @FXML
    void RegistroEntradadaAction(ActionEvent event) {
        cargarEscena("/views/FuncionarioViews/RegistroEntrada.fxml");
    }

    /**
     * Maneja la acción de carga de la vista de búsqueda de pacientes.
     *
     * @param event El evento de acción que desencadena la carga de la vista.
     */
    @FXML
    void BuscarPaciente(ActionEvent event) {
        cargarEscena("/views/FuncionarioViews/BuscarPaciente.fxml");
    }

    /**
     * Maneja la acción de carga de la vista de visualización de estadísticas.
     *
     * @param event El evento de acción que desencadena la carga de la vista.
     */
    @FXML
    void EstadisticasAction(ActionEvent event) {
        cargarEscena("/views/FuncionarioViews/Estadisticas.fxml");
    }

    /**
     * Maneja la acción de carga de la vista nóminas.
     *
     * @param event El evento de acción que desencadena la carga de la vista.
     */
    @FXML
    void NominaAction(ActionEvent event) {
        cargarEscena("/views/SistemasViews/Sistemas.fxml");
    }

    /**
     * Maneja la acción de cierre de sesión del funcionario. Muestra una confirmación al usuario
     * y, si el usuario confirma, vuelve a la escena de inicio de sesión.
     *
     * @param event El evento de acción que desencadena el cierre de sesión.
     * @throws IOException Si ocurre un error al cargar la escena de inicio de sesión.
     */
    @FXML
    void CerrarSesion(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setContentText("¿Estás seguro de que deseas cerrar sesión?");
        Optional<ButtonType> resultado = alert.showAndWait();
        if(resultado.get() == ButtonType.OK)SwitchToLoginScene(event);
    }

    //Scene Switches - Cambios de Escena
    private void SwitchToLoginScene(ActionEvent event) throws IOException {
        //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/Login.fxml")));
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Carga una nueva escena en el panel trasero de la vista principal, reemplazando la escena actual.
     *
     * @param fxmlResource La ruta al archivo FXML de la escena que se va a cargar.
     */
    public void cargarEscena(String fxmlResource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlResource));
            Node escenaNode = loader.load();
            paneTrasero.getChildren().clear();
            paneTrasero.getChildren().add(escenaNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //validaciones
    /**
     * Recibe y almacena información del tipo de funcionario como un Funcionario Administrativo.
     *
     * @param funcionario El objeto FuncionarioAdministrativo que se va a almacenar.
     */
    public void recibirDatos(FuncionarioAdministrativo funcionario){

        funcionarioAdministrativo = funcionario;
    }

    /**
     * Recibe y almacena información del tipo de funcionario como un Administrador de Sistemas.
     * Además, hace visibles ciertos botones relacionados con las funciones de administrador.
     *
     * @param funcionario El objeto AdministradorSistemas que se va a almacenar.
     */
    public void recibirDatos(AdministradorSistemas funcionario){
        cargarEscena("/views/FuncionarioViews/Estadisticas.fxml");
        administradorSistemas = funcionario;

        bttmRegistroEntrada.setVisible(false);
        bttmBuscarPaciente.setVisible(false);

        bttmBuscarPaciente.setLayoutY(162);
        bttmEstadistica.setLayoutY(2);
        bttmRegistroEntrada.setLayoutY(82);
        bttnNomina.setLayoutY(42);
        bttnNomina.setVisible(true);

    }

}
