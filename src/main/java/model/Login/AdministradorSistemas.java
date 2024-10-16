package model.Login;

import jakarta.persistence.Entity;
import lombok.*;
import model.Enum.EstadoCivil;
import model.FuncionarioAdministrativo;
import model.Login.Rol;
import model.Login.Usuario;
import model.Sector;

import java.time.LocalDate;

/**
 * Clase que representa un Funcionario Administrativo del tipo Informatico.
 */

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)

@Entity
public class AdministradorSistemas extends FuncionarioAdministrativo {

    public AdministradorSistemas(String nombre, String apellido, LocalDate fechaNacimiento, String domicilio, int DNI, int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo, Usuario usuario, Sector sector) {
        super(nombre, apellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo, usuario, sector);
    }

    /**
     * Metodo para asignar un Rol a un Usuario.
     * @param u el usuario al que se le asignara el rol
     * @param r rol que se le va a asignar
     */
    public void asignarRol(Usuario u, Rol r){
        u.setRol(r);
    }
}
