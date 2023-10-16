package model;

import jakarta.persistence.*;
import lombok.*;

import model.Enum.EstadoCivil;
import model.Login.Usuario;

import java.time.LocalDate;

/**
 * Clase que representa un funcionario del hospital.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
public class Funcionario extends Persona{
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "idUsuario", nullable = false, unique = true)
    private Usuario usuario;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "idSector", nullable = false)
    private Sector sector;

    //constructor
    public Funcionario(String nombre,String apellido, LocalDate fechaNacimiento, String domicilio,
                       int DNI, int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil,
                       String correo, Usuario usuario, Sector sector) {

        super(nombre, apellido, fechaNacimiento, domicilio,
              DNI, telefonoFijo, telefonoCelular, estadoCivil, correo);
        this.usuario = usuario;
        this.sector = sector;
    }
}
