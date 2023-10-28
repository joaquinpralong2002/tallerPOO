package datasource;

import datasource.interfaces.GenericoDAO;
import model.Enfermero;
import model.Triage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.GlobalSessionFactory;

import java.util.List;

public class EnfermeroDAO implements  GenericoDAO<Enfermero>{
    private SessionFactory sessionFactory;

    public EnfermeroDAO(){this.sessionFactory = GlobalSessionFactory.getSessionFactory();}

    @Override
    public Enfermero obtener(Long id){
        Session session = sessionFactory.openSession();
        Enfermero enfermero = session.get(Enfermero.class, id);
        session.close();
        return enfermero;
    }

    @Override
    public List<Enfermero> obtenerTodos(){
        Session session = sessionFactory.openSession();
        String query = "SELECT enfermero FROM Enfermero enfermero";
        List<Enfermero> enfermeros = session.createQuery(query, Enfermero.class).getResultList();
        session.close();
        return enfermeros;
    }

    /**
     * Obtiene un objeto de tipo Enfermero basado en su nombre y apellido.
     *
     * @param nombre    El nombre del enfermero a buscar.
     * @param apellido  El apellido del enfermero a buscar.
     * @return          Un objeto Enfermero que coincide con el nombre y apellido proporcionados,
     *                  o null si no se encuentra ninguna coincidencia.
     */
    public Enfermero obtener(String nombre, String apellido){
        Session session = sessionFactory.openSession();
        String query = "FROM Enfermero e WHERE e.nombre = :nombreParam AND e.apellido = :apellidoParam";
        Enfermero enfermero = session.createQuery(query, Enfermero.class).setParameter("nombreParam",nombre)
                .setParameter("apellidoParam",apellido).getSingleResultOrNull();
        session.close();
        return enfermero;
    }

    /**
     * Obtiene una lista de objetos de tipo Triage donde el color de triaje recomendado es diferente
     * al color de triaje final y el ID del enfermero coincide.
     *
     * @param id El ID del enfermero para filtrar los triajes.
     * @return Una lista de triajes que cumplen con las condiciones especificadas.
     */
    public List<Triage> TriagesCambiados(Long id){
        Session session = sessionFactory.openSession();
        String query = "SELECT triage FROM Triage triage WHERE triage.colorTriageRecomendado != colorTriageFinal AND triage.enfermero.id = :id";
        List<Triage> triages = session.createQuery(query, Triage.class).setParameter("id",id).getResultList();
        return triages;
    }
}