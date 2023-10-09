package model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
public class BoxAtencion {

    //El id de está clase será el número de box.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int numero;
    private int capacidad;
    private boolean disponible;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idAsignacion")
    private Asignacion asignacion;

    @OneToMany(mappedBy = "boxAtencion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RegistroEntrada> registrosEntradas = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Medico medico;

    public BoxAtencion(int numero, int capacidad, boolean disponible) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.disponible = disponible;
    }
//Sugerir borrar los primeros  3 atributos
//    public BoxAtencion(int numero, int capacidad, boolean disponible, Asignacion asignacion,
//                       List<RegistroEntrada> registrosEntradas, Medico medico) {
//        this.numero = numero;
//        this.capacidad = capacidad;
//        this.disponible = disponible;
//        this.asignacion = asignacion;
//        this.registrosEntradas = registrosEntradas;
//        this.medico = medico;
//    }

    public void agregarEntrada(RegistroEntrada registro){
        registrosEntradas.add(registro);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoxAtencion that = (BoxAtencion) o;
        return numero == that.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
}
