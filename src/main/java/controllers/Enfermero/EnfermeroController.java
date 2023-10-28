package controllers.Enfermero;

import controllers.Enfermero.TriageEnfermero.TriageEnfermeroController;
import controllers.Singletons.SingletonControladorPrimarioSalud;
import controllers.Singletons.SingletonEnfermero;
import datasource.PacienteDAO;
import datasource.RegistroEntradaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
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

    /**
     * Inicializa la vista del controlador de enfermero.
     * - Obtiene la instancia de la clase Enfermero desde SingletonEnfermero y la almacena en la variable enfermero.
     * - Obtiene los roles del enfermero desde SingletonEnfermero y los almacena en la variable roles.
     * - Inicializa una variable booleana llamada contieneTriage con el valor false para verificar si el enfermero tiene el rol "Triage".
     * - Itera a través de los roles en la lista roles y compara el nombre de cada rol con "Triage". Si se encuentra el rol "Triage", cambia el valor de contieneTriage a true.
     * - Verifica el valor de contieneTriage y si es false, oculta el botón bttmRealTriage, lo que significa que el botón "Triage" no se mostrará en la vista.
     */
    public void iniciarEnfermero(){
        //Verifica que el enfermero tenga el rol que le permita realizar triages.
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

    /**
     * Inicializa la tabla de pacientes en la vista del controlador de enfermero.
     * - Obtiene una instancia de RegistroEntradaDAO para acceder a los registros de entrada.
     * - Obtiene una lista de todos los registros de entrada desde RegistroEntradaDAO.
     * - Itera a través de los registros de entrada y agrega información a la tabla de pacientes si el registro no ha sido atendido.
     * - Verifica si el registro está triageado y, si es así, comprueba si tiene un color de triage final o recomendado.
     * - Agrega una instancia de PacienteTableClass a los datos de la tabla con información del paciente y el registro.
     * - Finalmente, establece los datos de la tabla de pacientes con los datos recopilados.
     */
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

    /**
     * Abre la ventana para realizar el triaje de un paciente seleccionado desde la tabla de pacientes en la vista de enfermero.
     *
     * @param event El evento que desencadenó la acción (pulsación del botón "Realizar Triaje").
     * @throws Exception Si ocurre un error durante el proceso.
     */
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
        SingletonControladorPrimarioSalud.getInstance().getController().setRegistroEntrada(registroEntrada);
        // Cambia a la nueva escena
        SingletonControladorPrimarioSalud.getInstance().getController().cargarEscena("/views/EnfermeroViews/TriageEnfermero/TriageEnfermero.fxml");

    }

    /**
     * Filtra y muestra los pacientes en la tabla de pacientes según los criterios especificados en los campos de búsqueda.
     * Los pacientes se filtran por nombre, apellido, DNI y color de triaje.
     */
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


    /**
     * Cierra la sesión actual del médico y regresa a la pantalla de inicio de sesión.
     *
     * @param event El evento de acción que desencadena esta operación.
     * @throws Exception Si se produce un error al cargar la vista de inicio de sesión.
     */
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
    /**
     * Borra la selección del ComboBox de color de triaje.
     * Esto restablece el ComboBox de color de triaje a su valor predeterminado (ninguno).
     */
    public void borrarColorTriageSeleccionado(){
        cmboxTriage.setValue(null);
    }

    public void verHistorialClinico(){

    }
}
