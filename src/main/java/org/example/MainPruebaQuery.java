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

        FuncionarioAdministrativoDAO funcionarioAdministrativoDAO = new FuncionarioAdministrativoDAO();

       /*
       Map<Long, Long> pacientes = funcionarioAdministrativoDAO.pacientesMasConsultas(LocalDate.of(2023,04,12),
                LocalDate.of(2023,10,26));

        System.out.println(pacientes);
        */


        MedicoDAO medicoDAO = new MedicoDAO();
        Map<Integer, String> triages = medicoDAO.TriageRangoFechas(LocalDate.of(2023,01,01),
                LocalDate.of(2023,12,26));
        System.out.println(triages);

    }
}
