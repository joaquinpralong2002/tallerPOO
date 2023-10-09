package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import model.Enum.EstadoCivil;
import model.Login.Usuario;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
public class FuncionarioAdministrativo extends Funcionario{


    @OneToMany(mappedBy = "funcionarioAdministrativo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RegistroEntrada> registrosEntradas = new ArrayList<>();



    public FuncionarioAdministrativo(String nombreApellido, LocalDate fechaNacimiento, String domicilio, int DNI, int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo, Usuario usuario, Sector sector) {
        super(nombreApellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo, usuario, sector);

    }

    public void RealizarRegistroEntrada(Paciente p,String descripcion){
        RegistroEntrada r = new RegistroEntrada(descripcion,p,this);
        this.registrosEntradas.add(r);
        p.agregarRegistroEntrada(r);
    }

    public List<Paciente> pacientesMasConsultas(LocalDate fecha1, LocalDate fecha2){
        List<Paciente> pacientes = new ArrayList<Paciente>();
        while (registrosEntradas.iterator().hasNext()){
            RegistroEntrada r = registrosEntradas.iterator().next();
            if(r.getFecha().equals(fecha1)
                    || r.getFecha().isBefore(fecha2)
                    || r.getFecha().isAfter(fecha1)
                    || r.getFecha().equals(fecha2)){
            }
        }
        return null;
    }



}
