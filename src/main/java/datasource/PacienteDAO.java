package datasource;
import datasource.interfaces.GenericoDAO;
import model.*;

import org.example.Main;
import org.hibernate.*;
import util.GlobalSessionFactory;

import java.util.List;

public class PacienteDAO implements GenericoDAO<Paciente> {
    private SessionFactory sessionFactory;

    public PacienteDAO(){
        this.sessionFactory = GlobalSessionFactory.getSessionFactory();
    }

    //************** BASICOS DE PACIENTE*******************
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
    //********************************************************************

    //REVISAR POR LAS DUDAS...

    public Paciente obtenerPorNombreYApellido(String nombre, String apellido) {
        Session session = sessionFactory.openSession();
        String query = "SELECT paciente FROM Paciente paciente WHERE paciente.nombre = :nombre and paciente.apellido = :apellido";
        Paciente paciente = session.createQuery(query, Paciente.class)
                .setParameter("nombre", nombre)
                .setParameter("apellido", apellido)
                .getSingleResultOrNull();
        session.close();
        return paciente;
    }

    public Paciente obtenerPorDni(String dni) {
        Session session = sessionFactory.openSession();
        String query = "SELECT paciente FROM Paciente paciente WHERE paciente.DNI = :dni";
        Paciente paciente = session.createQuery(query, Paciente.class)
                .setParameter("dni", dni)
                .getSingleResultOrNull();
        session.close();
        return paciente;
    }

}
