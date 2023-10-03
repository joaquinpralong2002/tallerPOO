package model;

import lombok.*;
import model.Enum.LugarAtencion;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Registro {
    private Long idRegistro;
    private LugarAtencion lugarAtencion;
    private Paciente paciente;
    private Medico medico;

    public Registro(LugarAtencion lugarAtencion, Paciente paciente, Medico medico){
        this.lugarAtencion = lugarAtencion;
        this.paciente = paciente;
        this.medico = medico;
    }



}
