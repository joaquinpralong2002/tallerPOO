package datasource;

import datasource.interfaces.GenericoDAO;
import model.Login.Usuario;
import model.Sector;
import model.Universidad;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.GlobalSessionFactory;

import java.util.List;

public class SectorDAO implements GenericoDAO<Sector> {
    private SessionFactory sessionFactory;

    public SectorDAO(){
        this.sessionFactory = GlobalSessionFactory.getSessionFactory();
    }

    @Override
    public Sector obtener(Long id) {
        Session session = sessionFactory.openSession();
        Sector sector = session.get(Sector.class, id);
        session.close();
        return sector;
    }

    @Override
    public List<Sector> obtenerTodos() {
        Session session = sessionFactory.openSession();
        String query = "SELECT sector FROM Sector sector";
        List<Sector> sectores = session.createQuery(query, Sector.class).getResultList();
        session.close();
        return sectores;
    }

    public Sector obtenerPorNombre(String nombre){
        Session session = sessionFactory.openSession();
        String query = "SELECT sector FROM Sector sector WHERE sector.nombre = :nombre";
        Sector sector = session.createQuery(query, Sector.class).setParameter("nombre",nombre).getSingleResultOrNull();
        return sector;
    }
}
