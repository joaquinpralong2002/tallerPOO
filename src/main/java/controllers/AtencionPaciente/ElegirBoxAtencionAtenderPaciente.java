package controllers.AtencionPaciente;

import controllers.AtencionPaciente.AtenderPacienteController;
import controllers.MedicoController;
import controllers.Singletons.SingletonControladorPrimarioSalud;
import controllers.Singletons.SingletonMedico;
import datasource.RegistroEntradaDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.BoxAtencion;
import model.Enum.ColorTriage;
import model.Enum.LugarAtencion;
import model.Login.Rol;
import model.Medico;
import model.Paciente;
import model.RegistroEntrada;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ElegirBoxAtencionAtenderPaciente {
    @FXML
    private Button IrAtrasButton;
    @FXML
    private Button IrAlBoxButton;
    @FXML
    private ToggleGroup grupoDeBotones = new ToggleGroup();

    @FXML
    private RadioButton BotonRedondoEmergencia, BotonRedondoConsultorio, BotonRedondoInternaciones;

    @FXML
    private Label BoxRecomendadoApp;
    private LugarAtencion lugarAtencionSeleccionada;
    private Stage medicoStage;
    private ColorTriage colorTriage;
    private Medico medico = SingletonMedico.getInstance().getMedico();
    private Paciente paciente;
    private RegistroEntrada registroEntrada;
    private List<Rol> roles = SingletonMedico.getInstance().getRoles();

    @FXML
    public void initialize(){
        this.paciente = SingletonControladorPrimarioSalud.getInstance().getController().getPaciente();
        this.registroEntrada = SingletonControladorPrimarioSalud.getInstance().getController().getRegistroEntrada();
        this.colorTriage = SingletonControladorPrimarioSalud.getInstance().getController().getColorTriage();
        setBoxRecomendadoApp(this.colorTriage);


        BotonRedondoEmergencia.setToggleGroup(grupoDeBotones);
        BotonRedondoConsultorio.setToggleGroup(grupoDeBotones);
        BotonRedondoInternaciones.setToggleGroup(grupoDeBotones);
    }

    /**
     * Maneja el evento de elegir una opción de lugar de atención y cargar la siguiente escena
     * para atender al paciente en el box seleccionado.
     *
     * @param event Evento que desencadena la acción.
     * @throws Exception Excepción lanzada en caso de errores.
     */
    @FXML
    void elegirBoxAtencion(ActionEvent event) throws Exception {
        // Obtiene la opción seleccionada
        lugarAtencionSeleccionada = getLugarAtencionSeleccionada();
            // Llama al método setOnAction() para pasar la variable a la siguiente escena
            IrAlBoxButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Ir al Box");
                    alert.setContentText("¿Está seguro de que desea asignar el Box seleccionado?");
                    Optional<ButtonType> resultado = alert.showAndWait();

                    // Establece la opción seleccionada en la siguiente escena
                    SingletonControladorPrimarioSalud.getInstance().getController().setLugarAtencion(lugarAtencionSeleccionada);
                    SingletonControladorPrimarioSalud.getInstance().getController().setPaciente(paciente);
                    SingletonControladorPrimarioSalud.getInstance().getController().setColorTriage(colorTriage);
                    SingletonControladorPrimarioSalud.getInstance().getController().setRegistroEntrada(registroEntrada);


                    // Cambia a la siguiente escena
                    if (resultado.get() == ButtonType.OK) {
                        SingletonControladorPrimarioSalud.getInstance().getController().cargarEscena("/views/MedicoViews/AtenderPaciente.fxml");
                    }
                }
            });
        }

    /**
     * Obtiene la opción de lugar de atención seleccionada por el médico.
     *
     * @return Enumeración que representa el lugar de atención seleccionado.
     */
    private LugarAtencion getLugarAtencionSeleccionada() {
        if (BotonRedondoEmergencia.isSelected()) {
            return LugarAtencion.Emergencia;
        } else if (BotonRedondoConsultorio.isSelected()) {
            return LugarAtencion.Consultorio;
        } else {
            return LugarAtencion.Internaciones;
        }
    }


    /**
     * Establece el texto en la interfaz de usuario para mostrar el box de atención recomendado
     * basado en el color del triaje.
     *
     * @param colorTriage El color del triaje del paciente.
     */
    private void setBoxRecomendadoApp(ColorTriage colorTriage) {
        this.colorTriage = colorTriage;
        if(colorTriage == ColorTriage.Rojo || colorTriage == ColorTriage.Naranja){
            BoxRecomendadoApp.setText("Internaciones");
        }
        else if(colorTriage == ColorTriage.Amarillo){
            BoxRecomendadoApp.setText("Emergencia");
        }
        else {
            BoxRecomendadoApp.setText("Consultorio");
        }
    }


    @FXML
    public void recibirDatos(Paciente paciente, RegistroEntrada registroEntrada){
        this.paciente = paciente;
        this.registroEntrada = registroEntrada;
    }

    public void BotonAtras(ActionEvent event) {
    }
}
