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

    @OneToOne(mappedBy = "asignacion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private BoxAtencion boxAtencion;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idAsignacion")
    private RegistroEntrada registroEntrada;


    public Asignacion(BoxAtencion boxAtencion,
                      RegistroEntrada registroEntrada) {
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
        this.boxAtencion = boxAtencion;
        this.registroEntrada = registroEntrada;
    }

}
