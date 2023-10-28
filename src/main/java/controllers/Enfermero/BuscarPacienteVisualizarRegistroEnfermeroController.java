package controllers.Enfermero;

import controllers.HistorialClinicoController;
import controllers.MedicoController;
import controllers.Singletons.SingletonEnfermero;
import controllers.Singletons.SingletonMedico;
import datasource.PacienteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Enfermero;
import model.Login.Rol;
import model.Medico;
import model.Paciente;

import java.io.IOException;
import java.util.List;

public class BuscarPacienteVisualizarRegistroEnfermeroController {
    @FXML
    private TextField txtDni;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbApellido;
    @FXML
    private Label lbTelFijo;
    @FXML
    private Label lbTelCel;
    @FXML
    private Label lbTelCont;
    private Paciente paciente;
    private List<Rol> roles = SingletonEnfermero.getInstance().getRoles();
    private Enfermero enfermero = SingletonEnfermero.getInstance().getEnfermero();

    @FXML
    public void initialize(){
        this.lbNombre.setText("Sin Datos");
        this.lbApellido.setText("Sin Datos");
        this.lbTelFijo.setText("Sin Datos");
        this.lbTelCel.setText("Sin Datos");
        this.lbTelCont.setText("Sin Datos");
    }

    /**
     * Maneja la acción de búsqueda de un paciente utilizando el número de DNI ingresado.
     * Verifica la existencia del paciente en la base de datos y muestra sus datos en la vista si se encuentra.
     *
     * @param actionEvent El evento de acción que desencadena la búsqueda del paciente.
     */
    public void BuscarPaciente(ActionEvent actionEvent) {
        String dni = this.txtDni.getText();
        try {
            ComprobarDni(dni);
            PacienteDAO pacienteDAO = new PacienteDAO();
            this.paciente = pacienteDAO.obtenerPorDni(dni);
            if(paciente == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText("Paciente no encontrado");
                alert.setContentText("Revise los datos ingresados, y vuelva a llenar el campo");
                alert.show();
            }else{
                SetearLabels(this.lbNombre, paciente.getNombre());
                SetearLabels(this.lbApellido, paciente.getApellido());
                SetearLabels(this.lbTelFijo,String.valueOf(paciente.getTelefonoFijo()));
                SetearLabels(this.lbTelCel, String.valueOf(paciente.getTelefonoCelular()));
                SetearLabels(this.lbTelCont, String.valueOf(paciente.getPersonaContacto()));
            }
        }catch (IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Datos Invalidos");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    /**
     * Establece el texto de un objeto Label con un valor de cadena especificado, mostrando "Sin datos" si el valor es nulo.
     *
     * @param label El objeto Label al que se le establecerá el texto.
     * @param dato El valor de cadena que se mostrará en el Label o "Sin datos" si es nulo.
     */
    public void SetearLabels(Label label, String dato){
        if(dato == null){
            label.setText("Sin datos");
        }else{
            label.setText(dato);
        }
    }

    /**
     * Comprueba la validez del número de DNI ingresado, asegurando que sea un número entero.
     *
     * @param dni El número de DNI a verificar.
     * @throws IllegalArgumentException Si el número de DNI no es un número entero.
     */
    public void ComprobarDni(String dni){
        if (!dni.matches("\\d+")){
            throw new IllegalArgumentException("El DNI debe ser un número entero");
        }
    }

    /**
     * Maneja la acción de regreso a la vista principal del médico.
     *
     * @param event El evento de acción que desencadena el regreso.
     * @throws IOException Si ocurre un error al cargar la vista principal del médico.
     */
    public void Volver(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/EnfermeroViews/Enfermero.fxml"));
        Parent rootFuncionario = loader.load();
        EnfermeroController controller = loader.getController();

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(rootFuncionario);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Maneja la acción de visualización de los registros de historial clínico del paciente.
     * Carga la vista del historial clínico y envía los datos del paciente al controlador correspondiente.
     *
     * @param event El evento de acción que desencadena la visualización del historial clínico.
     * @throws IOException Si ocurre un error al cargar la vista del historial clínico.
     */
    public void verRegistros(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/EnfermeroViews/HistorialClinicoEnfermero.fxml"));
        Parent rootFuncionario = loader.load();
        HistorialClinicoEnfermeroController controller = loader.getController();
        controller.recibirDatos(this.paciente);

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(rootFuncionario);
        stage.setScene(scene);
        stage.show();
    }
}
