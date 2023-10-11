package model;

import lombok.*;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
public class ResultadoDiagnostico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResultadoDiagnostico;
    private String descripcion;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "idPaciente", nullable = false)
    private Paciente paciente;

    public ResultadoDiagnostico(String descripcion){
        this.descripcion = descripcion;
    }

    public ResultadoDiagnostico(String descripcion, Paciente paciente){
        this.descripcion = descripcion;
        this.paciente = paciente;
    }
}
