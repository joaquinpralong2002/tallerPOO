package model;
import jakarta.persistence.*;
import lombok.*;
import model.Enum.EstadoCivil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

@Entity
public class Paciente extends Persona{
    private String personaContacto;

    @ToString.Exclude
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResultadoDiagnostico> resultadosDiagnosticos = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroEntrada> registrosEntradas = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Registro> registros = new LinkedHashSet<>();

    //sugerir cambiar tipo de persona de contacto a int q sea un numero de telefono
    public Paciente(String nombre, String apellido, LocalDate fechaNacimiento, String domicilio, int DNI,
                    int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo,
                    String personaContacto) {
        super(nombre, apellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo);
        this.personaContacto = personaContacto;
        this.resultadosDiagnosticos = new ArrayList<>();
        this.registrosEntradas = new ArrayList<>();
    }

    public Paciente(String nombre, String apellido, LocalDate fechaNacimiento, String domicilio, int DNI,
                    int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo,
                    String personaContacto, List<ResultadoDiagnostico> resultadosDiagnosticos) {
        super(nombre, apellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo);
        this.personaContacto = personaContacto;
        this.resultadosDiagnosticos = resultadosDiagnosticos;
        this.registrosEntradas = new ArrayList<>();
    }

    public Paciente(String nombre, String apellido, LocalDate fechaNacimiento, String domicilio, int DNI,
                    int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo,
                    String personaContacto, List<ResultadoDiagnostico> resultadosDiagnosticos, List<RegistroEntrada> registrosEntradas) {
        super(nombre, apellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo);
        this.personaContacto = personaContacto;
        this.resultadosDiagnosticos = resultadosDiagnosticos;
        this.registrosEntradas = registrosEntradas;
    }

    public Paciente(String nombre, String apellido, LocalDate fechaNacimiento, String domicilio, int DNI,
                    int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo,
                    String personaContacto, List<ResultadoDiagnostico> resultadosDiagnosticos,
                    List<RegistroEntrada> registrosEntradas, List<Registro> registros) {
        super(nombre, apellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo);
        this.personaContacto = personaContacto;
        this.resultadosDiagnosticos = resultadosDiagnosticos;
        this.registrosEntradas = registrosEntradas;
    }

    public void agregarResultadoDiagnostico(ResultadoDiagnostico resultado){
        resultadosDiagnosticos.add(resultado);
    }

    public void agregarRegistroEntrada(RegistroEntrada registro){
        registrosEntradas.add(registro);
    }

    public void agregarRegistros(Registro registro){registros.add(registro);}

}
