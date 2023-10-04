package model;

import model.EnumeracionesVariablesTriage.*;
import model.Enum.ColorTriage;

import lombok.*;

import java.awt.*;

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

    public ColorTriage getColorTriageRecomendado() {
        int suma = respiracion.getValor() + pulso.getValor() + estadoMental.getValor() + conciencia.getValor() + dolorPecho.getValor() +
                lesionGrave.getValor() + edad.getValor() + fiebre.getValor() + vomito.getValor() + dolorAbdominal.getValor() +
                signoShock.getValor() + lesionesLeves.getValor() + sangrado.getValor();
        if(suma >= 15){
            this.colorTriageRecomendado = ColorTriage.Rojo;
        } else if (suma >= 10 && suma <=14) {
            this.colorTriageRecomendado = ColorTriage.Naranja;
        } else if (suma >= 5 && suma <=9) {
            this.colorTriageRecomendado = ColorTriage.Amarrillo;
        } else if (suma > 0 && suma <=4) {
            this.colorTriageRecomendado = ColorTriage.Verde;
        } else {
            this.colorTriageRecomendado = ColorTriage.Azul;
        }
        return colorTriageRecomendado;
    }
}
