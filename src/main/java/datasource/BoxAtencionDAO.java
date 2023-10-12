package datasource;

import datasource.interfaces.GenericoDAO;
import model.BoxAtencion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.GlobalSessionFactory;

import java.util.List;

public class BoxAtencionDAO implements GenericoDAO<BoxAtencion> {

    private SessionFactory sessionFactory;

    public BoxAtencionDAO(){
        this.sessionFactory = GlobalSessionFactory.getSessionFactory();
    }

    @Override
    public BoxAtencion obtener(Long id) {
        Session session = sessionFactory.openSession();
        BoxAtencion boxAtencion = session.get(BoxAtencion.class, id);
        session.close();
        return boxAtencion;
    }

    @Override
    public List<BoxAtencion> obtenerTodos() {
        Session session = sessionFactory.openSession();
        String query = "SELECT boxAtencion FROM BoxAtencion boxAtencion";
        List<BoxAtencion> boxesAtencion = session.createQuery(query, BoxAtencion.class).getResultList();
        session.close();
        return boxesAtencion;
    }

    public List<BoxAtencion> obtenerTodosDisponibles() {
        Session session = sessionFactory.openSession();
        String query = "SELECT boxAtencion FROM BoxAtencion boxAtencion WHERE boxAtencion.disponible = true";
        List<BoxAtencion> boxesAtencionDisponibles = session.createQuery(query, BoxAtencion.class).getResultList();
        session.close();
        return boxesAtencionDisponibles;
    }
}
