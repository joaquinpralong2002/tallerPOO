package controllers.AtencionPaciente;

import controllers.MedicoController;
import controllers.Singletons.SingletonControladorPrimarioSalud;
import controllers.Singletons.SingletonMedico;
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
    private Medico medico = SingletonMedico.getInstance().getMedico();
    private List<Rol> roles = SingletonMedico.getInstance().getRoles();
    private BoxAtencion boxAtencion;
    private ColorTriage colorTriage;
    @FXML
    private Button realizarDiagnosticoButton;
    @FXML
    private TextArea campoDeTexto;
    @FXML
    private Label LabalTipoBox;

    @FXML
    public void initialize(){
        this.persona = SingletonControladorPrimarioSalud.getInstance().getController().getPaciente();
        this.lugarAtencionSeleccionada = SingletonControladorPrimarioSalud.getInstance().getController().getLugarAtencion();
        this.registroEntrada = SingletonControladorPrimarioSalud.getInstance().getController().getRegistroEntrada();
        this.colorTriage = SingletonControladorPrimarioSalud.getInstance().getController().getColorTriage();

        setLugarAtencionSeleccionada(SingletonControladorPrimarioSalud.getInstance().getController().getLugarAtencion());
    }


    /**
     * Maneja el evento de presionar el botón "Realizar Registro".
     * Captura el diagnóstico ingresado por el médico y muestra una alerta de confirmación.
     * Si el médico confirma la acción, asigna un lugar de atención al paciente y realiza el diagnóstico.
     *
     * @param event El evento de clic en el botón "Realizar Registro".
     * @throws Exception Si ocurre un error al cargar la vista de Medico.
     */
    public void BotonRealizarRegistro(ActionEvent event) throws Exception {
        // Llena el TextField
        String diagnostico = campoDeTexto.getText();

        // Verifica si el diagnóstico no está vacío
        if (diagnostico.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("El diagnóstico no puede estar vacío.");
            alert.showAndWait();
            return;
        }

        // Lanza una alerta para confirmar la acción
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar acción");
        alert.setContentText("¿Estás seguro de que deseas realizar el diagnóstico?");
        Optional<ButtonType> resultado = alert.showAndWait();

        // Si el usuario hace clic en el botón "Aceptar", entonces se realiza la acción
        if (resultado.get() == ButtonType.OK) {
            medico.asignarBox(registroEntrada, lugarAtencionSeleccionada);
            medico.atenderPaciente(persona, boxAtencion, diagnostico);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/MedicoViews/Medico.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            SingletonControladorPrimarioSalud.getInstance().getController().cargarEscena("/views/MedicoViews/Medico.fxml");
        }
    }

    /**
     * Establece el lugar de atención seleccionado y actualiza la interfaz de usuario con el tipo de lugar de atención.
     * Además, busca y asigna un box de atención disponible para el lugar seleccionado.
     *
     * @param lugarAtencionSeleccionada El lugar de atención seleccionado por el usuario.
     */
    public void setLugarAtencionSeleccionada(LugarAtencion lugarAtencionSeleccionada) {
        this.lugarAtencionSeleccionada = lugarAtencionSeleccionada;
        LabalTipoBox.setText(lugarAtencionSeleccionada.name());
        BoxAtencionDAO boxAtencionDAO = new BoxAtencionDAO();
        boxAtencion = boxAtencionDAO.obtenerDisponible(lugarAtencionSeleccionada);
    }


}
