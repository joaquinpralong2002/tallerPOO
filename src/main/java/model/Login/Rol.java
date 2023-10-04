package model.Login;

import lombok.*;
import model.Login.Usuario;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Rol {
    private Long idUsuario;
    private String nombre;
    private List<Usuario> usuarios;

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
}
