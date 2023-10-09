package model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
public class RegistroEntrada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRegistroEntrada;
    private LocalDate fecha;
    private LocalTime hora;
    private String descripcion;

    @OneToOne(mappedBy = "registroEntrada", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Asignacion asignacion;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private BoxAtencion boxAtencion;

    @OneToOne(mappedBy = "registroEntrada", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Triage triage;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FuncionarioAdministrativo funcionarioAdministrativo;

    //constructor con atributos popios de la clase
    public RegistroEntrada(String descripcion) {
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
        this.descripcion = descripcion;
    }

    //constructor con atributo Asignacion agregado
    public RegistroEntrada(String descripcion, Asignacion asignacion) {
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
        this.descripcion = descripcion;
        this.asignacion = asignacion;
    }

    //constructor con atributo Paciente agregado
    public RegistroEntrada(String descripcion, Asignacion asignacion,
                           Paciente paciente) {
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
        this.descripcion = descripcion;
        this.asignacion = asignacion;
        this.paciente = paciente;
    }

    //constructor con atributo BoxAtencion agregado
    public RegistroEntrada(String descripcion,Asignacion asignacion,
                           Paciente paciente, BoxAtencion boxAtencion) {
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
        this.descripcion = descripcion;
        this.asignacion = asignacion;
        this.paciente = paciente;
        this.boxAtencion = boxAtencion;
    }

    //constructor con atributo Triage agregado
    public RegistroEntrada(String descripcion,Asignacion asignacion,
                           Paciente paciente, BoxAtencion boxAtencion, Triage triage) {
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
        this.descripcion = descripcion;
        this.asignacion = asignacion;
        this.paciente = paciente;
        this.boxAtencion = boxAtencion;
        this.triage = triage;
    }

    //constructor con atributo FuncionarioAdministrativo agregado
    public RegistroEntrada(String descripcion,Asignacion asignacion,
                           Paciente paciente, BoxAtencion boxAtencion, Triage triage,
                           FuncionarioAdministrativo funcionarioAdministrativo) {
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
        this.descripcion = descripcion;
        this.asignacion = asignacion;
        this.paciente = paciente;
        this.boxAtencion = boxAtencion;
        this.triage = triage;
        this.funcionarioAdministrativo = funcionarioAdministrativo;
    }

    //constructor de Inicio (fecha, hora, descripcion, paciente, funcionario)
    public  RegistroEntrada(String descripcion, Paciente paciente, FuncionarioAdministrativo funcionarioAdministrativo){
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
        this.descripcion = descripcion;
        this.paciente = paciente;
        this.funcionarioAdministrativo = funcionarioAdministrativo;
    }

    public void setTriage(Triage triage){
        this.triage = triage;
    }
}
