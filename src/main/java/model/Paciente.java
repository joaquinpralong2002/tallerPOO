package model;
import lombok.*;
import model.Enum.EstadoCivil;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class Paciente extends Persona{
    private Persona personaContacto;
    private List<ResultadoDiagnostico> resultadosDiagnosticos;

    public Paciente(String nombreApellido, LocalDate fechaNacimiento, String domicilio, int DNI,
                    int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo,
                    Persona personaContacto) {
        super(nombreApellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo);
        this.personaContacto = personaContacto;
    }

    public Paciente(String nombreApellido, LocalDate fechaNacimiento, String domicilio, int DNI,
                    int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo,
                    Persona personaContacto, List<ResultadoDiagnostico> resultadosDiagnosticos) {
        super(nombreApellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo);
        this.personaContacto = personaContacto;
        this.resultadosDiagnosticos = resultadosDiagnosticos;
    }

    public void agregarResultadoDiagnostico(ResultadoDiagnostico r){
        resultadosDiagnosticos.add(r);
    }


}
