package controllers;


import controllers.Enfermero.EnfermeroController;
import controllers.Singletons.SingletonEnfermero;
import controllers.Singletons.SingletonMedico;
import datasource.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Enfermero;
import model.FuncionarioAdministrativo;
import model.Login.AdministradorSistemas;
import model.Login.Rol;
import model.Login.Usuario;
import model.Medico;

import java.util.List;

public class LoginController {
    //Declaraciones
    private FXMLLoader loaderFuncionario;
    private FuncionarioProController funcionarioProController;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    public void handleLoginButtonAction(ActionEvent event) throws Exception {
        // Obtén los datos de inicio de sesión
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario user = usuarioDAO.obtenerUsuarioPorNombre(username);

        // Comprueba si alguno de los campos está vacío
        if (controlarCampos()) {
            //comprueba que el valor de los campos sean correctos
            if (validarUsuario(user, username, password)) {
                var funcionario = usuarioDAO.obtenerFuncionarioPorIdUsuario(user.getIdUsuario());
                if (funcionario.getClass() == FuncionarioAdministrativo.class || funcionario.getClass() == AdministradorSistemas.class) {
                    //carga la escena de un Funcionario tanto FuncionarioAdministrativo como AdministradorSistemas
                    loaderFuncionario = new FXMLLoader();
                    loaderFuncionario.setLocation(getClass().getResource("/views/FuncionarioViews/FuncionarioEma.fxml"));
                    root = loaderFuncionario.load();
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    funcionarioProController = loaderFuncionario.getController();
                    //se evalúa qué tipo de funcionario es FuncionarioAdministrativo o AdministradorSistemas
                    if (funcionario.getClass() == AdministradorSistemas.class) {
                        funcionarioProController.recibirDatos((AdministradorSistemas) funcionario);
                    } else if (funcionario.getClass() == FuncionarioAdministrativo.class) {
                        funcionarioProController.recibirDatos((FuncionarioAdministrativo) funcionario);
                    }
                    //Muestra la escena cargada
                    stage.show();
                } else if (funcionario.getClass() == Medico.class) {
                    // Carga la escena de médico
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/views/MedicoViews/Medico.fxml"));
                    Parent rootMedico = loader.load();

                    //Se busca el objeto de médico para pasarlo al controlador
                    Medico medico = usuarioDAO.obtenerMedicoPorIdUsuario(user.getIdUsuario());
                    SingletonMedico.getInstance().setMedico(medico);
                    SingletonMedico.getInstance().setRoles(user.getRoles());

                    //Carga el controlador de médico, y le envía el médico y sus roles
                    MedicoController controller = loader.getController();
                    controller.iniciarMedico();


                    // Cambia a la nueva escena
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(rootMedico);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    FXMLLoader loaderEnfermero = new FXMLLoader();
                    loaderEnfermero.setLocation(getClass().getResource("/views/EnfermeroViews/Enfermero.fxml"));
                    Parent rootEnfermero = loaderEnfermero.load();

                    Enfermero enfermero = usuarioDAO.obtenerEnfermeroPorIdUsuario(user.getIdUsuario());

                    SingletonEnfermero.getInstance().setEnfermero(enfermero);
                    SingletonEnfermero.getInstance().setRoles(user.getRoles());

                    EnfermeroController enfermeroController = loaderEnfermero.getController();
                    enfermeroController.iniciarEnfermero();

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(rootEnfermero);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }

    }

    private boolean controlarCampos(){
        // Comprueba si alguno de los campos está vacío
        if (usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText("Sesión incorrecta");
            alert.setContentText("Debe rellenar ambos campos.");
            alert.show();
            return false;
        }
        return true;
    }

    private boolean validarUsuario(Usuario user, String u, String c) {
        if(user == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Sesión incorrecta");
            alert.setContentText("No existe ningún usuario con esas credenciales.");
            alert.show();
            return false;
        } else if (user.getNombreUsuario().equals(u)
                && user.getContrasenia().equals(c)){
            return true;
        }  else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Sesión incorrecta");
            alert.setContentText("El nombre de usuario o la contraseña son incorrectos.");
            alert.show();
            return false;
        }
    }
}
