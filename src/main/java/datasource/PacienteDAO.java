package datasource;
import datasource.interfaces.GenericoDAO;
import model.*;


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
        List<Paciente> paciente = session.createQuery(query).getResultList();
        session.close();
        return paciente;
    }
    //********************************************************************



}
