package controllers;

import datasource.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import model.Login.AdministradorSistemas;
import model.Login.Rol;
import model.Login.Usuario;
import model.Medico;
import model.Sector;

import java.io.IOException;
import java.util.List;

public class SistemasController {
    @AllArgsConstructor
    @ToString
    @Getter
    protected class UsuarioTableClass{
        String nombreUsuario;
        List<Rol> rolesUs;
        //List<String> roles = "";
        String nombreFuncionario;
        String apellidoFuncionario;
        String sectorFuncionario;

        /*
        public void obtenerRoles(){
            for(Rol rol: this.rolesUs){
                roles.add(rol.getNombre());
            }
        }*/
    }
    public TableView tblUsuarios;
    public TableColumn colNombUsu;
    public TableColumn colRoles;
    public TableColumn colNombFunc;
    public TableColumn colApeFunc;
    public TableColumn colSector;
    public TextField txtNombUsu;
    public TextField txtDniFunc;
    private List<Rol> roles;
    private AdministradorSistemas adminSistemas;
    private Usuario usuarioInicio;

    @FXML
    public void initialize(){
        this.colNombUsu.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
        this.colRoles.setCellValueFactory(new PropertyValueFactory<>("rolesUs"));
        this.colNombFunc.setCellValueFactory(new PropertyValueFactory<>("nombreFuncionario"));
        this.colApeFunc.setCellValueFactory(new PropertyValueFactory<>("apellidoFuncionario"));
        this.colSector.setCellValueFactory(new PropertyValueFactory<>("sectorFuncionario"));
        this.iniciarTabla();
    }

    public void iniciarTabla(){
        ObservableList<UsuarioTableClass> datosTabla = FXCollections.observableArrayList();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.obtenerTodos();
        for(Usuario u: usuarios){
            UsuarioTableClass usuarioTableClass = new UsuarioTableClass(u.getNombreUsuario(),u.getRoles(),u.getFuncionario().getNombre(),u.getFuncionario().getApellido(),u.getFuncionario().getSector().getNombre());
            //usuarioTableClass.obtenerRoles();
            datosTabla.add(usuarioTableClass);
        }
        this.tblUsuarios.setItems(datosTabla);
    }

    public void recibirDatos(List<Rol> roles, Usuario user,AdministradorSistemas adminSistemas) {
        this.roles = roles;
        this.usuarioInicio = user;
        this.adminSistemas = adminSistemas;
    }

    public void BuscarUsuario(ActionEvent actionEvent) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ObservableList usuarioTabla = FXCollections.observableArrayList();
        Usuario usuario1 = usuarioDAO.obtenerUsuarioPorNombre(this.txtNombUsu.getText());
        if(usuario1 != null){
            usuarioTabla.add(new UsuarioTableClass(usuario1.getNombreUsuario(), usuario1.getRoles(), usuario1.getFuncionario().getNombre(), usuario1.getFuncionario().getApellido(), usuario1.getFuncionario().getSector().getNombre()));
        }
        this.tblUsuarios.setItems(usuarioTabla);
    }

    public void CrearUsuario(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/SistemasViews/CrearUsuario.fxml"));
        Parent rootSistemas = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(rootSistemas);
        stage.setScene(scene);
        stage.show();
    }

    public void EditarUsuario(ActionEvent actionEvent) {
    }

    public void EliminarUsuario(ActionEvent actionEvent) {
    }

    public void CerrarSesion(ActionEvent actionEvent) {
    }
}
