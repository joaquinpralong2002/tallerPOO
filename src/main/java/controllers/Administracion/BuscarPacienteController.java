package controllers.Administracion;

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

    @FXML
    public void initialize(){
        this.lbNombre.setText("Sin Datos");
        this.lbApellido.setText("Sin Datos");
        this.lbTelFijo.setText("Sin Datos");
        this.lbTelCel.setText("Sin Datos");
        this.lbTelCont.setText("Sin Datos");
    }

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

    public void SetearLabels(Label label, String dato){
        if(dato == null){
            label.setText("Sin datos");
        }else{
            label.setText(dato);
        }
    }

    public void ComprobarDni(String dni){
        if (!dni.matches("\\d+")){
            throw new IllegalArgumentException("El DNI debe ser un número entero");
        }
    }

    public void CrearRegistroEntrada(ActionEvent event) throws IOException {
        if(paciente == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Datos Incompletos");
            alert.setContentText("Debe buscar el paciente primeramente");
            alert.show();
        }else{
            if(this.txtMotivo.getText().matches("^(?![0-9 ]{6,})[A-Za-z0-9 ]{6,}$")){
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

    public void CrearPaciente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/FuncionarioViews/RegistroEntrada.fxml"));
        Parent rootFuncionario = loader.load();

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(rootFuncionario);
        stage.setScene(scene);
        stage.show();
    }

    public void Volver(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/FuncionarioViews/Funcionario.fxml"));
        Parent rootFuncionario = loader.load();

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(rootFuncionario);
        stage.setScene(scene);
        stage.show();
    }

    public void recibirDatos(List<Rol> roles, Usuario user, FuncionarioAdministrativo funcionarioAdministrativo) {
        this.rolesUsuario = roles;
        this.usuarioIniciado = user;
        this.funcionarioAdministrativoIniciado = funcionarioAdministrativo;
    }
}
