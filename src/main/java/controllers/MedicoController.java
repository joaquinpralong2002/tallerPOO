package controllers;


import controllers.Triage.TriageController;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import model.Enum.ColorTriage;
import model.Login.Rol;
import model.Medico;
import model.Paciente;
import model.RegistroEntrada;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class MedicoController {
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

        public Paciente obtenerPaciente(Long id){
            PacienteDAO pacienteDAO = new PacienteDAO();
            Paciente paciente = pacienteDAO.obtener(id);
            return paciente;
        }
    }

    private List<Rol> roles;
    private Medico medico;
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
    @FXML
    private Button bttmAtender;
    @FXML
    private Button bttmCerrarSesion;



    @FXML
    public void initialize(){
        this.cmboxTriage.getItems().addAll(ColorTriage.values());
        this.colNomPac.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.colApePac.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        this.colColorTriage.setCellValueFactory(new PropertyValueFactory<>("colorTriage"));
        this.colHoraIng.setCellValueFactory(new PropertyValueFactory<>("hora"));
        this.colMotivo.setCellValueFactory(new PropertyValueFactory<>("motivo"));
        this.iniciarTabla();

    }

    @FXML
    public void recibirDatos(List<Rol> roles, Medico medico) {
        this.roles = roles;
        this.medico = medico;
        System.out.println(roles);
        boolean contieneTriage = false;
        for(int i = 0; i < roles.size(); i++){
            if(roles.get(i).getNombre().equals("Triage")) contieneTriage = true;
            System.out.println("Contiene triage: " + contieneTriage);
        }
        if(!contieneTriage){
            bttmRealTriage.setVisible(false);
        }
    }

    public void iniciarTablaDesdeTriage(){
        this.iniciarTabla();
    }

    private void iniciarTabla(){
        ObservableList<PacienteTableClass> datosTabla = FXCollections.observableArrayList();
        RegistroEntradaDAO registroEntradaDAO = new RegistroEntradaDAO();
        List<RegistroEntrada> listaRegistros = registroEntradaDAO.obtenerTodos();

        for(RegistroEntrada registro : listaRegistros){
            if(registro.isTriagiado()){
                if(registro.getTriage().getColorTriageFinal() != ColorTriage.Ninguno){
                    datosTabla.add(new PacienteTableClass(registro.getPaciente().getId(), registro.getPaciente().getNombre(), registro.getPaciente().getApellido(),
                            registro.getTriage().getColorTriageFinal(), registro.getHora(),registro.getDescripcion()));
                } else {
                    datosTabla.add(new PacienteTableClass(registro.getPaciente().getId(), registro.getPaciente().getNombre(), registro.getPaciente().getApellido(),
                            registro.getTriage().getColorTriageRecomendado(), registro.getHora(),registro.getDescripcion()));
                }
            } else {
                datosTabla.add(new PacienteTableClass(registro.getPaciente().getId(), registro.getPaciente().getNombre(), registro.getPaciente().getApellido(),
                        ColorTriage.Ninguno, registro.getHora(),registro.getDescripcion()));
            }

        }

        tblPacientes.setItems(datosTabla);
        }


    public void RealizarTriage(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MedicoViews/Triage/Triage.fxml"));
        Parent root = loader.load();

        TriageController controller = loader.getController();
        PacienteTableClass pacienteTableClass = (PacienteTableClass) tblPacientes.getSelectionModel().getSelectedItem();
        if (pacienteTableClass == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Debe seleccionar un paciente de la tabla.");
            alert.showAndWait();
            return;
        }
        Paciente paciente = pacienteTableClass.obtenerPaciente(pacienteTableClass.id);
        if (paciente.getRegistrosEntradas().get(paciente.getRegistrosEntradas().size() - 1).isTriagiado()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("El paciente ya fue triagado.");
            alert.showAndWait();
            return;
        }
        RegistroEntrada registroEntrada = paciente.getRegistrosEntradas().get(paciente.getRegistrosEntradas().size() - 1);
        controller.recibirDatos(registroEntrada, medico);
        // Cambia a la nueva escena
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void AtenderPaciente(ActionEvent event) throws Exception{
        // Atiende al paciente
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MedicoViews/ElegirBoxAtencion_AtenderPaciente.fxml"));
        Parent root = loader.load();
        PacienteTableClass pacienteTableClass = (PacienteTableClass) tblPacientes.getSelectionModel().getSelectedItem();
        if (pacienteTableClass == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Debe seleccionar un paciente de la tabla.");
            alert.showAndWait();
            return;
        }

        // Obtiene una referencia al escenario de la ventana Medico
        Stage medicoStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Pasa el escenario de la ventana Medico al controlador de ElegirBoxAtencion_AtenderPaciente
        ElegirBoxAtencionAtenderPaciente controller = loader.getController();
        controller.setMedicoStage(medicoStage);

        //Se verifica que el paciente tenga un triage asociado para atenderlo
        Long id = ((PacienteTableClass) tblPacientes.getSelectionModel().getSelectedItem()).getId();
        Paciente paciente = pacienteTableClass.obtenerPaciente(id);
        if(paciente.getRegistrosEntradas().get(paciente.getRegistrosEntradas().size() - 1).isTriagiado()) {
            // Cambia a la nueva escena
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Elegir box atención");
            stage.initModality(Modality.NONE);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No puede atender un paciente que no tenga un triage asociado.");
            alert.showAndWait();
            return;
        }
    }


    public void CerrarSesion(ActionEvent event) throws Exception {
        // Cierra la sesión del médico
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setContentText("¿Estás seguro de que deseas cerrar sesión?");
        Optional<ButtonType> resultado = alert.showAndWait();

        if(resultado.get() == ButtonType.OK){
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}

