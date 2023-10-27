package controllers;

import controllers.Singletons.SingletonMedico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import model.*;
import model.Enum.LugarAtencion;
import model.Login.Rol;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class HistorialClinicoController {
    @AllArgsConstructor
    @Getter
    @ToString
    protected class DatosRegistro{
        String fecha;
        String medico;
        LugarAtencion lugarAtencion;
        String resultadoDiagnostico;
    }

    @FXML
    private TableView registrosTableView;
    @FXML
    private TableColumn<LocalDate, String> fechaColumn;
    @FXML
    private TableColumn<Medico, String> medicoColumn;
    @FXML
    private TableColumn<LugarAtencion, String> lugarAtencionColumn;
    @FXML
    private Label resultadoDiagnosticoLabel;
    private Paciente paciente;
    private ObservableList<DatosRegistro> datosTabla = FXCollections.observableArrayList();
    private Medico medico = SingletonMedico.getInstance().getMedico();
    private List<Rol> roles = SingletonMedico.getInstance().getRoles();

    /**
     * Inicializa la interfaz de usuario y configura las propiedades de las columnas de la tabla de registros.
     * También establece un manejador de eventos para mostrar el resultado de diagnóstico cuando se selecciona un elemento en la tabla.
     */
    @FXML
    public void initialize() {
        this.fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        this.medicoColumn.setCellValueFactory(new PropertyValueFactory<>("medico"));
        this.lugarAtencionColumn.setCellValueFactory(new PropertyValueFactory<>("lugarAtencion"));

        registrosTableView.setOnMouseClicked(mouseEvent -> {
            DatosRegistro datosRegistro = (DatosRegistro) registrosTableView.getSelectionModel().getSelectedItem();
            if(datosRegistro != null){
                resultadoDiagnosticoLabel.setText(datosRegistro.getResultadoDiagnostico().toString());
            } else {
                resultadoDiagnosticoLabel.setText("Seleccione un elemento.");
            }
        });
    }


    public void recibirDatos(Paciente paciente){
        this.paciente = paciente;
        this.iniciarTabla();
    }

    /**
     * Inicia la tabla de registros del paciente, cargando los registros existentes del paciente
     * y mostrándolos en la tabla de visualización.
     */
    private void iniciarTabla() {
        List<Registro> registrosPaciente = paciente.getRegistros();

        for(Registro registroPaciente : registrosPaciente){
            String fecha = registroPaciente.getFechaRegistro().toString();
            String apellidoNombre = registroPaciente.getMedico().getApellido() + " " + registroPaciente.getMedico().getNombre();
            LugarAtencion lugarAtencion = registroPaciente.getLugarAtencion();
            String diagnostico = registroPaciente.getResultadoDiagnostico().getDescripcion();
            datosTabla.add(new DatosRegistro(fecha, apellidoNombre, lugarAtencion, diagnostico));
        }
        registrosTableView.setItems(datosTabla);
    }

    /**
     * Vuelve a la vista de búsqueda de pacientes.
     *
     * @param event El evento de acción que desencadena el regreso.
     * @throws IOException Si ocurre un error al cargar la vista.
     */
    public void volver(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MedicoViews/BuscarPacienteVisualizarRegistros.fxml"));
        Parent rootFuncionario = loader.load();
        MedicoController controller = loader.getController();

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(rootFuncionario);
        stage.setScene(scene);
        stage.show();
    }
}
