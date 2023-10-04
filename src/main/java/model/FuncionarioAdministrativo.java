package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import model.Enum.EstadoCivil;
import model.Login.Usuario;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class FuncionarioAdministrativo extends Funcionario{
    private Long idFuncionarioAdministrativo;
    private List<RegistroEntrada> registrosEntradas = new ArrayList<>();


    public FuncionarioAdministrativo(String nombreApellido, LocalDate fechaNacimiento, String domicilio, int DNI, int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo, Usuario usuario, Sector sector) {
        super(nombreApellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo, usuario, sector);

    }

    public void RealizarRegistroEntrada(Paciente p,String descripcion){
        RegistroEntrada r = new RegistroEntrada(descripcion,p,this);
        this.registrosEntradas.add(r);
        p.agregarRegistroEntrada(r);
    }
    //public List<Paciente> pacientesMasConsultas(LocalDate fecha1, LocalDate fecha2){}


}
