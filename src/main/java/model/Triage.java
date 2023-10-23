package model;

import datasource.TriageDAO;
import jakarta.persistence.*;
import model.EnumeracionesVariablesTriage.*;
import model.Enum.ColorTriage;

import lombok.*;

import java.util.Objects;

/**
 * Clase que se encarga de calcular el triage.
 * A esta clase pueden acceder los Medicos y Enfermeros, los cuales son encargados de asignar los colores
 * Dependiendo de los distintos síntomas del paciente, se le asigna un puntaje,
 * y en base a ese puntaje, se le asigna un color.
 *
 * Se relaciona con Registro de Entrada, Médico y Enfermero.
 */
@NoArgsConstructor
@Getter
@Setter
@ToString

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

    @Column(length = 255)
    private String motivoCambioTriage;

    @ToString.Exclude
    @OneToOne(mappedBy = "triage", cascade = CascadeType.ALL, orphanRemoval = true)
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
        this.colorTriageFinal = ColorTriage.Ninguno;
    }

    /**
     * Calcula el color de triage recomendado para el paciente.
     *
     * El color de triage se calcula sumando los valores de los siguientes indicadores:
     * - Respiración
     * - Pulso
     * - Estado mental
     * - Conciencia
     * - Dolor de pecho
     * - Lesión grave
     * - Edad
     * - Fiebre
     * - Vómito
     * - Dolor abdominal
     * - Signos de shock
     * - Lesiones leves
     * - Sangrado
     *
     * Los valores de los indicadores se obtienen de las propiedades del paciente.
     *
     * Los colores de triage son los siguientes:
     * 15 o más, Rojo: Requiere atención médica inmediata
     * 10 a 14, Naranja: Requiere atención médica urgente
     * 5 a 9, Amarillo: Requiere atención médica en un plazo más largo
     * 0 a 4 Verde: Condiciones no urgentes
     * 0 Azul: Condiciones que pueden ser tratadas en un entorno no hospitalario
     */
    public void calcularColorTriageRecomendado(){
        int suma = respiracion.getValor() + pulso.getValor() + estadoMental.getValor() + conciencia.getValor() + dolorPecho.getValor() +
                lesionGrave.getValor() + edad.getValor() + fiebre.getValor() + vomito.getValor() + dolorAbdominal.getValor() +
                signoShock.getValor() + lesionesLeves.getValor() + sangrado.getValor();
        if(suma >= 15){
            this.colorTriageRecomendado = ColorTriage.Rojo;
        } else if (suma >= 10 && suma <= 14) {
            this.colorTriageRecomendado = ColorTriage.Naranja;
        } else if (suma >= 5 && suma <= 9) {
            this.colorTriageRecomendado = ColorTriage.Amarillo;
        } else if (suma > 0 && suma <= 4) {
            this.colorTriageRecomendado = ColorTriage.Verde;
        } else {
            this.colorTriageRecomendado = ColorTriage.Azul;
        }
    }

    public static ColorTriage calcularColorTriageRecomendado(Respiracion respiracion, Pulso pulso, int vaLorPulso,
                                                             EstadoMental estadoMental, Conciencia conciencia, DolorPecho dolorPecho,
                                                             LecionesGraves lesionGrave, Edad edad, int valorEdad, Fiebre fiebre,
                                                             float valorFiebre, Vomitos vomito, DolorAbdominal dolorAbdominal, SignoShock signoShock,
                                                             LesionLeve lesionesLeves, Sangrado sangrado){


        int suma = respiracion.getValor() + pulso.getValor() + estadoMental.getValor() + conciencia.getValor() + dolorPecho.getValor() +
                lesionGrave.getValor() + edad.getValor() + fiebre.getValor() + vomito.getValor() + dolorAbdominal.getValor() +
                signoShock.getValor() + lesionesLeves.getValor() + sangrado.getValor();
        if(suma >= 15){
            return ColorTriage.Rojo;
        } else if (suma >= 10 && suma <= 14) {
            return ColorTriage.Naranja;
        } else if (suma >= 5 && suma <= 9) {
            return ColorTriage.Amarillo;
        } else if (suma > 0 && suma <= 4) {
            return ColorTriage.Verde;
        } else {
            return ColorTriage.Azul;
        }
    }

    public static boolean controlarTriage(ColorTriage colorAntes, ColorTriage colorDespues){
        return Math.abs(colorDespues.getValor() - colorAntes.getValor()) <= 2;
    }


    /**
     * Modifica el color de triage final del paciente.
     * El color de triage final es el que se asigna al paciente después de que un médico o enfermero lo haya revisado.
     * El método solo permite modificar el color de triage final si la diferencia entre el color final
     * y el color recomendado es de dos niveles o menos.
     *
     * @param colorFinal El color de triage final que se desea asignar al paciente.
     * @param motivo El motivo del cambio de triage.
     *
     * @return `true` si el color de triage final se pudo modificar, `false` de lo contrario.
     */

    public void modificarColorTriageFinal(ColorTriage colorFinal, String motivo){
        this.colorTriageFinal = colorFinal;
        this.motivoCambioTriage = motivo;
        TriageDAO triageDAO = new TriageDAO();
        triageDAO.actualizar(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triage triage = (Triage) o;
        return Objects.equals(idTriage, triage.idTriage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTriage);
    }
}
