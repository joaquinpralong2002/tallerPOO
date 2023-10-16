package model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.List;

/**
 * Clase que representa una institución educativa superior, de la que los medicos se recibierón.
 */

@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

@Entity
public class Universidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUniversidad;

    @NaturalId
    private String nombre;

    @OneToMany(mappedBy = "universidad", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Especialidad> especializaciones;

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
