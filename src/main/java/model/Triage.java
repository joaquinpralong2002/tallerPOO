package model;

import model.EnumeracionesVariablesTriage.*;
import model.Enum.ColorTriage;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class Triage{
    private Long idTriage;
    private Respiracion respiracion;
    private Pulso pulso;
    private int vaLorPulso;
    private EstadoMental estadoMental;
    private Conciencia conciencia;
    private DolorPecho dolorPecho;
    private LecionesGraves lesionGrave;
    private Edad edad;
    private int valorEdad;
    private Fiebre fiebre;
    private float valorFiebre;
    private Vomitos vomito;
    private DolorAbdominal dolorAbdominal;
    private SignoShock signoShock;
    private LesionLeve lesionesLeves;
    private Sangrado sangrado;
    private ColorTriage colorTriageRecomendado;
    private ColorTriage colorTriageFinal;
    private String motivoCambioTriage;
    private RegistroEntrada registroEntrada;
    private Medico triagiadorMec;
    private Enfermero triagiadorEnf;

    public Triage(Respiracion respiracion, Pulso pulso, int vaLorPulso,
                  EstadoMental estadoMental, Conciencia conciencia, DolorPecho dolorPecho,
                  LecionesGraves lesionGrave, Edad edad, int valorEdad, Fiebre fiebre,
                  float valorFiebre, Vomitos vomito, DolorAbdominal dolorAbdominal, SignoShock signoShock,
                  LesionLeve lesionesLeves, Sangrado sangrado, ColorTriage colorTriageRecomendado,
                  ColorTriage colorTriageFinal, String motivoCambioTriage, RegistroEntrada registroEntrada) {
        this.respiracion = respiracion;
        this.pulso = pulso;
        this.vaLorPulso = vaLorPulso;
        this.estadoMental = estadoMental;
        this.conciencia = conciencia;
        this.dolorPecho = dolorPecho;
        this.lesionGrave = lesionGrave;
        this.edad = edad;
        this.valorEdad = valorEdad;
        this.fiebre = fiebre;
        this.valorFiebre = valorFiebre;
        this.vomito = vomito;
        this.dolorAbdominal = dolorAbdominal;
        this.signoShock = signoShock;
        this.lesionesLeves = lesionesLeves;
        this.sangrado = sangrado;
        this.colorTriageRecomendado = colorTriageRecomendado;
        this.colorTriageFinal = colorTriageFinal;
        this.motivoCambioTriage = motivoCambioTriage;
        this.registroEntrada = registroEntrada;
    }

}
