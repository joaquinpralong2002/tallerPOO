package controllers.Administracion;

import controllers.FuncionarioProController;
import datasource.FuncionarioAdministrativoDAO;
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
import lombok.Setter;
import model.Enum.EstadoCivil;
import model.FuncionarioAdministrativo;
import model.Login.Rol;
import model.Login.Usuario;
import model.Paciente;
import model.RegistroEntrada;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class RegistroEntradaController {

    private List<Rol> rolesUsuario;
    private Usuario usuarioIniciado;
    private FuncionarioAdministrativo funcionarioAdministrativoIniciado;

    public TextField txtNomPac;
    public TextField txtApePac;
    public TextField txtDNIPac;
    public TextField txtTelFijoPac;
    public TextField txtTelCelPac;
    public DatePicker dateFechaNaciPac;
    public ComboBox cboxEstCivilPac;
    public TextField txtCorreoPac;
    public TextField txtNumPerContPac;
    public TextField txtDomiPac;
    public TextArea txtMotivoConsulta;

    @Setter
    private FuncionarioProController controllerPrincipal;

    @FXML
    public void initialize(){
        controllerPrincipal = FuncionarioProController.getControladorPrimario();
        this.cboxEstCivilPac.getItems().addAll(EstadoCivil.values());
    }

    /**
     * Crea un nuevo registro para un paciente con los datos proporcionados y realiza la persistencia de los datos.
     *
     * @param actionEvent El evento de acción que desencadena la creación del registro.
     */
    public void CrearRegistro(ActionEvent actionEvent) {
        String nombrePac = this.txtNomPac.getText();
        String apellidoPac = this.txtApePac.getText();
        LocalDate fechaNaciPac = this.dateFechaNaciPac.getValue();
        String domicilioPac = this.txtDomiPac.getText();
        String dniPac = this.txtDNIPac.getText();
        String telefonoFijoPac = this.txtTelFijoPac.getText();
        String telefonoCelPac = this.txtTelCelPac.getText();
        EstadoCivil estadoCivilPac = (EstadoCivil) this.cboxEstCivilPac.getValue();
        String correoPac = this.txtCorreoPac.getText();
        String teleonoPersonaContactoPac = this.txtNumPerContPac.getText();
        String motivoConsulta = this.txtMotivoConsulta.getText();

        try {
            validarDatosPaciente(nombrePac,apellidoPac,fechaNaciPac,domicilioPac,dniPac,telefonoFijoPac,telefonoCelPac,estadoCivilPac,correoPac,teleonoPersonaContactoPac,motivoConsulta);

            PacienteDAO pacienteDAO = new PacienteDAO();
            if(pacienteDAO.obtenerPorDni(dniPac) != null){
                throw new Exception();
            }

            Paciente paciente = new Paciente(nombrePac,apellidoPac,fechaNaciPac,domicilioPac,Integer.parseInt(dniPac),Integer.parseInt(telefonoFijoPac),Long.parseLong(telefonoCelPac),estadoCivilPac,correoPac,Integer.parseInt(teleonoPersonaContactoPac));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacion");
            alert.setHeaderText("Registro Creado Exitosamente");
            alert.show();

            //persistencia de los datos
            pacienteDAO.agregar(paciente);
            funcionarioAdministrativoIniciado.RealizarRegistroEntrada(paciente,this.txtMotivoConsulta.getText());

        }catch (IllegalArgumentException e) {
            // Mostrar un mensaje de error al usuario
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Datos Invalidos");
            alert.setContentText(e.getMessage());
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Paciente ya registrado");
            alert.show();
        }

    }

    /**
     * Valida los datos de un paciente antes de crear un registro. Lanza una excepción si algún dato es inválido.
     *
     * @param nombrePac                El nombre del paciente.
     * @param apellidoPac              El apellido del paciente.
     * @param fechaNaciPac             La fecha de nacimiento del paciente.
     * @param domicilioPac             El domicilio del paciente.
     * @param dniPac                   El número de DNI del paciente.
     * @param telefonoFijoPac          El número de teléfono fijo del paciente.
     * @param telefonoCelPac           El número de teléfono celular del paciente.
     * @param estadoCivilPac           El estado civil del paciente.
     * @param correoPac                El correo electrónico del paciente.
     * @param telefonoPersonaContactoPac  El número de teléfono de la persona de contacto del paciente.
     * @param motivoConsulta           El motivo de la consulta del paciente.
     * @throws IllegalArgumentException  Si algún dato es inválido.
     */
    public void validarDatosPaciente(String nombrePac, String apellidoPac, LocalDate fechaNaciPac, String domicilioPac, String dniPac, String telefonoFijoPac, String telefonoCelPac, EstadoCivil estadoCivilPac, String correoPac, String telefonoPersonaContactoPac, String motivoConsulta) {
       String patron = "^(?![0-9 ]{6,})[A-Za-z0-9 ]{6,}$";
       String patron2 = "^(?![0-9-]{3,})[A-Za-z0-9-]{3,}$";
        // Validar que los campos obligatorios no sean nulos
        if(!nombrePac.matches(patron2)){
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }

        if(!apellidoPac.matches(patron2)){
            throw new IllegalArgumentException("El apellido no puede estar vacio");
        }

        if(!domicilioPac.matches(patron)){
            throw new IllegalArgumentException("El domicilio no puede estar vacio");
        }

        if(estadoCivilPac == null){
            throw new IllegalArgumentException("El estado civil no puede estar vacio");
        }

        if(!correoPac.matches("^[A-Z-a-z0-9+_.-]+@(.+)$")){
            throw new IllegalArgumentException("El correo no puede estar vacio");
        }

        if(fechaNaciPac == null){
            throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacia");
        }

        if(!motivoConsulta.matches(patron)){
            throw new IllegalArgumentException("El motivo de la consulta no puede estar vacio");
        }

        /*
        El método matches() de la clase String se utiliza para verificar si una cadena de texto coincide con una expresión regular.
        En este caso, la expresión regular \\d+ se utiliza para verificar si la cadena de texto solo contiene dígitos.

        El carácter \\ se utiliza para escapar de caracteres especiales, como el carácter (+).
        El carácter d se utiliza para representar un dígito.
        El carácter + se utiliza para indicar que la expresión regular debe coincidir con uno o más dígitos.
        */

        // Validar que el DNI sea un número entero
        if (!dniPac.matches("\\d+")) {
            throw new IllegalArgumentException("El DNI debe ser un número entero");
        }

        // Validar que el teléfono fijo sea un número entero
        if (!telefonoFijoPac.matches("\\d+")) {
            throw new IllegalArgumentException("El teléfono fijo debe ser un número entero");
        }

        // Validar que el teléfono celular sea un número entero
        if (!telefonoCelPac.matches("\\d+")) {
            throw new IllegalArgumentException("El teléfono celular debe ser un número entero");
        }

        // Validar que el teléfono de la Persona de Conctacto sea un número entero
        if (!telefonoPersonaContactoPac.matches("\\d+")) {
            throw new IllegalArgumentException("El teléfono de la persona de contacto debe ser un número entero");
        }
    }


    public void recibirDatos(List<Rol> roles, Usuario usuario, FuncionarioAdministrativo funcionarioAdministrativo) {
        this.rolesUsuario = roles;
        this.usuarioIniciado = usuario;
        this.funcionarioAdministrativoIniciado = funcionarioAdministrativo;
    }

}
