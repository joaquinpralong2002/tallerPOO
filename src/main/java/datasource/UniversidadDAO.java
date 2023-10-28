package datasource;

import datasource.interfaces.GenericoDAO;
import model.Login.Usuario;
import model.Universidad;
import datasource.interfaces.GenericoDAO;
import org.hibernate.*;
import util.GlobalSessionFactory;

import java.util.List;
public class UniversidadDAO implements GenericoDAO<Universidad> {
    private SessionFactory sessionFactory;

    public UniversidadDAO(){
        this.sessionFactory = GlobalSessionFactory.getSessionFactory();
    }

    @Override
    public Universidad obtener(Long id) {
        Session session = sessionFactory.openSession();
        Universidad universidad = session.get(Universidad.class, id);
        session.close();
        return universidad;
    }

    @Override
    public List<Universidad> obtenerTodos() {
        Session session = sessionFactory.openSession();
        String query = "SELECT universidad FROM Universidad universidad";
        List<Universidad> universidad = session.createQuery(query, Universidad.class).getResultList();
        session.close();
        return universidad;
    }

    public Universidad obtenerUniversidadPorNombre(String nombre){
        Session session = sessionFactory.openSession();
        String query = "SELECT universidad FROM Universidad universidad WHERE universidad.nombre = :nombre";
        Universidad universidad = session.createQuery(query, Universidad.class)
                .setParameter("nombre", nombre)
                .getSingleResultOrNull();
        session.close();
        return universidad;
    }
}

