package controllers.Administracion;

import controllers.FuncionarioProController;
import datasource.PacienteDAO;
import datasource.RegistroEntradaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import model.FuncionarioAdministrativo;
import model.Login.Rol;
import model.Login.Usuario;
import model.Paciente;
import model.RegistroEntrada;

import java.io.IOException;
import java.util.List;

public class BuscarPacienteController {
    public TextField txtDni;
    public Label lbNombre;
    public Label lbApellido;
    public Label lbEdad;
    public Label lbTelFijo;
    public Label lbTelCel;
    public Label lbTelCont;
    public TextArea txtMotivo;
    private Paciente paciente;
    private List<Rol> rolesUsuario;
    private Usuario usuarioIniciado;
    private FuncionarioAdministrativo funcionarioAdministrativoIniciado;

    private FuncionarioProController controllerPrincipal;

    /**
     * Inicializa la funcionalidad del controlador.
     * Este método se llama automáticamente cuando se inicializa el controlador y se conecta a su vista correspondiente.
     * Realiza las siguientes tareas:
     * 1. Obtiene una referencia al controlador principal (FuncionarioProController) y al Funcionario Administrativo asociado.
     * 2. Comprueba si se ha proporcionado un paciente desde el controlador principal.
     *    - Si se proporciona un paciente, lo asigna a la variable de clase "paciente" y realiza la búsqueda del paciente.
     *    - Si no se proporciona un paciente, la variable "paciente" se mantiene como nula.
     * 3. Limpia la referencia al paciente en el controlador principal para evitar su uso repetido.
     */
    @FXML
    public void initialize(){
        controllerPrincipal = FuncionarioProController.getControladorPrimario();
        this.lbNombre.setText("Sin Datos");
        this.lbApellido.setText("Sin Datos");
        this.lbTelFijo.setText("Sin Datos");
        this.lbTelCel.setText("Sin Datos");
        this.lbTelCont.setText("Sin Datos");
    }

    /**
     * Busca un paciente por su número de DNI y muestra su información en la interfaz de usuario si se encuentra.
     * Si el paciente no se encuentra en la base de datos, se muestra un mensaje informativo.
     *
     * @param actionEvent El evento de acción que desencadenó la búsqueda del paciente.
     */
    public void BuscarPaciente(ActionEvent actionEvent) {
        String dni = this.txtDni.getText();
        try {
            ComprobarDni(dni);
            PacienteDAO pacienteDAO = new PacienteDAO();
            paciente = pacienteDAO.obtenerPorDni(dni);
            if(paciente == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText("Paciente no encontrado");
                alert.setContentText("Revise los datos ingresados, y vuelva a llenar el campo");
                alert.show();
                this.lbNombre.setText("Sin Datos");
                this.lbApellido.setText("Sin Datos");
                this.lbTelFijo.setText("Sin Datos");
                this.lbTelCel.setText("Sin Datos");
                this.lbTelCont.setText("Sin Datos");
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
     * Busca y muestra la información del paciente en la vista.
     * Este método recupera los datos del paciente proporcionado y los muestra en los campos correspondientes de la vista.
     * Los datos incluyen el DNI, nombre, apellido, teléfono fijo, teléfono celular y teléfono de contacto del paciente.
     */
    public void BuscarPaciente(){
        this.txtDni.setText(String.valueOf(paciente.getDNI()));

        SetearLabels(this.lbNombre, paciente.getNombre());
        SetearLabels(this.lbApellido, paciente.getApellido());
        SetearLabels(this.lbTelFijo,String.valueOf(paciente.getTelefonoFijo()));
        SetearLabels(this.lbTelCel, String.valueOf(paciente.getTelefonoCelular()));
        SetearLabels(this.lbTelCont, String.valueOf(paciente.getPersonaContacto()));
    }

    /**
     * Establece el texto de un objeto Label con el valor proporcionado, o muestra "Sin datos" si el valor es nulo.
     *
     * @param label El objeto Label al que se le establecerá el texto.
     * @param dato  El valor que se establecerá en el Label o nulo si no hay datos disponibles.
     */
    public void SetearLabels(Label label, String dato){
        if(dato == null){
            label.setText("Sin datos");
        }else{
            label.setText(dato);
        }
    }

    /**
     * Comprueba si el valor del DNI es un número entero.
     *
     * @param dni El valor del Documento Nacional de Identidad (DNI) a comprobar.
     * @throws IllegalArgumentException Si el DNI no es un número entero.
     */
    public void ComprobarDni(String dni){
        if (!dni.matches("\\d+")){
            throw new IllegalArgumentException("El DNI debe ser un número entero");
        }
    }

    /**
     * Crea un registro de entrada para un paciente con el motivo de consulta especificado.
     *
     * @param event El evento que desencadena la creación del registro de entrada.
     * @throws IOException Si se produce un error al cargar la interfaz de usuario.
     */
    public void CrearRegistroEntrada(ActionEvent event) throws IOException {
        if(paciente == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Datos Incompletos");
            alert.setContentText("Debe buscar el paciente primeramente");
            alert.show();
        }else{
            if(!this.txtMotivo.getText().matches("\\s.*") || this.txtMotivo.getText().matches("^(?![0-9 ]{6,})[A-Za-z0-9 ]{6,}$")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacion");
                alert.setHeaderText("Registro Creado Exitosamente");
                alert.show();
                funcionarioAdministrativoIniciado.RealizarRegistroEntrada(paciente,this.txtMotivo.getText());
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Datos Incompletos");
                alert.setContentText("Debe escribir el motivo de consulta");
                alert.show();
            }

        }

    }

    /**
     * Carga la interfaz de usuario para registrar un nuevo paciente.
     *
     * @param event El evento que desencadena la carga de la interfaz.
     * @throws IOException Si se produce un error al cargar la interfaz de registro de paciente.
     */
    public void CrearPaciente(ActionEvent event) throws IOException {
        controllerPrincipal.cargarEscena("/views/FuncionarioViews/RegistroEntrada.fxml");
    }

    public void recibirDatos(List<Rol> roles, Usuario user, FuncionarioAdministrativo funcionarioAdministrativo) {
        this.rolesUsuario = roles;
        this.usuarioIniciado = user;
        this.funcionarioAdministrativoIniciado = funcionarioAdministrativo;
    }
}
