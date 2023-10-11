package model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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



    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Paciente paciente;

    @OneToOne(mappedBy = "registroEntrada", cascade = CascadeType.ALL)
    private Triage triage;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private FuncionarioAdministrativo funcionariosAdministrativo;

    @OneToOne(mappedBy = "registroEntrada", optional = false, orphanRemoval = true)
    private Asignacion asignacion;

    public void setFuncionariosAdministrativo(FuncionarioAdministrativo funcionariosAdministrativo) {
        this.funcionariosAdministrativo = funcionariosAdministrativo;
    }


    public void setAsignacion(Asignacion asignacion) {
        this.asignacion = asignacion;
    }

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

    //constructor con atributo Triage agregado
    public RegistroEntrada(String descripcion,Asignacion asignacion,
                           Paciente paciente, Triage triage) {
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
        this.descripcion = descripcion;
        this.asignacion = asignacion;
        this.paciente = paciente;
        this.triage = triage;
    }

    //constructor con atributo FuncionarioAdministrativo agregado
    public RegistroEntrada(String descripcion,Asignacion asignacion,
                           Paciente paciente, Triage triage,
                           FuncionarioAdministrativo funcionarioAdministrativo) {
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
        this.descripcion = descripcion;
        this.asignacion = asignacion;
        this.paciente = paciente;
        this.triage = triage;
        this.funcionariosAdministrativo = funcionarioAdministrativo;
    }

    //constructor de Inicio (fecha, hora, descripcion, paciente, funcionario)
    public  RegistroEntrada(String descripcion, Paciente paciente, FuncionarioAdministrativo funcionarioAdministrativo){
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
        this.descripcion = descripcion;
        this.paciente = paciente;
        this.funcionariosAdministrativo = funcionarioAdministrativo;
    }

    public void setTriage(Triage triage){
        this.triage = triage;
    }
}
