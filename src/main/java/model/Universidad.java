package model;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

public class Universidad {

    private Long idUniversidad;

    private String nombre;

    private List<Especialidad>especializaciones;

    public Universidad(String nombre){
        this.nombre = nombre;
    }

    /**
     * Metodo para agregar una nueva especializacion a la lista
     * @param e especializacion a ser agregada
     */
    public void agregarEspecializacion(Especialidad e){
        especializaciones.add(e);
    }

    /**
     * Metodo para eliminar una nueva especializacion a la lista
     * @param e especializacion a ser eliminada
     */
    public void eliminarEspecializacion(Especialidad e){
        especializaciones.remove(e);
    }

}
