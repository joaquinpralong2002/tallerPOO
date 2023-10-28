package datasource;

import datasource.interfaces.GenericoDAO;
import model.Enum.ColorTriage;
import model.Triage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.GlobalSessionFactory;

import java.util.List;

public class TriageDAO implements GenericoDAO<Triage> {
    private SessionFactory sessionFactory;

    public TriageDAO(){this.sessionFactory = GlobalSessionFactory.getSessionFactory();}

    @Override
    public Triage obtener(Long id){
        Session session = sessionFactory.openSession();
        Triage triage = session.get(Triage.class, id);
        session.close();
        return triage;
    }

    @Override
    public List<Triage> obtenerTodos(){
        Session session = sessionFactory.openSession();
        String query = "SELECT triage FROM Triage triage";
        List<Triage> triages = session.createQuery(query, Triage.class).getResultList();
        session.close();;
        return triages;
    }

    /**
     * Obtiene una lista de triages por su color.
     *
     * @param color El color de triage por el cual se desea filtrar.
     * @return Una lista de triages que tienen el color especificado.
     */
    public List<Triage> obtenerPorColor(ColorTriage color){
        Session session = sessionFactory.openSession();
        String query = "SELECT triage FROM Triage triage WHERE triage.color :color";
        List<Triage> triages = session.createQuery(query, Triage.class).setParameter("color", color).getResultList();
        session.close();
        return triages;
    }
}
