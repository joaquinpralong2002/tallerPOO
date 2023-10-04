package model;

import lombok.*;
import model.Enum.ColorTriage;
import model.Enum.EstadoCivil;
import model.Login.Usuario;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString


public class Enfermero extends Funcionario implements CapacitadoTriage{
    private Long idEnfermero;

    public Enfermero(String nombreApellido, LocalDate fechaNacimiento, String domicilio,
                     int DNI, int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil,
                     String correo, Usuario usuario, Sector sector) {
        super(nombreApellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo, usuario,sector);
    }

    @Override
    public ColorTriage realizarTriage(RegistroEntrada r) {
        return null;
    }

    @Override
    public void cambiarColorTriage(Paciente p, String motivo) {

    }
}
