package org.example;
import model.*;
import model.Enum.*;
import model.EnumeracionesVariablesTriage.*;
import model.Login.*;

import util.GlobalSessionFactory;

import java.time.LocalDate;
import java.time.LocalTime;


public class Main {
    public static void main(String[] args) {
        
        //objetos ramdom pa probar
        Asignacion asignacion1 = new Asignacion(LocalDate.now(), LocalTime.now());
        Enfermero enf1 = new Enfermero();


        //Pensa como: el funcionario entra al sistema, llega una persona y en la ventana donde crea el registro
        FuncionarioAdministrativo f1 = new FuncionarioAdministrativo();

        //toma los datos del paciente
        Paciente p1 = new Paciente("juancito flores",
                LocalDate.of(2000,8,11),
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
        Medico m1 = new Medico("Pepito Moreno",
                LocalDate.of(1987,2,20),
                "sasffas",
                12345679,
                0,
                0,
                EstadoCivil.Casado,
                "dsac",
                null,
                new Sector("Cardiologia"),
                "1a2b3c4d5e");
        m1.agregarEspecializacion(ep);

        System.out.println(m1);

        //empieza a hacerles las preguntas y seleccionarlas en la ventana y le da al boton de crear triage
        m1.realizarTriage(p1.getRegistrosEntradas().get(0));
        //si el que realizo el triage cambia el resultado setear el motivo y el color nuevo

        //ponele q el mismo medico va a atender ese paciente, el medico vera una lista de pacientes en la ventana y va a decidir atenderlo
        m1.atenderPaciente(p1, box1,p1.getRegistrosEntradas().get(0));



        GlobalSessionFactory gsf = new GlobalSessionFactory();
        //gsf.InitGlobalSessionFactory(args[0], args[1], args[2]);
        gsf.InitGlobalSessionFactory("usuario","basededatostallerpoo123","jdbc:mysql://localhost:3306/tallerdb", "org.hibernate.dialect.MySQLDialect");
        gsf.getSessionFactory().inTransaction(session -> {
            session.persist(f1);
            session.persist(p1);
            session.persist(universidad);
            session.persist(ep);
            session.persist(box1);
            session.persist(m1);
        });
    }
}