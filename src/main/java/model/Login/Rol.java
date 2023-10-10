package model.Login;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    private String nombre;

    @ManyToMany(mappedBy = "roles")
    private List<Usuario> usuarios;

    public Rol(String nombre){
        this.nombre = nombre;
    }
    public Rol(String nombre, List<Usuario> usuarios) {
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
