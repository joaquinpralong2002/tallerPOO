package model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
public class Asignacion {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsignacion;
    private LocalDate fecha;
    private LocalTime hora;


    private BoxAtencion boxAtencion;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private RegistroEntrada registroEntrada;

    public Asignacion(LocalDate fecha, LocalTime hora) {
        this.fecha = fecha;
        this.hora = hora;
    }

    public Asignacion(LocalDate fecha, LocalTime hora, BoxAtencion boxAtencion,
                      RegistroEntrada registroEntrada) {
        this.fecha = fecha;
        this.hora = hora;
        this.boxAtencion = boxAtencion;
        this.registroEntrada = registroEntrada;
    }

}
