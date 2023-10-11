package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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


    @OneToMany(mappedBy = "funcionariosAdministrativo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroEntrada> registrosEntradas = new ArrayList<>();

    public FuncionarioAdministrativo(String nombreApellido, LocalDate fechaNacimiento, String domicilio, int DNI, int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo, Usuario usuario, Sector sector) {
        super(nombreApellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo, usuario, sector);
    }

    public void RealizarRegistroEntrada(Paciente p,String descripcion){
        RegistroEntrada r = new RegistroEntrada(descripcion,p,this);
        this.registrosEntradas.add(r);
        asignacionBox(r);
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

    private void asignacionBox(RegistroEntrada registroEntrada){
        Asignacion asignacion = null;
        while(BoxAtencion.boxesAtencion.iterator().hasNext() && asignacion == null){
            BoxAtencion box = BoxAtencion.boxesAtencion.iterator().next();
            if(box.isDisponible()) {
                asignacion = new Asignacion(registroEntrada, box);
            }
        }
    }

}
