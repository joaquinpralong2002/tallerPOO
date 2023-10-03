package model;

import java.util.List;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class Sector {
    private Long idSector;

    private String nombre;

    private List<Funcionario> funcionarios;

    Sector(String nombre){
        this.nombre = nombre;
    }

}
