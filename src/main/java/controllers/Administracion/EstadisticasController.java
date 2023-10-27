package controllers.Administracion;

import controllers.FuncionarioProController;
import datasource.FuncionarioAdministrativoDAO;
import datasource.FuncionarioDAO;
import datasource.MedicoDAO;
import datasource.PacienteDAO;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import model.Enum.ColorTriage;
import model.Paciente;
import model.Medico;
import model.Triage;

import java.time.LocalDate;
import java.util.Map;

public class EstadisticasController {

    @Setter
    private FuncionarioProController controllerPrincipal;
    @FXML
    private AnchorPane paneFondo;


    //PANEL 1


    @FXML
    private TextField nombreMedicoTextField;
    @FXML
    private TextField apellidoMedicoTextField;
    @FXML
    private Label cantidadPacientesPane1Label;
    @FXML
    private Label cantidadPacientesInvisibleLabelPane1;
    @FXML
    private DatePicker fechaInicialPane1;
    @FXML
    private DatePicker fechaFinalPane1;


    //PANEL 2


    @FXML
    private DatePicker fechaInicialPane2;
    @FXML
    private DatePicker fechaFinalPane2;
    @FXML
    private TextField edad1Pane2;
    @FXML
    private TextField edad2Pane2;
    @FXML
    private Label cantidadPacientesPane2Label;
    @FXML
    private Label cantidadPacientesInvisibleLabelPane2;


    //PANEL 3


    @FXML
    private DatePicker fechaInicialPane3;
    @FXML
    private DatePicker fechaFinalPane3;
    @AllArgsConstructor
    @ToString
    @Getter
    protected class PacienteClass{
        String nombreApellido;
        Long cantidadConsultas;
        private static PacienteDAO pacienteDAO = new PacienteDAO();
        public static Paciente obtenerPaciente(Long id) {
            Paciente paciente = pacienteDAO.obtener(id);
            return paciente;
        }
    }



    //PANEL 4


    @FXML
    private DatePicker fechaInicialPane4;
    @FXML
    private DatePicker fechaFinalPane4;
    @AllArgsConstructor
    @ToString
    @Getter
    protected class MedicoClass{
        String nombreApellido;
        Long cantidadConsultas;
        private static MedicoDAO medicoDAO = new MedicoDAO();
        public static Medico obtenerMedico(Long id) {
            Medico medico = medicoDAO.obtener(id);
            return medico;
        }
    }

    //PANEL 5
    @FXML
    private DatePicker fechaInicialPane5;
    @FXML
    private DatePicker fechaFinalPane5;

    //TABLA
    @FXML
    private TableView tablaEstadisticas;

    @AllArgsConstructor
    @ToString
    @Getter
    protected class TriageClass{
        ColorTriage colorTriage;
        Long cantidadColor;
    }


    /**
     * Inicializa el controlador al cargar la vista. Se configura el controlador principal y se ocultan las etiquetas
     * que muestran la cantidad de pacientes.
     */
    @FXML
    public void initialize(){
        controllerPrincipal = FuncionarioProController.getControladorPrimario();
        cantidadPacientesInvisibleLabelPane1.setVisible(false);
        cantidadPacientesInvisibleLabelPane2.setVisible(false);
    }

    /**
     * Muestra la cantidad de pacientes atendidos por un médico en un rango de fechas especificado.
     * Cuando se presiona el botón, se hace visible un label para mostrar la cantidad de pacientes y se realiza el cálculo.
     * Si faltan datos, se muestra una alerta de error indicando que todos los campos deben completarse.
     */
    public void cantidadPacientesPorMedico(){
        //Al apretar el botón se hace visible el label para mostrar la cantidad de pacientes.
        cantidadPacientesInvisibleLabelPane1.setVisible(true);
        MedicoDAO medicoDAO = new MedicoDAO();
        String nombreMedico = nombreMedicoTextField.getText();
        String apellidoMedico = apellidoMedicoTextField.getText();

        if(nombreMedico.isEmpty() || apellidoMedico.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Falta de datos");
            alert.setContentText("Debe rellenar todos los campos.");
            alert.show();
            return;
        } else {
            Medico medico = medicoDAO.obtener(nombreMedico, apellidoMedico);
            LocalDate fecha1 = fechaInicialPane1.getValue();
            LocalDate fecha2 = fechaFinalPane1.getValue();
            cantidadPacientesPane1Label.setText(Long.toString(medicoDAO.cantidadPacientesAtendidos(medico, fecha1, fecha2)));
        }
    }

    /**
     * Muestra la cantidad de pacientes atendidos por un médico en un rango de fechas y un rango de edades específicos.
     * Cuando se presiona el botón, se hace visible un label para mostrar la cantidad de pacientes y se realiza el cálculo.
     * Si faltan datos, se muestra una alerta de error indicando que todos los campos deben completarse.
     */
    public void cantidadPacientesAtendidosPorFechasYEdades(){
        //Al apretar el botón se hace visible el label para mostrar la cantidad de pacientes.
        cantidadPacientesInvisibleLabelPane2.setVisible(true);
        MedicoDAO medicoDAO = new MedicoDAO();


        if(edad1Pane2.getText().isEmpty() || edad2Pane2.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Falta de datos");
            alert.setContentText("Debe rellenar todos los campos.");
            alert.show();
            return;
        } else {
            int edad1 = Integer.parseInt(edad1Pane2.getText());
            int edad2 = Integer.parseInt(edad2Pane2.getText());
            LocalDate fecha1 = fechaInicialPane2.getValue();
            LocalDate fecha2 = fechaFinalPane2.getValue();
            cantidadPacientesPane2Label.setText(Long.toString(medicoDAO.cantidadPacientesAtendidos(fecha1, fecha2, edad1, edad2)));
        }

    }

    /**
     * Muestra una tabla de estadísticas con los pacientes que han tenido más consultas médicas en un rango de fechas específico.
     * Al llamar a este método, se eliminan las columnas existentes en la tabla y se crean nuevas columnas para mostrar el nombre de los pacientes y la cantidad de consultas que han tenido.
     * Además, se establece el ancho de las columnas y se popula la tabla con los datos obtenidos del DAO de Funcionario Administrativo.
     * Si no se especifican fechas, la tabla estará vacía. Los resultados se muestran en orden descendente por cantidad de consultas.
     */
    public void pacientesMasConsultasRangoFechas(){
        FuncionarioAdministrativoDAO funcionarioAdministrativoDAO = new FuncionarioAdministrativoDAO();
        PacienteDAO pacienteDAO = new PacienteDAO();

        LocalDate fechaInicial = fechaInicialPane3.getValue();
        LocalDate fechaFinal = fechaFinalPane3.getValue();

        // Elimina las columnas actuales de la tabla
        tablaEstadisticas.getColumns().clear();

        // Crea nuevas columnas
        TableColumn<Paciente, String> columnaNombreApellido = new TableColumn<>("Paciente");
        TableColumn<Paciente, Long> columnaCantidadConsultas = new TableColumn<>("Cantidad de consultas");

        //Reacomoda el tamaño de las columnas
        columnaNombreApellido.setPrefWidth(308);
        columnaCantidadConsultas.setPrefWidth(307);


        columnaNombreApellido.setCellValueFactory(new PropertyValueFactory<>("nombreApellido"));
        columnaCantidadConsultas.setCellValueFactory(new PropertyValueFactory<>("cantidadConsultas"));

        //Crea una lista con los elementos que se agregarán a la tabla
        ObservableList<PacienteClass> datosTabla = FXCollections.observableArrayList();

        // Agrega las nuevas columnas a la tabla
        tablaEstadisticas.getColumns().addAll(columnaNombreApellido, columnaCantidadConsultas);

        // Obten los datos de tu DAO
        Map<Long, Long> pacientesMasConsultas = funcionarioAdministrativoDAO.pacientesMasConsultas(fechaInicial,fechaFinal);

        for(Long key : pacientesMasConsultas.keySet()){
            Paciente paciente = PacienteClass.obtenerPaciente(key);
            datosTabla.add(new PacienteClass(paciente.getNombre()+ " " + paciente.getApellido(), pacientesMasConsultas.get(key)));
        }

        //Setea los elementos en la tabla
        tablaEstadisticas.setItems(datosTabla);

    }

    /**
     * Muestra una tabla de estadísticas con los médicos que han atendido a la mayor cantidad de pacientes en un rango de fechas específico.
     * Al llamar a este método, se eliminan las columnas existentes en la tabla y se crean nuevas columnas para mostrar el nombre de los médicos y la cantidad de pacientes atendidos.
     * Además, se establece el ancho de las columnas y se popula la tabla con los datos obtenidos del DAO de Médico.
     * Si no se especifican fechas, la tabla estará vacía. Los resultados se muestran en orden descendente por cantidad de pacientes atendidos.
     */
    public void medicosMasPacientesRangoFechas(){
        MedicoDAO medicoDAO = new MedicoDAO();

        LocalDate fechaInicial = fechaInicialPane4.getValue();
        LocalDate fechaFinal = fechaFinalPane4.getValue();

        tablaEstadisticas.getColumns().clear();

        // Crea nuevas columnas
        TableColumn<Medico, String> columnaNombreApellido = new TableColumn<>("Medico");
        TableColumn<Medico, Long> columnaCantidadConsultas = new TableColumn<>("Cantidad de consultas");

        //Reacomoda el tamaño de las columnas
        columnaNombreApellido.setPrefWidth(308);
        columnaCantidadConsultas.setPrefWidth(307);

        columnaNombreApellido.setCellValueFactory(new PropertyValueFactory<>("nombreApellido"));
        columnaCantidadConsultas.setCellValueFactory(new PropertyValueFactory<>("cantidadConsultas"));

        //Crea una lista con los elementos que se agregarán a la tabla
        ObservableList<MedicoClass> datosTabla = FXCollections.observableArrayList();

        // Agrega las nuevas columnas a la tabla
        tablaEstadisticas.getColumns().addAll(columnaNombreApellido, columnaCantidadConsultas);

        // Obteniendo los datos del DAO
        Map<Long, Long> medicoMasConsultas = medicoDAO.obtenerMedicoConMasPacientesAtendidosEnRango(fechaInicial,fechaFinal);

        for(Long key : medicoMasConsultas.keySet()){
            Medico medico = MedicoClass.obtenerMedico(key);
            datosTabla.add(new MedicoClass(medico.getNombre()+ " " + medico.getApellido(), medicoMasConsultas.get(key)));
        }

        //Setea los elementos en la tabla
        tablaEstadisticas.setItems(datosTabla);
    }

    /**
     * Muestra una tabla de estadísticas con los triages realizados por colores en un rango de fechas específico.
     * Al llamar a este método, se eliminan las columnas existentes en la tabla y se crean nuevas columnas para mostrar los colores de triage
     * y la cantidad de triages realizados en cada color.
     * Además, se establece el ancho de las columnas y se popula la tabla con los datos obtenidos del DAO de Médico.
     * Si no se especifican fechas, la tabla estará vacía. Los resultados se muestran en orden ascendente por color de triage.
     */
    public void triagesRealizadosRangoFechas() {
        MedicoDAO medicoDAO = new MedicoDAO();

        LocalDate fechaInicial = fechaInicialPane5.getValue();
        LocalDate fechaFinal = fechaFinalPane5.getValue();

        tablaEstadisticas.getColumns().clear();

        // Crea nuevas columnas
        TableColumn<TriageClass, ColorTriage> columnaColorTriage = new TableColumn<>("Color de triage");
        TableColumn<TriageClass, Long> cantidadDeTriages = new TableColumn<>("Cantidad de triages");

        columnaColorTriage.setPrefWidth(308);
        cantidadDeTriages.setPrefWidth(307);

        columnaColorTriage.setCellValueFactory(new PropertyValueFactory<>("colorTriage"));
        cantidadDeTriages.setCellValueFactory(new PropertyValueFactory<>("cantidadColor"));

        // Crea una lista con los elementos que se agregarán a la tabla
        ObservableList<TriageClass> datosTabla = FXCollections.observableArrayList();

        tablaEstadisticas.getColumns().addAll(columnaColorTriage, cantidadDeTriages);


        // Obtiene los datos de la base de datos
        Map<ColorTriage, Long> triages = medicoDAO.TriageRangoFechas(fechaInicial, fechaFinal);

        // Verifica si triages tiene datos
        if (triages != null && !triages.isEmpty()) {
            for (ColorTriage key : triages.keySet()) {
                datosTabla.add(new TriageClass(key, triages.get(key)));
            }
        }

        tablaEstadisticas.setItems(datosTabla);
    }

}
