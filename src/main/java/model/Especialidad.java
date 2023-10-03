package model;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class Especialidad {

    private Long idEspecializacion;

    private String nombre;
    private LocalDate fecha;

    private Universidad universidad;
    private Medico medico;

    public Especialidad(String nombre, LocalDate fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public Especialidad(String nombre, LocalDate fecha, Universidad universidad, Medico medico) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.universidad = universidad;
        this.medico = medico;
    }

}
