package controllers;


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
import model.Enum.ColorTriage;
import model.Enum.EstadoCivil;
import model.Paciente;
import model.RegistroEntrada;
import model.Triage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MedicoController {
    public TableColumn colNomPac;
    public TableColumn colApePac;
    public TableColumn colColorTriage;
    public TableColumn colHoraIng;
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
        cmboxTriage.getItems().addAll(ColorTriage.values());

        this.colNomPac.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        this.colApePac.setCellValueFactory(new PropertyValueFactory<>("Apellido"));
        this.colColorTriage.setCellValueFactory(new PropertyValueFactory<>("Color Triage"));
        this.colHoraIng.setCellValueFactory(new PropertyValueFactory<>("Hora Ingreso"));

        RegistroEntradaDAO registroEntradaDAO = new RegistroEntradaDAO();
        List<RegistroEntrada> registrosEntradas = registroEntradaDAO.obtenerTodos();
        ObservableList datosOB = FXCollections.observableArrayList();

        /*while (registrosEntradas.iterator().hasNext()){
            RegistroEntrada registroEntrada = registrosEntradas.iterator().next();
            Paciente paciente = registroEntrada.getPaciente();
            Triage triage = registroEntrada.getTriage();

            String nombrePaciente = paciente.getNombre();
            String apellidoPaciente = paciente.getApellido();
            String colorTriage = triage.getColorTriageRecomendado().name();
            LocalTime horaIngreso = registroEntrada.getHora();

            datosOB.addAll(nombrePaciente,apellidoPaciente,colorTriage,horaIngreso);
        }*/
        this.tblPacientes.setItems(datosOB);
    }


    public void RealizarTriage(ActionEvent event) throws Exception {
        Paciente paciente = new Paciente("Juan" ,"Pérez", LocalDate.of(1975,11,3),"Sargento Rodriguez",20113654,
                4259761,3454698743L, EstadoCivil.Casado,"juancitoperez@gmail.com"
                ,"Pepe Sand");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MedicoViews/Triage.fxml"));
        Parent root = loader.load();

        TriageController controller = loader.getController();
        controller.recibirDatos(paciente);
        //controller.recibirDatos((Paciente) tblPacientes.getSelectionModel().getSelectedItem());

        // Cambia a la nueva escena
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void AtenderPaciente(ActionEvent event) throws Exception{
        // Atiende al paciente
        Parent root = FXMLLoader.load(getClass().getResource("/views/MedicoViews/AtenderPaciente.fxml"));

        // Cambia a la nueva escena
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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

        if(resultado.get() == ButtonType.OK){
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

}
