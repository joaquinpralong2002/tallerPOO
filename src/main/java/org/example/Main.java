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
import java.util.List;
import java.util.Set;


public class Main {
    public static void main(String[] args) {

        //Se inicializa el Session Factory de la clase GlobalSessionFactory.
        GlobalSessionFactory init = new GlobalSessionFactory();
        init.InitGlobalSessionFactory();

        //Se crean los DAOS que se utilizaran para interactuar con la base de datos.
        PacienteDAO pacienteDAO = new PacienteDAO();
        BoxAtencionDAO boxAtencionDAO = new BoxAtencionDAO();
        RolDAO rolDAO = new RolDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        SectorDAO sectorDAO = new SectorDAO();
        FuncionarioAdministrativoDAO funcionarioAdministrativoDAO = new FuncionarioAdministrativoDAO();
        UniversidadDAO universidadDAO = new UniversidadDAO();
        EspecialidadDAO especialidadDAO = new EspecialidadDAO();
        MedicoDAO medicoDAO = new MedicoDAO();
        AdministradorSistemasDAO administradorSistemasDAO = new AdministradorSistemasDAO();
        RegistroDAO registroDAO = new RegistroDAO();
        ResultadoDiagnosticoDAO resultadoDiagnosticoDAO = new ResultadoDiagnosticoDAO();

        //Creacion de Administrador Sistemas
        Rol rolSistemas = new Rol("Sistemas");
        rolDAO.agregar(rolSistemas);

        Usuario usuarioAdminSistemas = new Usuario("villalbaangel","villalbaangel123",List.of(rolSistemas));
        usuarioDAO.agregar(usuarioAdminSistemas);
        rolSistemas.setUsuarios(Set.of(usuarioAdminSistemas));
        usuarioAdminSistemas.setRoles(List.of(rolSistemas));

        Sector sectorInformatica = new Sector("Informatica");
        sectorDAO.agregar(sectorInformatica);

        AdministradorSistemas administradorSistemas = new AdministradorSistemas("Angel","Villalba",LocalDate.of(1996,5,11),"Jose Rucci 340", 41432875, 439378921, 345873536, EstadoCivil.Soltero, "villalbaangel@gmail.com",usuarioAdminSistemas,sectorInformatica);
        funcionarioAdministrativoDAO.agregar(administradorSistemas);
        usuarioAdminSistemas.setFuncionario(administradorSistemas);
        usuarioDAO.actualizar(usuarioAdminSistemas);

        //Se crea un box de atención
        BoxAtencion boxAtencion = new BoxAtencion(LugarAtencion.Consultorio,1,30,true);
        boxAtencionDAO.agregar(boxAtencion);

        //Creación de un paciente.
        Paciente paciente = new Paciente("Juan" ,"Pérez",LocalDate.of(1975,11,3),"Sargento Rodriguez",20113654,
                4259761,3454698743L,EstadoCivil.Casado,"juancitoperez@gmail.com"
        ,"Pepe Sand");
        pacienteDAO.agregar(paciente);

        Paciente paciente2 = new Paciente("Johanna", "Ramírez", LocalDate.of(1998,6,19),"Gatto 1929",
                25698413, 4259813, 3456987136L, EstadoCivil.Casado, "johannitaramirez@gmail.com", "Roman");
        pacienteDAO.agregar(paciente2);

        //*********Asignacion del rol al funcionario*******
        Rol rol = new Rol("Funcionario");
        rolDAO.agregar(rol);

        //*********Usuario del Funcionario y seter del rol*********
        Usuario usuario = new Usuario("danielalopez","perrochichuA8€");
        usuarioDAO.agregar(usuario);
        rol.setUsuarios(Set.of(usuario));
        usuario.setRoles(List.of(rol));

        //*********Sector al cual pertenece el funcionario*********
        Sector sector = new Sector("Funcionario");
        sectorDAO.agregar(sector);

        //*********Funcionario Administrativo que toma los datos del paciente*********
        FuncionarioAdministrativo funAdmin = new FuncionarioAdministrativo("Daniela", "López",LocalDate.of(1980,3,2)
        ,"Rocamora 91",31598762,42698756,3454169865L,EstadoCivil.Soltero,"danielitalop@hotmail.com",usuario, sector);
        funcionarioAdministrativoDAO.agregar(funAdmin);
        usuario.setFuncionario(funAdmin);
        usuarioDAO.actualizar(usuario);

        //*********Registro de entrada del funcionario administrativo al paciente*********
        funAdmin.RealizarRegistroEntrada(paciente,"Dolor de cabeza y fiebre");

        funAdmin.RealizarRegistroEntrada(paciente2, "Vómitos y diarrea");

        //*******************CREACION DEL MEDICO******************************
        //*******************MEDICOOOOOOO***********************************
        Rol rolMedico = new Rol("Medico");
        Rol rolMedico2 = new Rol("Triage");
        rolDAO.agregar(rolMedico);
        rolDAO.agregar(rolMedico2);

        Usuario usuarioMedico = new Usuario("juancarlosramirez@gmail.com","juancito01&");
        usuarioDAO.agregar(usuarioMedico);
        rolMedico.setUsuarios(Set.of(usuarioMedico));
        usuarioMedico.setRoles(List.of(rolMedico, rolMedico2));
        usuarioDAO.actualizar(usuarioMedico);

        //Sector al cual pertenece el medico
        Sector sectorMedico = new Sector("Incisiones");
        sectorDAO.agregar(sectorMedico);

        //Universidad del medico
        Universidad universidadMedico = new Universidad("Facultad de Ciencias de la Salud (FCSA)");
        universidadDAO.agregar(universidadMedico);

        //Especialidad del medico
        Especialidad especialidad = new Especialidad("Ninguna",LocalDate.of(1999,4,1),universidadMedico);
        especialidadDAO.agregar(especialidad);

        //Creacion del medico
        Medico medico = new Medico("Ramirez","Juan", LocalDate.of(1955,3,21),"La ferre",11345433,
                11054332, 3455321243L, EstadoCivil.Casado, "juancarlosramirez@gmail.com",usuarioMedico, sectorMedico,
                "123456789", List.of(especialidad));
        medicoDAO.agregar(medico);
        usuarioMedico.setFuncionario(medico);

        medico.realizarTriage(Respiracion.Normal, Pulso.Normal,12, EstadoMental.Normal, Conciencia.Consciente,
                DolorPecho.NoPresnte, LecionesGraves.NoPresentes, Edad.NinioAnciano,81, Fiebre.Alta, 38, Vomitos.SinVomito,
                DolorAbdominal.NoPresente, SignoShock.NoPresente, LesionLeve.NoPresente, Sangrado.NoPresente);

        medico.confirmarTriage(paciente.getRegistrosEntradas().get(paciente.getRegistrosEntradas().size() - 1),
                medico.getTriagesRealizados().get(medico.getTriagesRealizados().size() - 1),medico.getTriagesRealizados().get(medico.getTriagesRealizados().size() - 1).getColorTriageRecomendado());

        medico.atenderPaciente(paciente, boxAtencion, "CORONAVAIRUS");



        especialidad.setMedico(medico);
        sectorMedico.setFuncionarios(Set.of(medico));

        System.out.println(medico.toString());


    }
}