package datasource;

import datasource.interfaces.GenericoDAO;
import model.Asignacion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.GlobalSessionFactory;

import java.util.List;

public class AsignacionDAO implements GenericoDAO<Asignacion> {
    private SessionFactory sessionFactory;

    public AsignacionDAO(){
        this.sessionFactory = GlobalSessionFactory.getSessionFactory();
    }

    @Override
    public Asignacion obtener(Long id) {
        Session session = sessionFactory.openSession();
        Asignacion asignacion = session.get(Asignacion.class, id);
        session.close();
        return asignacion;
    }

    @Override
    public List<Asignacion> obtenerTodos() {
        Session session = sessionFactory.openSession();
        String query = "SELECT asignacion FROM Asignacion asignacion";
        List<Asignacion> asignaciones = session.createQuery(query, Asignacion.class).getResultList();
        session.close();
        return asignaciones;
    }
}
