package controllers.Triage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Enum.ColorTriage;
import model.Medico;
import model.RegistroEntrada;
import model.Triage;

import java.io.IOException;

public class ModificarTriageController {
    @FXML
    private TextArea motivoCambioTextArea;
    @FXML
    private ComboBox<ColorTriage> nuevoColorTriageComboBox;
    @FXML
    private Button aceptarButton;
    private ColorTriage colorAntesModificacion;
    private ColorTriage colorDespuesModificacion;
    private Medico medico;
    private RegistroEntrada registroEntrada;

    public void initialize() {
        nuevoColorTriageComboBox.getItems().addAll(ColorTriage.Rojo, ColorTriage.Naranja, ColorTriage.Amarillo,
                ColorTriage.Verde, ColorTriage.Azul);
    }

    public void recibirDatos(Medico medico, RegistroEntrada registroEntrada, ColorTriage colorTriage){
        this.colorAntesModificacion = colorTriage;
        this.registroEntrada = registroEntrada;
        this.medico = medico;
    }

    public void aceptarCambioTriage(){
        if((nuevoColorTriageComboBox.getSelectionModel().getSelectedItem() == null) || motivoCambioTextArea.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Debe asignar el nuevo color y brindar el motivo del cambio.");
            alert.showAndWait();
            return;
        } else {
            if(Triage.controlarTriage(colorAntesModificacion, nuevoColorTriageComboBox.getValue())){
                TriageSingleton.getInstance().setColorTriageCambiado(colorAntesModificacion);
                TriageSingleton.getInstance().setColorTriageAsignado(nuevoColorTriageComboBox.getValue());
                TriageSingleton.getInstance().setMotivoCambioTriage(motivoCambioTextArea.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmación");
                alert.setContentText("El cambio de color se produjo satisfactoriamente");
                alert.showAndWait();
                return;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("El cambio de color no puede superar dos niveles.");
                alert.showAndWait();
                return;
            }
        }
    }


    public void cancelarCambioTriage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MedicoViews/Triage/Triage.fxml"));
        Parent root = loader.load();
        TriageController controller = loader.getController();
        controller.recibirDatos(registroEntrada, medico);
        controller.restaurarEstado();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
