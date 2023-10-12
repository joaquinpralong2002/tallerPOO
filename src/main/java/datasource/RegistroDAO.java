package datasource;

import datasource.interfaces.GenericoDAO;
import model.Registro;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.GlobalSessionFactory;

import java.util.List;

public class RegistroDAO implements  GenericoDAO<Registro>{
    private SessionFactory sessionFactory;

    public RegistroDAO(){this.sessionFactory = GlobalSessionFactory.getSessionFactory();}

    @Override
    public Registro obtener(Long id){
        Session session = sessionFactory.openSession();
        Registro registro = session.get(Registro.class, id);
        session.close();
        return registro;
    }

    @Override
    public List<Registro> obtenerTodos(){
        Session session = sessionFactory.openSession();
        String query = "SELECT registro FROM Registro registro";
        List<Registro> registros = session.createQuery(query, Registro.class).getResultList();
        session.close();
        return registros;
    }
}
