package model;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

/**
 * Clase destinada a asignar el sector a los distintos Funcionarios
 */

@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSector;

    private String nombre;

    @OneToMany(mappedBy = "sector", orphanRemoval = true)
    private Set<Funcionario> funcionarios = new LinkedHashSet<>();

    public Sector(String nombre){
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sector sector = (Sector) o;
        return Objects.equals(nombre, sector.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}
