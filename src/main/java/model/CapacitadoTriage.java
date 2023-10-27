package model;

import model.Enum.ColorTriage;
import model.Enum.LugarAtencion;
import model.EnumeracionesVariablesTriage.*;

/**
 * Interfaz que será implementada por aquellos funcionarios (médico y enfermero) que tienen la capacidad de realizar triages,
 * y de cambiar el color de los mismos.
 */
public interface CapacitadoTriage {


    /**
     * Realiza un proceso de triaje para determinar el color de triaje recomendado para un paciente
     * en función de múltiples signos y síntomas vitales.
     *
     * @param respiracion     El estado de la respiración del paciente.
     * @param pulso           El pulso del paciente.
     * @param valorPulso      El valor numérico del pulso.
     * @param estadoMental    El estado mental del paciente.
     * @param conciencia      El nivel de conciencia del paciente.
     * @param dolorPecho      La presencia de dolor en el pecho del paciente.
     * @param lecionesGraves  La presencia de lesiones graves en el paciente.
     * @param edad            La edad del paciente.
     * @param valorEdad       El valor numérico de la edad.
     * @param fiebre          La presencia de fiebre en el paciente.
     * @param valorFiebre     El valor numérico de la fiebre.
     * @param vomitos         La presencia de vómitos en el paciente.
     * @param dolorAbdominal  El dolor abdominal del paciente.
     * @param signoShock      La presencia de signos de shock en el paciente.
     * @param lesionLeve      La presencia de lesiones leves en el paciente.
     * @param sangrado        La presencia de sangrado en el paciente.
     *
     * @return El objeto Triage que contiene el resultado del triaje, incluyendo el color de triaje recomendado.
     */
    public Triage realizarTriage(Respiracion respiracion, Pulso pulso, int valorPulso, EstadoMental estadoMental,
                                 Conciencia conciencia, DolorPecho dolorPecho, LecionesGraves lecionesGraves, Edad edad,
                                 int valorEdad, Fiebre fiebre, float valorFiebre, Vomitos vomitos, DolorAbdominal dolorAbdominal,
                                 SignoShock signoShock, LesionLeve lesionLeve, Sangrado sangrado);

    /**
     * Cambia el color de triaje final para un registro de triaje específico.
     *
     * @param triage      El registro de triaje al que se le cambiará el color de triaje final.
     * @param colorTriage El nuevo color de triaje final que se asignará al registro de triaje.
     * @param motivo      El motivo o justificación del cambio de color de triaje.
     */
    public void cambiarColorTriage(Triage triage, ColorTriage colorTriage, String motivo);


    /**
     * Confirma un triaje y actualiza el registro de entrada con el color de triaje final y otros detalles.
     *
     * @param registroEntrada El registro de entrada asociado al triaje.
     * @param triage          El triaje que se va a confirmar.
     * @param colorFinal      El color de triaje final seleccionado para el triaje.
     * @return `true` si la confirmación del triaje se realizó con éxito, de lo contrario, `false`.
     */
    public boolean confirmarTriage(RegistroEntrada registroEntrada, Triage triage, ColorTriage colorFinal);


    /**
     * Asigna un box de atención a un registro de entrada, considerando el color de triage y el lugar de atención del paciente.
     *
     * @param registroEntrada El registro de entrada del paciente que se va a asignar a un box de atención.
     * @param lugarAtencion   El lugar de atención al que corresponde enviar al paciente.
     * @return True si se pudo asignar un box al paciente, False si no hay box disponibles.
     */
    public boolean asignarBox(RegistroEntrada registroEntrada, LugarAtencion lugarAtencion);
}
