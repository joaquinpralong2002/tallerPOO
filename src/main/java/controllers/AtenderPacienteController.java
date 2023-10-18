package controllers;

import datasource.RegistroDAO;
import datasource.ResultadoDiagnosticoDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleAction;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.BoxAtencion;
import model.Enum.LugarAtencion;
import model.Paciente;

import java.util.Optional;

public class AtenderPacienteController {
    private Paciente paciente;
    private Triage triage;
    @FXML
    private TextField campoDeTexto;
    @FXML
    private Button atrasButton;
    @FXML
    private ComboBox tipoDeBoxComboBox;
    @FXML
    private ComboBox<LugarAtencion> consultorioComboBox;
    @FXML
    private ComboBox<LugarAtencion> emergenciaComboBox;
    @FXML
    private ComboBox<LugarAtencion> internacionesComboBox;

    @FXML
    public void initialize(){
        consultorioComboBox.getItems().add(LugarAtencion.Consultorio);
        emergenciaComboBox.getItems().add(LugarAtencion.Emergencia);
        internacionesComboBox.getItems().add(LugarAtencion.Internaciones);
    }

    @FXML
    public void recibirDatos(Paciente persona) {
        //Método para recibir el paciente y el triage asociado de la escena anterior.
        this.paciente = persona;
        this.triage = triage;
    }

    //Metodo para guardar en que Box de atencion lo atendieron, se guarda en el Registro


    public void RealizarRegistro(AccessibleAction action) throws Exception{
        RegistroDAO registro = new RegistroDAO();
        ResultadoDiagnosticoDAO resultadoDiagnostico = new ResultadoDiagnosticoDAO();


        TextField textField = new TextField();
        // Llena el TextField
        textField.setText(String.valueOf(campoDeTexto));
        // Agrega un evento al botón
        atrasButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Guarda la información del TextField
                String informacion = textField.getText();

                // Envía la información a otro lugar
                //Deberia guardar en el ResultadoDiagnosticoDAO?...
            }
        });
    }
    public void CerrarSesion(ActionEvent event) throws Exception {
        // Volver atras a Médico
        Parent root = FXMLLoader.load(getClass().getResource("/views/MedicoViews/Medico.fxml"));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setContentText("¿Estás seguro de que deseas cerrar sesión?");
        Optional<ButtonType> resultado = alert.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
