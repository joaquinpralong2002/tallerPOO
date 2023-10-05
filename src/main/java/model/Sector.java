package model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.*;

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

    @OneToOne(mappedBy = "sector")
    private List<Funcionario> funcionarios;

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
