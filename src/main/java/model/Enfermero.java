package model;

import jakarta.persistence.*;
import lombok.*;
import model.Enum.ColorTriage;
import model.Enum.EstadoCivil;
import model.Login.Usuario;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
public class Enfermero extends Funcionario implements CapacitadoTriage{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEnfermero;

    @OneToMany(mappedBy = "triagiadorEnfermero", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Triage>triagesRealizados;

    //constructor
    public Enfermero(String nombreApellido, LocalDate fechaNacimiento, String domicilio,
                     int DNI, int telefonoFijo, long telefonoCelular, EstadoCivil estadoCivil,
                     String correo, Usuario usuario, Sector sector) {
        super(nombreApellido, fechaNacimiento, domicilio, DNI, telefonoFijo, telefonoCelular, estadoCivil, correo, usuario,sector);
        triagesRealizados = new LinkedList<>();
    }


    /**
     * @param r de tipo RegistroEntrada
     * @return
     */
    @Override
    public ColorTriage realizarTriage(RegistroEntrada r) {
        Triage triage = new Triage();
        triage.calcularColorTriageRecomendado();
        r.setTriage(triage);
        this.triagesRealizados.add(triage);
        return triage.getColorTriageRecomendado();
    }

    /**
     * @param t de tipo Triage, sirve para saber el triage que se le va a cambiar el color
     * @param color de tipo ColorTriage, el nuevo color que tendra el triage
     * @param motivo De tipo String, sirve para almacenar el motivo por el cual el color del triage fue modificado
     */
    @Override
    public void cambiarColorTriage(Triage t, ColorTriage color, String motivo) {
        t.modificarColorTriageFinal(color, motivo);
    }
}
