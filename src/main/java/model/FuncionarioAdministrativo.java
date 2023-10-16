package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import datasource.AsignacionDAO;
import datasource.BoxAtencionDAO;
import datasource.RegistroEntradaDAO;
import jakarta.persistence.*;
import lombok.*;
import model.Enum.EstadoCivil;
import model.Login.Usuario;

/**
 * Clase que representa un Funcionario Administrativo.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
public class FuncionarioAdministrativo extends Funcionario{


    @OneToMany(mappedBy = "funcionariosAdministrativo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroEntrada> registrosEntradas = new ArrayList<>();

    public FuncionarioAdministrativo(String nombre, String apellido, LocalDate fechaNacimiento, String domicilio, int DNI, int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo, Usuario usuario, Sector sector) {
        super(nombre, apellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo, usuario, sector);
    }

    /**
     * Realiza un registro de entrada para un paciente.
     * @param paciente El paciente al que se le va a realizar el registro de entrada.
     * @param descripcion La descripción del registro de entrada.
     */
    public void RealizarRegistroEntrada(Paciente paciente, String descripcion){
        RegistroEntrada r = new RegistroEntrada(descripcion,paciente,this);
        RegistroEntradaDAO registroEntradaDAO = new RegistroEntradaDAO();
        registroEntradaDAO.agregar(r);
        this.registrosEntradas.add(r);
        paciente.agregarRegistroEntrada(r);
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
