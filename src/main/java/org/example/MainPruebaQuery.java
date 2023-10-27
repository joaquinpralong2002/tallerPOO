package org.example;

import datasource.FuncionarioAdministrativoDAO;
import datasource.MedicoDAO;
import util.GlobalSessionFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import model.Paciente;

public class MainPruebaQuery {

    public static void main(String[] args) {
        GlobalSessionFactory globalSessionFactory = new GlobalSessionFactory();
        globalSessionFactory.InitGlobalSessionFactory();
        MedicoDAO medicoDAO = new MedicoDAO();

        //1ºCantidad de pacientes atendidos por un médico para un rango de fechas dadas
        System.out.println("Cantidad pacientes atendidos dadas fechas y médico:"
                + medicoDAO.cantidadPacientesAtendidos(medicoDAO.obtener(6L), LocalDate.of(2023, 04, 12),
                LocalDate.of(2024, 01, 01)));

        //2ºCantidad de pacientes atendidos en un rango de fechas y edades dadas
        System.out.println("Cantidad pacientes atendidos dadas fechas y edades:" +
                medicoDAO.cantidadPacientesAtendidos(LocalDate.of(2023, 04, 12), LocalDate.of(2024, 01, 01),
                        59, 61));

        //3ºPaciente/s que más consultaron en un rango de fechas
        FuncionarioAdministrativoDAO funcionarioAdministrativoDAO = new FuncionarioAdministrativoDAO();

        Map<Long, Long> pacientes = funcionarioAdministrativoDAO.pacientesMasConsultas(LocalDate.of(2023, 04, 12),
                LocalDate.of(2023, 10, 26));

        System.out.println(pacientes);


        //4º Médico/s que más pacientes haya atendido en un rango de fechas
        System.out.println(medicoDAO.obtenerMedicoConMasPacientesAtendidosEnRango(LocalDate.of(2023, 01, 01),
                LocalDate.of(2023, 12, 26)));


        //5º Triage realizado en un rango de fechas, indicándose la cantidad de cada color
        System.out.println(medicoDAO.TriageRangoFechas(LocalDate.of(2023, 01, 01),
                LocalDate.of(2023, 12, 26)));



    }
}
