package controllers.Sistemas;

import datasource.FuncionarioDAO;
import datasource.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Funcionario;
import model.Login.Usuario;

import java.io.IOException;

public class CrearUsuarioController {
    public TextField txtNombUsu;
    public PasswordField txtPassw;
    public CheckBox cbFuncionario;
    public CheckBox cbInformatico;
    public CheckBox cbMedico;
    public CheckBox cbEnfermero;
    public TextField txtDniFunc;
    public Label lbNomFunc;
    public Label lbApeFunc;
    public Label lbDniFunc;
    public Label lbSectorFunc;

    Funcionario funcionario;

    public void BuscarFuncionario(ActionEvent actionEvent) {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        String dni = this.txtDniFunc.getText();
        try {
            ValidarDNI(dni);

            funcionario = funcionarioDAO.obtenerPorDni(dni);
            if(funcionario != null) {
                this.lbNomFunc.setText(funcionario.getNombre());
                this.lbApeFunc.setText(funcionario.getApellido());
                this.lbDniFunc.setText(String.valueOf(funcionario.getDNI()));
                this.lbSectorFunc.setText(funcionario.getSector().getNombre());
            }else {
                throw new Exception("El funcionario no existe");
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Problemas para obtener los datos del funcionario.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void CrearUsuario(ActionEvent actionEvent) {
        String nombreUsusario = this.txtNombUsu.getText();
        String contraseniaUsuario = this.txtPassw.getText();
        try {
            comprobarCampos(nombreUsusario, contraseniaUsuario);
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.agregar(new Usuario(nombreUsusario, contraseniaUsuario,funcionario));
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Problemas en la creacion del usuario.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    private void comprobarCampos(String nombreUsusario, String contraseniaUsuario) throws Exception {
        boolean comprobarNombre = nombreUsusario.matches("^[^s]+$");
        boolean comprobarContrasenia = contraseniaUsuario.matches("^[^s]+$");
        boolean comprobarFuncionario = funcionario == null;

        if(!comprobarNombre && !comprobarContrasenia && comprobarFuncionario){
            throw new Exception("Campos vacios, porfavor ingrese los datos requeridos");
        }

        if (!comprobarNombre){
            throw new Exception("El nombre de usuario no puede estar vacio");
        }

        if (!comprobarContrasenia){
            throw new Exception("La contraseña no puede estar vacia");
        }

        if (comprobarFuncionario){
            throw new Exception("Debe seleccionar un funcionario al que le sera asignado el usuario");
        }
    }

    public void ValidarDNI(String dni) throws Exception {
        if(!dni.matches("\\d+")){
            throw  new Exception("El dni ingresado debe ser un numero entero y no debe estar vacio");
        }
     }
    public void Volver(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/SistemasViews/Sistemas.fxml"));
        Parent rootFuncionario = loader.load();

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(rootFuncionario);
        stage.setScene(scene);
        stage.show();
    }
}
