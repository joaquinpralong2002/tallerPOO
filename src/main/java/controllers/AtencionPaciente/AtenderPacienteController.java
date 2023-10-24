package controllers.AtencionPaciente;

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
import lombok.Getter;
import model.BoxAtencion;
import model.Enum.LugarAtencion;
import model.Paciente;
import model.Medico;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AtenderPacienteController {
    private Paciente paciente;
    @Getter
    private LugarAtencion lugarAtencionSeleccionada;
    private Medico medico;
    private BoxAtencion boxAtencion;
    @FXML
    private Button realizarDiagnosticoButton;
    @FXML
    private TextArea campoDeTexto;
    @FXML
    private Button atrasButton;
    @FXML
    private Label LabalTipoBox;

    @FXML
    void initialize(URL url, ResourceBundle rb) {
        // Obtiene el tipo de BoxAtencion seleccionado
        LugarAtencion lugarAtencionSeleccionada = getLugarAtencionSeleccionada();

        // Establece el texto del Label
        LabalTipoBox.setText(lugarAtencionSeleccionada.toString());
    }


    @FXML
    public void recibirDatos(Paciente persona, Medico medico,BoxAtencion boxAtencion) {
        //Método para recibir el paciente y medico asociado de la escena anterior.
        this.paciente = persona;
        this.medico = medico;
        this.boxAtencion = boxAtencion;
    }


    //Metodo para guardar en que Box de atencion lo atendieron, se guarda en el Registro
    //Revisar si anda...
    public void BotonRealizarRegistro(AccessibleAction action, Paciente persona, BoxAtencion boxAtencion) throws Exception{

        // Llena el TextField
        String diagnostico = campoDeTexto.getText();

        realizarDiagnosticoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Lanza una alerta para confirmar la acción
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar acción");
                alert.setContentText("¿Estás seguro de que deseas realizar el diagnóstico?");
                Optional<ButtonType> resultado = alert.showAndWait();

                // Si el usuario hace clic en el botón "Aceptar", entonces se realiza la acción
                if (resultado.get() == ButtonType.OK) {
                    medico.atenderPaciente(persona,boxAtencion,diagnostico);
                }
            }
        });
    }

    public void BotonAtras(ActionEvent event) throws Exception {
        // Volver atras a Médico
        Parent root = FXMLLoader.load(getClass().getResource("/views/MedicoViews/Medico.fxml"));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Ir atrás");
        alert.setContentText("¿Estás seguro de que deseas volver a la pestaña anterior?");
        Optional<ButtonType> resultado = alert.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void setLugarAtencionSeleccionada(LugarAtencion lugarAtencionSeleccionada) {
        this.lugarAtencionSeleccionada = lugarAtencionSeleccionada;
        LabalTipoBox.setText(lugarAtencionSeleccionada.name());
    }

}
