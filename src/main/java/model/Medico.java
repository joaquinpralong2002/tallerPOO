package model;
import datasource.*;
import jakarta.persistence.*;
import lombok.*;
import model.Enum.ColorTriage;
import model.Enum.EstadoCivil;
import model.Enum.LugarAtencion;
import model.EnumeracionesVariablesTriage.*;
import model.Login.Usuario;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase que representa un Funcionario de tipo Médico, que implementa los métodos de la interfaz CapacitadoTriage.
 */
@NoArgsConstructor
@Getter
@Setter

@Entity
public class Medico extends Funcionario implements CapacitadoTriage{
    @Column(unique = true)
    private String numMatricula;

    @OneToMany(mappedBy = "medico", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Especialidad> especializaciones;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "numeroBox")
    private BoxAtencion boxAtencion;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
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
     * Agrega una especialización al médico.
     * @param especialidad La especialización a agregar.
     */
    public void agregarEspecializacion(Especialidad especialidad){
        especializaciones.add(especialidad);
    }

    /**
     * Elimina una especialización del médico.
     * @param especialidad La especialización a eliminar.
     */
    public void eliminarEspecializacion(Especialidad especialidad){
        especializaciones.remove(especialidad);
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
        triage.setMedico(this);
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

    /**
     * Realiza la atención médica a un paciente en un box de atención, registra la atención y los diagnósticos correspondientes.
     *
     * @param paciente             El paciente que está siendo atendido.
     * @param box                  El box de atención donde se realiza la atención médica.
     * @param descripcionDiagnostico La descripción del diagnóstico médico.
     */
    public void atenderPaciente(Paciente paciente, BoxAtencion box, String descripcionDiagnostico){
        RegistroDAO registroDAO = new RegistroDAO();
        ResultadoDiagnosticoDAO resultadoDiagnosticoDAO = new ResultadoDiagnosticoDAO();
        PacienteDAO pacienteDAO = new PacienteDAO();
        BoxAtencionDAO boxAtencionDAO = new BoxAtencionDAO();
        box.setMedico(this);
        boxAtencionDAO.actualizar(box);
        Registro registro = new Registro(box.getLugarAtencion(), paciente, this);

        registroDAO.agregar(registro);
        paciente.agregarRegistros(registro);

        ResultadoDiagnostico resultadoDiagnostico = new ResultadoDiagnostico(descripcionDiagnostico, paciente);
        resultadoDiagnosticoDAO.agregar(resultadoDiagnostico);
        paciente.agregarResultadoDiagnostico(resultadoDiagnostico);
        registro.setResultadoDiagnostico(resultadoDiagnostico);
        registro.setFechaRegistro(LocalDate.now());
        registroDAO.actualizar(registro);

        RegistroEntradaDAO registroEntradaDAO = new RegistroEntradaDAO();
        registroEntradaDAO.actualizar(paciente.getRegistrosEntradas().get(paciente.getRegistrosEntradas().size() - 1));

        pacienteDAO.actualizar(paciente);
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
    public boolean asignarBox(RegistroEntrada registroEntrada, LugarAtencion lugarAtencion){
        RegistroEntradaDAO registroEntradaDAO = new RegistroEntradaDAO();
        //Se crea el DAO de box de atención.
        BoxAtencionDAO boxAtencionDAO = new BoxAtencionDAO();
        //Se obtiene el color de triage asociado al registro de entrada, y en base a este se elige a qué lugar de atención
        //corresponde enviar al paciente.
        ColorTriage colorTriage = registroEntrada.getTriage().getColorTriageRecomendado();
        BoxAtencion boxAsignado = boxAtencionDAO.obtenerDisponible(lugarAtencion);

        //Si se encontró un box para el paciente, se crea una asignación asociada al registro de entrada y al box.
        if(boxAsignado != null){
            registroEntrada.setAtendido(true);
            Asignacion asignacion = new Asignacion(registroEntrada, boxAsignado);
            System.out.println(asignacion);
            boxAsignado.setDisponible(false);
            boxAtencionDAO.actualizar(boxAsignado);
            AsignacionDAO asignacionDAO = new AsignacionDAO();
            asignacionDAO.agregar(asignacion);
            registroEntrada.setAsignacion(asignacion);
            registroEntradaDAO.actualizar(registroEntrada);
            PacienteDAO pacienteDAO = new PacienteDAO();
            pacienteDAO.actualizar(registroEntrada.getPaciente());
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
