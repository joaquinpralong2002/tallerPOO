package datasource;

import datasource.interfaces.GenericoDAO;
import model.Enum.ColorTriage;
import model.Medico;
import model.Triage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.GlobalSessionFactory;
import java.util.HashMap;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MedicoDAO implements GenericoDAO<Medico>{
    private SessionFactory sessionFactory;

    public MedicoDAO(){this.sessionFactory = GlobalSessionFactory.getSessionFactory();}

    @Override
    public Medico obtener(Long id){
        Session session = sessionFactory.openSession();
        Medico medico = session.get(Medico.class, id);
        session.close();
        return medico;
    }

    @Override
    public List<Medico> obtenerTodos(){
        Session session = sessionFactory.openSession();
        String query = "SELECT medico FROM Medico medico";
        List<Medico> medicos = session.createQuery(query, Medico.class).getResultList();
        session.close();
        return medicos;
    }

    public Map<Integer, String> TriageRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        Session session = sessionFactory.openSession();

        Map<Integer, String> TriageRangoFechas = new HashMap<>();

        String query = "SELECT re.idTriage AS idTriage, t.colorTriageFinal AS colorTriageFinal, COUNT(*) AS cantidad " +
                "FROM RegistroEntrada re " +
                "JOIN re.triage t " +
                "WHERE re.fecha >= :fechaInicio " +
                "AND re.fecha <= :fechaFin " +
                "GROUP BY idTriage, colorTriageFinal";

        List<Triage> triages = session.createQuery(query, Triage.class)
                .setParameter("fechaInicio", fechaInicio)
                .setParameter("fechaFin", fechaFin)
                .getResultList();

        for(int i = 0; i < triages.size(); i++){
            Integer cantidadTriages = (Integer) session.createQuery("SELECT COUNT(t) FROM Triage t WHERE t.paciente = :paciente")
                    .setParameter("paciente", triages.get(i))
                    .getSingleResult();
            TriageRangoFechas.put(cantidadTriages, triages.get(i).getColorTriageFinal().toString());
        }

        session.close();
        return TriageRangoFechas;
    }
    /*
    Consultado en MySQL que FUNCIONA
        SELECT registroentrada.idTriage AS idTriage, triage.colorTriageFinal AS colorTriageFinal, COUNT(*) AS cantidad
        FROM registroentrada
        INNER JOIN triage ON registroentrada.idTriage = triage.idTriage
        WHERE registroentrada.fecha >= '2023-01-01'
        AND registroentrada.fecha <= '2023-12-31'
        GROUP BY idTriage, colorTriageFinal;
     */


}
