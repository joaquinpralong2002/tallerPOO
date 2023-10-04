package model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class RegistroEntrada {
    private Long idRegistroEntrada;
    private LocalDate fecha;
    private LocalTime hora;
    private String descripcion;

    private Asignacion asignacion;
    private Paciente paciente;
    private BoxAtencion boxAtencion;
    private Triage triage;
    private FuncionarioAdministrativo funcionarioAdministrativo;

    //constructor con atributos popios de la clase
    public RegistroEntrada(LocalDate fecha, LocalTime hora, String descripcion) {
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
    }

    //constructor con atributo Asignacion agregado
    public RegistroEntrada(LocalDate fecha, LocalTime hora, String descripcion, Asignacion asignacion) {
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.asignacion = asignacion;
    }

    //constructor con atributo Paciente agregado
    public RegistroEntrada(LocalDate fecha, LocalTime hora, String descripcion, Asignacion asignacion,
                           Paciente paciente) {
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.asignacion = asignacion;
        this.paciente = paciente;
    }

    //constructor con atributo BoxAtencion agregado
    public RegistroEntrada(LocalDate fecha, LocalTime hora, String descripcion,Asignacion asignacion,
                           Paciente paciente, BoxAtencion boxAtencion) {
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.asignacion = asignacion;
        this.paciente = paciente;
        this.boxAtencion = boxAtencion;
    }

    //constructor con atributo Triage agregado
    public RegistroEntrada(LocalDate fecha, LocalTime hora, String descripcion,Asignacion asignacion,
                           Paciente paciente, BoxAtencion boxAtencion, Triage triage) {
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.asignacion = asignacion;
        this.paciente = paciente;
        this.boxAtencion = boxAtencion;
        this.triage = triage;
    }

    //constructor con atributo FuncionarioAdministrativo agregado
    public RegistroEntrada(LocalDate fecha, LocalTime hora, String descripcion,Asignacion asignacion,
                           Paciente paciente, BoxAtencion boxAtencion, Triage triage,
                           FuncionarioAdministrativo funcionarioAdministrativo) {
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.asignacion = asignacion;
        this.paciente = paciente;
        this.boxAtencion = boxAtencion;
        this.triage = triage;
        this.funcionarioAdministrativo = funcionarioAdministrativo;
    }

    //constructor de Inicio (fecha, hora, descripcion, paciente, funcionario)
    public  RegistroEntrada(LocalDate fecha, LocalTime hora, String descripcion, Paciente paciente,FuncionarioAdministrativo funcionarioAdministrativo){
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.paciente = paciente;
        this.funcionarioAdministrativo = funcionarioAdministrativo;
    }


}
