package model;
import lombok.*;
import model.Enum.EstadoCivil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class Paciente extends Persona{
    private Long idPaciente;
    private Persona personaContacto;

    private List<ResultadoDiagnostico> resultadosDiagnosticos;
    private List<RegistroEntrada> registrosEntradas = new ArrayList<>();
    private List<Registro> registros;

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

    public Paciente(String nombreApellido, LocalDate fechaNacimiento, String domicilio, int DNI,
                    int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo,
                    Persona personaContacto, List<ResultadoDiagnostico> resultadosDiagnosticos, List<RegistroEntrada> registrosEntradas) {
        super(nombreApellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo);
        this.personaContacto = personaContacto;
        this.resultadosDiagnosticos = resultadosDiagnosticos;
        this.registrosEntradas = registrosEntradas;
    }

    public Paciente(String nombreApellido, LocalDate fechaNacimiento, String domicilio, int DNI,
                    int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo,
                    Persona personaContacto, List<ResultadoDiagnostico> resultadosDiagnosticos,
                    List<RegistroEntrada> registrosEntradas, List<Registro> registros) {
        super(nombreApellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo);
        this.personaContacto = personaContacto;
        this.resultadosDiagnosticos = resultadosDiagnosticos;
        this.registrosEntradas = registrosEntradas;
        this.registros = registros;
        this.registros = registros;
    }

    public void agregarResultadoDiagnostico(ResultadoDiagnostico resultado){
        resultadosDiagnosticos.add(resultado);
    }

    public void agregarRegistroEntrada(RegistroEntrada registro){
        registrosEntradas.add(registro);
    }

}
