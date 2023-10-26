package controllers.Enfermero.TriageEnfermero;

import lombok.Getter;
import lombok.Setter;
import model.Enfermero;
import model.Enum.ColorTriage;
import model.EnumeracionesVariablesTriage.*;
import model.Medico;
import model.RegistroEntrada;

@Getter
@Setter
public class DatosTriageEnfermero {
    private Respiracion respiracion;
    private int pulsoCardiaco;
    private Pulso pulso;
    private EstadoMental estadoMental;
    private Conciencia conciencia;
    private DolorPecho dolorPecho;
    private LecionesGraves lecionesGraves;
    private Edad edad;
    private int edadAños;
    private float temperatura;
    private Fiebre fiebre;
    private Vomitos vomitos;
    private DolorAbdominal dolorAbdominal;
    private SignoShock signoShock;
    private LesionLeve lesionLeve;
    private Sangrado sangrado;
    private final ColorTriage colorTriageAsignado;
    private ColorTriage colorTriageCambiado;
    private String motivoCambioTriage;
    private RegistroEntrada registroEntrada;
    private Enfermero enfermero;

    public DatosTriageEnfermero(Respiracion respiracion, int pulsoCardiaco, Pulso pulso, EstadoMental estadoMental, Conciencia conciencia, DolorPecho dolorPecho, LecionesGraves lecionesGraves, Edad edad, int edadAños, float temperatura, Fiebre fiebre, Vomitos vomitos, DolorAbdominal dolorAbdominal, SignoShock signoShock, LesionLeve lesionLeve, Sangrado sangrado, ColorTriage colorTriageAsignado, RegistroEntrada registroEntrada, Enfermero enfermero) {
        this.respiracion = respiracion;
        this.pulsoCardiaco = pulsoCardiaco;
        this.pulso = pulso;
        this.estadoMental = estadoMental;
        this.conciencia = conciencia;
        this.dolorPecho = dolorPecho;
        this.lecionesGraves = lecionesGraves;
        this.edad = edad;
        this.edadAños = edadAños;
        this.temperatura = temperatura;
        this.fiebre = fiebre;
        this.vomitos = vomitos;
        this.dolorAbdominal = dolorAbdominal;
        this.signoShock = signoShock;
        this.lesionLeve = lesionLeve;
        this.sangrado = sangrado;
        this.colorTriageAsignado = colorTriageAsignado;
        this.registroEntrada = registroEntrada;
        this.enfermero = enfermero;
    }
}
