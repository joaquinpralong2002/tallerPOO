package controllers;

import datasource.RegistroEntradaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import model.*;
import model.Enum.ColorTriage;
import model.Enum.LugarAtencion;
import model.Login.Rol;


import java.time.LocalDate;
import java.util.List;

public class HistorialClinicoController {
    @AllArgsConstructor
    @Getter
    @ToString
    protected class DatosRegistro{
        LocalDate fecha;
        Medico medico;
        LugarAtencion lugarAtencion;
        //private ResultadoDiagnostico resultadoDiagnostico;
    }

    @FXML
    private TableView registrosTableView;
    @FXML
    private TableColumn<LocalDate, String> fechaColumn;
    @FXML
    private TableColumn<Medico, String> medicoColumn;
    @FXML
    private TableColumn<LugarAtencion, String> lugarAtencionColumn;
    private Paciente paciente;
    private ObservableList<DatosRegistro> datosTabla = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        this.fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        this.medicoColumn.setCellValueFactory(new PropertyValueFactory<>("medico"));
        this.lugarAtencionColumn.setCellValueFactory(new PropertyValueFactory<>("lugarAtencion"));
    }

    public void recibirDatos(Paciente paciente){
        this.paciente = paciente;
        this.iniciarTabla();
    }

    private void iniciarTabla() {
        List<Registro> registrosPaciente = paciente.getRegistros();

        for(Registro registroPaciente : registrosPaciente){
            datosTabla.add(new DatosRegistro(registroPaciente.getFechaRegistro(), registroPaciente.getMedico(),
                    registroPaciente.getLugarAtencion()));
        }
        registrosTableView.setItems(datosTabla);
    }

}
