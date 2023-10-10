package org.example;
import model.*;
import model.Enum.*;
import model.EnumeracionesVariablesTriage.*;
import model.Login.*;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import util.GlobalSessionFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.TRUE;


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


        //*********Tomando datos del paciente************
        Paciente paciente = new Paciente("Juan Pérez",LocalDate.of(1975,11,3),"Sargento Rodriguez",20113654,
                4259761,3454698743L,EstadoCivil.Casado,"juancitoperez@gmail.com"
        ,"Pepe Sand");

        //*********Asignacion del rol al funcionario*******
        Rol rol = new Rol("Administrativo");

        //*********Usuario del Funcionario y seter del rol*********
        Usuario usuario = new Usuario("danielalopez","perrochichuA8€");
        rol.setUsuarios(List.of(usuario));
        usuario.setRoles(List.of(rol));

        //*********Sector al cual pertenece el funcionario*********
        Sector sector = new Sector("Administración");

        //*********Funcionario Administrativo que toma los datos del paciente*********
        FuncionarioAdministrativo funAdmin = new FuncionarioAdministrativo("Daniela López",LocalDate.of(1980,3,2)
        ,"Rocamora 91",31598762,42698756,3454169865L,EstadoCivil.Soltero,"danielitalop@hotmail.com",usuario, sector);

        //*********Registro de entrada del funcionario administrativo al paciente*********
        funAdmin.RealizarRegistroEntrada(paciente,"Dolor de cabeza y fiebre");

        //*********Box de atencion por defecto del hospital*********
        BoxAtencion boxAtencion = new BoxAtencion(30,true);

        //*********Registro de la asignacion hacia el box de atencion*********
        Asignacion asignacion = new Asignacion(funAdmin.getRegistrosEntradas().get(0), boxAtencion);

        //*******************CREACION DEL MEDICO******************************
        //*******************MEDICOOOOOOO***********************************
        Rol rolMedico = new Rol("Medico");

        Usuario usuarioMedico = new Usuario("juancarlosramirez@gmail.com","juancito01&");
        rolMedico.setUsuarios(List.of(usuarioMedico));
        usuarioMedico.setRoles(List.of(rolMedico));

        //Sector al cual pertenece el medico
        Sector sectorMedico = new Sector("Incisiones");

        //Universidad del medico
        Universidad universidadMedico = new Universidad("Facultad de Ciencias de la Salud (FCSA)");

        //Especialidad del medico
        Especialidad especialidad = new Especialidad("Ninguna",LocalDate.of(1999,4,1),universidadMedico);

        //Creacion del medico
        Medico medico = new Medico("Ramirez Juan", LocalDate.of(1955,03,21),"La ferre",11345433,
                11054332, 3455321243L, EstadoCivil.Casado, "juancarlosramirez@gmail.com",usuarioMedico, sectorMedico,
                "123456789", List.of(especialidad));

        medico.agregarBox(boxAtencion);

        especialidad.setMedico(medico);
        sectorMedico.setFuncionarios(List.of(medico));

        medico.realizarTriage(funAdmin.getRegistrosEntradas().get(0));

        Registro registro = new Registro(LugarAtencion.Consultorio,paciente,medico);

        ResultadoDiagnostico resultadoDiagnostico = new ResultadoDiagnostico("Dolor de cabeza y fiebre",paciente);

        System.out.println(medico.toString());

        SessionFactory sessionFactory = new Configuration()
                //Clases mapeadas
                .addAnnotatedClass(AdministradorSistemas.class)
                .addAnnotatedClass(Rol.class)
                .addAnnotatedClass(Usuario.class)
                .addAnnotatedClass(Asignacion.class)
                .addAnnotatedClass(BoxAtencion.class)
                .addAnnotatedClass(Enfermero.class)
                .addAnnotatedClass(Especialidad.class)
                .addAnnotatedClass(Funcionario.class)
                .addAnnotatedClass(FuncionarioAdministrativo.class)
                .addAnnotatedClass(Medico.class)
                .addAnnotatedClass(Paciente.class)
                .addAnnotatedClass(Persona.class)
                .addAnnotatedClass(Registro.class)
                .addAnnotatedClass(RegistroEntrada.class)
                .addAnnotatedClass(ResultadoDiagnostico.class)
                .addAnnotatedClass(Sector.class)
                .addAnnotatedClass(Triage.class)
                .addAnnotatedClass(Universidad.class)
                //url
                .setProperty(AvailableSettings.URL, "jdbc:mysql://localhost:3306/tallerdb")
                // Credenciales
                .setProperty(AvailableSettings.USER, "usuario")
                .setProperty(AvailableSettings.PASS, "basededatostallerpoo123")
                .setProperty(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQLDialect")
                .setProperty(AvailableSettings.DRIVER, "com.mysql.cj.jdbc.Driver")
                // Automatic schema export
                // SQL logging
                .setProperty(AvailableSettings.SHOW_SQL, TRUE.toString())
                .setProperty(AvailableSettings.FORMAT_SQL, TRUE.toString())
                .setProperty(AvailableSettings.HIGHLIGHT_SQL, TRUE.toString())
                .setProperty(AvailableSettings.HBM2DDL_AUTO, "create-drop")
                // Creación de SessionFactory
                .buildSessionFactory();
        //GlobalSessionFactory gsf = new GlobalSessionFactory();
        //gsf.InitGlobalSessionFactory(args[0], args[1], args[2]);
        //gsf.InitGlobalSessionFactory("usuario","basededatostallerpoo123","jdbc:mysql://localhost:3306/tallerdb", "org.hibernate.dialect.MySQLDialect");
        sessionFactory.inTransaction(session -> {
            session.persist(boxAtencion);
            session.persist(paciente);
            session.persist(rol);
            session.persist(usuario);
            session.persist(sector);
            session.persist(funAdmin);
            session.persist(funAdmin.getRegistrosEntradas().get(0));
            session.persist(rolMedico);
            session.persist(usuarioMedico);
            session.persist(sectorMedico);
            session.persist(universidadMedico);
            session.persist(resultadoDiagnostico);
        });
    }
}