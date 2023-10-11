package model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
public class BoxAtencion {

    protected static List<BoxAtencion> boxesAtencion = new ArrayList<>();

    //El id de está clase será el número de box.
    @Id
    private int numero;
    private int capacidad;
    private boolean disponible;


    @OneToMany(mappedBy = "boxAtencion", orphanRemoval = true)
    private Set<Asignacion> asignaciones = new LinkedHashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idMedico")
    private Medico medico;


    public BoxAtencion(int numero, int capacidad, boolean disponible) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.disponible = disponible;
        boxesAtencion.add(this);
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


    public void agregarAsignacion(Asignacion asignacion){
        this.asignaciones.add(asignacion);
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
