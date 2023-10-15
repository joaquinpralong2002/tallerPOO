package controllers;

import datasource.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    public void handleLoginButtonAction(ActionEvent event) {
        // Obtén los datos de inicio de sesión
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        // Comprueba si los datos son válidos
        if (username.equals(usuarioDAO.obtenerUsuarioPorNombre(username).getNombreUsuario()) &&
                password.equals(usuarioDAO.obtenerUsuarioPorNombre(username).getContrasenia())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText("Sesión correcta");
            alert.setContentText("El usuario ha iniciado sesión correctamente.");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Sesión incorrecta");
            alert.setContentText("El nombre de usuario o la contraseña son incorrectos.");
            alert.show();
        }
    }
}
