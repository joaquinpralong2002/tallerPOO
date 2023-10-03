package model;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class BoxAtencion {
    //El id de está clase será el número de box.
    private int numero;
    private int capacidad;
    private boolean disponible;

    private Asignacion asignacion;
    private List<RegistroEntrada> registrosEntradas;
    private Medico medico;

    public BoxAtencion(int numero, int capacidad, boolean disponible) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.disponible = disponible;
    }

    public BoxAtencion(int numero, int capacidad, boolean disponible, Asignacion asignacion) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.disponible = disponible;
        this.asignacion = asignacion;
    }

    public BoxAtencion(int numero, int capacidad, boolean disponible, Asignacion asignacion,
                       List<RegistroEntrada> registrosEntradas) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.disponible = disponible;
        this.asignacion = asignacion;
        this.registrosEntradas = registrosEntradas;
    }

    public BoxAtencion(int numero, int capacidad, boolean disponible, Asignacion asignacion,
                       List<RegistroEntrada> registrosEntradas, Medico medico) {
        this.numero = numero;
        this.capacidad = capacidad;
        this.disponible = disponible;
        this.asignacion = asignacion;
        this.registrosEntradas = registrosEntradas;
        this.medico = medico;
    }

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
