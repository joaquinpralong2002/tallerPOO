package controllers.Triage;

import lombok.Getter;
import lombok.Setter;
import model.Enum.ColorTriage;
import model.EnumeracionesVariablesTriage.*;
import model.Medico;
import model.RegistroEntrada;

import java.util.List;

@Getter
@Setter
public class TriageSingleton {
    private static TriageSingleton instance;
    private List<String> usuarios;
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
    private ColorTriage colorTriageAsignado;
    private ColorTriage colorTriageCambiado;
    private String motivoCambioTriage;
    private RegistroEntrada registroEntrada;
    private Medico medico;

    private TriageSingleton() {
    }

    public static TriageSingleton getInstance() {
        if (instance == null) {
            instance = new TriageSingleton();
        }
        return instance;
    }


}

