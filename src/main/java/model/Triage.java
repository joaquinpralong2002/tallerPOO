package model;

import jakarta.persistence.*;
import model.EnumeracionesVariablesTriage.*;
import model.Enum.ColorTriage;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

@Entity
public class Triage{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTriage;

    @Enumerated(EnumType.STRING)
    private Respiracion respiracion;

    @Enumerated(EnumType.STRING)
    private Pulso pulso;
    private int vaLorPulso;

    @Enumerated(EnumType.STRING)
    private EstadoMental estadoMental;

    @Enumerated(EnumType.STRING)
    private Conciencia conciencia;

    @Enumerated(EnumType.STRING)
    private DolorPecho dolorPecho;

    @Enumerated(EnumType.STRING)
    private LecionesGraves lesionGrave;

    @Enumerated(EnumType.STRING)
    private Edad edad;
    private int valorEdad;

    @Enumerated(EnumType.STRING)
    private Fiebre fiebre;
    private float valorFiebre;

    @Enumerated(EnumType.STRING)
    private Vomitos vomito;

    @Enumerated(EnumType.STRING)
    private DolorAbdominal dolorAbdominal;

    @Enumerated(EnumType.STRING)
    private SignoShock signoShock;

    @Enumerated(EnumType.STRING)
    private LesionLeve lesionesLeves;

    @Enumerated(EnumType.STRING)
    private Sangrado sangrado;

    @Enumerated(EnumType.STRING)
    private ColorTriage colorTriageRecomendado;

    @Enumerated(EnumType.STRING)
    private ColorTriage colorTriageFinal;
    private String motivoCambioTriage;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
    @JoinColumn(name = "idRegistroEntrada", nullable = true)
    private RegistroEntrada registroEntrada;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idMedico")
    private Medico medico;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idEnfermero")
    private Enfermero enfermero;


    public Triage(Respiracion respiracion, Pulso pulso, int vaLorPulso,
                  EstadoMental estadoMental, Conciencia conciencia, DolorPecho dolorPecho,
                  LecionesGraves lesionGrave, Edad edad, int valorEdad, Fiebre fiebre,
                  float valorFiebre, Vomitos vomito, DolorAbdominal dolorAbdominal, SignoShock signoShock,
                  LesionLeve lesionesLeves, Sangrado sangrado) {
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
    }

    public void calcularColorTriageRecomendado(){
        int suma = respiracion.getValor() + pulso.getValor() + estadoMental.getValor() + conciencia.getValor() + dolorPecho.getValor() +
                lesionGrave.getValor() + edad.getValor() + fiebre.getValor() + vomito.getValor() + dolorAbdominal.getValor() +
                signoShock.getValor() + lesionesLeves.getValor() + sangrado.getValor();
        if(suma >= 15){
            this.colorTriageRecomendado = ColorTriage.Rojo;
        } else if (suma >= 10 && suma <= 14) {
            this.colorTriageRecomendado = ColorTriage.Naranja;
        } else if (suma >= 5 && suma <= 9) {
            this.colorTriageRecomendado = ColorTriage.Amarrillo;
        } else if (suma > 0 && suma <= 4) {
            this.colorTriageRecomendado = ColorTriage.Verde;
        } else {
            this.colorTriageRecomendado = ColorTriage.Azul;
        }
    }


    public boolean modificarColorTriageFinal(ColorTriage colorFinal, String motivo){
        if(colorFinal.getValor() - colorTriageRecomendado.getValor() <= 2){
            this.colorTriageFinal = colorFinal;
            this.motivoCambioTriage = motivo;
            return true;
        } else {
            System.out.println("No se puede asignar un color de triage que tenga dos niveles de diferencia con el recomendado por el sistema.");
            return false;
        }
    }

}
