package datasource;

import datasource.interfaces.GenericoDAO;
import model.Login.Rol;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.GlobalSessionFactory;

import java.util.List;

public class RolDAO implements GenericoDAO<Rol> {
    private SessionFactory sessionFactory;

    public RolDAO(){
        this.sessionFactory = GlobalSessionFactory.getSessionFactory();
    }

    @Override
    public Rol obtener(Long id) {
        Session session = sessionFactory.openSession();
        Rol rol = session.get(Rol.class, id);
        session.close();
        return rol;
    }

    @Override
    public List<Rol> obtenerTodos() {
        Session session = sessionFactory.openSession();
        String query = "SELECT rol FROM Rol rol";
        List<Rol> roles = session.createQuery(query, Rol.class).getResultList();
        session.close();
        return roles;
    }
}
