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

    /**
     * Inicia la información del administrador de sistemas cargando los roles y el administrador desde
     * una instancia única de SingletonAdministradorSistema.
     */
    public void iniciarAdministradorSistemas(){
        this.roles = SingletonAdministradorSistema.getInstance().getRoles();
        this.adminSistemas = SingletonAdministradorSistema.getInstance().getAdministradorSistemas();
    }

    @FXML
    public void initialize(){
        this.colNombUsu.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
        this.colRoles.setCellValueFactory(new PropertyValueFactory<>("roles"));
        this.colNombFunc.setCellValueFactory(new PropertyValueFactory<>("nombreFuncionario"));
        this.colApeFunc.setCellValueFactory(new PropertyValueFactory<>("apellidoFuncionario"));
        this.colSector.setCellValueFactory(new PropertyValueFactory<>("sectorFuncionario"));
        this.iniciarTabla();

        txtNombUsu.textProperty().addListener((observable,oldValue,newValue) -> filtrarUsuario());
    }

    /**
     * Filtra los elementos en la tabla de usuarios basándose en el nombre de usuario ingresado en el campo de texto.
     * Los elementos que contienen el nombre de usuario ingresado se muestran en la tabla.
     */
    private void filtrarUsuario() {
        Predicate<UsuarioTableClass> predicate = usuario ->{
          String nombreUsuarioFiltro = txtNombUsu.getText();
          return usuario.getNombreUsuario().contains(nombreUsuarioFiltro);
        };
        tblUsuarios.setItems(datosTabla.filtered(predicate));
    }

    /**
     * Inicia la tabla de usuarios cargando los datos de usuarios desde la base de datos
     * y mostrándolos en la tabla.
     */
    public void iniciarTabla(){
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.obtenerTodos();
        for(Usuario u: usuarios){
            UsuarioTableClass usuarioTableClass = new UsuarioTableClass(u.getNombreUsuario(),u.getNombreRoles(),u.getFuncionario().getNombre(),u.getFuncionario().getApellido(),u.getFuncionario().getSector().getNombre());
            datosTabla.add(usuarioTableClass);
        }
        this.tblUsuarios.setItems(datosTabla);
    }

    /**
     * Busca un usuario en la base de datos según el nombre de usuario ingresado en el campo de texto.
     * Si se encuentra un usuario con el nombre especificado, se muestra en la tabla.
     * Si no se encuentra ningún usuario, se muestra un mensaje de error.
     *
     * @param actionEvent El evento de acción que desencadena la búsqueda.
     */
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

    /**
     * Comprueba si el nombre de usuario ingresado es válido, asegurando que no esté vacío.
     *
     * @param nombreUsuario El nombre de usuario a verificar.
     * @throws Exception Si el nombre de usuario está vacío, se arroja una excepción con un mensaje de error.
     */
    public void ComprobarCampo(String nombreUsuario) throws Exception {
        if(!nombreUsuario.matches("^[^\s]+$")){
            throw new Exception("Los campos no pueden estar vacios");
        }
    }

    /**
     * Abre la vista para crear un nuevo usuario. Carga la interfaz de usuario desde el archivo FXML correspondiente
     * y muestra la ventana para la creación de usuario.
     *
     * @param event El evento de acción que desencadena la creación de usuario.
     * @throws IOException Si ocurre un error al cargar la interfaz de usuario desde el archivo FXML.
     */
    public void CrearUsuario(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/SistemasViews/CrearUsuario.fxml"));
        Parent rootSistemas = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(rootSistemas);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Abre la vista para editar un usuario existente. Esta función se encarga de preparar la ventana
     * y realizar la selección de un usuario de la tabla antes de la edición.
     *
     * @param actionEvent El evento de acción que desencadena la edición de usuario.
     */
    public void EditarUsuario(ActionEvent actionEvent) {
        //setear ventana primero

        //seleccion de usuario
        UsuarioTableClass usuarioTableClass = (UsuarioTableClass) tblUsuarios.getSelectionModel().getSelectedItem();
        if(usuarioTableClass == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Debe seleccionar un usuario de la tabla.");
            alert.showAndWait();
        }

        //setear 2da parte de la ventana
    }

    /**
     * Elimina un usuario seleccionado de la tabla y de la base de datos después de confirmación.
     * Antes de eliminar, muestra una confirmación al usuario.
     *
     * @param actionEvent El evento de acción que desencadena la eliminación de usuario.
     */
    public void EliminarUsuario(ActionEvent actionEvent) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        UsuarioTableClass usuarioTableClass = (UsuarioTableClass) this.tblUsuarios.getSelectionModel().getSelectedItem();

        if(usuarioTableClass == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Debe seleccionar un usuario de la tabla.");
            alert.showAndWait();
        }
        Usuario usuario = usuarioDAO.obtenerUsuarioPorNombre(usuarioTableClass.nombreUsuario);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar Usuario");
        alert.setContentText("¿Estás seguro de que deseas eliminar este usuario?");
        Optional<ButtonType> resultado = alert.showAndWait();

        if (resultado.get() == ButtonType.OK) {
            usuarioDAO.borrar(usuario);
            datosTabla.remove(usuarioTableClass);
            this.tblUsuarios.refresh();

            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Información");
            alert2.setHeaderText("Usuario eliminado exitosamente");
            alert2.show();
        }
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
