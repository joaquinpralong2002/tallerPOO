package controllers;

import controllers.Singletons.SingletonAdministradorSistema;
import controllers.Singletons.SingletonUsuario;
import controllers.Sistemas.EditarUsuarioController;
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
    private FuncionarioProController controllerPrincipal;

    public void iniciarAdministradorSistemas(){
        controllerPrincipal = FuncionarioProController.getControladorPrimario();
        this.roles = SingletonAdministradorSistema.getInstance().getRoles();
        this.adminSistemas = SingletonAdministradorSistema.getInstance().getAdministradorSistemas();
    }
    /**
     * Inicializa la vista y configura las propiedades de las columnas en una tabla.
     * También inicia la tabla y configura un filtro para el nombre de usuario.
     */
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

    /**
     * Inicializa la tabla de usuarios cargando datos de la base de datos y mostrándolos en la vista.
     *
     * Este método obtiene la lista de usuarios desde la base de datos utilizando la clase UsuarioDAO,
     * crea objetos de UsuarioTableClass a partir de los datos obtenidos y los agrega a una lista de datosTabla.
     * Finalmente, establece esta lista de datosTabla como el modelo de datos de la tabla tblUsuarios para
     * mostrar los usuarios en la vista.
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
     * Filtra los usuarios en una tabla según el valor del campo de nombre de usuario.
     * El método toma el valor del campo de filtro de nombre de usuario, y filtra la tabla
     * mostrando solo las filas que contienen el valor del filtro en el campo de nombre de usuario.
     * @implNote Este método utiliza un Predicate para realizar el filtrado.
     */
    private void filtrarUsuario() {
        Predicate<UsuarioTableClass> predicate = usuario ->{
          String nombreUsuarioFiltro = txtNombUsu.getText();
          return usuario.getNombreUsuario().contains(nombreUsuarioFiltro);
        };
        tblUsuarios.setItems(datosTabla.filtered(predicate));
    }

    /**
     * Abre una nueva ventana para crear un nuevo usuario.
     *
     * Este método carga la vista "CrearUsuario.fxml" utilizando un FXMLLoader, crea una nueva ventana
     * (Stage) y muestra la vista en esa ventana. La nueva ventana se abre como una ventana separada
     * para permitir la creación de un nuevo usuario.
     *
     * @param event El evento que desencadenó la creación del usuario.
     * @throws IOException Si ocurre un error al cargar la vista "CrearUsuario.fxml".
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
     * Abre una nueva ventana para editar un usuario seleccionado de la tabla.
     *
     * Este método obtiene el usuario seleccionado en la tabla, verifica si se ha seleccionado uno,
     * y si es así, carga la vista "EditarUsuario.fxml" utilizando un FXMLLoader. Luego, establece el
     * usuario seleccionado en una instancia Singleton para que pueda ser accedido por la ventana de
     * edición. Finalmente, muestra la vista de edición del usuario en una nueva ventana.
     *
     * @param event El evento que desencadenó la edición del usuario.
     * @throws IOException Si ocurre un error al cargar la vista "EditarUsuario.fxml".
     */
    public void EditarUsuario(ActionEvent event) throws IOException {
        UsuarioTableClass usuarioTableClass = (UsuarioTableClass) tblUsuarios.getSelectionModel().getSelectedItem();
        if(usuarioTableClass == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Debe seleccionar un usuario de la tabla.");
            alert.showAndWait();
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        controllerPrincipal.cargarEscena("/views/SistemasViews/EditarUsuario.fxml");
        controllerPrincipal.setUsuario(usuarioDAO.obtenerUsuarioPorNombre(usuarioTableClass.nombreUsuario));

    }

    /**
     * Elimina un usuario seleccionado de la tabla.
     *
     * Este método permite eliminar un usuario seleccionado de la tabla. Primero, obtiene el usuario seleccionado
     * de la tabla y verifica si se ha seleccionado uno. Luego, muestra un diálogo de confirmación para asegurarse
     * de que el usuario desea eliminar al usuario. Si el usuario confirma la eliminación, se realiza la eliminación
     * en la base de datos a través de la clase UsuarioDAO. Posteriormente, se elimina el usuario de la lista de datos
     * y se actualiza la vista de la tabla. Se muestra una notificación de éxito después de eliminar el usuario.
     *
     * @param actionEvent El evento que desencadenó la eliminación del usuario.
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
