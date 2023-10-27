package controllers.Sistemas;

import controllers.FuncionarioProController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class EditarUsuarioController {
    public TextField txtNombUsu;
    public PasswordField txtPass;
    public CheckBox cbFuncionario;
    public CheckBox cbInformatico;
    public CheckBox cbMedico;
    public CheckBox cbEnfermero;

    private FuncionarioProController controllerPrincipal;
    @FXML
    public void initialize(){
        controllerPrincipal = FuncionarioProController.getControladorPrimario();}
    public void Editar(ActionEvent actionEvent) {
    }

    public void Volver(ActionEvent actionEvent) {
        controllerPrincipal.cargarEscena("/views/SistemasViews/Sistemas.fxml");
    }
}
