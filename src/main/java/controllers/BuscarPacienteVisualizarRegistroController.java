package controllers;

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
import model.Login.Rol;
import model.Medico;
import model.Paciente;

import java.io.IOException;
import java.util.List;

public class BuscarPacienteVisualizarRegistroController {
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
    private List<Rol> roles = SingletonMedico.getInstance().getRoles();
    private Medico medico = SingletonMedico.getInstance().getMedico();

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

    public void Volver(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MedicoViews/Medico.fxml"));
        Parent rootFuncionario = loader.load();
        MedicoController controller = loader.getController();

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(rootFuncionario);
        stage.setScene(scene);
        stage.show();
    }

    public void verRegistros(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MedicoViews/HistorialClinico.fxml"));
        Parent rootFuncionario = loader.load();
        HistorialClinicoController controller = loader.getController();
        controller.recibirDatos(this.paciente);

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(rootFuncionario);
        stage.setScene(scene);
        stage.show();
    }
}
