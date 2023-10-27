package controllers.Sistemas;

import controllers.FuncionarioProController;
import controllers.Singletons.SingletonUsuario;
import datasource.RolDAO;
import datasource.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Enfermero;
import model.Enum.Roles.RolesEnfermeros;
import model.Enum.Roles.RolesFuncionarios;
import model.Enum.Roles.RolesMedico;
import model.Funcionario;
import model.FuncionarioAdministrativo;
import model.Login.AdministradorSistemas;
import model.Login.Rol;
import model.Login.Usuario;
import model.Medico;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EditarUsuarioController {
    //COSAS DEL USUARIO
    @FXML
    private TextField txtNombUsu;
    @FXML
    private PasswordField txtPassw;
    @FXML
    private PasswordField txtConfirmPassw;
    @FXML
    private Label lbTipoPersonal;
    @FXML
    private ScrollPane scpaneFuncionario;
    @FXML
    private ScrollPane scpaneMedico;
    @FXML
    private ScrollPane scpaneEnfermero;
    public ScrollPane scpaneFuncionario2;
    //ROLES FUNCIONARIO ADMINISTRATIVO
    @FXML
    private CheckBox ckboxAdminHosp;
    @FXML
    private CheckBox ckboxDircMed;
    @FXML
    private CheckBox ckboxDircEnf;
    @FXML
    private CheckBox ckboxRRHH;
    @FXML
    private CheckBox ckboxDircFinan;
    @FXML
    private CheckBox ckboxGertGeneral;
    @FXML
    private CheckBox ckboxGertOpe;
    @FXML
    private CheckBox ckboxGertServ;
    @FXML
    private CheckBox ckboxCordSegu;
    @FXML
    private CheckBox ckboxJefeAlm;
    //ROLES ADMINISTRADOR DE SISTEMAS
    @FXML
    private CheckBox ckboxAdminSist1;
    @FXML
    private CheckBox ckboxDircTecn;
    @FXML
    private CheckBox ckboxAnalDatos;
    @FXML
    private CheckBox ckboxSegrInf;
    @FXML
    private CheckBox ckboxRedes;
    //ROLES MEDICO
    @FXML
    private CheckBox ckboxTriage;
    @FXML
    private CheckBox ckboxMedEmer;
    @FXML
    private CheckBox ckboxMedGen;
    @FXML
    private CheckBox ckboxMedEsp;
    @FXML
    private CheckBox ckboxCirujano;
    @FXML
    private CheckBox ckboxAnestesiologo;
    @FXML
    private CheckBox ckboxRadiologo;
    @FXML
    private CheckBox ckboxPsiquiatra;
    @FXML
    private CheckBox ckboxMedCuidInts;
    @FXML
    private CheckBox ckboxGeriatra;
    @FXML
    private CheckBox ckboxPediatra;
    @FXML
    private CheckBox ckboxMedAtencPrim;
    //ROLES ENFERMERO
    @FXML
    private CheckBox ckboxTriage2;
    @FXML
    private CheckBox ckboxCuidGenr;
    @FXML
    private CheckBox ckboxCuidInt;
    @FXML
    private CheckBox ckboxEmergencias;
    @FXML
    private CheckBox ckboxPediatra2;
    @FXML
    private CheckBox ckboxAtenPrim;
    @FXML
    private CheckBox ckboxSaludMental;
    @FXML
    private CheckBox ckboxOncologia;

    //COSAS DE FUNCIONARIO
    @FXML
    private Label lbNomFunc;
    @FXML
    private Label lbApeFunc;
    @FXML
    private Label lbDniFunc;
    @FXML
    private Label lbSectorFunc;
    @FXML
    private Label lbRoles;

    private String nombreUsu;
    private String password;
    private String passwordConfirm;
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Usuario usuario = SingletonUsuario.getInstance().getUsuario();
    List<Rol> rolesNuevos = new ArrayList<Rol>();
    private FuncionarioProController controllerPrincipal;

    /**
     * Inicializa la vista y establece una referencia al controlador principal de la aplicación.
     *
     * Este método se llama durante la inicialización de la vista y se utiliza para establecer una referencia
     * al controlador principal de la aplicación (controllerPrincipal). Esta referencia permite la comunicación
     * entre esta vista y el controlador principal para coordinar la funcionalidad de la aplicación.
     */
    @FXML
    public void initialize(){
        controllerPrincipal = FuncionarioProController.getControladorPrimario();
    }

    /**
     * Inicia la vista de edición del usuario con los datos del usuario actual.
     *
     * Este método se utiliza para iniciar la vista de edición del usuario con los datos del usuario
     * actualmente seleccionado. Obtiene el usuario actual desde una instancia Singleton y luego llama
     * al método "IniciarVentana()" para mostrar la información del usuario en la vista de edición.
     */
    public void iniciarUsuario(){
        usuario = SingletonUsuario.getInstance().getUsuario();
        IniciarVentana();
    }

    /**
     * Inicia la vista de edición del usuario con los datos del usuario actual.
     *
     * Este método se utiliza para inicializar la vista de edición del usuario con los datos del usuario
     * actualmente seleccionado. Configura los componentes de la vista, como etiquetas de nombre, apellido,
     * DNI, sector, roles y el nombre de usuario, con la información del usuario y su función. También establece
     * la visibilidad de ciertos elementos de acuerdo a la función del usuario.
     */
    private void IniciarVentana() {
       Funcionario funcionario = usuario.getFuncionario();

        this.lbNomFunc.setText(funcionario.getNombre());
        this.lbApeFunc.setText(funcionario.getApellido());
        this.lbDniFunc.setText(String.valueOf(funcionario.getDNI()));
        this.lbSectorFunc.setText(funcionario.getSector().getNombre());
        SetearLebelRoles();

        if(usuario.getFuncionario().getClass() == FuncionarioAdministrativo.class){
            SetearVisibilidadRoles(true,false,false,false,false,"Funcionario Administrativo");

        }
        if(usuario.getFuncionario().getClass() == AdministradorSistemas.class){
            SetearVisibilidadRoles(false,true,false,false,false,"Administrador de Sistemas");
        }
        if(usuario.getFuncionario().getClass() == Medico.class){
            SetearVisibilidadRoles(false,false,true,false,true,"Medico");
        }
        if(usuario.getFuncionario().getClass() == Enfermero.class){
            SetearVisibilidadRoles(false,false,false,true,false,"Enfermero");
        }

        this.txtNombUsu.setText(usuario.getNombreUsuario());
    }

    /**
     * Establece la visibilidad de componentes en función del tipo de personal.
     *
     * Este método se utiliza para controlar la visibilidad de componentes en la vista de edición del usuario
     * según el tipo de personal al que pertenece. Permite mostrar u ocultar ciertos elementos de la vista,
     * como paneles y etiquetas, en función de la función del usuario.
     *
     * @param bol1      Indica si se debe mostrar el panel de Funcionario Administrativo.
     * @param bol2      Indica si se debe mostrar el panel de Administrador de Sistemas.
     * @param bol3      Indica si se debe mostrar el panel de Médico.
     * @param bol4      Indica si se debe mostrar el panel de Enfermero.
     * @param bol5      Indica si se debe mostrar la etiqueta de tipo de personal.
     * @param personal  El nombre del tipo de personal a mostrar en la etiqueta.
     */
    public void SetearVisibilidadRoles(boolean bol1, boolean bol2, boolean bol3, boolean bol4, boolean bol5, String personal){
        this.scpaneFuncionario.setVisible(bol1);
        this.scpaneFuncionario2.setVisible(bol2);
        this.scpaneMedico.setVisible(bol3);
        this.scpaneEnfermero.setVisible(bol4);
        this.lbTipoPersonal.setVisible(bol5);
        this.lbTipoPersonal.setText(personal);
    }

    /**
     * Establece el contenido de la etiqueta de roles del usuario.
     *
     * Este método se utiliza para configurar el contenido de la etiqueta que muestra los roles del usuario.
     * Obtiene la lista de roles del usuario y crea una cadena que los enumera, separados por comas, para mostrar
     * en la etiqueta.
     */
    public void SetearLebelRoles(){
        String roles = "";
        for (int x=0; x < usuario.getRoles().size(); x++){
            if(x != usuario.getRoles().size()-1){
                roles += usuario.getNombreRoles().get(x) + ", ";
            }else {
                roles += usuario.getNombreRoles().get(x);
            }
        }
        this.lbRoles.setText(roles);
    }

    /**
     * Realiza la edición de un usuario con los cambios especificados.
     *
     * Este método se utiliza para editar un usuario con los cambios especificados en la vista de edición. Primero,
     * obtiene los valores ingresados para el nombre de usuario y las contraseñas, y realiza comprobaciones de los
     * campos y roles. Luego, si se confirma la edición, actualiza el usuario en la base de datos a través de la
     * clase UsuarioDAO. Muestra una notificación de éxito después de la edición.
     *
     * @param actionEvent El evento que desencadenó la edición del usuario.
     */
    public void Editar(ActionEvent actionEvent) {
        nombreUsu = this.txtNombUsu.getText();
        password = this.txtPassw.getText();
        passwordConfirm = this.txtConfirmPassw.getText();

        try{
            GuardarRoles();
            ComprobarCampos();

            if(!nombreUsu.equals(usuario.getNombreUsuario())){
                usuario.setNombreUsuario(nombreUsu);
            }
            usuario.setContrasenia(password);

            usuario.setRoles(rolesNuevos);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Editar Usuario");
            alert.setContentText("¿Estás seguro de que deseas cambiar este usuario?");
            Optional<ButtonType> resultado = alert.showAndWait();

            if (resultado.get() == ButtonType.OK) {
                usuarioDAO.actualizar(usuario);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setContentText("Usuario actualizado exitosamente");
                alert.show();
                //EMA cuando confirma el cambio debe volver a la ventana de Sistemas
            }


        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Campos vacíos o valores incorrectos");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    /**
     * Realiza comprobaciones de los campos de nombre de usuario y contraseñas.
     *
     * Este método se utiliza para comprobar los campos de nombre de usuario y contraseñas en la vista de edición.
     * Verifica si el nombre de usuario, la contraseña y la confirmación de contraseña están vacíos, y si la
     * confirmación de contraseña coincide con la contraseña ingresada. Si se encuentran problemas en alguno de
     * estos campos, se lanza una excepción con un mensaje de error.
     *
     * @throws Exception Si se encuentran problemas en los campos, se lanza una excepción con un mensaje de error.
     */
    private void ComprobarCampos() throws Exception {
        if (nombreUsu.isEmpty()){
            throw  new Exception("El nombre de usuario no puede estar vacío");
        }
        if (password.isEmpty()){
            throw  new Exception("La contrasenñ no puede estar vacío");
        }
        if (passwordConfirm.isEmpty()){
            throw new Exception("La contraseña no puede estar vacía");
        }

        if (!passwordConfirm.equals(password)){
            throw new Exception("La contraseña no son iguales revise los datos");
        }
    }

    /**
     * Guarda los roles seleccionados por el usuario.
     *
     * Este método se utiliza para guardar los roles seleccionados por el usuario en la vista de edición. Verifica
     * qué roles han sido marcados a través de los CheckBox y obtiene los objetos de rol correspondientes a partir
     * de una instancia de RolDAO. Los roles seleccionados se almacenan en la lista de rolesNuevos.
     */
    public void GuardarRoles(){
        RolDAO rolDAO = new RolDAO();
        //ROLES FUNCIONARIO ADMINISTRATIVO
        if(ckboxAdminHosp.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesFuncionarios.AdministradorHospitalario.name()));}
        if(ckboxDircMed.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesFuncionarios.DirectorMedico.name()));}
        if(ckboxDircEnf.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesFuncionarios.DirectorEnfermeria.name()));}
        if(ckboxRRHH.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesFuncionarios.GerenteRecursosHumanos.name()));}
        if(ckboxDircFinan.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesFuncionarios.DirectorFinanciero.name()));}
        if(ckboxGertGeneral.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesFuncionarios.GerenteGeneral.name()));}
        if(ckboxGertOpe.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesFuncionarios.GerenteOperaciones.name()));}
        if(ckboxGertServ.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesFuncionarios.GerenteServiciosPaciente.name()));}
        if(ckboxCordSegu.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesFuncionarios.CoordinadorSeguridadHospitalaria.name()));}
        if(ckboxJefeAlm.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesFuncionarios.JefeAlmacen.name()));}
        //ROLES ADMINISTRADOR SISTEMAS
        if(ckboxAdminSist1.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesFuncionarios.AdministradorSistema.name()));}
        if(ckboxDircTecn.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesFuncionarios.DirectorTecnologia.name()));}
        if(ckboxAnalDatos.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesFuncionarios.AnalistaDatos.name()));}
        if(ckboxSegrInf.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesFuncionarios.SeguridadInformatica.name()));}
        if(ckboxRedes.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesFuncionarios.Redes.name()));}
        //ROLES MEDICO
        if(ckboxTriage.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesMedico.Triage.name()));}
        if(ckboxMedEmer.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesMedico.MedicoEmergencias.name()));}
        if(ckboxMedGen.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesMedico.MedicoGeneral.name()));}
        if(ckboxMedEsp.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesMedico.MedicoEspecialista.name()));}
        if(ckboxCirujano.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesMedico.Cirujano.name()));}
        if(ckboxAnestesiologo.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesMedico.Anestesiologo.name()));}
        if(ckboxRadiologo.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesMedico.Radiologo.name()));}
        if(ckboxPsiquiatra.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesMedico.Psiquiatra.name()));}
        if(ckboxMedCuidInts.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesMedico.MedicoCuidadosIntensivos.name()));}
        if(ckboxGeriatra.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesMedico.Geriatra.name()));}
        if(ckboxPediatra.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesMedico.Pediatra.name()));}
        if(ckboxMedAtencPrim.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesMedico.MedicoAtencionPrimaria.name()));}
        //ROLES ENFERMERO
        if(ckboxTriage2.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesEnfermeros.Triage.name()));}
        if(ckboxCuidGenr.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesEnfermeros.CuidadosGenerales.name()));}
        if(ckboxCuidInt.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesEnfermeros.CuidadosIntensivos.name()));}
        if(ckboxEmergencias.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesEnfermeros.Emergencias.name()));}
        if(ckboxPediatra2.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesEnfermeros.Pediatrico.name()));}
        if(ckboxAtenPrim.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesEnfermeros.AtencionPrimaria.name()));}
        if(ckboxSaludMental.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesEnfermeros.SaludMental.name()));}
        if(ckboxOncologia.isSelected()){rolesNuevos.add(rolDAO.obtenerPorNombre(RolesEnfermeros.Oncologia.name()));}
    }
}
