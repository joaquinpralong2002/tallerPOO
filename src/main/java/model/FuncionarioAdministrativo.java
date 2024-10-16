package model;

import datasource.RegistroEntradaDAO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import model.Enum.EstadoCivil;
import model.Login.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un Funcionario Administrativo.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)

@Entity
public class FuncionarioAdministrativo extends Funcionario{


    @OneToMany(mappedBy = "funcionariosAdministrativo", orphanRemoval = true,fetch = FetchType.EAGER)
    @ToString.Exclude
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
}
