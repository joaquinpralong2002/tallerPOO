package model;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import model.Enum.LugarAtencion;

@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Registro {
    private Long idRegistro;
    private LugarAtencion lugarAtencion;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idPaciente")
    private Paciente paciente;
    private Medico medico;

    public Registro(LugarAtencion lugarAtencion, Paciente paciente, Medico medico){
        this.lugarAtencion = lugarAtencion;
        this.paciente = paciente;
        this.medico = medico;
    }
}
