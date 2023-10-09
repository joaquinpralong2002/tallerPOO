package model;

import jakarta.persistence.*;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import model.Enum.LugarAtencion;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idPaciente")
    private Paciente paciente;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idMedico")
    private Medico medico;

    public Registro(LugarAtencion lugarAtencion, Paciente paciente, Medico medico){
        this.lugarAtencion = lugarAtencion;
        this.paciente = paciente;
        this.medico = medico;
    }
}
