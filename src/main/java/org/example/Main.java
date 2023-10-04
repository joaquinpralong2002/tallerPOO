package org.example;
import model.*;
import model.Enum.EstadoCivil;
import model.EnumeracionesVariablesTriage.Respiracion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

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
        f1.RealizarRegistroEntrada(p1,LocalDate.now(), LocalTime.now(),"dolor cabeza");

        //Ahora un medico o enfermero X lo llama para realizar el triage, ya esta en la ventana del programa para realizar el triage
        Especialidad ep = new Especialidad("Cardiologia Clinica",LocalDate.of(2014,5,11));
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
                "1a2b3c4d5e",
                null);
        m1.agregarEspecializacion(ep);

        //empieza a hacerles las preguntas y seleccionarlas en la ventana y le da al boton de crear triage
        m1.realizarTriage(p1);
        //si el que realizo el triage cambia el resultado setear el motivo y el color nuevo

        //ponele q el mismo medico va a atender ese paciente, el medico vera una lista de pacientes en la ventana y va a decidir atenderlo
        //se enviara a un administrativo que el medico quiere atender a tal paciente y se pasan todos los registros, selecciona y asigna un box disponible
    }
}