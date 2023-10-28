package datasource;

import datasource.interfaces.GenericoDAO;
import model.BoxAtencion;
import model.Enum.LugarAtencion;
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

    /**
     * Metodo para solicitar todos los boxes disponibles, de todas las areas
     * @return Lista de tipo BoxAtencion
     */
    public List<BoxAtencion> obtenerTodosDisponibles() {
        Session session = sessionFactory.openSession();
        String query = "SELECT boxAtencion FROM BoxAtencion boxAtencion WHERE boxAtencion.disponible = true";
        List<BoxAtencion> boxesAtencionDisponibles = session.createQuery(query, BoxAtencion.class).getResultList();
        session.close();
        return boxesAtencionDisponibles;
    }

    /**
     * Metodo para solicitar un box de atencion segun su area
     * @param lugarAtencion enum que puede ser Consultorio, Emergencia, Internaciones
     * @return de tipo BoxAtencion
     */
    public BoxAtencion obtenerDisponible(LugarAtencion lugarAtencion){
        Session session = sessionFactory.openSession();
        String query = "SELECT box FROM BoxAtencion box WHERE box.lugarAtencion = :lugarAtencion";
        List<BoxAtencion> boxAtencion = session.createQuery(query, BoxAtencion.class)
                .setParameter("lugarAtencion", lugarAtencion).getResultList();
        session.close();
        return boxAtencion.get(0);
    }
}
