package datasource;

import datasource.interfaces.GenericoDAO;
import model.FuncionarioAdministrativo;
import model.Login.Usuario;
import model.Paciente;
import model.Triage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import util.GlobalSessionFactory;
import java.time.LocalDate;

import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class FuncionarioAdministrativoDAO implements  GenericoDAO<FuncionarioAdministrativo>{
    private SessionFactory sessionFactory;

    public FuncionarioAdministrativoDAO(){this.sessionFactory = GlobalSessionFactory.getSessionFactory();}

    @Override
    public FuncionarioAdministrativo obtener(Long id){
        Session session = sessionFactory.openSession();
        FuncionarioAdministrativo funcionarioAdministrativo = session.get(FuncionarioAdministrativo.class, id);
        session.close();
        return funcionarioAdministrativo;
    }

    @Override
    public List<FuncionarioAdministrativo> obtenerTodos(){
        Session session = sessionFactory.openSession();
        String query = "SELECT funcionarioAdmin FROM FuncionarioAdministrativo funcionarioAdmin";
        List<FuncionarioAdministrativo> funcionarioAdministrativos = session.createQuery(query, FuncionarioAdministrativo.class).getResultList();
        session.close();
        return funcionarioAdministrativos;
    }

    public Map<Long, Long> pacientesMasConsultas(LocalDate fecha, LocalDate fecha2){
        Session session = sessionFactory.openSession();

        Map<Long, Long> pacientesMasConsultas = new HashMap<>();

        String hql =  "SELECT p\n" +
                "FROM Paciente p\n" +
                "INNER JOIN p.registrosEntradas re\n" +
                "WHERE re.fecha BETWEEN :fechaInicio AND :fechaFin\n" +
                "GROUP BY p\n" +
                "ORDER BY COUNT(re) DESC";

        List<Paciente> pacientes = session.createQuery(hql, Paciente.class).setParameter("fechaInicio",fecha)
                .setParameter("fechaFin", fecha2).getResultList();

        for(int i = 0; i < pacientes.size(); i++){
            Long cantidadRegistros = (Long) session.createQuery("SELECT COUNT(re) FROM RegistroEntrada re WHERE re.paciente = :paciente")
                    .setParameter("paciente", pacientes.get(i))
                    .getSingleResult();
            pacientesMasConsultas.put(pacientes.get(i).getId(), cantidadRegistros);
        }

        session.close();
        return pacientesMasConsultas;
    }
}
