package controllers;

import datasource.PacienteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Paciente;

import java.io.IOException;

public class BuscarPacienteController {
    public TextField txtDni;
    public Label lbNombre;
    public Label lbApellido;
    public Label lbEdad;
    public Label lbTelFijo;
    public Label lbTelCel;
    public Label lbTelCont;
    public TextArea txtMotivo;


    public void BuscarPaciente(ActionEvent actionEvent) {
        String dni = this.txtDni.getText();
        try {
            ComprobarDni(dni);
            PacienteDAO pacienteDAO = new PacienteDAO();
            Paciente paciente = pacienteDAO.obtenerPorDni(dni);


            this.lbNombre.setText(paciente.getNombre());
            this.lbApellido.setText(paciente.getApellido());
            this.lbTelFijo.setText(String.valueOf(paciente.getTelefonoFijo()));
            this.lbTelCel.setText(String.valueOf(paciente.getTelefonoCelular()));
            this.lbTelCont.setText(String.valueOf(paciente.getPersonaContacto()));
        }catch (IllegalArgumentException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Datos Invalidos");
            alert.setContentText(e.getMessage());
            System.out.println(e);
            alert.show();
        }

    }

    public void ComprobarDni(String dni){
        if (!dni.matches("\\d+")){
            throw new IllegalArgumentException("El DNI debe ser un número entero");
        }
    }

    public void CrearRegistroEntrada(ActionEvent event) throws IOException {
    }

    public void CrearPaciente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/FuncionarioViews/RegistroEntrada.fxml"));
        Parent rootFuncionario = loader.load();

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(rootFuncionario);
        stage.setScene(scene);
        stage.show();
    }

    public void Volver(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/FuncionarioViews/Funcionario.fxml"));
        Parent rootFuncionario = loader.load();

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(rootFuncionario);
        stage.setScene(scene);
        stage.show();
    }
}
