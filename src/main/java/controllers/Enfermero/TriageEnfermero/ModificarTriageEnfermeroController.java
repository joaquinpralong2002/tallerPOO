package controllers.Enfermero.TriageEnfermero;

import controllers.Singletons.SingletonControladorPrimarioSalud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import lombok.Setter;
import model.Enum.ColorTriage;
import model.Triage;

import java.io.IOException;

public class ModificarTriageEnfermeroController {
    @FXML
    private TextArea motivoCambioTextArea;
    @FXML
    private ComboBox<ColorTriage> nuevoColorTriageComboBox;
    @Setter
    private DatosTriageEnfermero datosTriageEnfermero;

    /**
     * Inicializa el controlador de modificación de triaje.
     * Este método se ejecuta al cargar la vista de modificación de triaje y se encarga de
     * poblar la lista desplegable con los colores de triaje disponibles para su modificación.
     */
    public void initialize() {
        nuevoColorTriageComboBox.getItems().addAll(ColorTriage.Rojo, ColorTriage.Naranja, ColorTriage.Amarillo,
                ColorTriage.Verde, ColorTriage.Azul);
        this.datosTriageEnfermero = SingletonControladorPrimarioSalud.getInstance().getController().getDatosTriageEnfermero();
    }

    /**
     * Procesa la solicitud de cambio de color de triaje y registra el motivo del cambio.
     *
     * Este método verifica si se ha seleccionado un nuevo color de triaje y si se ha proporcionado
     * un motivo para el cambio. Luego, controla si el cambio de color es válido en función de las
     * restricciones específicas. Si el cambio es válido y el motivo no supera el límite de caracteres,
     * registra el nuevo color y el motivo del cambio. En caso contrario, muestra mensajes de error
     * apropiados. Finalmente, muestra un mensaje de confirmación al usuario.
     */
    public void aceptarCambioTriage(){
        if((nuevoColorTriageComboBox.getSelectionModel().getSelectedItem() == null) || (motivoCambioTextArea.getText().isEmpty())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Debe asignar el nuevo color y brindar el motivo del cambio.");
            alert.showAndWait();
            return;
        } else {
            if(Triage.controlarTriage(this.datosTriageEnfermero.getColorTriageAsignado(), nuevoColorTriageComboBox.getValue())){
                if(motivoCambioTextArea.getText().length() > 255){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Se superó el límite de carácteres.");
                    alert.showAndWait();
                    return;
                } else {
                    datosTriageEnfermero.setColorTriageCambiado(nuevoColorTriageComboBox.getValue());
                    datosTriageEnfermero.setMotivoCambioTriage(motivoCambioTextArea.getText());
                    System.out.println("el enfermero es:" + datosTriageEnfermero.getEnfermero());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmación");
                    alert.setContentText("El cambio de color se produjo satisfactoriamente");
                    alert.showAndWait();
                    return;
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("El cambio de color no puede superar dos niveles.");
                alert.showAndWait();
                return;
            }
        }
    }

    /**
     * Permite al usuario volver a la vista de triaje original.
     *
     * @param event El evento de acción que desencadenó esta función.
     * @throws IOException Si ocurre un error al cargar la vista original.
     */
    public void volverCambioTriage(ActionEvent event) throws IOException {
        SingletonControladorPrimarioSalud.getInstance().getController().setDatosTriageEnfermero(this.datosTriageEnfermero);
        SingletonControladorPrimarioSalud.getInstance().getController().cargarEscena("/views/EnfermeroViews/TriageEnfermero/TriageEnfermero.fxml", false);
    }
}
