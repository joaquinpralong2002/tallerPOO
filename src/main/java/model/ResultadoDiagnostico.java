package model;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class ResultadoDiagnostico {
    private Long idResultadoDiagnostico;
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idPaciente")
    private Paciente paciente;

    public ResultadoDiagnostico(String descripcion){
        this.descripcion = descripcion;
    }

    public ResultadoDiagnostico(String descripcion, Paciente paciente){
        this.descripcion = descripcion;
        this.paciente = paciente;
    }
}
