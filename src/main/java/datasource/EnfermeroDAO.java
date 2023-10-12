package datasource;

import datasource.interfaces.GenericoDAO;
import model.Enfermero;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.GlobalSessionFactory;

import java.util.List;

public class EnfermeroDAO implements  GenericoDAO<Enfermero>{
    private SessionFactory sessionFactory;

    public EnfermeroDAO(){this.sessionFactory = GlobalSessionFactory.getSessionFactory();}

    @Override
    public Enfermero obtener(Long id){
        Session session = sessionFactory.openSession();
        Enfermero enfermero = session.get(Enfermero.class, id);
        session.close();
        return enfermero;
    }

    @Override
    public List<Enfermero> obtenerTodos(){
        Session session = sessionFactory.openSession();
        String query = "SELECT enfermero FROM Enfermero enfermero";
        List<Enfermero> enfermeros = session.createQuery(query, Enfermero.class).getResultList();
        session.close();
        return enfermeros;
    }
}