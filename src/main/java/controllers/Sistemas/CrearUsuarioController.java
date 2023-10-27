package controllers.Sistemas;

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

    public void SetearVisibilidadRoles(boolean bol1, boolean bol2, boolean bol3, boolean bol4, boolean bol5, String personal){
        this.scpaneFuncionario.setVisible(bol1);
        this.scpaneFuncionario2.setVisible(bol2);
        this.scpaneMedico.setVisible(bol3);
        this.scpaneEnfermero.setVisible(bol4);
        paneTitulo.setVisible(bol5);
        this.lbTipoPersonal.setText(personal);
    }


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

                    usuarioDAO.actualizar(usuario);
                    sectorDAO.actualizar(sector);

                    break;
                case "Administrador de Sistemas":
                    AdministradorSistemasDAO administradorSistemasDAO = new AdministradorSistemasDAO();

                    AdministradorSistemas administradorSistemas = new AdministradorSistemas(nombreFunc,apellidoFun,fechaNaci,domicilio,Integer.parseInt(dni),Integer.parseInt(telefonoFijo),Long.parseLong(telefonoCelular),estadoCivil,correo,usuario,sector);
                    administradorSistemasDAO.agregar(administradorSistemas);

                    usuario.setFuncionario(administradorSistemas);
                    sector.setFuncionarios(Set.of(administradorSistemas));

                    usuarioDAO.actualizar(usuario);
                    sectorDAO.actualizar(sector);
                    break;
                case "Medico":
                    MedicoDAO medicoDAO = new MedicoDAO();
                    UniversidadDAO universidadDAO = new UniversidadDAO();
                    EspecialidadDAO especialidadDAO = new EspecialidadDAO();

                    Universidad universidad = new Universidad(this.txtUniversidad.getText());
                    universidadDAO.agregar(universidad);

                    Especialidad especialidad = new Especialidad(this.txtEspecialidad.getText(),this.dateFechaObt.getValue(),universidad);
                    especialidadDAO.agregar(especialidad);

                    Medico medico = new Medico(nombreFunc,apellidoFun,fechaNaci,domicilio,Integer.parseInt(dni),Integer.parseInt(telefonoFijo),Long.parseLong(telefonoCelular),estadoCivil,correo,usuario,sector,this.txtMatricula.getText(),List.of(especialidad));
                    medicoDAO.agregar(medico);

                    usuario.setFuncionario(medico);
                    especialidad.setMedico(medico);
                    sector.setFuncionarios(Set.of(medico));

                    usuarioDAO.actualizar(usuario);
                    sectorDAO.actualizar(sector);
                    especialidadDAO.actualizar(especialidad);
                    break;
                case "Enfermero":
                    EnfermeroDAO enfermeroDAO = new EnfermeroDAO();

                    Enfermero enfermero = new Enfermero(nombreFunc,apellidoFun,fechaNaci,domicilio,Integer.parseInt(dni),Integer.parseInt(telefonoFijo),Long.parseLong(telefonoCelular),estadoCivil,correo,usuario,sector);
                    enfermeroDAO.agregar(enfermero);

                    usuario.setFuncionario(enfermero);
                    sector.setFuncionarios(Set.of(enfermero));

                    usuarioDAO.actualizar(usuario);
                    sectorDAO.actualizar(sector);
                    break;
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Campos vacíos o valores incorrectos");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    public void GuardarRoles(){
        //ROLES FUNCIONARIO ADMINISTRATIVO
        if(ckboxAdminHosp.isSelected()){roles.add(new Rol(RolesFuncionarios.AdministradorHospitalario.name()));}
        if(ckboxDircMed.isSelected()){roles.add(new Rol(RolesFuncionarios.DirectorMedico.name()));}
        if(ckboxDircEnf.isSelected()){roles.add(new Rol(RolesFuncionarios.DirectorEnfermeria.name()));}
        if(ckboxRRHH.isSelected()){roles.add(new Rol(RolesFuncionarios.GerenteRecursosHumanos.name()));}
        if(ckboxDircFinan.isSelected()){roles.add(new Rol(RolesFuncionarios.DirectorFinanciero.name()));}
        if(ckboxGertGeneral.isSelected()){roles.add(new Rol(RolesFuncionarios.GerenteGeneral.name()));}
        if(ckboxGertOpe.isSelected()){roles.add(new Rol(RolesFuncionarios.GerenteOperaciones.name()));}
        if(ckboxGertServ.isSelected()){roles.add(new Rol(RolesFuncionarios.GerenteServiciosPaciente.name()));}
        if(ckboxCordSegu.isSelected()){roles.add(new Rol(RolesFuncionarios.CoordinadorSeguridadHospitalaria.name()));}
        if(ckboxJefeAlm.isSelected()){roles.add(new Rol(RolesFuncionarios.JefeAlmacen.name()));}
        //ROLES ADMINISTRADOR SISTEMAS
        if(ckboxAdminSist1.isSelected()){roles.add(new Rol(RolesFuncionarios.AdministradorSistema.name()));}
        if(ckboxDircTecn.isSelected()){roles.add(new Rol(RolesFuncionarios.DirectorTecnologia.name()));}
        if(ckboxAnalDatos.isSelected()){roles.add(new Rol(RolesFuncionarios.AnalistaDatos.name()));}
        if(ckboxSegrInf.isSelected()){roles.add(new Rol(RolesFuncionarios.SeguridadInformatica.name()));}
        if(ckboxRedes.isSelected()){roles.add(new Rol(RolesFuncionarios.Redes.name()));}
        //ROLES MEDICO
        if(ckboxTriage.isSelected()){roles.add(new Rol(RolesMedico.Triage.name()));}
        if(ckboxMedEmer.isSelected()){roles.add(new Rol(RolesMedico.MedicoEmergencias.name()));}
        if(ckboxMedGen.isSelected()){roles.add(new Rol(RolesMedico.MedicoGeneral.name()));}
        if(ckboxMedEsp.isSelected()){roles.add(new Rol(RolesMedico.MedicoEspecialista.name()));}
        if(ckboxCirujano.isSelected()){roles.add(new Rol(RolesMedico.Cirujano.name()));}
        if(ckboxAnestesiologo.isSelected()){roles.add(new Rol(RolesMedico.Anestesiologo.name()));}
        if(ckboxRadiologo.isSelected()){roles.add(new Rol(RolesMedico.Radiologo.name()));}
        if(ckboxPsiquiatra.isSelected()){roles.add(new Rol(RolesMedico.Psiquiatra.name()));}
        if(ckboxMedCuidInts.isSelected()){roles.add(new Rol(RolesMedico.MedicoCuidadosIntensivos.name()));}
        if(ckboxGeriatra.isSelected()){roles.add(new Rol(RolesMedico.Geriatra.name()));}
        if(ckboxPediatra.isSelected()){roles.add(new Rol(RolesMedico.Pediatra.name()));}
        if(ckboxMedAtencPrim.isSelected()){roles.add(new Rol(RolesMedico.MedicoAtencionPrimaria.name()));}
        //ROLES ENFERMERO
        if(ckboxTriage2.isSelected()){roles.add(new Rol(RolesEnfermeros.Triage.name()));}
        if(ckboxCuidGenr.isSelected()){roles.add(new Rol(RolesEnfermeros.CuidadosGenerales.name()));}
        if(ckboxCuidInt.isSelected()){roles.add(new Rol(RolesEnfermeros.CuidadosIntensivos.name()));}
        if(ckboxEmergencias.isSelected()){roles.add(new Rol(RolesEnfermeros.Emergencias.name()));}
        if(ckboxPediatra2.isSelected()){roles.add(new Rol(RolesEnfermeros.Pediatrico.name()));}
        if(ckboxAtenPrim.isSelected()){roles.add(new Rol(RolesEnfermeros.AtencionPrimaria.name()));}
        if(ckboxSaludMental.isSelected()){roles.add(new Rol(RolesEnfermeros.SaludMental.name()));}
        if(ckboxOncologia.isSelected()){roles.add(new Rol(RolesEnfermeros.Oncologia.name()));}
    }

    private void comprobarCampos() throws Exception {
        boolean comprobarNombreFun = nombreFunc.matches("^[^s]+$");
        boolean comprobarApellidoFun = apellidoFun.matches("^[^s]+$");
        boolean comprobarDni = dni.matches("^[^s]+$") && dni.matches("\\d+");
        boolean comprobarDomicilio = domicilio.matches("^[^s]+$");
        boolean comprobarfechaNaci = fechaNaci != null;
        boolean comprobarEstadoCivil = estadoCivil != null;
        boolean comprobarCorreo = correo.matches("^[A-Z-a-z0-9+_.-]+@(.+)$");
        boolean comprobarTelFijo = telefonoFijo.matches("^[^s]+$") && telefonoFijo.matches("\\d+");
        boolean comprobarTelCelular = telefonoCelular.matches("^[^s]+$") && telefonoCelular.matches("\\d+");

        boolean comprobarNombreUsu = nombreUsu.matches("^[^s]+$");
        boolean comprobarContrasenia = password.matches("^[^s]+$");
        boolean comprobarContraseniaConfirm = passwordConfirm.matches("^[^s]+$") && passwordConfirm.equals(password);

        if (!comprobarNombreFun){
            throw new Exception("El nombre del funcionario no puede estar vacío");
        }

       if (!comprobarApellidoFun){
           throw new Exception("El apellido no puede estar vacío");
       }

       if (!comprobarDni){
           throw new Exception("El DNI no puede estar vacío, y debe ser llenado con números");
       }

       if (!comprobarDomicilio){
           throw new Exception("El domicilio no puede estar vacío");
       }

       if (!comprobarfechaNaci){
           throw new Exception("La fecha de nacimiento no puede estar vacío");
       }

       if (!comprobarEstadoCivil){
           throw new Exception("Debe seleccionar una opción como estado civil");
       }

       if (!comprobarCorreo){
           throw new Exception("El correo no puede estar vacío");
       }

       if (!comprobarTelFijo){
           throw new Exception("El telefono fijo no puede estar vacío");
       }

       if (!comprobarTelCelular){
           throw new Exception("El telefono celular no puede estar vacío");
       }

        if (!comprobarNombreUsu){
            throw new Exception("El nombre de usuario no puede estar vacío");
        }
       if (!comprobarContrasenia){
           throw new Exception("La contraseña no puede estar vacía");
       }

       if (!comprobarContraseniaConfirm){
           throw new Exception("La contraseña no puede estar vacía, y debe ser igual a la anterior propuesta");
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
