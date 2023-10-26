package org.example;
import datasource.*;
import model.*;
import model.Enum.*;
import model.EnumeracionesVariablesTriage.*;
import model.Login.*;

import model.Login.Rol;
import org.hibernate.SessionFactory;
import util.GlobalSessionFactory;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;


public class Main {
    public static void main(String[] args) {

        //Se inicializa el Session Factory de la clase GlobalSessionFactory.
        GlobalSessionFactory init = new GlobalSessionFactory();
        init.InitGlobalSessionFactory();

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
        Medico medico = new Medico("Ramirez","Juan", LocalDate.of(1955,3,21),"La ferre",11345433,11054332, 3455321243L, EstadoCivil.Casado, "juancarlosramirez@gmail.com",usuarioMedico, sectorMedico,"123456789", List.of(especialidad));

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


//************************************************* Realizacion de triages **************************************//
        //medico.realizarTriage(Respiracion.Normal, Pulso.Normal,12, EstadoMental.Normal, Conciencia.Consciente,DolorPecho.NoPresnte, LecionesGraves.NoPresentes, Edad.NinioAnciano,81, Fiebre.Alta, 38, Vomitos.SinVomito,DolorAbdominal.NoPresente, SignoShock.NoPresente, LesionLeve.NoPresente, Sangrado.NoPresente);

        /*Triage triage = new Triage(registroEntrada1, medico,Respiracion.Normal, Pulso.Normal,22, EstadoMental.Normal, Conciencia.Consciente,DolorPecho.NoPresnte, LecionesGraves.NoPresentes, Edad.Adulto,25, Fiebre.SinFiebre, 30, Vomitos.Moderado,DolorAbdominal.DolorAbdominalModerado, SignoShock.NoPresente, LesionLeve.NoPresente, Sangrado.NoPresente);

        Triage triage3 = new Triage(registroEntrada3, enfermero,Respiracion.Normal,Pulso.Normal,15,EstadoMental.Normal,Conciencia.Consciente,DolorPecho.NoPresnte,LecionesGraves.NoPresentes,Edad.NinioAnciano,63,Fiebre.SinFiebre,28,Vomitos.SinVomito,DolorAbdominal.NoPresente,SignoShock.NoPresente,LesionLeve.NoPresente,Sangrado.NoPresente);

        Triage triage4 = new Triage(registroEntrada4, medico3,Respiracion.Normal,Pulso.Anormal,60,EstadoMental.Leve,Conciencia.Consciente,DolorPecho.NoPresnte,LecionesGraves.Presentes,Edad.NinioAnciano,10,Fiebre.SinFiebre,28,Vomitos.SinVomito,DolorAbdominal.NoPresente,SignoShock.Presente,LesionLeve.Presente,Sangrado.SangradoModerado);

        Triage triage5 = new Triage(registroEntrada5, medico,Respiracion.Normal,Pulso.Normal,20,EstadoMental.Normal,Conciencia.Consciente,DolorPecho.NoPresnte,LecionesGraves.NoPresentes,Edad.Adulto,30,Fiebre.SinFiebre,28,Vomitos.SinVomito,DolorAbdominal.DolorAbdominalModerado,SignoShock.NoPresente,LesionLeve.Presente,Sangrado.NoPresente);

        Triage triage6 = new Triage(registroEntrada6, medico,Respiracion.Normal,Pulso.Normal,15,EstadoMental.Normal,Conciencia.Consciente,DolorPecho.NoPresnte,LecionesGraves.NoPresentes,Edad.Adulto,37,Fiebre.SinFiebre,28,Vomitos.SinVomito,DolorAbdominal.NoPresente,SignoShock.NoPresente,LesionLeve.NoPresente,Sangrado.NoPresente);

        Triage triage7 = new Triage(registroEntrada7, enfermero,Respiracion.Normal,Pulso.Normal,30,EstadoMental.Normal,Conciencia.Consciente,DolorPecho.NoPresnte,LecionesGraves.NoPresentes,Edad.Adulto,15,Fiebre.SinFiebre,28,Vomitos.SinVomito,DolorAbdominal.NoPresente,SignoShock.NoPresente,LesionLeve.NoPresente,Sangrado.NoPresente);

        Triage triage8 = new Triage(registroEntrada8, medico3,Respiracion.Normal,Pulso.Normal,25,EstadoMental.Leve,Conciencia.PerdidaConsciencia,DolorPecho.NoPresnte,LecionesGraves.NoPresentes,Edad.Adulto,19,Fiebre.SinFiebre,28,Vomitos.Intenso,DolorAbdominal.DolorAbdominalSevero,SignoShock.NoPresente,LesionLeve.NoPresente,Sangrado.NoPresente);

        Triage triage9 = new Triage(registroEntrada9, enfermero,Respiracion.Normal,Pulso.Normal,15,EstadoMental.Normal,Conciencia.Consciente,DolorPecho.NoPresnte,LecionesGraves.NoPresentes,Edad.Adulto,28,Fiebre.SinFiebre,28,Vomitos.SinVomito,DolorAbdominal.NoPresente,SignoShock.NoPresente,LesionLeve.NoPresente,Sangrado.NoPresente);

        //Seteo del Color Triage Recomendado
        triage.calcularColorTriageRecomendado();
        triage3.calcularColorTriageRecomendado();
        triage4.calcularColorTriageRecomendado();
        triage5.calcularColorTriageRecomendado();
        triage6.calcularColorTriageRecomendado();
        triage7.calcularColorTriageRecomendado();
        triage8.calcularColorTriageRecomendado();
        triage9.calcularColorTriageRecomendado();*/

//************************************************* Confirmacion de triages **************************************//
        /*//Confirmacion de Triages del Medico
        medico.confirmarTriage(paciente.getRegistrosEntradas().get(paciente.getRegistrosEntradas().size() - 1),medico.getTriagesRealizados().get(medico.getTriagesRealizados().size() - 1),medico.getTriagesRealizados().get(medico.getTriagesRealizados().size() - 1).getColorTriageRecomendado());
        medico.confirmarTriage(registroEntrada1, triage,triage.getColorTriageRecomendado());
        medico.confirmarTriage(registroEntrada5, triage5,triage5.getColorTriageRecomendado());
        medico.confirmarTriage(registroEntrada6, triage6,triage6.getColorTriageRecomendado());

        //Confirmacion de Triages del Enfermero
        enfermero.confirmarTriage(registroEntrada3,triage3,triage3.getColorTriageRecomendado());
        enfermero.confirmarTriage(registroEntrada7,triage7,triage7.getColorTriageRecomendado());
        enfermero.confirmarTriage(registroEntrada9,triage9,triage9.getColorTriageRecomendado());

        //Confirmacion de Triages del Medico 3
        medico3.confirmarTriage(registroEntrada4,triage4,triage4.getColorTriageRecomendado());
        medico3.confirmarTriage(registroEntrada8,triage8,triage8.getColorTriageRecomendado());*/

//************************************************* Atencion de Pacientes **************************************//
        /*medico.atenderPaciente(paciente, boxAtencion, "CORONAVAIRUS");
        medico4.atenderPaciente(paciente3,box1,"Parasitos");
        medico2.atenderPaciente(paciente5,box2,"Sobreesfuerzo muscular");
        medico3.atenderPaciente(paciente7,box3,"Sobreesfuerzo muscular");
        medico5.atenderPaciente(paciente11,box2,"Sin resulados");
        */
        //no deja q un medico haga mas de 1 registro
//        medico.atenderPaciente(paciente4,box6,"Corte en la cabeza");
//        medico.atenderPaciente(paciente6,box7,"Corte en el antebrazo");
//        medico2.atenderPaciente(paciente8,box4,"Migraña generada por estres");
//        medico4.atenderPaciente(paciente9,box8,"Contucion cerebral por golpe");
//        medico.atenderPaciente(paciente10,box11,"Coma Alcholico");






        //HICE CAGADA
    }
}