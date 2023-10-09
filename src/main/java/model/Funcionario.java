package model;

import jakarta.persistence.*;
import lombok.*;

import model.Enum.EstadoCivil;
import model.Login.Usuario;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
public class Funcionario extends Persona{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFuncionario;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Sector sector;

    //constructor
    public Funcionario(String nombreApellido, LocalDate fechaNacimiento, String domicilio,
                       int DNI, int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil,
                       String correo, Usuario usuario, Sector sector) {

        super(nombreApellido, fechaNacimiento, domicilio,
              DNI, telefonoFijo, telefonoCelular, estadoCivil, correo);
        this.usuario = usuario;
        this.sector = sector;
    }
}
