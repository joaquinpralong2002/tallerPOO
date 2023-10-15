package model;

import jakarta.persistence.*;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import model.Enum.LugarAtencion;

import java.util.Objects;

/**
 * Clase que queda como Registro luego de que el paciente es atendido por el Médico, donde conserva el lugar de atención.
 *
 * Se relaciona con Paciente y Médico
 */

@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
public class Registro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRegistro;

    @Enumerated(EnumType.STRING)
    private LugarAtencion lugarAtencion;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "idMedico", nullable = false)
    private Medico medico;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "idPaciente", nullable = false)
    private Paciente paciente;

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Registro(LugarAtencion lugarAtencion, Paciente paciente, Medico medico){
        this.lugarAtencion = lugarAtencion;
        this.paciente = paciente;
        this.medico = medico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Registro registro = (Registro) o;
        return Objects.equals(idRegistro, registro.idRegistro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRegistro);
    }
}
