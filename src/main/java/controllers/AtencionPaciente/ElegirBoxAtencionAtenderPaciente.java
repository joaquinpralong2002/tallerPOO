package controllers.AtencionPaciente;

import controllers.AtencionPaciente.AtenderPacienteController;
import controllers.MedicoController;
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

import java.io.IOException;
import java.util.Optional;

public class ElegirBoxAtencionAtenderPaciente {
    @FXML
    private Button IrAtrasButton;
    @FXML
    private Button IrAlBoxButton;
    @FXML
    private RadioButton BotonRedondoEmergencia, BotonRedondoConsultorio, BotonRedondoInternaciones;
    @FXML
    private BoxAtencion boxAtencion;
    @FXML
    private Label BoxRecomendadoApp;
    private LugarAtencion lugarAtencionSeleccionada;
    private Stage medicoStage;
    private ColorTriage colorTriage;


    @FXML
    void elegirBoxAtencion(ActionEvent event) throws Exception {
        // Obtiene la opción seleccionada
        lugarAtencionSeleccionada = getLugarAtencionSeleccionada();

        if (BotonRedondoEmergencia.isSelected() && BotonRedondoConsultorio.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Solo puede seleccionar un Box de Atención a la vez.");
            alert.showAndWait();
            BotonRedondoEmergencia.setSelected(false);
            BotonRedondoConsultorio.setSelected(false);
            BotonRedondoInternaciones.setSelected(false);
        } else if (BotonRedondoInternaciones.isSelected() && BotonRedondoConsultorio.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Solo puede seleccionar un Box de Atención a la vez.");
            alert.showAndWait();
            BotonRedondoEmergencia.setSelected(false);
            BotonRedondoConsultorio.setSelected(false);
            BotonRedondoInternaciones.setSelected(false);
        } else if (BotonRedondoInternaciones.isSelected() && BotonRedondoEmergencia.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Solo puede seleccionar un Box de Atención a la vez.");
            alert.showAndWait();
            BotonRedondoEmergencia.setSelected(false);
            BotonRedondoConsultorio.setSelected(false);
            BotonRedondoInternaciones.setSelected(false);
        } else {
            // Llama al método setOnAction() para pasar la variable a la siguiente escena
            IrAlBoxButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        // Carga la siguiente escena
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/views/MedicoViews/AtenderPaciente.fxml"));
                        Parent root = loader.load();
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Ir al Box");
                        alert.setContentText("¿Está seguro de que desea asignar el Box seleccionado?");
                        Optional<ButtonType> resultado = alert.showAndWait();

                        // Establece la opción seleccionada en la siguiente escena
                        AtenderPacienteController controller = loader.getController();
                        controller.setLugarAtencionSeleccionada(lugarAtencionSeleccionada);
                        //Metodo para cerrar la pestaña de Medico
                        medicoStage.close();

                        // Cambia a la siguiente escena
                        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Error al cargar la escena AtenderPaciente.fxml");
                        alert.showAndWait();
                    }
                }
            });
        }
    }
    private LugarAtencion getLugarAtencionSeleccionada() {
        if (BotonRedondoEmergencia.isSelected()) {
            return LugarAtencion.Emergencia;
        } else if (BotonRedondoConsultorio.isSelected()) {
            return LugarAtencion.Consultorio;
        } else {
            return LugarAtencion.Internaciones;
        }
    }


    public void setBoxRecomendadoApp(ColorTriage colorTriage) {
        this.colorTriage = colorTriage;
        if(colorTriage == ColorTriage.Rojo || colorTriage == ColorTriage.Naranja){
            BoxRecomendadoApp.setText("Internaciones");
        }
        if(colorTriage == ColorTriage.Amarillo){
            BoxRecomendadoApp.setText("Emergencia");
        }
        else {
            BoxRecomendadoApp.setText("Consultorio");
        }
    }

    public void BotonAtras(ActionEvent event) throws Exception {
        // Volver atras a Médico
        Parent root = FXMLLoader.load(getClass().getResource("/views/MedicoViews/Medico.fxml"));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Ir atrás");
        alert.setContentText("¿Estás seguro de que deseas volver a la pestaña anterior?");
        Optional<ButtonType> resultado = alert.showAndWait();

        //Metodo para cerrar la pestaña de Medico
        medicoStage.close();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void setMedicoStage(Stage medicoStage) {
        this.medicoStage = medicoStage;
    }
}
