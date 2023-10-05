package org.example;
import model.*;
import model.Enum.*;
import model.EnumeracionesVariablesTriage.*;
import model.Login.*;

import util.GlobalSessionFactory;

import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {

        GlobalSessionFactory gsf = new GlobalSessionFactory();
        gsf.InitGlobalSessionFactory(args[0], args[1], args[2]);


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
        //se enviara a un administrativo que el medico quiere atender a tal paciente y se pasan todos los registros, selecciona y asigna un box disponible
    }
}