package controllers;

import datasource.UsuarioDAO;
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
import model.Login.Usuario;

public class LoginController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    @FXML
    private Button Loginbutton;

    public void handleLoginButtonAction(ActionEvent event) throws Exception {
        // Obtén los datos de inicio de sesión
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        UsuarioDAO usuarioDAO = new UsuarioDAO();


        // Comprueba si alguno de los campos está vacío
        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText("Sesión incorrecta");
            alert.setContentText("Debe rellenar ambos campos.");
            alert.show();
        } else {
            boolean existeUsuario = usuarioDAO.existeUsuarioPorNombre(username);
            if (existeUsuario) {
                if(username.equals(usuarioDAO.obtenerUsuarioPorNombre(username).getNombreUsuario())
                && usuarioDAO.obtenerUsuarioPorNombre(username).getContrasenia().equals(password)){
                    //if(usuarioDAO.obtenerUsuarioPorNombre(username).getRoles().size() == 1){
                        Usuario usuario = usuarioDAO.obtenerUsuarioPorNombre(username);
                        String rol = usuario.getRoles().get(0).getNombre();
                        switch (rol){
                            case "Medico":
                                    // Carga la escena de medico

                                    Parent rootMedico = FXMLLoader.load(getClass().getResource("/views/MedicoViews/Medico.fxml"));

                                    // Cambia a la nueva escena
                                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                                    Scene scene = new Scene(rootMedico);
                                    stage.setScene(scene);
                                    stage.show();
                                break;
                            case "Administrador":
                                break;
                            case "Funcionario":
                                    Parent rootFuncionario = FXMLLoader.load(getClass().getResource("/views/FuncionarioViews/Funcionario.fxml"));

                                    Stage stage1 = (Stage)((Node)event.getSource()).getScene().getWindow();
                                    Scene scene1 = new Scene(rootFuncionario);
                                    stage1.setScene(scene1);
                                    stage1.show();
                                break;
                            default:
                        }

                    //}

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Sesión incorrecta");
                    alert.setContentText("El nombre de usuario o la contraseña son incorrectos.");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Sesión incorrecta");
                alert.setContentText("No existe ningún usuario con esas credenciales.");
                alert.show();
            }
        }
    }
}
