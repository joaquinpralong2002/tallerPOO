package controllers.Sistemas;

import controllers.FuncionarioProController;
import datasource.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;
import model.Enum.EstadoCivil;
import model.Enum.Roles.RolesEnfermeros;
import model.Enum.Roles.RolesFuncionarios;
import model.Enum.Roles.RolesMedico;
import model.Login.AdministradorSistemas;
import model.Login.Usuario;
import model.Login.Rol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Set;

public class CrearUsuarioController {

    //COSAS DE FUNCIONARIO
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
    //COSAS DE LA ESPECIALIDAD DEL MEDICO
    public Pane paneTitulo;
    public TextField txtEspecialidad;
    public DatePicker dateFechaObt;
    public TextField txtUniversidad;
    public TextField txtMatricula;

    //COSAS DEL USUARIO
    public TextField txtNombUsu;
    public PasswordField txtPass;
    public PasswordField txtConfirmPass;
    public ScrollPane scpaneFuncionario;
    public ScrollPane scpaneMedico;
    public ScrollPane scpaneEnfermero;
    public ScrollPane scpaneFuncionario2;
    public Label lbTipoPersonal;
    //ROLES FUNCIONARIO ADMINISTRATIVO
    public CheckBox ckboxAdminHosp;
    public CheckBox ckboxDircMed;
    public CheckBox ckboxDircEnf;
    public CheckBox ckboxRRHH;
    public CheckBox ckboxDircFinan;
    public CheckBox ckboxGertGeneral;
    public CheckBox ckboxGertOpe;
    public CheckBox ckboxGertServ;
    public CheckBox ckboxCordSegu;
    public CheckBox ckboxJefeAlm;
    //ROLES ADMINISTRADOR DE SISTEMAS
    public CheckBox ckboxAdminSist1;
    public CheckBox ckboxDircTecn;
    public CheckBox ckboxAnalDatos;
    public CheckBox ckboxSegrInf;
    public CheckBox ckboxRedes;
    //ROLES MEDICO
    public CheckBox ckboxTriage;
    public CheckBox ckboxMedEmer;
    public CheckBox ckboxMedGen;
    public CheckBox ckboxMedEsp;
    public CheckBox ckboxCirujano;
    public CheckBox ckboxAnestesiologo;
    public CheckBox ckboxRadiologo;
    public CheckBox ckboxPsiquiatra;
    public CheckBox ckboxMedCuidInts;
    public CheckBox ckboxGeriatra;
    public CheckBox ckboxPediatra;
    public CheckBox ckboxMedAtencPrim;
    //ROLES ENFERMERO
    public CheckBox ckboxTriage2;
    public CheckBox ckboxCuidGenr;
    public CheckBox ckboxCuidInt;
    public CheckBox ckboxEmergencias;
    public CheckBox ckboxPediatra2;
    public CheckBox ckboxAtenPrim;
    public CheckBox ckboxSaludMental;
    public CheckBox ckboxOncologia;

    List<Rol> roles = new ArrayList<Rol>();


    //VARIABLES
    Funcionario funcionario;
    private String nombreFunc;
    private String apellidoFun;
    private String dni;
    private String domicilio;
    private LocalDate fechaNaci;
    private EstadoCivil estadoCivil;
    private String correo;
    private String telefonoFijo;
    private String telefonoCelular;
    private Sector sector;

    private String nombreUsu;
    private String password;
    private String passwordConfirm;

    private FuncionarioProController controllerPrincipal;

    /**
     * Inicializa la vista y configura las opciones iniciales para la creación de usuarios.
     * Carga valores en las listas desplegables, oculta secciones específicas y establece escuchadores de cambios en el tipo de personal.
     */
    @FXML
    public void initialize(){
        controllerPrincipal = FuncionarioProController.getControladorPrimario();
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

    /**
     * Carga la lista de sectores disponibles en la lista desplegable de selección de sector.
     * Obtiene la lista de sectores de la base de datos y la muestra en el control de selección.
     */
    private void cargarSectores() {
        SectorDAO sectorDAO = new SectorDAO();
        List<Sector> sectores = sectorDAO.obtenerTodos();
        List<String> sectores2 = new ArrayList<>();
        for(Sector sector: sectores){
            sectores2.add(sector.getNombre());
        }
        this.cboxSector.getItems().addAll(sectores2);
    }

    /**
     * Muestra o oculta las opciones de roles de acuerdo al tipo de personal seleccionado.
     * Dependiendo del tipo de personal elegido en la lista desplegable, muestra las opciones de roles correspondientes
     * y oculta las demás. Además, establece un título para la sección de roles.
     */
    private void mostrarRoles() {
        //Predicate<> predicate ->
        String tipoPersonal = this.cboxTipoPersonal.getSelectionModel().getSelectedItem().toString();
        switch (tipoPersonal){
            case "Funcionario Administrativo":
                SetearVisibilidadRoles(true,false,false,false,false,"Funcionario Administrativo");
                break;
            case "Administrador de Sistemas":
                SetearVisibilidadRoles(false,true,false,false,false,"Administrador de Sistemas");
                break;
            case "Medico":
                SetearVisibilidadRoles(false,false,true,false,true,"Medico");
                break;
            case "Enfermero":
                SetearVisibilidadRoles(false,false,false,true,false,"Enfermero");
                break;
            default:
                SetearVisibilidadRoles(true,false,false,false,false,"");
                break;
        }
    }

    /**
     * Establece la visibilidad de secciones de roles y el título según los valores booleanos proporcionados.
     * Controla la visibilidad de varias secciones de roles y establece un título para la sección según los valores booleanos
     * proporcionados. El parámetro "personal" se utiliza para definir el tipo de personal seleccionado.
     *
     * @param bol1     Visibilidad de la sección de Funcionario Administrativo.
     * @param bol2     Visibilidad de la segunda sección de Funcionario Administrativo.
     * @param bol3     Visibilidad de la sección de Médico.
     * @param bol4     Visibilidad de la sección de Enfermero.
     * @param bol5     Visibilidad del título de la sección de roles.
     * @param personal El tipo de personal seleccionado.
     */
    public void SetearVisibilidadRoles(boolean bol1, boolean bol2, boolean bol3, boolean bol4, boolean bol5, String personal){
        this.scpaneFuncionario.setVisible(bol1);
        this.scpaneFuncionario2.setVisible(bol2);
        this.scpaneMedico.setVisible(bol3);
        this.scpaneEnfermero.setVisible(bol4);
        paneTitulo.setVisible(bol5);
        this.lbTipoPersonal.setText(personal);
    }


    /**
     * Maneja la acción de creación de un nuevo usuario y su correspondiente personal, según el tipo de personal seleccionado.
     * Recolecta y valida los datos ingresados en la vista, crea un nuevo usuario y personal en la base de datos, y establece
     * relaciones entre ellos y el sector correspondiente. Limpia los campos después de la creación exitosa.
     *
     * @param actionEvent El evento de acción que desencadena la creación del usuario y personal.
     */
    public void Crear(ActionEvent actionEvent) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        SectorDAO sectorDAO = new SectorDAO();

        String tipo = this.cboxTipoPersonal.getValue().toString();

        nombreFunc = this.txtNombreFun.getText();
        apellidoFun = this.txtApellido.getText();
        dni = this.txtDni.getText();
        domicilio = this.txtDomicilio.getText();
        fechaNaci = this.dateFechaNaci.getValue();
        estadoCivil = (EstadoCivil) this.cboxEstadoCivil.getValue();
        correo = this.txtCorreo.getText();
        telefonoFijo = this.txtTelFijo.getText();
        telefonoCelular = this.txtTelCelular.getText();
        sector = sectorDAO.obtenerPorNombre(this.cboxSector.getValue().toString());

        nombreUsu = this.txtNombUsu.getText();
        password = this.txtPass.getText();
        passwordConfirm = this.txtConfirmPass.getText();

        GuardarRoles();


        try{
            comprobarCampos();

            Usuario usuario = new Usuario(nombreUsu,password,roles);
            usuarioDAO.agregar(usuario);

            switch (tipo){
                case "Funcionario Administrativo":
                    FuncionarioAdministrativoDAO funcionarioAdministrativoDAO = new FuncionarioAdministrativoDAO();

                    FuncionarioAdministrativo funcionarioAdministrativo = new FuncionarioAdministrativo(nombreFunc,apellidoFun,fechaNaci,domicilio,Integer.parseInt(dni),Integer.parseInt(telefonoFijo),Long.parseLong(telefonoCelular),estadoCivil,correo,usuario,sector);
                    funcionarioAdministrativoDAO.agregar(funcionarioAdministrativo);

                    usuario.setFuncionario(funcionarioAdministrativo);
                    sector.setFuncionarios(Set.of(funcionarioAdministrativo));

                    break;
                case "Administrador de Sistemas":
                    AdministradorSistemasDAO administradorSistemasDAO = new AdministradorSistemasDAO();

                    AdministradorSistemas administradorSistemas = new AdministradorSistemas(nombreFunc,apellidoFun,fechaNaci,domicilio,Integer.parseInt(dni),Integer.parseInt(telefonoFijo),Long.parseLong(telefonoCelular),estadoCivil,correo,usuario,sector);
                    administradorSistemasDAO.agregar(administradorSistemas);

                    usuario.setFuncionario(administradorSistemas);
                    sector.setFuncionarios(Set.of(administradorSistemas));

                    break;
                case "Medico":
                    String matricula = this.txtMatricula.getText();
                    if(!matricula.matches("^[^s]+$")){
                        throw new Exception("La matricula no puede estar vacia");
                    }
                    MedicoDAO medicoDAO = new MedicoDAO();
                    UniversidadDAO universidadDAO = new UniversidadDAO();
                    EspecialidadDAO especialidadDAO = new EspecialidadDAO();

                    Universidad universidad = new Universidad(this.txtUniversidad.getText());
                    universidadDAO.agregar(universidad);

                    Especialidad especialidad = new Especialidad(this.txtEspecialidad.getText(),this.dateFechaObt.getValue(),universidad);
                    especialidadDAO.agregar(especialidad);

                    Medico medico = new Medico(nombreFunc,apellidoFun,fechaNaci,domicilio,Integer.parseInt(dni),Integer.parseInt(telefonoFijo),Long.parseLong(telefonoCelular),estadoCivil,correo,usuario,sector,matricula,List.of(especialidad));
                    medicoDAO.agregar(medico);

                    usuario.setFuncionario(medico);
                    especialidad.setMedico(medico);
                    sector.setFuncionarios(Set.of(medico));

                    especialidadDAO.actualizar(especialidad);

                    break;
                case "Enfermero":
                    EnfermeroDAO enfermeroDAO = new EnfermeroDAO();

                    Enfermero enfermero = new Enfermero(nombreFunc,apellidoFun,fechaNaci,domicilio,Integer.parseInt(dni),Integer.parseInt(telefonoFijo),Long.parseLong(telefonoCelular),estadoCivil,correo,usuario,sector);
                    enfermeroDAO.agregar(enfermero);

                    usuario.setFuncionario(enfermero);
                    sector.setFuncionarios(Set.of(enfermero));

                    break;
            }
            usuarioDAO.actualizar(usuario);
            sectorDAO.actualizar(sector);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText("Personal creado y añadido exitosamente");
            alert.show();
            LimpiarCheckBoxs();
            roles.clear();
            Volver();
            //EMA cuando lo guarda debe volver a la ventana de Sistemas

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Campos vacíos o valores incorrectos");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    /**
     * Limpia la selección de roles en los CheckBox de la vista de creación de usuario.
     * Restablece a "no seleccionado" todos los roles en la vista, lo que permite una creación de usuario
     * sin roles seleccionados si es necesario.
     */
    private void LimpiarCheckBoxs() {
        //ROLES FUNCIONARIO ADMINISTRATIVO
        ckboxAdminHosp.setSelected(false);
        ckboxDircMed.setSelected(false);
        ckboxDircEnf.setSelected(false);
        ckboxRRHH.setSelected(false);
        ckboxDircFinan.setSelected(false);
        ckboxGertGeneral.setSelected(false);
        ckboxGertOpe.setSelected(false);
        ckboxGertServ.setSelected(false);
        ckboxCordSegu.setSelected(false);
        ckboxJefeAlm.setSelected(false);
        //ROLES ADMINISTRADOR SISTEMAS
        ckboxAdminSist1.setSelected(false);
        ckboxDircTecn.setSelected(false);
        ckboxAnalDatos.setSelected(false);
        ckboxSegrInf.setSelected(false);
        ckboxRedes.setSelected(false);
        //ROLES MEDICO
        ckboxTriage.setSelected(false);
        ckboxMedEmer.setSelected(false);
        ckboxMedGen.setSelected(false);
        ckboxMedEsp.setSelected(false);
        ckboxCirujano.setSelected(false);
        ckboxAnestesiologo.setSelected(false);
        ckboxRadiologo.setSelected(false);
        ckboxPsiquiatra.setSelected(false);
        ckboxMedCuidInts.setSelected(false);
        ckboxGeriatra.setSelected(false);
        ckboxPediatra.setSelected(false);
        ckboxMedAtencPrim.setSelected(false);
        //ROLES ENFERMERO
        ckboxTriage2.setSelected(false);
        ckboxCuidGenr.setSelected(false);
        ckboxCuidInt.setSelected(false);
        ckboxEmergencias.setSelected(false);
        ckboxPediatra2.setSelected(false);
        ckboxAtenPrim.setSelected(false);
        ckboxSaludMental.setSelected(false);
        ckboxOncologia.setSelected(false);
    }

    /**
     * Guarda los roles seleccionados en los CheckBox de la vista en la lista de roles "roles".
     * Se encarga de agregar los roles correspondientes a la lista de roles "roles" según los CheckBox seleccionados
     * en la vista.
     */
    public void GuardarRoles(){
        RolDAO rolDAO = new RolDAO();
        //ROLES FUNCIONARIO ADMINISTRATIVO
        if(ckboxAdminHosp.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesFuncionarios.AdministradorHospitalario.name()));}
        if(ckboxDircMed.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesFuncionarios.DirectorMedico.name()));}
        if(ckboxDircEnf.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesFuncionarios.DirectorEnfermeria.name()));}
        if(ckboxRRHH.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesFuncionarios.GerenteRecursosHumanos.name()));}
        if(ckboxDircFinan.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesFuncionarios.DirectorFinanciero.name()));}
        if(ckboxGertGeneral.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesFuncionarios.GerenteGeneral.name()));}
        if(ckboxGertOpe.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesFuncionarios.GerenteOperaciones.name()));}
        if(ckboxGertServ.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesFuncionarios.GerenteServiciosPaciente.name()));}
        if(ckboxCordSegu.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesFuncionarios.CoordinadorSeguridadHospitalaria.name()));}
        if(ckboxJefeAlm.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesFuncionarios.JefeAlmacen.name()));}
        //ROLES ADMINISTRADOR SISTEMAS
        if(ckboxAdminSist1.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesFuncionarios.AdministradorSistema.name()));}
        if(ckboxDircTecn.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesFuncionarios.DirectorTecnologia.name()));}
        if(ckboxAnalDatos.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesFuncionarios.AnalistaDatos.name()));}
        if(ckboxSegrInf.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesFuncionarios.SeguridadInformatica.name()));}
        if(ckboxRedes.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesFuncionarios.Redes.name()));}
        //ROLES MEDICO
        if(ckboxTriage.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesMedico.Triage.name()));}
        if(ckboxMedEmer.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesMedico.MedicoEmergencias.name()));}
        if(ckboxMedGen.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesMedico.MedicoGeneral.name()));}
        if(ckboxMedEsp.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesMedico.MedicoEspecialista.name()));}
        if(ckboxCirujano.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesMedico.Cirujano.name()));}
        if(ckboxAnestesiologo.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesMedico.Anestesiologo.name()));}
        if(ckboxRadiologo.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesMedico.Radiologo.name()));}
        if(ckboxPsiquiatra.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesMedico.Psiquiatra.name()));}
        if(ckboxMedCuidInts.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesMedico.MedicoCuidadosIntensivos.name()));}
        if(ckboxGeriatra.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesMedico.Geriatra.name()));}
        if(ckboxPediatra.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesMedico.Pediatra.name()));}
        if(ckboxMedAtencPrim.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesMedico.MedicoAtencionPrimaria.name()));}
        //ROLES ENFERMERO
        if(ckboxTriage2.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesEnfermeros.Triage.name()));}
        if(ckboxCuidGenr.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesEnfermeros.CuidadosGenerales.name()));}
        if(ckboxCuidInt.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesEnfermeros.CuidadosIntensivos.name()));}
        if(ckboxEmergencias.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesEnfermeros.Emergencias.name()));}
        if(ckboxPediatra2.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesEnfermeros.Pediatrico.name()));}
        if(ckboxAtenPrim.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesEnfermeros.AtencionPrimaria.name()));}
        if(ckboxSaludMental.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesEnfermeros.SaludMental.name()));}
        if(ckboxOncologia.isSelected()){roles.add(rolDAO.obtenerPorNombre(RolesEnfermeros.Oncologia.name()));}
    }

    /**
     * Realiza la validación de los campos de entrada en la vista de creación de usuario.
     * Lanza una excepción si alguno de los campos requeridos está vacío o si se detecta una discrepancia en las contraseñas.
     *
     * @throws Exception Si alguno de los campos requeridos está vacío o si las contraseñas no coinciden.
     */
    private void comprobarCampos() throws Exception {

        if (nombreFunc.isEmpty()){
            throw new Exception("El nombre del funcionario no puede estar vacío");
        }

       if (apellidoFun.isEmpty()){
           throw new Exception("El apellido no puede estar vacío");
       }

       if (!dni.matches("\\d+")){
           throw new Exception("El DNI no puede estar vacío, y debe ser llenado con números");
       }

       if (domicilio.isEmpty()){
           throw new Exception("El domicilio no puede estar vacío");
       }

       if (fechaNaci == null){
           throw new Exception("La fecha de nacimiento no puede estar vacío");
       }

       if (estadoCivil == null){
           throw new Exception("Debe seleccionar una opción como estado civil");
       }

       if (!correo.matches("^[A-Z-a-z0-9+_.-]+@(.+)$")){
           throw new Exception("El correo no puede estar vacío");
       }

       if (!telefonoFijo.matches("\\d+")){
           throw new Exception("El telefono fijo no puede estar vacío");
       }

       if (!telefonoCelular.matches("\\d+")){
           throw new Exception("El telefono celular no puede estar vacío");
       }

        if (nombreUsu.isEmpty()){
            throw new Exception("El nombre de usuario no puede estar vacío");
        }
       if (password.isEmpty()){
           throw new Exception("La contraseña no puede estar vacía");
       }

       if (passwordConfirm.isEmpty()){
           throw new Exception("La contraseña no puede estar vacía");
       }

       if (!passwordConfirm.equals(password)){
           throw new Exception("La contraseña no son iguales revise los datos");
       }
    }

    /**
     * Vuelve a la vista principal de administración de sistemas cuando se presiona el botón de "Volver".
     */
    public void Volver(){
        controllerPrincipal.cargarEscena("/views/SistemasViews/Sistemas.fxml");
    }
}
