package model;
import datasource.AsignacionDAO;
import datasource.BoxAtencionDAO;
import jakarta.persistence.*;
import lombok.*;
import model.Enum.ColorTriage;
import model.Enum.EstadoCivil;
import model.Enum.LugarAtencion;
import model.EnumeracionesVariablesTriage.*;
import model.Login.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter

@Entity
public class Medico extends Funcionario implements CapacitadoTriage{
    private String numMatricula;

    @OneToMany(mappedBy = "medico", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Especialidad> especializaciones;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "numeroBox")
    private BoxAtencion boxAtencion;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Triage> triagesRealizados = new ArrayList<>();

    public Medico(String nombre, String apellido, LocalDate fechaNacimiento, String domicilio, int DNI,
                  int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo,
                  Usuario usuario, Sector sector, String numMatricula, List<Especialidad> especializaciones) {
        super(nombre, apellido, fechaNacimiento, domicilio,
              DNI, telefonoFijo, telefonoCelular, estadoCivil, correo, usuario, sector);
        this.numMatricula = numMatricula;
        this.especializaciones = especializaciones;
    }

    public Medico(String nombre, String apellido, LocalDate fechaNacimiento, String domicilio, int DNI,
                  int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo,
                  Usuario usuario, Sector sector, String numMatricula) {
        super(nombre, apellido, fechaNacimiento, domicilio,
                DNI, telefonoFijo, telefonoCelular, estadoCivil, correo, usuario, sector);
        this.numMatricula = numMatricula;
        this.especializaciones = new ArrayList<>();
    }


    /**
     * Metodo para agregar una nueva especializacion a la lista
     * @param e especializacion a ser agregada
     */
    public void agregarEspecializacion(Especialidad e){
        especializaciones.add(e);
    }

    /**
     * Metodo para eliminar una nueva especializacion a la lista
     * @param e especializacion a ser eliminada
     */
    public void eliminarEspecializacion(Especialidad e){
        especializaciones.remove(e);
    }


    /**
     * Método para realizar un triage, que crea una instancia de la clase con el mismo nombre,
     * calcula el color recomendado por el sistema, y devuelve el mismo.
     * @param
     * @return ColorTriage
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
     * @param triage de tipo Triage, sirve para saber el triage que se le va a cambiar el color
     * @param color de tipo ColorTriage, el nuevo color que tendra el triage
     * @param motivo De tipo String, sirve para almacenar el motivo por el cual el color del triage fue modificado
     */
    @Override
    public void cambiarColorTriage(Triage triage, ColorTriage color, String motivo) {
        triage.modificarColorTriageFinal(color, motivo);
    }

    @Override
    public boolean confirmarTriage(RegistroEntrada r, Triage triage){
        triage.setMedico(this);
        triage.setRegistroEntrada(r);
        r.setTriage(triage);
        this.triagesRealizados.add(triage);
        return true;
    }

    public void atenderPaciente(Paciente p, BoxAtencion box, RegistroEntrada reg){
    }

    public int cantidadPacientesAtendidos(LocalDate fecha1, LocalDate fecha2){
        return 0;
    }

    public int cantidadPacientesAtendidos(LocalDate fecha1, LocalDate fecha2, int edad1, int edad2){
        return 0;
    }

    public int cantidadPacientesAtendidos(int edad1, int edad2){
        return 0;
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

    @Override
    public String toString() {
        return "Medico{" +
                "numMatricula='" + numMatricula + '\'' +
                '}';
    }
}
