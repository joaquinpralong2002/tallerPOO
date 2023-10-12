package model;
import jakarta.persistence.*;
import lombok.*;
import model.Enum.ColorTriage;
import model.Enum.EstadoCivil;
import model.EnumeracionesVariablesTriage.*;
import model.Login.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;
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

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoxAtencion> boxesAtencion = new ArrayList<>();

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Triage> triagesRealizados = new ArrayList<>();

    public Medico(String nombre, String apellido, LocalDate fechaNacimiento, String domicilio, int DNI,
                  int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo,
                  Usuario usuario, Sector sector, String numMatricula, List<Especialidad> especializaciones) {
        super(nombre, apellido, fechaNacimiento, domicilio,
              DNI, telefonoFijo, telefonoCelular, estadoCivil, correo, usuario, sector);
        this.numMatricula = numMatricula;
        this.especializaciones = especializaciones;
        this.boxesAtencion = new LinkedList<>();
    }

    public Medico(String nombre, String apellido, LocalDate fechaNacimiento, String domicilio, int DNI,
                  int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil, String correo,
                  Usuario usuario, Sector sector, String numMatricula) {
        super(nombre, apellido, fechaNacimiento, domicilio,
                DNI, telefonoFijo, telefonoCelular, estadoCivil, correo, usuario, sector);
        this.numMatricula = numMatricula;
        this.especializaciones = new ArrayList<>();
        this.boxesAtencion = new LinkedList<>();
    }

    /**
     * Metodo para agregar un Box de atencion de la lista
     * @param b Box de atencion a ser agregado
     */
    public void agregarBox(BoxAtencion b){
        this.boxesAtencion.add(b);
    }

    /**
     * Metodo para eliminar un Box de atencion de la lista
     * @param b Box de atencion a ser eliminado
     */
     public void eliminarBox(BoxAtencion b){
        this.boxesAtencion.remove(b);
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
     * @param r de tipo RegistroEntrada
     * @return ColorTriage
     */
    @Override
    public ColorTriage realizarTriage(RegistroEntrada r) {
        //asignarle el RegistroEntrada que se tomo
        //crear triage, pasarle todos los parametros del triage que vendran de la ventana, asignarle el paciente y el q realizo el triage
        //asignarle al paciente p el triage
        //asignarle al medico el triage

        Triage triage = new Triage(Respiracion.Normal, Pulso.Normal, 80, EstadoMental.Normal, Conciencia.Consciente, DolorPecho.NoPresnte, LecionesGraves.NoPresentes, Edad.NinioAnciano, 85, Fiebre.Moderada, (float)38.5, Vomitos.SinVomito, DolorAbdominal.NoPresente, SignoShock.NoPresente, LesionLeve.NoPresente, Sangrado.NoPresente);
        triage.calcularColorTriageRecomendado();
        triage.setMedico(this);
        triage.setRegistroEntrada(r);
        r.setTriage(triage);
        this.triagesRealizados.add(triage);
        return triage.getColorTriageRecomendado();
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

    public void atenderPaciente(Paciente p, BoxAtencion box, RegistroEntrada reg){
        Asignacion asig = new Asignacion(reg, box);
        box.agregarAsignacion(asig);
        box.setMedico(this);
        box.setDisponible(false);
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
    public String toString() {
        return "Medico{" +
                "numMatricula='" + numMatricula + '\'' +
                '}';
    }
}
