package model;

import datasource.AsignacionDAO;
import datasource.BoxAtencionDAO;
import datasource.RegistroEntradaDAO;
import datasource.TriageDAO;
import jakarta.persistence.*;
import lombok.*;
import model.Enum.ColorTriage;
import model.Enum.EstadoCivil;
import model.Enum.LugarAtencion;
import model.EnumeracionesVariablesTriage.*;
import model.Login.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un Funcionario de tipo Enfermero, que implementa los métodos de la interfaz CapacitadoTriage.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)

@Entity
public class Enfermero extends Funcionario implements CapacitadoTriage{
    @ToString.Exclude
    @OneToMany(mappedBy = "enfermero", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Triage> triagesRealizados = new ArrayList<>();

    //constructor
    public Enfermero(String nombre, String apellido, LocalDate fechaNacimiento, String domicilio,
                     int DNI, int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil,
                     String correo, Usuario usuario, Sector sector) {
        super(nombre, apellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo, usuario,sector);
    }

    @Override
    public Triage realizarTriage(Respiracion respiracion, Pulso pulso, int valorPulso, EstadoMental estadoMental,
                                 Conciencia conciencia, DolorPecho dolorPecho, LecionesGraves lecionesGraves, Edad edad,
                                 int valorEdad, Fiebre fiebre, float valorFiebre, Vomitos vomitos, DolorAbdominal dolorAbdominal,
                                 SignoShock signoShock, LesionLeve lesionLeve, Sangrado sangrado) {

        Triage triage = new Triage(respiracion, pulso, valorPulso, estadoMental, conciencia, dolorPecho, lecionesGraves, edad,
                valorEdad, fiebre, valorFiebre, vomitos, dolorAbdominal, signoShock, lesionLeve, sangrado);
        triage.calcularColorTriageRecomendado();
        TriageDAO triageDAO = new TriageDAO();
        triageDAO.agregar(triage);
        this.triagesRealizados.add(triage);
        return triage;
    }

    @Override
    public void cambiarColorTriage(Triage triage, ColorTriage colorTriage, String motivo) {
        triage.modificarColorTriageFinal(colorTriage, motivo);
    }

    @Override
    public boolean confirmarTriage(RegistroEntrada registroEntrada, Triage triage, ColorTriage colorfinal){
        triage.setEnfermero(this);
        triage.setColorTriageFinal(colorfinal);
        triage.setRegistroEntrada(registroEntrada);
        registroEntrada.setTriage(triage);
        registroEntrada.setTriagiado(true);

        TriageDAO triageDAO = new TriageDAO();
        triageDAO.actualizar(triage);
        RegistroEntradaDAO registroEntradaDAO = new RegistroEntradaDAO();
        registroEntradaDAO.actualizar(registroEntrada);

        return true;
    }

    @Override
    public boolean asignarBox(RegistroEntrada registroEntrada, LugarAtencion lugarAtencion){
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
