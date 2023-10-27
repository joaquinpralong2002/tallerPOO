package controllers.Sistemas;

import controllers.FuncionarioProController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditarUsuarioController {

    @FXML
    private Button bttmCrear;

    @FXML
    private Button bttmVolver;

    @FXML
    private Label lbApeFunc;

    @FXML
    private Label lbDniFunc;

    @FXML
    private Label lbNomFunc;

    @FXML
    private Label lbSectorFunc;

    @FXML
    private Label lbSectorFunc1;

    @FXML
    private TextField txtNombUsu;

    @FXML
    private PasswordField txtPassw;

    private FuncionarioProController controllerPrincipal;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.controllerPrincipal = FuncionarioProController.getControladorPrimario();
    }
    @FXML
    void CrearUsuario(ActionEvent event) {

    }

    public void Volver(ActionEvent event) throws IOException {
        if (controllerPrincipal == null ){
            System.out.println("hola");
            //controllerPrincipal.cargarEscena("/views/SistemasViews/Sistemas.fxml");
        }
    }

}

