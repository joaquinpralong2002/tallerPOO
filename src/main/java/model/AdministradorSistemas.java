package model;

import lombok.*;
import model.Enum.EstadoCivil;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AdministradorSistemas extends FuncionarioAdministrativo{
    private Long idAdministradorSistemas;

    public AdministradorSistemas(String nombreApellido, LocalDate fechaNacimiento, String domicilio, int DNI, int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo, Usuario usuario, Sector sector) {
        super(nombreApellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo, usuario, sector);
    }

    public void asignarRol(Usuario u, Rol r){
        u.setRol(r);
    }
}
