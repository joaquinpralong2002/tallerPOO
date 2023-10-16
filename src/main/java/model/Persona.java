package model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.*;
import model.Enum.EstadoCivil;
import org.hibernate.annotations.NaturalId;

/**
 * Representa a una persona en el sistema.
 */

@Getter
@Setter
@NoArgsConstructor
@ToString

@MappedSuperclass
public class Persona {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String domicilio;
    @NaturalId
    private int DNI;
    private int telefonoFijo;
    private long telefonoCelular;
    @Enumerated(EnumType.STRING)
    private EstadoCivil estadoCivil;
    private String correo;

    public Persona(String nombre, String apellido, LocalDate fechaNacimiento, String domicilio,
                   int DNI, int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.DNI = DNI;
        this.telefonoFijo = telefonoFijo;
        this.telefonoCelular = telefonoCelular;
        this.estadoCivil = estadoCivil;
        this.correo = correo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return DNI == persona.DNI;
    }

    @Override
    public int hashCode() {
        return Objects.hash(DNI);
    }
}
