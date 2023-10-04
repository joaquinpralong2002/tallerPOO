package model;
import lombok.*;
import model.Enum.EstadoCivil;
import model.Login.Usuario;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class Funcionario extends Persona{

    private Long idFuncionario;

    private Usuario usuario;
    private Sector sector;

    public Funcionario(String nombreApellido, LocalDate fechaNacimiento, String domicilio,
                       int DNI, int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil,
                       String correo, Usuario usuario, Sector sector) {

        super(nombreApellido, fechaNacimiento, domicilio,
              DNI, telefonoFijo, telefonoCelular, estadoCivil, correo);
        this.usuario = usuario;
        this.sector = sector;
    }


}
