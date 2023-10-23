package controllers;

import datasource.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import model.Login.Rol;
import model.Login.Usuario;
import model.Sector;

import java.util.List;

public class SistemasController {
    public TableView tblUsuarios;
    public TableColumn colNombUsu;
    public TableColumn colRoles;
    public TableColumn colNombFunc;
    public TableColumn colApeFunc;
    public TableColumn colSector;
    public TextField txtNombUsu;
    public TextField txtDniFunc;

    @FXML
    public void initialize(){
        this.colNombUsu.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
        this.colNombUsu.setCellValueFactory(new PropertyValueFactory<>("roles"));
        this.colNombUsu.setCellValueFactory(new PropertyValueFactory<>("nombreFuncionario"));
        this.colNombUsu.setCellValueFactory(new PropertyValueFactory<>("apellidoFuncionario"));
        this.colNombUsu.setCellValueFactory(new PropertyValueFactory<>("sectorFuncionario"));
        //this.iniciarTabla();
    }

    public void iniciarTabla(){
        ObservableList<UsuarioTableClass> datosTabla = FXCollections.observableArrayList();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.obtenerTodos();
        for(Usuario u: usuarios){
            datosTabla.add(new UsuarioTableClass(u.getIdUsuario(),u.getNombreUsuario(),u.getRoles(),u.getFuncionario().getNombre(),u.getFuncionario().getApellido(),u.getFuncionario().getSector()));
        }
        this.tblUsuarios.setItems(datosTabla);
    }

    public void CerrarSesion(ActionEvent actionEvent) {
    }

    public void BuscarUsuario(ActionEvent actionEvent) {
    }

    public void CrearUsuario(ActionEvent actionEvent) {
    }

    public void EditarUsuario(ActionEvent actionEvent) {
    }

    public void EliminarUsuario(ActionEvent actionEvent) {
    }

    @AllArgsConstructor
    @ToString
    @Getter
    protected class UsuarioTableClass{
        Long id;
        String nombreUsuario;
        List<Rol> Roles;
        String nombreFuncionario;
        String apellidoFuncionario;
        Sector sectorFuncionario;
    }
}
