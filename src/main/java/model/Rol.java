package model;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Rol {
    private Long idUsuario;
    private String nombre;
    private List<Usuario> usuarios;


}
