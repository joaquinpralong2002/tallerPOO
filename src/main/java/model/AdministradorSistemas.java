package model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AdministradorSistemas extends FuncionarioAdministrativo{
    private Long idAdministradorSistemas;

    public void asignarRol(Usuario u, Rol r){
        u.setRol(r);
    }
}
