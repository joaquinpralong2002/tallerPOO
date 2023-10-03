package model;

import java.time.LocalDate;
import java.util.Objects;

import lombok.*;
import model.Enum.EstadoCivil;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class Persona {
    private Long idPersona;
    private String nombreApellido;
    private LocalDate fechaNacimiento;
    private String domicilio;
    private int DNI;
    private int telefonoFijo;
    private long telefonoCelular;
    private EstadoCivil estadoCivil;
    private String correo;

    public Persona(String nombreApellido, LocalDate fechaNacimiento, String domicilio,
                   int DNI, int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo) {
        this.nombreApellido = nombreApellido;
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
