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
import lombok.Setter;
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
    @Setter
    private DatosTriage datosTriage;

    public void initialize() {
        nuevoColorTriageComboBox.getItems().addAll(ColorTriage.Rojo, ColorTriage.Naranja, ColorTriage.Amarillo,
                ColorTriage.Verde, ColorTriage.Azul);
    }

    public void aceptarCambioTriage(){
        if((nuevoColorTriageComboBox.getSelectionModel().getSelectedItem() == null) || motivoCambioTextArea.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Debe asignar el nuevo color y brindar el motivo del cambio.");
            alert.showAndWait();
            return;
        } else {
            if(Triage.controlarTriage(this.datosTriage.getColorTriageAsignado(), nuevoColorTriageComboBox.getValue())){
                System.out.println("Color asignado: " + this.datosTriage.getColorTriageAsignado() + ", color seleccionado:" +
                        nuevoColorTriageComboBox.getValue());
                datosTriage.setColorTriageCambiado(nuevoColorTriageComboBox.getValue());
                datosTriage.setMotivoCambioTriage(motivoCambioTextArea.getText());
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


    public void volverCambioTriage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MedicoViews/Triage/Triage.fxml"));
        Parent root = loader.load();
        TriageController controller = loader.getController();
        controller.setDatosTriage(this.datosTriage);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
