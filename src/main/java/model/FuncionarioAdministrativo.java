package model;

import java.time.LocalDate;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class FuncionarioAdministrativo extends Funcionario{
    private Long idFuncionarioAdministrativo;
    private List<RegistroEntrada> registrosEntradas;

    //public List<Paciente> pacientesMasConsultas(LocalDate fecha1, LocalDate fecha2){}


}
