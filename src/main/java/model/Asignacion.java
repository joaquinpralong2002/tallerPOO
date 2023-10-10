package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalTime;
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "asignacion")
public class Asignacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAsignacion", nullable = false)
    private Long idAsignacion;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora", nullable = false, unique = true)
    private LocalTime hora;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "idRegistroEntrada", nullable = false)
    private RegistroEntrada registroEntrada;

    @ManyToOne(optional = false)
    @JoinColumn(name = "numero", nullable = false, unique = true)
    private BoxAtencion boxAtencion;

    public Asignacion(RegistroEntrada registroEntrada, BoxAtencion boxAtencion) {
        this.fecha = LocalDate.now();
        this.hora = LocalTime.now();
        this.registroEntrada = registroEntrada;
        this.boxAtencion = boxAtencion;
    }
}