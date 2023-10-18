package controllers;


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
import model.Enum.ColorTriage;
import model.Enum.EstadoCivil;
import model.Login.Rol;
import model.Medico;
import model.Paciente;
import model.RegistroEntrada;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class MedicoController {
    private List<Rol> roles;
    private Medico medico;
    @FXML
    public TableColumn<Paciente, String> colNomPac;
    @FXML
    public TableColumn<Paciente, String> colApePac;
    @FXML
    public TableColumn<ColorTriage, String> colColorTriage;
    @FXML
    public TableColumn<RegistroEntrada, LocalTime> colHoraIng;
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
        this.iniciarTabla();
    }

    @FXML
    public void recibirDatos(List<Rol> roles, Medico medico) {
        this.roles = roles;
        this.medico = medico;
        System.out.println(medico.toString());
    }

    private void iniciarTabla(){
        ObservableList datosTabla = FXCollections.observableArrayList();
        RegistroEntradaDAO registroEntradaDAO = new RegistroEntradaDAO();
        List<RegistroEntrada> listaregistros = registroEntradaDAO.obtenerTodos();

        for(RegistroEntrada registro : listaregistros){
            datosTabla.add(new PacienteTableClass(registro.getPaciente().getNombre(), registro.getPaciente().getApellido(),
                    registro.getTriage().getColorTriageFinal(), registro.getHora()));
        }

        tblPacientes.setItems(datosTabla);
        }


    public void RealizarTriage(ActionEvent event) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MedicoViews/Triage.fxml"));
        Parent root = loader.load();

        TriageController controller = loader.getController();
        controller.recibirDatos((RegistroEntrada) tblPacientes.getSelectionModel().getSelectedItem(), medico);

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

    @AllArgsConstructor
    @ToString
    @Getter
    class PacienteTableClass {
        private String nombre;
        private String apellido;
        private ColorTriage colorTriage;
        private LocalTime hora;
    }
}

