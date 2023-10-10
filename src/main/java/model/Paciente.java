package model;
import jakarta.persistence.*;
import lombok.*;
import model.Enum.EstadoCivil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

@Entity
public class Paciente extends Persona{
    private String personaContacto;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ResultadoDiagnostico> resultadosDiagnosticos;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RegistroEntrada> registrosEntradas;

    @OneToMany(mappedBy = "paciente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Registro> registros;


    //sugerir cambiar tipo de persona de contacto a int q sea un numero de telefono
    public Paciente(String nombreApellido, LocalDate fechaNacimiento, String domicilio, int DNI,
                    int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo,
                    String personaContacto) {
        super(nombreApellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo);
        this.personaContacto = personaContacto;
        this.resultadosDiagnosticos = new ArrayList<>();
        this.registrosEntradas = new ArrayList<>();
        this.registros = new ArrayList<>();
    }

    public Paciente(String nombreApellido, LocalDate fechaNacimiento, String domicilio, int DNI,
                    int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo,
                    String personaContacto, List<ResultadoDiagnostico> resultadosDiagnosticos) {
        super(nombreApellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo);
        this.personaContacto = personaContacto;
        this.resultadosDiagnosticos = resultadosDiagnosticos;
        this.registrosEntradas = new ArrayList<>();
        this.registros = new ArrayList<>();
    }

    public Paciente(String nombreApellido, LocalDate fechaNacimiento, String domicilio, int DNI,
                    int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo,
                    String personaContacto, List<ResultadoDiagnostico> resultadosDiagnosticos, List<RegistroEntrada> registrosEntradas) {
        super(nombreApellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo);
        this.personaContacto = personaContacto;
        this.resultadosDiagnosticos = resultadosDiagnosticos;
        this.registrosEntradas = registrosEntradas;
        this.registros = new ArrayList<>();
    }

    public Paciente(String nombreApellido, LocalDate fechaNacimiento, String domicilio, int DNI,
                    int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo,
                    String personaContacto, List<ResultadoDiagnostico> resultadosDiagnosticos,
                    List<RegistroEntrada> registrosEntradas, List<Registro> registros) {
        super(nombreApellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo);
        this.personaContacto = personaContacto;
        this.resultadosDiagnosticos = resultadosDiagnosticos;
        this.registrosEntradas = registrosEntradas;
        this.registros = registros;
    }

    public void agregarResultadoDiagnostico(ResultadoDiagnostico resultado){
        resultadosDiagnosticos.add(resultado);
    }

    public void agregarRegistroEntrada(RegistroEntrada registro){
        registrosEntradas.add(registro);
    }

    public void agregarRegistros(Registro registro){registros.add(registro);}

}
