package model;

import model.Enum.ColorTriage;

public interface CapacitadoTriage {

    /**
     * Metodo que realiza un Triage en base al paciente otorgado por paramentro
     * @param e de tipo RegistroEntrada
     * @return Devuelve un triage de tipo ColorTriage
     */
    public ColorTriage realizarTriage(RegistroEntrada e);

    /**
     * Metodo que modifica el color de un triage ya realizado
     * @param t de tipo Triage, sirve para saber el triage que se le va a cambiar el color
     * @param motivo De tipo String, sirve para almacenar el motivo por el cual el color del triage fue modificado
     */
    public void cambiarColorTriage(Triage t, ColorTriage c, String motivo);
}
