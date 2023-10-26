package controllers.AtencionPaciente;

import controllers.MedicoController;
import datasource.BoxAtencionDAO;
import datasource.RegistroEntradaDAO;
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
import model.Enum.ColorTriage;
import model.Enum.LugarAtencion;
import model.Login.Rol;
import model.Paciente;
import model.Medico;
import model.RegistroEntrada;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AtenderPacienteController {
    private Paciente persona;
    @Getter
    private LugarAtencion lugarAtencionSeleccionada;
    private RegistroEntrada registroEntrada;
    private Medico medico;
    private List<Rol> roles;
    private BoxAtencion boxAtencion;
    private ColorTriage colorTriage;
    @FXML
    private Button realizarDiagnosticoButton;
    @FXML
    private TextArea campoDeTexto;
    @FXML
    private Button atrasButton;
    @FXML
    private Label LabalTipoBox;


    //Metodo para guardar en que Box de atencion lo atendieron, se guarda en el Registro
    //Revisar si anda...
    public void BotonRealizarRegistro(ActionEvent event) throws Exception {

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
                    medico.asignarBox(registroEntrada);
                    medico.atenderPaciente(persona,boxAtencion,diagnostico);


                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/views/MedicoViews/Medico.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    MedicoController medicoController = loader.getController();
                    medicoController.recibirDatos(roles, medico);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        });
    }

    public void setLugarAtencionSeleccionada(LugarAtencion lugarAtencionSeleccionada) {
        this.lugarAtencionSeleccionada = lugarAtencionSeleccionada;
        LabalTipoBox.setText(lugarAtencionSeleccionada.name());
        BoxAtencionDAO boxAtencionDAO = new BoxAtencionDAO();
        boxAtencion = boxAtencionDAO.obtenerDisponible(lugarAtencionSeleccionada);
    }

    public void BotonAtras(ActionEvent event) throws Exception {
        // Volver atras a Médico
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/views/MedicoViews/Medico.fxml"));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Ir atrás");
        alert.setContentText("¿Estás seguro de que deseas volver a la pestaña anterior?");
        Optional<ButtonType> resultado = alert.showAndWait();
        MedicoController controller = new MedicoController();
        controller.recibirDatos(roles, medico);
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void recibirDatos(Medico medico, Paciente persona, ColorTriage colorTriage, RegistroEntrada registroEntrada, List<Rol> roles){
        this.medico = medico;
        this.persona = persona;
        this.colorTriage = colorTriage;
        this.registroEntrada = registroEntrada;
        this.roles = roles;
    }

}
