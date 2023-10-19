package controllers;

import datasource.FuncionarioAdministrativoDAO;
import datasource.PacienteDAO;
import datasource.RegistroEntradaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Enum.EstadoCivil;
import model.FuncionarioAdministrativo;
import model.Paciente;
import model.RegistroEntrada;

import java.time.LocalDate;

public class RegistroEntradaController {

    
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

    @FXML
    public void initialize(){
        this.cboxEstCivilPac.getItems().addAll(EstadoCivil.values());
    }
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

            Paciente paciente = new Paciente(nombrePac,apellidoPac,fechaNaciPac,domicilioPac,Integer.parseInt(dniPac),Integer.parseInt(telefonoFijoPac),Long.parseLong(telefonoCelPac),estadoCivilPac,correoPac,teleonoPersonaContactoPac);

            //buscar una forma de traer el funcionario que realizo el registro entrada
            //buscarlo desde el dao de usuario, pero abria que pasar el usuario que se logeo de ventana en ventana
            //buscarlo desde el dao de funcionarioadministrativo, buscado por el usuario que se logeo tambien
            FuncionarioAdministrativoDAO daoFuncAdm = new FuncionarioAdministrativoDAO();
            FuncionarioAdministrativo funcionarioAdministrativo = new FuncionarioAdministrativo();

            //para luego setearlo al registro de entrada que creo
            RegistroEntrada registroEntrada = new RegistroEntrada(motivoConsulta,paciente,funcionarioAdministrativo);

            //persistencia de los datos
            PacienteDAO pacienteDAO = new PacienteDAO();
            pacienteDAO.agregar(paciente);
            RegistroEntradaDAO registroEntradaDAO = new RegistroEntradaDAO();
            registroEntradaDAO.agregar(registroEntrada);

        }catch (IllegalArgumentException e) {
            // Mostrar un mensaje de error al usuario
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Datos Invalidos");
            alert.setContentText("Debe llenar todos los campos, con la informacion correspondiente.");
            alert.show();
        }

    }

    public void validarDatosPaciente(String nombrePac, String apellidoPac, LocalDate fechaNaciPac, String domicilioPac, String dniPac, String telefonoFijoPac, String telefonoCelPac, EstadoCivil estadoCivilPac, String correoPac, String telefonoPersonaContactoPac, String motivoConsulta) {
        // Validar que los campos obligatorios no sean nulos
        if (nombrePac == null || apellidoPac == null || domicilioPac == null || estadoCivilPac == null || correoPac == null || fechaNaciPac == null || motivoConsulta == null) {
            throw new IllegalArgumentException("Los campos obligatorios no pueden ser nulos");
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


}
