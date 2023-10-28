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

    /**
     * Obtiene un médico por su nombre y apellido.
     *
     * @param nombre El nombre del médico a buscar.
     * @param apellido El apellido del médico a buscar.
     * @return El médico correspondiente al nombre y apellido proporcionados, o null si no se encuentra ningún médico con esa combinación.
     */
    public Medico obtener(String nombre, String apellido){
        Session session = sessionFactory.openSession();
        String query = "FROM Medico m WHERE m.nombre = :nombreParam AND m.apellido = :apellidoParam";
        Medico medico = session.createQuery(query, Medico.class).setParameter("nombreParam",nombre)
                .setParameter("apellidoParam",apellido).getSingleResultOrNull();
        session.close();
        return medico;
    }

    /**
     * Obtiene un mapa que muestra la cantidad de triages realizados por cada color de triage en un rango de fechas.
     *
     * @param fechaInicio Fecha de inicio del rango.
     * @param fechaFin Fecha de fin del rango.
     * @return Un mapa que asigna a cada color de triage la cantidad de triages realizados dentro del rango de fechas.
     */
    public Map<ColorTriage, Long> TriageRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        Session session = sessionFactory.openSession();

        Map<ColorTriage, Long> triageRangoFechas = new HashMap<>();

        String query = "SELECT colorTriageFinal, COUNT(*) AS cantidad FROM Triage WHERE registroEntrada.fecha BETWEEN :fechaInicio AND :fechaFin GROUP BY colorTriageFinal";

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

    /**
     * Obtiene una lista de triages que han tenido un cambio de color de triage recomendado por el médico.
     *
     * @param id El ID del médico.
     * @return Una lista de triages que han tenido un cambio en el color de triage recomendado.
     */
    public List<Triage> TriagesCambiados(Long id){
        Session session = sessionFactory.openSession();
        String query = "SELECT triage FROM Triage triage WHERE triage.colorTriageRecomendado != colorTriageFinal AND triage.medico.id = :id";
        List<Triage> triages = session.createQuery(query, Triage.class).setParameter("id",id).getResultList();
        return triages;
    }

    /**
     * Calcula la cantidad de pacientes atendidos por un médico en un rango de fechas específico.
     *
     * @param medico El médico cuya actividad se está evaluando.
     * @param fecha La fecha de inicio del rango de fechas.
     * @param fecha2 La fecha de fin del rango de fechas.
     * @return La cantidad de pacientes atendidos por el médico en el rango de fechas dado.
     */
    public Long cantidadPacientesAtendidos(Medico medico, LocalDate fecha, LocalDate fecha2){
        Session session = sessionFactory.openSession();
        String query = "SELECT COUNT(DISTINCT r.paciente) FROM Registro r WHERE r.medico = :medico AND r.fechaRegistro >= :fechaInicio " +
                "AND r.fechaRegistro <= :fechaFin";;
        Long pacientes = (Long) session.createQuery(query).setParameter("fechaInicio", fecha)
                .setParameter("fechaFin",fecha2).setParameter("medico", medico).getSingleResult();
        session.close();
        return pacientes;
    }

    /**
     * Calcula la cantidad de pacientes atendidos en un rango de fechas y un rango de edades específicos.
     *
     * @param fechaRango La fecha de inicio del rango de fechas.
     * @param fechaRango2 La fecha de fin del rango de fechas.
     * @param edad1 El valor mínimo del rango de edades.
     * @param edad2 El valor máximo del rango de edades.
     * @return La cantidad de pacientes atendidos que cumplen con los criterios de fecha y edad especificados.
     */
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

    /**
     * Obtiene un mapa que relaciona el ID del médico con la cantidad de pacientes atendidos por ese médico en un rango de fechas especificado.
     *
     * @param fechaInicio La fecha de inicio del rango.
     * @param fechaFin La fecha de fin del rango.
     * @return Un mapa donde la clave es el ID del médico y el valor es la cantidad de pacientes atendidos por ese médico en el rango de fechas.
     */
    public Map<Long, Long> obtenerMedicoConMasPacientesAtendidosEnRango(LocalDate fechaInicio, LocalDate fechaFin) {
        Map<Long, Long> medicosMasConsultas = new HashMap<>();

        Session session = sessionFactory.openSession();
        String query = "SELECT r.medico " +
                "FROM Registro r " +
                "WHERE r.fechaRegistro >= :fechaInicio AND r.fechaRegistro <= :fechaFin " +
                "GROUP BY r.medico " +
                "ORDER BY COUNT(DISTINCT r.paciente.id) DESC";

        List<Medico> medicos = session.createQuery(query, Medico.class)
                .setParameter("fechaInicio", fechaInicio)
                .setParameter("fechaFin", fechaFin)
                .getResultList();


        for (int i = 0; i < medicos.size(); i++) {
            Long cantidadConsultas = (Long) session.createQuery("SELECT COUNT(re) FROM Registro re WHERE re.medico = :medico")
                    .setParameter("medico", medicos.get(i))
                    .getSingleResult();
            medicosMasConsultas.put(medicos.get(i).getId(), cantidadConsultas);
        }
        session.close();
        return medicosMasConsultas;
    }
}
