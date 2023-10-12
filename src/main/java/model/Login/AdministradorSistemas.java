package model.Login;

import lombok.*;
import model.Enum.EstadoCivil;
import model.FuncionarioAdministrativo;
import model.Login.Rol;
import model.Login.Usuario;
import model.Sector;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AdministradorSistemas extends FuncionarioAdministrativo {

    public AdministradorSistemas(String nombre, String apellido, LocalDate fechaNacimiento, String domicilio, int DNI, int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo, Usuario usuario, Sector sector) {
        super(nombre, apellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo, usuario, sector);
    }

    public void asignarRol(Usuario u, Rol r){
        u.setRol(r);
    }
}
