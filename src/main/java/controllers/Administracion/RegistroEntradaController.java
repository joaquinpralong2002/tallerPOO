package controllers.Administracion;

import controllers.FuncionarioProController;
import datasource.FuncionarioAdministrativoDAO;
import datasource.FuncionarioDAO;
import datasource.PacienteDAO;
import datasource.RegistroEntradaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import model.Enum.EstadoCivil;
import model.Funcionario;
import model.FuncionarioAdministrativo;
import model.Login.Rol;
import model.Login.Usuario;
import model.Paciente;
import model.RegistroEntrada;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RegistroEntradaController {

    private List<Rol> rolesUsuario;
    private Usuario usuarioIniciado;

    public TextField txtNomPac;
    public TextField txtApePac;
    public TextField txtDNIPac;
    public TextField txtTelFijoPac;
    public TextField txtTelCelPac;
    public DatePicker dateFechaNaciPac;
    public ComboBox<EstadoCivil> cboxEstCivilPac;
    public TextField txtCorreoPac;
    public TextField txtNumPerContPac;
    public TextField txtDomiPac;
    @Setter
    private FuncionarioProController controllerPrincipal;
    @Getter
    private FuncionarioAdministrativo funcionarioAdministrativoIniciado;

    private boolean personalAPaciente;

    @FXML
    public void initialize(){
        controllerPrincipal = FuncionarioProController.getControladorPrimario();
        funcionarioAdministrativoIniciado = controllerPrincipal.getFuncionarioAdministrativo();
        this.cboxEstCivilPac.getItems().addAll(EstadoCivil.values());
        personalAPaciente = false;
    }


    public void RegistrarPaciente(ActionEvent actionEvent) {

        try {
            validarDatosPaciente();
            System.out.println(personalAPaciente);
            if (personalAPaciente == false){
                if (ComprobarExistenciaPaciente(txtDNIPac.getText())) {
                    return; // Salir del método si el paciente ya existe
                }
            }
            PacienteDAO pacienteDAO = new PacienteDAO();

            Paciente paciente = new Paciente(txtNomPac.getText(),txtApePac.getText(),dateFechaNaciPac.getValue(),txtDomiPac.getText(),
                    Integer.parseInt(txtDNIPac.getText()),Long.parseLong(txtTelFijoPac.getText()),Long.parseLong(txtTelCelPac.getText()),
                    (EstadoCivil) cboxEstCivilPac.getValue(),txtCorreoPac.getText(),Long.parseLong(txtNumPerContPac.getText()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacion");
            alert.setHeaderText("Paciente Registrado Correctamente");
            alert.setContentText("¿Desea crear un registro de entrada con este paciente?");
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> resultado = alert.showAndWait();

            if (resultado.get() == ButtonType.OK) {
                personalAPaciente = false;
                controllerPrincipal.setPaciente(paciente);
                controllerPrincipal.cargarEscena("/views/FuncionarioViews/BuscarPaciente.fxml");
            }else{
                RestablecerCampos();
                personalAPaciente = false;
            }

            //persistencia de los datos
            pacienteDAO.agregar(paciente);

        }catch (IllegalArgumentException e) {
            // Mostrar un mensaje de error al usuario
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Datos Invalidos");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    /**
     * Valida los datos de un paciente antes de crear un registro. Lanza una excepción si algún dato es inválido.
     *
     * @throws IllegalArgumentException  Si algún dato es inválido.
     */
    public void validarDatosPaciente() {
        String patron = "^[A-Za-z0-9 ]+$";
        String espacioBlanco = "\\s.*";

        // Validar que los campos obligatorios no sean nulos
        if(txtNomPac.getText().matches(espacioBlanco) || !txtNomPac.getText().matches(patron)){
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }

        if(txtApePac.getText().matches(espacioBlanco) || !txtApePac.getText().matches(patron)){
            throw new IllegalArgumentException("El apellido no puede estar vacio");
        }

        if(txtDomiPac.getText().matches(espacioBlanco) || !txtDomiPac.getText().matches(patron)){
            throw new IllegalArgumentException("El domicilio no puede estar vacio");
        }

        if(cboxEstCivilPac.getValue() == null){
            throw new IllegalArgumentException("El estado civil no puede estar vacio");
        }

        if(txtCorreoPac.getText().matches(espacioBlanco) || !txtCorreoPac.getText().matches("^[A-Z-a-z0-9+_.-]+@(.+)$")){
            throw new IllegalArgumentException("El correo no puede estar vacio");
        }

        if(dateFechaNaciPac.getValue() == null){
            throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacia");
        }


        /*
        El método matches() de la clase String se utiliza para verificar si una cadena de texto coincide con una expresión regular.
        En este caso, la expresión regular \\d+ se utiliza para verificar si la cadena de texto solo contiene dígitos.

        El carácter \\ se utiliza para escapar de caracteres especiales, como el carácter (+).
        El carácter d se utiliza para representar un dígito.
        El carácter + se utiliza para indicar que la expresión regular debe coincidir con uno o más dígitos.
        */

        // Validar que el DNI sea un número entero
        if (!txtDNIPac.getText().matches("\\d+")) {
            throw new IllegalArgumentException("El DNI debe ser un número entero");
        }

        // Validar que el teléfono fijo sea un número entero
        if (!txtTelFijoPac.getText().matches("\\d+")) {
            throw new IllegalArgumentException("El teléfono fijo debe ser un número entero");
        }

        // Validar que el teléfono celular sea un número entero
        if (!txtTelCelPac.getText().matches("\\d+")) {
            throw new IllegalArgumentException("El teléfono celular debe ser un número entero");
        }

        // Validar que el teléfono de la Persona de Conctacto sea un número entero
        if (!txtNumPerContPac.getText().matches("\\d+")) {
            throw new IllegalArgumentException("El teléfono de la persona de contacto debe ser un número entero");
        }
    }

    public void RestablecerCampos(){
        txtNomPac.clear();
        txtNomPac.setEditable(true);
        txtApePac.clear();
        txtApePac.setEditable(true);
        txtDNIPac.clear();
        txtDomiPac.setEditable(true);
        txtCorreoPac.clear();
        txtTelCelPac.clear();
        txtDomiPac.clear();
        txtNumPerContPac.clear();
        txtTelFijoPac.clear();
        dateFechaNaciPac.setValue(null);
        dateFechaNaciPac.setDisable(true);
        dateFechaNaciPac.getEditor().setDisable(true);
        cboxEstCivilPac.setValue(null);
        cboxEstCivilPac.setEditable(true);
        cboxEstCivilPac.setDisable(true);
    }

    private boolean ComprobarExistenciaPaciente(String dni) {
        PacienteDAO pacienteDAO = new PacienteDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

        Funcionario funcionario = funcionarioDAO.obtenerPorDni(dni);
        if (funcionario != null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("DNI encontrado como personal del hospital");
            alert.setContentText("¿Quiere ingresar este personal como paciente?");
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> resultado = alert.showAndWait();

            if (resultado.get() == ButtonType.OK){
                SetearPersonalComoPAciente(funcionario);
                personalAPaciente = true;
            }else{
                txtDNIPac.clear();
            }
            return true;
        }

        Paciente pacienteExistente = pacienteDAO.obtenerPorDni(dni);

        if (pacienteExistente != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Paciente ya registrado");
            alert.setContentText("Un paciente con el DNI que ah ingresado ya esta registrado, verifique el dato ingresado");
            alert.show();
            return true; // Indica que el paciente ya existe
        }
        return false; // Indica que el paciente no existe
    }

    private void SetearPersonalComoPAciente(Funcionario funcionario) {

        txtNomPac.setText(funcionario.getNombre());
        txtNomPac.setEditable(false);

        txtApePac.setText(funcionario.getApellido());
        txtApePac.setEditable(false);

        txtDomiPac.setText(funcionario.getDomicilio());
        txtDomiPac.setEditable(false);

        txtCorreoPac.setText(funcionario.getCorreo());

        txtTelFijoPac.setText(String.valueOf(funcionario.getTelefonoFijo()));

        txtTelCelPac.setText(String.valueOf(funcionario.getTelefonoCelular()));

        dateFechaNaciPac.setValue(funcionario.getFechaNacimiento());
        dateFechaNaciPac.setDisable(false);
        dateFechaNaciPac.getEditor().setDisable(false);

        cboxEstCivilPac.setValue(funcionario.getEstadoCivil());
        cboxEstCivilPac.setEditable(false);
        cboxEstCivilPac.setDisable(false);

    }

    public void recibirDatos(List<Rol> roles, Usuario usuario, FuncionarioAdministrativo funcionarioAdministrativo) {
        this.rolesUsuario = roles;
        this.usuarioIniciado = usuario;
    }
}
