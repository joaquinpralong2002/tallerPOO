package model;

import model.Enum.ColorTriage;
import model.EnumeracionesVariablesTriage.*;

public interface CapacitadoTriage {

    /**
     * Metodo que realiza un Triage en base al paciente otorgado por paramentro
     * @param
     * @return Devuelve un triage de tipo ColorTriage
     */
    public Triage realizarTriage(Respiracion respiracion, Pulso pulso, int valorPulso, EstadoMental estadoMental,
                                 Conciencia conciencia, DolorPecho dolorPecho, LecionesGraves lecionesGraves, Edad edad,
                                 int valorEdad, Fiebre fiebre, int valorFiebre, Vomitos vomitos, DolorAbdominal dolorAbdominal,
                                 SignoShock signoShock, LesionLeve lesionLeve, Sangrado sangrado);

    /**
     * Metodo que modifica el color de un triage ya realizado
     * @param t de tipo Triage, sirve para saber el triage que se le va a cambiar el color
     * @param motivo De tipo String, sirve para almacenar el motivo por el cual el color del triage fue modificado
     */
    public void cambiarColorTriage(Triage t, ColorTriage c, String motivo);


    public boolean confirmarTriage(RegistroEntrada re, Triage t);

    public boolean asignarBox(RegistroEntrada registroEntrada);
}
