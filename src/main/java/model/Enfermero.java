package model;

import datasource.AsignacionDAO;
import datasource.BoxAtencionDAO;
import jakarta.persistence.*;
import lombok.*;
import model.Enum.ColorTriage;
import model.Enum.EstadoCivil;
import model.Login.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
public class Enfermero extends Funcionario implements CapacitadoTriage{
    @ToString.Exclude
    @OneToMany(mappedBy = "enfermero", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Triage> triagesRealizados = new ArrayList<>();

    //constructor
    public Enfermero(String nombre, String apellido, LocalDate fechaNacimiento, String domicilio,
                     int DNI, int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil,
                     String correo, Usuario usuario, Sector sector) {
        super(nombre, apellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo, usuario,sector);
    }


    /**
     * @param r de tipo RegistroEntrada
     * @return
     */
    @Override
    public Triage realizarTriage(Respiracion respiracion, Pulso pulso, int valorPulso, EstadoMental estadoMental,
                                      Conciencia conciencia, DolorPecho dolorPecho, LecionesGraves lecionesGraves, Edad edad,
                                      int valorEdad, Fiebre fiebre, int valorFiebre, Vomitos vomitos, DolorAbdominal dolorAbdominal,
                                      SignoShock signoShock, LesionLeve lesionLeve, Sangrado sangrado) {
        Triage triage = new Triage(respiracion, pulso, valorPulso, estadoMental, conciencia, dolorPecho, lecionesGraves, edad,
                valorEdad, fiebre, valorFiebre, vomitos, dolorAbdominal, signoShock, lesionLeve, sangrado);
        triage.calcularColorTriageRecomendado();
        return triage;
    }

    /**
     * @param t de tipo Triage, sirve para saber el triage que se le va a cambiar el color
     * @param color de tipo ColorTriage, el nuevo color que tendra el triage
     * @param motivo De tipo String, sirve para almacenar el motivo por el cual el color del triage fue modificado
     */
    @Override
    public void cambiarColorTriage(Triage t, ColorTriage color, String motivo) {
        t.modificarColorTriageFinal(color, motivo);
    }

    @Override
    public boolean confirmarTriage(RegistroEntrada r, Triage triage){
        triage.setEnfermero(this);
        triage.setRegistroEntrada(r);
        r.setTriage(triage);
        this.triagesRealizados.add(triage);

        return true;
    }

    @Override
    public boolean asignarBox(RegistroEntrada registroEntrada){
        //Se crea el DAO de box de atención.
        BoxAtencionDAO boxAtencionDAO = new BoxAtencionDAO();
        //Se obtiene el color de triage asociado al registro de entrada, y en base a este se elige a qué lugar de atención
        //corresponde enviar al paciente.
        ColorTriage colorTriage = registroEntrada.getTriage().getColorTriageRecomendado();
        BoxAtencion boxAsignado;
        if(colorTriage == ColorTriage.Rojo) boxAsignado = boxAtencionDAO.obtenerDisponible(LugarAtencion.Internaciones);
        else if (colorTriage == ColorTriage.Naranja) boxAsignado = boxAtencionDAO.obtenerDisponible(LugarAtencion.Emergencia);
        else boxAsignado = boxAtencionDAO.obtenerDisponible(LugarAtencion.Consultorio);

        //Si se encontró un box para el paciente, se crea una asignación asociada al registro de entrada y al box.
        if(boxAsignado != null){
            Asignacion asignacion = new Asignacion(registroEntrada, boxAsignado);
            boxAsignado.setDisponible(false);
            boxAtencionDAO.actualizar(boxAsignado);
            AsignacionDAO asignacionDAO = new AsignacionDAO();
            asignacionDAO.agregar(asignacion);
            return true;
        }
        else {
            System.out.println("No hay box de atención disponibles.");
            return false;
        }
    }

}
