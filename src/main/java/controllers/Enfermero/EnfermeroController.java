package controllers.Enfermero;

import controllers.AtencionPaciente.ElegirBoxAtencionAtenderPaciente;
import controllers.BuscarPacienteVisualizarRegistroController;
import controllers.Enfermero.TriageEnfermero.TriageEnfermeroController;
import controllers.MedicoController;
import controllers.Singletons.SingletonEnfermero;
import controllers.Triage.TriageController;
import datasource.PacienteDAO;
import datasource.RegistroEntradaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import model.*;
import model.Enum.ColorTriage;
import model.Login.Rol;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class EnfermeroController {
    @AllArgsConstructor
    @ToString
    @Getter
    protected class PacienteTableClass {
        Long id;
        String nombre;
        String apellido;
        ColorTriage colorTriage;
        LocalTime hora;
        String motivo;
        int dni;
        private static PacienteDAO pacienteDAO = new PacienteDAO();

        public Paciente obtenerPaciente(Long id) {
            PacienteDAO pacienteDAO = new PacienteDAO();
            Paciente paciente = pacienteDAO.obtener(id);
            return paciente;
        }
    }

    private List<Rol> roles;
    private Enfermero enfermero;
    @FXML
    private TableColumn<Paciente, String> colNomPac;
    @FXML
    private TableColumn<Paciente, String> colApePac;
    @FXML
    private TableColumn<ColorTriage, String> colColorTriage;
    @FXML
    private TableColumn<RegistroEntrada, LocalTime> colHoraIng;
    @FXML
    public TableColumn<RegistroEntrada, String> colMotivo;
    @FXML
    private TableView tblPacientes;
    @FXML
    private TextField txtNombPac;
    @FXML
    private ComboBox cmboxTriage;
    @FXML
    private TextField txtApellPac;
    @FXML
    private TextField txtDNIPac;
    @FXML
    private Button bttmRealTriage;

    private ObservableList<PacienteTableClass> datosTabla = FXCollections.observableArrayList();


    public void iniciarEnfermero(){
        enfermero = SingletonEnfermero.getInstance().getEnfermero();
        roles = SingletonEnfermero.getInstance().getRoles();
        boolean contieneTriage = false;
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).getNombre().equals("Triage")) contieneTriage = true;
        }
        if (!contieneTriage) {
            bttmRealTriage.setVisible(false);
        }
    }

    @FXML
    public void initialize() {
        this.cmboxTriage.getItems().addAll(ColorTriage.values());
        this.colNomPac.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.colApePac.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        this.colColorTriage.setCellValueFactory(new PropertyValueFactory<>("colorTriage"));
        this.colHoraIng.setCellValueFactory(new PropertyValueFactory<>("hora"));
        this.colMotivo.setCellValueFactory(new PropertyValueFactory<>("motivo"));
        this.iniciarTabla();
        txtNombPac.textProperty().addListener((observable,oldValue,newValue) -> filtrarPacientes());
        txtApellPac.textProperty().addListener((observable,oldValue,newValue) -> filtrarPacientes());
        txtDNIPac.textProperty().addListener((observable,oldValue,newValue) -> filtrarPacientes());
        cmboxTriage.valueProperty().addListener((observable,oldValue,newValue) -> filtrarPacientes());
    }

    private void iniciarTabla() {

        RegistroEntradaDAO registroEntradaDAO = new RegistroEntradaDAO();
        List<RegistroEntrada> listaRegistros = registroEntradaDAO.obtenerTodos();

        for (RegistroEntrada registro : listaRegistros) {
            if (!registro.isAtendido()) {
                if (registro.isTriagiado()) {
                    if (registro.getTriage().getColorTriageFinal() != ColorTriage.Ninguno) {
                        datosTabla.add(new PacienteTableClass(registro.getPaciente().getId(), registro.getPaciente().getNombre(), registro.getPaciente().getApellido(),
                                registro.getTriage().getColorTriageFinal(), registro.getHora(), registro.getDescripcion(), registro.getPaciente().getDNI()));
                    } else {
                        datosTabla.add(new PacienteTableClass(registro.getPaciente().getId(), registro.getPaciente().getNombre(), registro.getPaciente().getApellido(),
                                registro.getTriage().getColorTriageRecomendado(), registro.getHora(), registro.getDescripcion(), registro.getPaciente().getDNI()));
                    }
                } else {
                    datosTabla.add(new PacienteTableClass(registro.getPaciente().getId(), registro.getPaciente().getNombre(), registro.getPaciente().getApellido(),
                            ColorTriage.Ninguno, registro.getHora(), registro.getDescripcion(), registro.getPaciente().getDNI()));
                }
                tblPacientes.setItems(datosTabla);
            }
        }
    }

    public void RealizarTriage(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/EnfermeroViews/TriageEnfermero/TriageEnfermero.fxml"));
        Parent root = loader.load();

        TriageEnfermeroController controller = loader.getController();
        PacienteTableClass pacienteTableClass = (PacienteTableClass) tblPacientes.getSelectionModel().getSelectedItem();
        if (pacienteTableClass == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Debe seleccionar un paciente de la tabla.");
            alert.showAndWait();
            return;
        }
        Paciente paciente = pacienteTableClass.obtenerPaciente(pacienteTableClass.id);
        if (paciente.getRegistrosEntradas().get(paciente.getRegistrosEntradas().size() - 1).isTriagiado()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("El paciente ya fue triagado.");
            alert.showAndWait();
            return;
        }
        RegistroEntrada registroEntrada = paciente.getRegistrosEntradas().get(paciente.getRegistrosEntradas().size() - 1);
        controller.recibirDatos(registroEntrada);
        // Cambia a la nueva escena
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void filtrarPacientes(){
        Predicate<PacienteTableClass> predicate = paciente -> {
            String nombreFilter = txtNombPac.getText().toLowerCase();
            String apellidoFilter = txtApellPac.getText().toLowerCase();
            String dniFilter = txtDNIPac.getText();
            ColorTriage colorTriageFilter = (ColorTriage) cmboxTriage.getSelectionModel().getSelectedItem();

            // Verificar si el campo no está vacío y si el paciente cumple con el filtro
            boolean nombreMatch = nombreFilter.isEmpty() || paciente.getNombre().toLowerCase().contains(nombreFilter);
            boolean apellidoMatch = apellidoFilter.isEmpty() || paciente.getApellido().toLowerCase().contains(apellidoFilter);
            boolean dniMatch = dniFilter.isEmpty() || Integer.toString(paciente.getDni()).contains(dniFilter);
            boolean colorTriageMatch = colorTriageFilter == null || paciente.getColorTriage() == colorTriageFilter;

            // Combinar resultados de todos los filtros
            return nombreMatch && apellidoMatch && dniMatch && colorTriageMatch;
        };
        tblPacientes.setItems(datosTabla.filtered(predicate));
    }


    public void verHistorialClinico(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MedicoViews/BuscarPacienteVisualizarRegistros.fxml"));
        Parent root = loader.load();
        BuscarPacienteVisualizarRegistroController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void CerrarSesion(ActionEvent event) throws Exception {
        // Cierra la sesión del médico
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setContentText("¿Estás seguro de que deseas cerrar sesión?");
        Optional<ButtonType> resultado = alert.showAndWait();

        if (resultado.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    public void borrarColorTriageSeleccionado(){
        cmboxTriage.setValue(null);
    }

}
