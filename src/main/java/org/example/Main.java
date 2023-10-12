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
        /*
        //objetos ramdom pa probar
        Asignacion asignacion1 = new Asignacion(LocalDate.now(), LocalTime.now());


        //Pensa como: el funcionario entra al sistema, llega una persona y en la ventana donde crea el registro
        FuncionarioAdministrativo f1 = new FuncionarioAdministrativo();

        //toma los datos del paciente
        Paciente p1 = new Paciente("juancito flores",
                LocalDate.of(2000,
                        8,11),
                "asdas",
                12345678,
                0,
                0,
                EstadoCivil.Soltero,
                "dasfafvfcas",
                null);

        //pone los datos en la ventana, los datos del registro tambien (fecha,hora y descripcion), y le da al boton crear registro
        f1.RealizarRegistroEntrada(p1,"dolor cabeza");

        //Ahora un medico o enfermero X lo llama para realizar el triage, ya esta en la ventana del programa para realizar el triage
        Universidad universidad = new Universidad("Uner");
        Especialidad ep = new Especialidad("Cardiologia Clinica",LocalDate.of(2014,5,11));
        BoxAtencion box1 = new BoxAtencion(1,2,true);
        ep.setUniversidad(universidad);
        Rol rol = new Rol("Salud");
        Usuario usuario = new Usuario("usuario","Pepito01@",);
        rol.setUsuarios(List.of(usuario));
        Medico m1 = new Medico("Pepito Moreno",
                LocalDate.of(1987,2,20),
                "sasffas",
                12345679,
                0,
                0,
                EstadoCivil.Casado,
                "dsac",
                new Sector("Cardiologia"),
                "1a2b3c4d5e");
        m1.agregarEspecializacion(ep);

        System.out.println(m1);

        //empieza a hacerles las preguntas y seleccionarlas en la ventana y le da al boton de crear triage
        m1.realizarTriage(p1.getRegistrosEntradas().get(0));
        //si el que realizo el triage cambia el resultado setear el motivo y el color nuevo

        //ponele q el mismo medico va a atender ese paciente, el medico vera una lista de pacientes en la ventana y va a decidir atenderlo
        m1.atenderPaciente(p1, box1,p1.getRegistrosEntradas().get(0));
        */

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