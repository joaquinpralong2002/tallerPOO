package model;

import lombok.*;

@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class ResultadoDiagnostico {
    private String descripcion;
    private Paciente paciente;

    public ResultadoDiagnostico(String descripcion){
        this.descripcion = descripcion;
    }

    public ResultadoDiagnostico(String descripcion, Paciente paciente){
        this.descripcion = descripcion;
        this.paciente = paciente;
    }

}
