package datasource;

import datasource.interfaces.GenericoDAO;
import model.FuncionarioAdministrativo;
import model.Login.AdministradorSistemas;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.GlobalSessionFactory;

import java.util.List;

public class AdministradorSistemasDAO implements GenericoDAO<AdministradorSistemas> {
    private SessionFactory sessionFactory;

    public AdministradorSistemasDAO(){this.sessionFactory = GlobalSessionFactory.getSessionFactory();}
    @Override
    public AdministradorSistemas obtener(Long id) {
        Session session = sessionFactory.openSession();
        AdministradorSistemas administradorSistemas = session.get(AdministradorSistemas.class, id);
        session.close();
        return administradorSistemas;
    }

    @Override
    public List<AdministradorSistemas> obtenerTodos() {
        Session session = sessionFactory.openSession();
        String query = "SELECT administradorSistemas FROM AdministradorSistemas administradorSistemas";
        List<AdministradorSistemas> administradoresSistemas = session.createQuery(query, AdministradorSistemas.class).getResultList();
        session.close();
        return administradoresSistemas;
    }
}
