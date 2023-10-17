package model;

import model.Enum.ColorTriage;
import model.EnumeracionesVariablesTriage.*;

/**
 * Interfaz que será implementada por aquellos funcionarios (médico y enfermero) que tienen la capacidad de realizar triages,
 * y de cambiar el color de los mismos.
 */
public interface CapacitadoTriage {


    /** Realiza el triage de un paciente utilizando los datos proporcionados.
     @param respiracion El tipo de respiración del paciente.
     @param pulso El pulso del paciente.
     @param valorPulso El valor del pulso del paciente.
     @param estadoMental El estado mental del paciente.
     @param conciencia El nivel de conciencia del paciente.
     @param dolorPecho La presencia de dolor en el pecho del paciente.
     @param lecionesGraves La presencia de lesiones graves en el paciente.
     @param edad La edad del paciente.
     @param valorEdad El valor de la edad del paciente.
     @param fiebre La presencia de fiebre en el paciente.
     @param valorFiebre El valor de la fiebre del paciente.
     @param vomitos La presencia de vómitos en el paciente.
     @param dolorAbdominal La presencia de dolor abdominal en el paciente.
     @param signoShock La presencia de signos de shock en el paciente.
     @param lesionLeve La presencia de lesiones leves en el paciente.
     @param sangrado La presencia de sangrado en el paciente.
     @return El triage del paciente.
     */
    public Triage realizarTriage(Respiracion respiracion, Pulso pulso, int valorPulso, EstadoMental estadoMental,
                                 Conciencia conciencia, DolorPecho dolorPecho, LecionesGraves lecionesGraves, Edad edad,
                                 int valorEdad, Fiebre fiebre, float valorFiebre, Vomitos vomitos, DolorAbdominal dolorAbdominal,
                                 SignoShock signoShock, LesionLeve lesionLeve, Sangrado sangrado);

    /**Cambia el color de triage de un paciente.
     @param triage El triage del paciente.
     @param colorTriage El nuevo color de triage.
     @param motivo El motivo del cambio de color de triage.
     */
    public void cambiarColorTriage(Triage triage, ColorTriage colorTriage, String motivo);


    /**
     * Confirma el triage de un paciente.
     * @param registroEntrada El registro de entrada del paciente.
     * @param triage El triage del paciente.
     * @return True si el triage se confirmó correctamente, false en caso contrario.
     */
    public boolean confirmarTriage(RegistroEntrada registroEntrada, Triage triage, ColorTriage colorFinal);


    /**
     * Asigna un box de atención a un paciente.
     * @param registroEntrada El registro de entrada del paciente.
     * @return True si se pudo asignar un box de atención, false en caso contrario.
     */
    public boolean asignarBox(RegistroEntrada registroEntrada);
}
