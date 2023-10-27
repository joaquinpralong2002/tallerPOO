package controllers;

import controllers.Singletons.SingletonAdministradorSistema;
import datasource.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import model.Login.AdministradorSistemas;
import model.Login.Rol;
import model.Login.Usuario;
import model.Medico;
import model.Sector;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class SistemasController {
    public TableView tblUsuarios;

    public TableColumn colNombUsu;
    public TableColumn colRoles;
    public TableColumn colNombFunc;
    public TableColumn colApeFunc;
    public TableColumn colSector;
    public TextField txtNombUsu;
    private ObservableList<UsuarioTableClass> datosTabla = FXCollections.observableArrayList();
    private List<Rol> roles;
    private AdministradorSistemas adminSistemas;

    private FuncionarioProController controllerPrincipal;

    public void iniciarAdministradorSistemas(){
        this.roles = SingletonAdministradorSistema.getInstance().getRoles();
        this.adminSistemas = SingletonAdministradorSistema.getInstance().getAdministradorSistemas();
    }

    @FXML
    public void initialize(){
        controllerPrincipal = FuncionarioProController.getControladorPrimario();

        this.colNombUsu.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
        this.colRoles.setCellValueFactory(new PropertyValueFactory<>("roles"));
        this.colNombFunc.setCellValueFactory(new PropertyValueFactory<>("nombreFuncionario"));
        this.colApeFunc.setCellValueFactory(new PropertyValueFactory<>("apellidoFuncionario"));
        this.colSector.setCellValueFactory(new PropertyValueFactory<>("sectorFuncionario"));
        this.iniciarTabla();

        txtNombUsu.textProperty().addListener((observable,oldValue,newValue) -> filtrarUsuario());
    }

    private void filtrarUsuario() {
        Predicate<UsuarioTableClass> predicate = usuario ->{
          String nombreUsuarioFiltro = txtNombUsu.getText();
          return usuario.getNombreUsuario().contains(nombreUsuarioFiltro);
        };
        tblUsuarios.setItems(datosTabla.filtered(predicate));
    }

    public void iniciarTabla(){
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.obtenerTodos();
        for(Usuario u: usuarios){
            UsuarioTableClass usuarioTableClass = new UsuarioTableClass(u.getNombreUsuario(),u.getNombreRoles(),u.getFuncionario().getNombre(),u.getFuncionario().getApellido(),u.getFuncionario().getSector().getNombre());
            datosTabla.add(usuarioTableClass);
        }
        this.tblUsuarios.setItems(datosTabla);
    }


    public void BuscarUsuario(ActionEvent actionEvent) {
        String nombreUsuario = this.txtNombUsu.getText();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ObservableList usuarioTabla = FXCollections.observableArrayList();
        try {
            ComprobarCampo(nombreUsuario);
            Usuario usuario = usuarioDAO.obtenerUsuarioPorNombre(nombreUsuario);
            if(usuario != null){
                usuarioTabla.add(new UsuarioTableClass(usuario.getNombreUsuario(), usuario.getNombreRoles(), usuario.getFuncionario().getNombre(), usuario.getFuncionario().getApellido(), usuario.getFuncionario().getSector().getNombre()));
            }else{
                throw new Exception("Usuario no encontrado revise los datos ingresados");
            }

            this.tblUsuarios.setItems(usuarioTabla);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Datos Invalidos");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void ComprobarCampo(String nombreUsuario) throws Exception {
        if(!nombreUsuario.matches("^[^\s]+$")){
            throw new Exception("Los campos no pueden estar vacios");
        }
    }

    public void CrearUsuario(ActionEvent event) throws IOException {
        controllerPrincipal.cargarEscena("/views/SistemasViews/CrearUsuario.fxml");
        /**
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/SistemasViews/CrearUsuario.fxml"));
        Parent rootSistemas = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(rootSistemas);
        stage.setScene(scene);
        stage.show();**/
    }

    public void EditarUsuario(ActionEvent actionEvent) {
        //setear ventana primero

        //seleccion de usuario
        UsuarioTableClass usuarioTableClass = (UsuarioTableClass) tblUsuarios.getSelectionModel().getSelectedItem();
        if(usuarioTableClass == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Debe seleccionar un usuario de la tabla.");
            alert.showAndWait();
        }else  {
            controllerPrincipal.cargarEscena("/views/SistemasViews/EditarUsuario.fxml");
        }

        //setear 2da parte de la ventana
    }

    public void EliminarUsuario(ActionEvent actionEvent) {
    }


    @AllArgsConstructor
    @ToString
    @Getter
    protected class UsuarioTableClass{
        String nombreUsuario;
        List<String> roles;
        String nombreFuncionario;
        String apellidoFuncionario;
        String sectorFuncionario;
    }
}
