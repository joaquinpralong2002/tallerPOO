package model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDate;

/**
 * Clase que representa especialidades de médicos.
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

@Entity
public class Especialidad {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEspecialidad;

    private String nombre;
    private LocalDate fecha;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "idUniversidad")
    private Universidad universidad;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Medico medico;

    public Especialidad(String nombre, LocalDate fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public Especialidad(String nombre, LocalDate fecha, Universidad universidad) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.universidad = universidad;
    }

    public Especialidad(String nombre, LocalDate fecha, Universidad universidad, Medico medico) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.universidad = universidad;
        this.medico = medico;
    }
}
