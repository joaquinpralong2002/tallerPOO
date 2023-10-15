package model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Clase que registra al paciente al momento de ingresar al hospital.
 * Luego de ser registrado, el médico y enfermero tienen acceso a esta clase para realizar el triage,
 * como también asignar el Box de Atención correspondiente
 *
 * Posee una relación con Asignación, Box de Atención, Triage y FuncionarioAdministrativo
 */
@Getter
@NoArgsConstructor
@ToString

@Entity
public class RegistroEntrada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRegistroEntrada;
    private LocalDate fecha;
    private LocalTime hora;
    private String descripcion;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private FuncionarioAdministrativo funcionariosAdministrativo;

    @OneToOne(mappedBy = "registroEntrada", optional = false, orphanRemoval = true)
    private Asignacion asignacion;

    @OneToOne(mappedBy = "registroEntrada", cascade = CascadeType.ALL, orphanRemoval = true)
    private Triage triage;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "idPaciente", nullable = false)
    private Paciente paciente;

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistroEntrada that = (RegistroEntrada) o;
        return Objects.equals(idRegistroEntrada, that.idRegistroEntrada);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRegistroEntrada);
    }
}
