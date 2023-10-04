package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.*;
import model.Enum.EstadoCivil;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class FuncionarioAdministrativo extends Funcionario{
    private Long idFuncionarioAdministrativo;
    private List<RegistroEntrada> registrosEntradas;


    public FuncionarioAdministrativo(String nombreApellido, LocalDate fechaNacimiento, String domicilio, int DNI, int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo, Usuario usuario, Sector sector) {
        super(nombreApellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo, usuario, sector);

    }

    public void RealizarRegistroEntrada(Paciente p, LocalDate fecha, LocalTime hora,String descripcion){
        RegistroEntrada r = new RegistroEntrada(fecha,hora,descripcion,p,this);
        this.registrosEntradas.add(r);
        p.agregarRegistroEntrada(r);
    }
    //public List<Paciente> pacientesMasConsultas(LocalDate fecha1, LocalDate fecha2){}


}
