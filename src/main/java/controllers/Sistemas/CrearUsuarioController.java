package controllers.Sistemas;

import datasource.FuncionarioDAO;
import datasource.SectorDAO;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Enum.EstadoCivil;
import model.Funcionario;
import model.Login.Usuario;
import model.Sector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CrearUsuarioController {

    public TextField txtNombreFun;
    public TextField txtApellido;
    public TextField txtDni;
    public TextField txtDomicilio;
    public DatePicker dateFechaNaci;
    public TextField txtCorreo;
    public TextField txtTelFijo;
    public TextField txtTelCelular;
    public ComboBox cboxEstadoCivil;
    public ComboBox cboxTipoPersonal;
    public ComboBox cboxSector;
    public Pane paneTitulo;
    public TextField txtEspecialidad;
    public DatePicker dateFechaObt;
    public TextField txtUniversidad;
    public TextField txtNombUsu;
    public PasswordField txtPass;
    public PasswordField txtConfirmPass;
    public ScrollPane scpaneFuncionario;
    public ScrollPane scpaneMedico;
    public ScrollPane scpaneEnfermero;
    public ScrollPane scpaneFuncionario2;
    public Label lbTipoPersonal;

    Funcionario funcionario;

    @FXML
    public void initialize(){
        this.cboxEstadoCivil.getItems().addAll(EstadoCivil.values());
        this.cboxTipoPersonal.getItems().addAll(List.of("Funcionario Administrativo","Administrador de Sistemas","Medico","Enfermero"));
        cargarSectores();

        this.scpaneEnfermero.setVisible(false);
        this.scpaneMedico.setVisible(false);
        this.scpaneFuncionario.setVisible(false);
        this.scpaneFuncionario2.setVisible(false);
        this.paneTitulo.setVisible(false);

        this.cboxTipoPersonal.valueProperty().addListener((observable,oldValue,newValue) -> mostrarRoles());

    }

    private void cargarSectores() {
        SectorDAO sectorDAO = new SectorDAO();
        List<Sector> sectores = sectorDAO.obtenerTodos();
        List<String> sectores2 = new ArrayList<>();
        for(Sector sector: sectores){
            sectores2.add(sector.getNombre());
        }
        this.cboxSector.getItems().addAll(sectores2);
    }

    private void mostrarRoles() {
        //Predicate<> predicate ->
        String tipoPersonal = this.cboxTipoPersonal.getSelectionModel().getSelectedItem().toString();
        switch (tipoPersonal){
            case "Funcionario Administrativo":
                this.scpaneFuncionario.setVisible(true);
                this.scpaneFuncionario2.setVisible(false);
                this.scpaneEnfermero.setVisible(false);
                this.scpaneMedico.setVisible(false);
                paneTitulo.setVisible(false);
                this.lbTipoPersonal.setText("Funcionario Administrativo");
                break;
            case "Administrador de Sistemas":
                this.scpaneFuncionario2.setVisible(true);
                this.scpaneFuncionario.setVisible(false);
                this.scpaneEnfermero.setVisible(false);
                this.scpaneMedico.setVisible(false);
                paneTitulo.setVisible(false);
                this.lbTipoPersonal.setText("Administrador de Sistemas");
                break;
            case "Medico":
                this.scpaneMedico.setVisible(true);
                this.scpaneEnfermero.setVisible(false);
                this.scpaneFuncionario.setVisible(false);
                this.scpaneFuncionario2.setVisible(false);
                paneTitulo.setVisible(true);
                this.lbTipoPersonal.setText("Medico");
                break;
            case "Enfermero":
                this.scpaneEnfermero.setVisible(true);
                this.scpaneFuncionario.setVisible(false);
                this.scpaneFuncionario2.setVisible(false);
                this.scpaneMedico.setVisible(false);
                paneTitulo.setVisible(false);
                this.lbTipoPersonal.setText("Enfermero");
                break;
            default:
                this.scpaneFuncionario.setVisible(false);
                this.scpaneFuncionario2.setVisible(false);
                this.scpaneMedico.setVisible(false);
                this.scpaneEnfermero.setVisible(false);
                paneTitulo.setVisible(false);
                this.lbTipoPersonal.setText("");
                break;
        }
    }

    public void Crear(ActionEvent actionEvent) {


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
