package datasource;

import datasource.interfaces.GenericoDAO;
import model.Enum.ColorTriage;
import model.Medico;
import model.Triage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.GlobalSessionFactory;

import java.util.ArrayList;
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
    
    public Map<ColorTriage, Long> TriageRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        Session session = sessionFactory.openSession();

        Map<ColorTriage, Long> triageRangoFechas = new HashMap<>();

        String query = "SELECT colorTriageRecomendado, COUNT(*) AS cantidad FROM Triage WHERE registroEntrada.fecha BETWEEN :fechaInicio AND :fechaFin GROUP BY colorTriageRecomendado";

        List<Object[]> triages = session.createQuery(query)
                .setParameter("fechaInicio", fechaInicio)
                .setParameter("fechaFin", fechaFin)
                .getResultList();

        for (Object[] triage : triages) {
            ColorTriage color = (ColorTriage) triage[0];
            Long cantidad = (Long) triage[1];
            triageRangoFechas.put(color, cantidad);
        }

        session.close();
        return triageRangoFechas;
    }

    public Long cantidadPacientesAtendidos(Medico medico, LocalDate fecha, LocalDate fecha2){
        Session session = sessionFactory.openSession();
        String query = "SELECT COUNT(DISTINCT r.paciente) FROM Registro r WHERE r.medico = :medico AND r.fechaRegistro >= :fechaInicio " +
                "AND r.fechaRegistro <= :fechaFin";;
        Long pacientes = (Long) session.createQuery(query).setParameter("fechaInicio", fecha)
                .setParameter("fechaFin",fecha2).setParameter("medico", medico).getSingleResult();
        session.close();
        return pacientes;
    }

    public Long cantidadPacientesAtendidos(LocalDate fechaRango, LocalDate fechaRango2, int edad1, int edad2){
        Session session = sessionFactory.openSession();
        String query = "SELECT COUNT(DISTINCT r.paciente) " +
                "FROM Registro r " +
                "WHERE r.fechaRegistro >= :fechaInicio " +
                "AND r.fechaRegistro <= :fechaFin " +
                "AND r.paciente.edad >= :edadInicio " +
                "AND r.paciente.edad <= :edadFin";
        Long pacientes = (Long) session.createQuery(query).setParameter("fechaInicio", fechaRango)
                .setParameter("fechaFin",fechaRango2)
                .setParameter("edadInicio", edad1).setParameter("edadFin",edad2).getSingleResult();
        session.close();
        return pacientes;
    }

    public List<Medico> obtenerMedicoConMasPacientesAtendidosEnRango(LocalDate fechaInicio, LocalDate fechaFin) {
        Session session = sessionFactory.openSession();
        String query = "SELECT r.medico.id " +
                "FROM Registro r " +
                "WHERE r.fechaRegistro >= :fechaInicio AND r.fechaRegistro <= :fechaFin " +
                "GROUP BY r.medico.id " +
                "ORDER BY COUNT(DISTINCT r.paciente.id) DESC";

        List<Long> idsMedico = session.createQuery(query, Long.class)
                .setParameter("fechaInicio", fechaInicio)
                .setParameter("fechaFin", fechaFin)
                .getResultList();

        List<Medico> medicos = new ArrayList<>();
        for (Long idMedico : idsMedico) {
            Medico medico = session.get(Medico.class, idMedico);
            medicos.add(medico);
        }

        session.close();
        return medicos;
    }





}
