package org.example;
import datasource.*;
import model.*;
import model.Enum.*;
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
        RegistroDAO registroDAO = new RegistroDAO();
        ResultadoDiagnosticoDAO resultadoDiagnosticoDAO = new ResultadoDiagnosticoDAO();

        //Se crea un box de atención
        BoxAtencion boxAtencion = new BoxAtencion(1,30,true);
        boxAtencionDAO.agregar(boxAtencion);

        //Creación de un paciente.
        Paciente paciente = new Paciente("Juan" ,"Pérez",LocalDate.of(1975,11,3),"Sargento Rodriguez",20113654,
                4259761,3454698743L,EstadoCivil.Casado,"juancitoperez@gmail.com"
        ,"Pepe Sand");
        pacienteDAO.agregar(paciente);

        //*********Asignacion del rol al funcionario*******
        Rol rol = new Rol("Administrativo");
        rolDAO.agregar(rol);

        //*********Usuario del Funcionario y seter del rol*********
        Usuario usuario = new Usuario("danielalopez","perrochichuA8€");
        usuarioDAO.agregar(usuario);
        rol.setUsuarios(Set.of(usuario));
        usuario.setRoles(Set.of(rol));

        //*********Sector al cual pertenece el funcionario*********
        Sector sector = new Sector("Administración");
        sectorDAO.agregar(sector);

        //*********Funcionario Administrativo que toma los datos del paciente*********
        FuncionarioAdministrativo funAdmin = new FuncionarioAdministrativo("Daniela", "López",LocalDate.of(1980,3,2)
        ,"Rocamora 91",31598762,42698756,3454169865L,EstadoCivil.Soltero,"danielitalop@hotmail.com",usuario, sector);
        funcionarioAdministrativoDAO.agregar(funAdmin);

        //*********Registro de entrada del funcionario administrativo al paciente*********
        funAdmin.RealizarRegistroEntrada(paciente,"Dolor de cabeza y fiebre");


        //*******************CREACION DEL MEDICO******************************
        //*******************MEDICOOOOOOO***********************************
        Rol rolMedico = new Rol("Medico");
        rolDAO.agregar(rolMedico);

        Usuario usuarioMedico = new Usuario("juancarlosramirez@gmail.com","juancito01&");
        usuarioDAO.agregar(usuarioMedico);
        rolMedico.setUsuarios(Set.of(usuarioMedico));
        usuarioMedico.setRoles(Set.of(rolMedico));

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

        medico.atenderPaciente(paciente, boxAtencion, paciente.getRegistrosEntradas().get(paciente.getRegistrosEntradas().size() - 1));
        medico.agregarBox(boxAtencion);

        especialidad.setMedico(medico);
        sectorMedico.setFuncionarios(Set.of(medico));

        medico.realizarTriage(funAdmin.getRegistrosEntradas().get(0));

        Registro registro = new Registro(LugarAtencion.Consultorio,paciente,medico);
        registroDAO.agregar(registro);

        ResultadoDiagnostico resultadoDiagnostico = new ResultadoDiagnostico("Dolor de cabeza y fiebre",paciente);
        resultadoDiagnosticoDAO.agregar(resultadoDiagnostico);

        System.out.println(medico.toString());

    }
}