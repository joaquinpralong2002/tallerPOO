package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.FuncionarioAdministrativo;
import model.Login.Rol;
import model.Login.Usuario;

import java.util.List;

public class FuncionarioController {

    private List<Rol> rolesUsuario;
    private Usuario usuarioIniciado;
    private FuncionarioAdministrativo funcionarioAdministrativoIniciado;

    public Button bttmRegEnt;

    public void CrearRegistroEntrada(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/FuncionarioViews/RegistroEntrada.fxml"));
        Parent rootFuncionario = loader.load();

        RegistroEntradaController registroEntradaController = loader.getController();
        registroEntradaController.recibirDatos(rolesUsuario, usuarioIniciado, funcionarioAdministrativoIniciado);

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(rootFuncionario);
        stage.setScene(scene);
        stage.show();
    }

    public void recibirDatos(List<Rol> roles, Usuario user, FuncionarioAdministrativo funcionarioAdministrativo) {
        this.rolesUsuario = roles;
        this.usuarioIniciado = user;
        this.funcionarioAdministrativoIniciado = funcionarioAdministrativo;
    }
}
