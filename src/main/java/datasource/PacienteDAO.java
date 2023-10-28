package datasource;
import datasource.interfaces.GenericoDAO;
import model.*;

import model.Enum.ColorTriage;
import org.example.Main;
import org.hibernate.*;
import util.GlobalSessionFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO implements GenericoDAO<Paciente> {
    private SessionFactory sessionFactory;

    public PacienteDAO(){
        this.sessionFactory = GlobalSessionFactory.getSessionFactory();
    }

    @Override
    public Paciente obtener(Long id) {
        Session session = sessionFactory.openSession();
        Paciente paciente = session.get(Paciente.class, id);
        session.close();
        return paciente;
    }
    @Override
    public List<Paciente> obtenerTodos() {
        Session session = sessionFactory.openSession();
        String query = "SELECT paciente FROM Paciente paciente";
        List<Paciente> paciente = session.createQuery(query, Paciente.class).getResultList();
        session.close();
        return paciente;
    }

    public List<Paciente> obtenerPorNombre(String nombre) {
        Session session = sessionFactory.openSession();
        String query = "SELECT paciente FROM Paciente paciente WHERE paciente.nombre = :nombre";
        List<Paciente> paciente = session.createQuery(query, Paciente.class)
                .setParameter("nombre", nombre)
                .getResultList();
        session.close();
        return paciente;
    }

    /**
     * Obtiene un paciente por su número de DNI.
     *
     * @param dni El número de DNI del paciente que se desea buscar.
     * @return El paciente cuyo número de DNI coincide con el valor especificado, o null si no se encuentra ninguno.
     */
    public Paciente obtenerPorDni(String dni) {
        Session session = sessionFactory.openSession();
        String query = "SELECT paciente FROM Paciente paciente WHERE paciente.DNI = :dni";
        Paciente paciente = session.createQuery(query, Paciente.class)
                .setParameter("dni", dni)
                .getSingleResultOrNull();
        session.close();
        return paciente;
    }


    public List<Paciente> obtenerPorApellido(String apellido) {
        Session session = sessionFactory.openSession();
        String query = "SELECT paciente FROM Paciente paciente WHERE paciente.apellido = :apellido";
        List<Paciente> pacientes = session.createQuery(query, Paciente.class)
                .setParameter("apellido", apellido)
                .getResultList();
        session.close();
        return pacientes;
    }

    public List<Paciente> obtenerPorColorTriage(ColorTriage colorTriage) {
        Session session = sessionFactory.openSession();
        TriageDAO triageDAO = new TriageDAO();
        List<Triage> triages = triageDAO.obtenerPorColor(colorTriage);
        List<Paciente> pacientes = new ArrayList<>();
        for(int i = 0; i < triages.size(); i++){
            pacientes.add(triages.get(i).getRegistroEntrada().getPaciente());
        }
        session.close();
        return pacientes;
    }
}
