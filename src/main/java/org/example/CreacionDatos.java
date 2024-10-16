package org.example;
import datasource.*;
import model.*;
import model.Enum.*;
import model.Enum.Roles.RolesEnfermeros;
import model.Enum.Roles.RolesFuncionarios;
import model.Enum.Roles.RolesMedico;
import model.Login.*;

import model.Login.Rol;
import util.GlobalSessionFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;


public class CreacionDatos {

    public static void iniciarCarga(String[] args){

        //Se inicializa el Session Factory de la clase GlobalSessionFactory.
        GlobalSessionFactory init = new GlobalSessionFactory();
        for(int i = 0; i < args.length; i++){
            System.out.println(args[i]);
        }

        String url = args[1];
        String nombreUsuario = args[2];
        String contrasenia = args[3];

        if(args.length == 4) {
            String esquema;
            int crearDatosDefecto = Integer.parseInt(args[0]);
            System.out.println(crearDatosDefecto);
            if(crearDatosDefecto == 0){
                esquema = "update";
                init.InitGlobalSessionFactory(url, nombreUsuario, contrasenia, esquema);
            }
            else {
                esquema = "create-drop";
                init.InitGlobalSessionFactory(url, nombreUsuario, contrasenia, esquema);
                Crear();
            }


            init.cambiarEsquema(url, nombreUsuario, contrasenia);
        } else init.InitGlobalSessionFactory();
    }
    private static void Crear() {

//**************************************************************** DATOS del Programa *******************************************************//

//************************************************* DAOS ********************************************************//
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        AdministradorSistemasDAO adminSisDAO = new AdministradorSistemasDAO();
        EnfermeroDAO enfermeroDAO = new EnfermeroDAO();
        PacienteDAO pacienteDAO = new PacienteDAO();
        BoxAtencionDAO boxAtencionDAO = new BoxAtencionDAO();
        RolDAO rolDAO = new RolDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        SectorDAO sectorDAO = new SectorDAO();
        FuncionarioAdministrativoDAO funcionarioAdministrativoDAO = new FuncionarioAdministrativoDAO();
        UniversidadDAO universidadDAO = new UniversidadDAO();
        EspecialidadDAO especialidadDAO = new EspecialidadDAO();
        MedicoDAO medicoDAO = new MedicoDAO();
        RegistroEntradaDAO registroEntradaDAO = new RegistroEntradaDAO();


//************************************************* Boxes *******************************************************//

        //Creacion Boxes en Colsultorio
        BoxAtencion boxAtencion = new BoxAtencion(LugarAtencion.Consultorio,1,5,true);
        BoxAtencion box1 = new BoxAtencion(LugarAtencion.Consultorio, 2, 5, true);
        BoxAtencion box2 = new BoxAtencion(LugarAtencion.Consultorio, 3, 5, true);
        BoxAtencion box3 = new BoxAtencion(LugarAtencion.Consultorio, 4, 5, true);
        BoxAtencion box4 = new BoxAtencion(LugarAtencion.Consultorio, 5, 5, true);
        BoxAtencion box5 = new BoxAtencion(LugarAtencion.Consultorio, 6, 5, true);

        //Creacion Boxes en Emergencia
        BoxAtencion box6 = new BoxAtencion(LugarAtencion.Emergencia, 7, 5, true);
        BoxAtencion box7 = new BoxAtencion(LugarAtencion.Emergencia, 8, 5, true);
        BoxAtencion box8 = new BoxAtencion(LugarAtencion.Emergencia, 9, 5, true);
        BoxAtencion box9 = new BoxAtencion(LugarAtencion.Emergencia, 10, 5, true);
        BoxAtencion box10 = new BoxAtencion(LugarAtencion.Emergencia, 11, 5, true);

        //Creacion Boxes en Internaciones
        BoxAtencion box11 = new BoxAtencion(LugarAtencion.Internaciones, 12, 5, true);
        BoxAtencion box12 = new BoxAtencion(LugarAtencion.Internaciones, 13, 5, true);
        BoxAtencion box13 = new BoxAtencion(LugarAtencion.Internaciones, 14, 5, true);

        //Persistencia
        boxAtencionDAO.agregar(boxAtencion);
        boxAtencionDAO.agregar(box1);
        boxAtencionDAO.agregar(box2);
        boxAtencionDAO.agregar(box3);
        boxAtencionDAO.agregar(box4);
        boxAtencionDAO.agregar(box5);
        boxAtencionDAO.agregar(box6);
        boxAtencionDAO.agregar(box7);
        boxAtencionDAO.agregar(box8);
        boxAtencionDAO.agregar(box9);
        boxAtencionDAO.agregar(box10);
        boxAtencionDAO.agregar(box11);
        boxAtencionDAO.agregar(box12);
        boxAtencionDAO.agregar(box13);

//************************************************* Roles *******************************************************//

        //Creacion
        Rol rolFuncionario = new Rol("Funcionario");
        Rol rolMedico = new Rol("Medico");
        Rol rolMedico2 = new Rol("Triage");
        Rol rolEnfermero = new Rol("Enfermero");
        Rol rolSistemas = new Rol("Sistemas");


        //Persistencia
        rolDAO.agregar(rolFuncionario);
        rolDAO.agregar(rolMedico);
        rolDAO.agregar(rolMedico2);
        rolDAO.agregar(rolEnfermero);
        rolDAO.agregar(rolSistemas);

        rolDAO.agregar(new Rol(RolesFuncionarios.AdministradorHospitalario.name()));
        rolDAO.agregar(new Rol(RolesFuncionarios.DirectorMedico.name()));
        rolDAO.agregar(new Rol(RolesFuncionarios.DirectorEnfermeria.name()));
        rolDAO.agregar(new Rol(RolesFuncionarios.GerenteRecursosHumanos.name()));
        rolDAO.agregar(new Rol(RolesFuncionarios.DirectorFinanciero.name()));
        rolDAO.agregar(new Rol(RolesFuncionarios.GerenteGeneral.name()));
        rolDAO.agregar(new Rol(RolesFuncionarios.GerenteOperaciones.name()));
        rolDAO.agregar(new Rol(RolesFuncionarios.GerenteServiciosPaciente.name()));
        rolDAO.agregar(new Rol(RolesFuncionarios.CoordinadorSeguridadHospitalaria.name()));
        rolDAO.agregar(new Rol(RolesFuncionarios.JefeAlmacen.name()));
        //ROLES ADMINISTRADOR SISTEMAS
        rolDAO.agregar(new Rol(RolesFuncionarios.AdministradorSistema.name()));
        rolDAO.agregar(new Rol(RolesFuncionarios.DirectorTecnologia.name()));
        rolDAO.agregar(new Rol(RolesFuncionarios.AnalistaDatos.name()));
        rolDAO.agregar(new Rol(RolesFuncionarios.SeguridadInformatica.name()));
        rolDAO.agregar(new Rol(RolesFuncionarios.Redes.name()));
        //ROLES MEDICO
        rolDAO.agregar(new Rol(RolesMedico.MedicoEmergencias.name()));
        rolDAO.agregar(new Rol(RolesMedico.MedicoGeneral.name()));
        rolDAO.agregar(new Rol(RolesMedico.MedicoEspecialista.name()));
        rolDAO.agregar(new Rol(RolesMedico.Cirujano.name()));
        rolDAO.agregar(new Rol(RolesMedico.Anestesiologo.name()));
        rolDAO.agregar(new Rol(RolesMedico.Radiologo.name()));
        rolDAO.agregar(new Rol(RolesMedico.Psiquiatra.name()));
        rolDAO.agregar(new Rol(RolesMedico.MedicoCuidadosIntensivos.name()));
        rolDAO.agregar(new Rol(RolesMedico.Geriatra.name()));
        rolDAO.agregar(new Rol(RolesMedico.Pediatra.name()));
        rolDAO.agregar(new Rol(RolesMedico.MedicoAtencionPrimaria.name()));
        //ROLES ENFERMERO
        rolDAO.agregar(new Rol(RolesEnfermeros.CuidadosGenerales.name()));
        rolDAO.agregar(new Rol(RolesEnfermeros.CuidadosIntensivos.name()));
        rolDAO.agregar(new Rol(RolesEnfermeros.Emergencias.name()));
        rolDAO.agregar(new Rol(RolesEnfermeros.Pediatrico.name()));
        rolDAO.agregar(new Rol(RolesEnfermeros.AtencionPrimaria.name()));
        rolDAO.agregar(new Rol(RolesEnfermeros.SaludMental.name()));
        rolDAO.agregar(new Rol(RolesEnfermeros.Oncologia.name()));


//************************************************* Usuarios ****************************************************//

        //Creacion
        Usuario usuario = new Usuario("danielalopez","perrochichuA8€",List.of(rolFuncionario));
        Usuario usuarioMedico = new Usuario("juancarlosramirez@gmail.com","juancito01&",List.of(rolMedico,rolMedico2));
        Usuario usuarioMedico2 = new Usuario("blancoesteban","blancoesteban123",List.of(rolMedico));
        Usuario usuarioMedico3 = new Usuario("marisolniez","marisolniez123",List.of(rolMedico,rolMedico2));
        Usuario usuarioMedico4 = new Usuario("paizignacio","paizignacio123",List.of(rolMedico));
        Usuario usuarioMedico5 = new Usuario("nuñesleonel","nuñesleonel123",List.of(rolMedico));
        Usuario usuarioEnfermero = new Usuario("arcematias","arcematias123",List.of(rolEnfermero,rolMedico2));
        Usuario usuarioFuncAdmin = new Usuario("carrozogerman","carrozogerman123",List.of(rolFuncionario));
        Usuario usuarioFuncAdmin2 = new Usuario("analiamelgar","analiamelgar",List.of(rolFuncionario));
        Usuario usuarioFuncionario = new Usuario("balbuenaoscar","balbuenaoscar123",List.of(rolFuncionario));
        Usuario usuarioAdminSistemas = new Usuario("villalbaangel","villalbaangel123",List.of(rolSistemas));

        //Persistencia
        usuarioDAO.agregar(usuario);
        usuarioDAO.agregar(usuarioMedico);
        usuarioDAO.agregar(usuarioMedico2);
        usuarioDAO.agregar(usuarioMedico3);
        usuarioDAO.agregar(usuarioMedico4);
        usuarioDAO.agregar(usuarioMedico5);
        usuarioDAO.agregar(usuarioEnfermero);
        usuarioDAO.agregar(usuarioFuncAdmin);
        usuarioDAO.agregar(usuarioFuncAdmin2);
        usuarioDAO.agregar(usuarioFuncionario);
        usuarioDAO.agregar(usuarioAdminSistemas);

        //Seteo los Usuarios que tendra cada Rol
        rolMedico.setUsuarios(Set.of(usuarioMedico, usuarioMedico2,usuarioMedico3,usuarioMedico4,usuarioMedico5));
        rolMedico2.setUsuarios(Set.of(usuarioMedico, usuarioMedico3, usuarioEnfermero));
        rolEnfermero.setUsuarios(Set.of(usuarioEnfermero));
        rolFuncionario.setUsuarios(Set.of(usuario, usuarioFuncAdmin, usuarioFuncAdmin2, usuarioFuncionario));
        rolSistemas.setUsuarios(Set.of(usuarioAdminSistemas));

        //Actualizar Roles en la BD
        rolDAO.actualizar(rolMedico);
        rolDAO.actualizar(rolMedico2);
        rolDAO.actualizar(rolEnfermero);
        rolDAO.actualizar(rolFuncionario);
        rolDAO.actualizar(rolSistemas);


//************************************************* Sectores del Hospital ***************************************//

        //Creacion
        Sector sectorEmergencias = new Sector("Emergencias");
        Sector sectorClinico = new Sector("Clinico");
        Sector sectorQuirugico = new Sector("Quirurgico");
        Sector sectorAdmicion = new Sector("Admicion de pacientes");
        Sector sectorAdministracion = new Sector("Administracion");
        Sector sectorInformatica = new Sector("Informatica");
        Sector sectorGerencia = new Sector("Gerencia");
        Sector sectorMedico = new Sector("Incisiones");

        //Persistencia
        sectorDAO.agregar(sectorEmergencias);
        sectorDAO.agregar(sectorClinico);
        sectorDAO.agregar(sectorQuirugico);
        sectorDAO.agregar(sectorAdmicion);
        sectorDAO.agregar(sectorAdministracion);
        sectorDAO.agregar(sectorInformatica);
        sectorDAO.agregar(sectorGerencia);
        sectorDAO.agregar(sectorMedico);


//************************************************* Universidades ***********************************************//

        //Creacion
        Universidad universidadMedico = new Universidad("Facultad de Ciencias de la Salud (FCSA)");
        Universidad uba = new Universidad("Universidad de Buenos Aires (UBA)");
        Universidad unlp = new Universidad("Universidad Nacional de la Planta");

        //Persistencia
        universidadDAO.agregar(universidadMedico);
        universidadDAO.agregar(uba);
        universidadDAO.agregar(unlp);


//************************************************* Especialidades ***********************************************//

        //Creacion
        Especialidad especialidad = new Especialidad("Ninguna",LocalDate.of(1999,4,1),universidadMedico);
        Especialidad especialidad1 = new Especialidad("Cirugia",LocalDate.of(2000,4,11),uba);
        Especialidad especialidad2 = new Especialidad("Ginecologia",LocalDate.of(2014,9,20),universidadMedico);
        Especialidad especialidad3 = new Especialidad("Cirugia ",LocalDate.of(1993,5,25),uba);
        Especialidad especialidad4 = new Especialidad("Pediatria",LocalDate.of(2018,11,20),unlp);

        //Persistencia
        especialidadDAO.agregar(especialidad);
        especialidadDAO.agregar(especialidad1);
        especialidadDAO.agregar(especialidad2);
        especialidadDAO.agregar(especialidad3);
        especialidadDAO.agregar(especialidad4);


//************************************************* Medicos ******************************************************//

        //Creacion Medicos
        Medico medico = new Medico("Juan","Ramirez", LocalDate.of(1955,3,21),"La ferre",11345433,11054332, 3455321243L, EstadoCivil.Casado, "juancarlosramirez@gmail.com",usuarioMedico, sectorMedico,"123456789", List.of(especialidad));

        Medico medico2 = new Medico("Esteban","Blanco", LocalDate.of(1980,10,5),"Lamadrid 666",12498532,4231248, 345698754, EstadoCivil.Soltero, "blancoesteban@gmail.com",usuarioMedico2, sectorEmergencias,"J4C83N45", List.of(especialidad1));

        Medico medico3 = new Medico("Marisol","Niez", LocalDate.of(1985,9,2),"Urdinarain 550",14353859,4235325, 345376537, EstadoCivil.Soltero, "marisolniez@gmail.com",usuarioMedico3, sectorClinico,"235J6S98N3", List.of(especialidad2));

        Medico medico4 = new Medico("Ignacio","Paiz", LocalDate.of(1962,1,12),"Velez Sarfiel 23",13947293,387487465, 345376533, EstadoCivil.Casado, "iganciopaiz@gmail.com",usuarioMedico4, sectorQuirugico,"35NK46N7L3", List.of(especialidad3));

        Medico medico5 = new Medico("Leonel","Nuñez", LocalDate.of(1993,2,3),"Humberto Primo 934",1245534532,435335232, 345377658, EstadoCivil.Conviviente, "nuñesleonel@gmail.com",usuarioMedico5, sectorClinico,"3LK5875NK356", List.of(especialidad4));

        //Creacion Enfermero
        Enfermero enfermero = new Enfermero("Martin","Arce",LocalDate.of(1994, 5, 19), "Balcarce 50", 38648306, 33525423, 34583742, EstadoCivil.Soltero, "arcematias@gmail.com",usuarioEnfermero, sectorClinico);

        //Persistencia
        medicoDAO.agregar(medico2);
        medicoDAO.agregar(medico3);
        medicoDAO.agregar(medico4);
        medicoDAO.agregar(medico5);
        enfermeroDAO.agregar(enfermero);

        //Seteo los Medicos a la Especialidad
        especialidad.setMedico(medico);
        especialidad1.setMedico(medico2);
        especialidad2.setMedico(medico3);
        especialidad3.setMedico(medico4);
        especialidad4.setMedico(medico5);

        //Seteo los Medicos(Funcionarios) al Sector que trabajan
        sectorMedico.setFuncionarios(Set.of(medico));
        sectorEmergencias.setFuncionarios(Set.of(medico2));
        sectorClinico.setFuncionarios(Set.of(medico3,medico5,enfermero));
        sectorQuirugico.setFuncionarios(Set.of(medico4));

        //Seteo los Medicos(Funcionarios) a su Usuario correspondiente
        usuarioMedico.setFuncionario(medico);
        usuarioMedico2.setFuncionario(medico2);
        usuarioMedico3.setFuncionario(medico3);
        usuarioMedico4.setFuncionario(medico4);
        usuarioMedico5.setFuncionario(medico5);
        usuarioEnfermero.setFuncionario(enfermero);

        //Actualizar Usuarios en la BD
        usuarioDAO.actualizar(usuarioMedico);
        usuarioDAO.actualizar(usuarioMedico2);
        usuarioDAO.actualizar(usuarioMedico3);
        usuarioDAO.actualizar(usuarioMedico4);
        usuarioDAO.actualizar(usuarioMedico5);
        usuarioDAO.actualizar(usuarioEnfermero);


//************************************************* Funcionarios  *******************************************//

        //Creacion
        FuncionarioAdministrativo funAdmin = new FuncionarioAdministrativo("Daniela", "López",LocalDate.of(1980,3,2),"Rocamora 91",31598762,42698756,3454169865L,EstadoCivil.Soltero,"danielitalop@hotmail.com",usuario, sectorAdmicion);

        FuncionarioAdministrativo funcionarioAdmin = new FuncionarioAdministrativo("German", "Carrozo",LocalDate.of(1985,7,11),"Santisima Trinidad 1320",31948747,412346324,34537643,EstadoCivil.Casado,"carrozogerman@hotmail.com", usuarioFuncAdmin, sectorAdmicion);

        FuncionarioAdministrativo funcionarioAdmin2 = new FuncionarioAdministrativo("Analia", "Melgar", LocalDate.of(1987, 1, 20), "Las Heras 340", 15387792, 48365762, 345873762, EstadoCivil.Soltero, "analiamelgar@hotmail.com", usuarioFuncAdmin2, sectorAdministracion);

        Funcionario funcionario = new Funcionario("Oscar", "Balbuena", LocalDate.of(1995, 5, 11), "Peru 45", 40217284, 428973223, 345874656, EstadoCivil.Soltero, "balbuenaoscar@hotmail.com", usuarioFuncionario, sectorGerencia);

        AdministradorSistemas administradorSistemas = new AdministradorSistemas("Angel","Villalba",LocalDate.of(1996,5,11),"Jose Rucci 340", 41432875, 439378921, 345873536, EstadoCivil.Soltero, "villalbaangel@gmail.com",usuarioAdminSistemas,sectorInformatica);

        //Persistencia
        funcionarioAdministrativoDAO.agregar(funAdmin);
        funcionarioAdministrativoDAO.agregar(funcionarioAdmin);
        funcionarioAdministrativoDAO.agregar(funcionarioAdmin2);
        funcionarioDAO.agregar(funcionario);
        adminSisDAO.agregar(administradorSistemas);

        //Seteo los Funcionarios a su Usuario correspondiente
        usuario.setFuncionario(funAdmin);
        usuarioFuncAdmin.setFuncionario(funcionarioAdmin);
        usuarioFuncAdmin2.setFuncionario(funcionarioAdmin2);
        usuarioFuncionario.setFuncionario(funcionario);
        usuarioAdminSistemas.setFuncionario(administradorSistemas);

        //Seteo los Funcionarios al Sector que trabajan
        sectorAdmicion.setFuncionarios(Set.of(funcionarioAdmin));
        sectorAdministracion.setFuncionarios(Set.of(funcionarioAdmin2));
        sectorInformatica.setFuncionarios(Set.of(administradorSistemas));
        sectorGerencia.setFuncionarios(Set.of(funcionario));

        //Actualizar los Usuarios en la BD
        usuarioDAO.actualizar(usuario);
        usuarioDAO.actualizar(usuarioFuncAdmin);
        usuarioDAO.actualizar(usuarioFuncAdmin2);
        usuarioDAO.actualizar(usuarioFuncionario);
        usuarioDAO.actualizar(usuarioAdminSistemas);

//************************************************* Pacientes ***************************************************//

        //Creacion
        Paciente paciente = new Paciente("Juan" ,"Pérez",LocalDate.of(1975,11,3),"Sargento Rodriguez",20113654,4259761,3454698743L,EstadoCivil.Casado,"juancitoperez@gmail.com", 141414325);

        Paciente paciente2 = new Paciente("Johanna", "Ramírez", LocalDate.of(1998,6,19),"Gatto 1929",25698413, 4259813, 3456987136L, EstadoCivil.Casado, "johannitaramirez@gmail.com", 124124123);

        Paciente paciente3 = new Paciente("Maria","Flores",LocalDate.of(1987,3,18),"Guemez 129", 13193282, 13435233,12434542,EstadoCivil.Casado,"mariaF32@gmail.com",122414646);

        Paciente paciente4 = new Paciente("Roman","Martinez",LocalDate.of(1962,11,1),"Quintana 654", 49184164, 12453532, 235877633,EstadoCivil.Viudo,"martinezroman@gmail.com",438785323);

        Paciente paciente5 = new Paciente("Camilo","Juares",LocalDate.of(1990,5,23),"Av. San Lorenzo 400", 74183654, 13873985, 358768277,EstadoCivil.Soltero,"camilito@gmail.com",842792931);

        Paciente paciente6 = new Paciente("Agostina","Pilar",LocalDate.of(2004,2,1),"San luis 20", 43194827, 876846332, 63768357, EstadoCivil.Soltero,"agos123@gmail.com",146871266);

        Paciente paciente7 = new Paciente("Gabriel","Flores",LocalDate.of(1998,11,6),"GuemezEntre Rios 490", 18294831, 375437545, 765386536,EstadoCivil.Soltero,"gabiflores@gmail.com",37652532);

        Paciente paciente8 = new Paciente("Facundo","Lopez",LocalDate.of(2000,3,18),"Laprida 550", 40294928, 451235356, 34535325,EstadoCivil.Soltero,"faculopez@gmail.com",375676562);

        Paciente paciente9 = new Paciente("Juan","Quiroz",LocalDate.of(1990,7,30),"Saveedra 70", 36219846, 894128981, 345718383,EstadoCivil.Conviviente,"juanchi@gmail.com",345766378);

        Paciente paciente10 = new Paciente("Juan Pablo","Jurado",LocalDate.of(2001,3,11),"Yrigoyen 20", 43921034, 42567553, 345127761,EstadoCivil.Soltero,"jpjurado@gmail.com",34588292);

        Paciente paciente11 = new Paciente("Julieta","Cabrera",LocalDate.of(1987,5,1),"Urquiza 789", 29484123, 42664643, 345764746,EstadoCivil.Conviviente,"julic@gmail.com",345873872);

        //Persistencia
        pacienteDAO.agregar(paciente);
        pacienteDAO.agregar(paciente2);
        pacienteDAO.agregar(paciente3);
        pacienteDAO.agregar(paciente4);
        pacienteDAO.agregar(paciente5);
        pacienteDAO.agregar(paciente6);
        pacienteDAO.agregar(paciente7);
        pacienteDAO.agregar(paciente8);
        pacienteDAO.agregar(paciente9);
        pacienteDAO.agregar(paciente10);
        pacienteDAO.agregar(paciente11);

//************************************************* Comienzo de Tramites ***************************************************//

//************************************************* Registros de Entradas ***************************************//

        //Creacion
        RegistroEntrada registroEntrada1 = new RegistroEntrada("Dolor estomacal",paciente3,funcionarioAdmin,LocalDate.of(2023,4,12), LocalTime.of(14,32));

        RegistroEntrada registroEntrada2 = new RegistroEntrada("Sangrado en la cabeza y contucion",paciente4,funcionarioAdmin,LocalDate.of(2023,4,12),LocalTime.of(9,12));

        RegistroEntrada registroEntrada3 = new RegistroEntrada("Dolor de espalda",paciente5,funcionarioAdmin,LocalDate.of(2023,4,23),LocalTime.of(6,0));

        RegistroEntrada registroEntrada4 = new RegistroEntrada("Sangrado en el brazo",paciente6,funcionarioAdmin,LocalDate.of(2023,7,12),LocalTime.of(13,43));

        RegistroEntrada registroEntrada5 = new RegistroEntrada("Dolor muscular",paciente7,funcionarioAdmin,LocalDate.of(2023,5,20),LocalTime.of(18,23));

        RegistroEntrada registroEntrada6 = new RegistroEntrada("Dolor de cabeza",paciente8,funcionarioAdmin2,LocalDate.of(2023,5,2),LocalTime.of(9,24));

        RegistroEntrada registroEntrada7 = new RegistroEntrada("Vision nublada, y dolor de cabeza",paciente9,funAdmin,LocalDate.of(2023,10,16),LocalTime.of(4,12));

        RegistroEntrada registroEntrada8 = new RegistroEntrada("Coma alcholico",paciente10,funcionarioAdmin2,LocalDate.of(2024,1,1),LocalTime.of(9,32));

        RegistroEntrada registroEntrada9 = new RegistroEntrada("Revision",paciente11,funcionarioAdmin2,LocalDate.of(2023,6,7),LocalTime.of(8,46));

        //Persistencia
        registroEntradaDAO.agregar(registroEntrada1);
        registroEntradaDAO.agregar(registroEntrada2);
        registroEntradaDAO.agregar(registroEntrada3);
        registroEntradaDAO.agregar(registroEntrada4);
        registroEntradaDAO.agregar(registroEntrada5);
        registroEntradaDAO.agregar(registroEntrada6);
        registroEntradaDAO.agregar(registroEntrada7);
        registroEntradaDAO.agregar(registroEntrada8);
        registroEntradaDAO.agregar(registroEntrada9);

        //Realizacion a travez der los metodos
        funAdmin.RealizarRegistroEntrada(paciente,"Dolor de cabeza y fiebre");

        funAdmin.RealizarRegistroEntrada(paciente2, "Vómitos y diarrea");


    }
}