package datasource;

import datasource.interfaces.GenericoDAO;
import model.Medico;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.GlobalSessionFactory;

import java.util.List;

public class MedicoDAO implements GenericoDAO<Medico>{
    private SessionFactory sessionFactory;

    public MedicoDAO(){this.sessionFactory = GlobalSessionFactory.getSessionFactory();}

    @Override
    public Medico obtener(Long id){
        Session session = sessionFactory.openSession();
        Medico medico = session.get(Medico.class, id);
        session.close();
        return medico;
    }

    @Override
    public List<Medico> obtenerTodos(){
        Session session = sessionFactory.openSession();
        String query = "SELECT medico FROM Medico medico";
        List<Medico> medicos = session.createQuery(query, Medico.class).getResultList();
        session.close();
        return medicos;
    }
}
