package model;

import jakarta.persistence.*;
import lombok.*;
import model.Asignacion;
import model.Enum.LugarAtencion;
import model.Medico;

import java.util.*;

/**
 * Clase que representa un box de atención, que posee un número asociado, una capacidad, y un booleano que indica si
 * está disponible.
 */
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
public class BoxAtencion {
    @Id
    private int numero;
    private int capacidad;
    private boolean disponible;

    @Enumerated(EnumType.STRING)
    private LugarAtencion lugarAtencion;

    @OneToMany(mappedBy = "boxAtencion", orphanRemoval = true)
    @ToString.Exclude
    private Set<Asignacion> asignaciones = new LinkedHashSet<>();

    @OneToOne(mappedBy = "boxAtencion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Medico medico;


    public BoxAtencion(LugarAtencion lugarAtencion, int numero, int capacidad, boolean disponible) {
        this.lugarAtencion = lugarAtencion;
        this.numero = numero;
        this.capacidad = capacidad;
        this.disponible = disponible;
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
