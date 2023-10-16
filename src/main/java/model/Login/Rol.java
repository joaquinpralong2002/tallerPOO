package model.Login;

import jakarta.persistence.*;
import lombok.*;


import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Clase que representa los roles que pueden tener los usuarios de la aplicación.
 */
@Getter
@Setter
@NoArgsConstructor

@Entity
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    private String nombre;

    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<Usuario> usuarios = new LinkedHashSet<>();


    public Rol(String nombre){
        this.nombre = nombre;
    }
    public Rol(String nombre, Set<Usuario> usuarios) {
        this.nombre = nombre;
        this.usuarios = usuarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rol rol = (Rol) o;
        return Objects.equals(nombre, rol.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    @Override
    public String toString() {
        return "Rol{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
}
