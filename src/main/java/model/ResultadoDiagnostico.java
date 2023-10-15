package model;

import lombok.*;
import jakarta.persistence.*;

/**
 * Clase que guarda el resultado del diagnóstico del paciente, otorgado por el médico
 *
 * Se relaciona solamente con Paciente
 */

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
