package datasource;

import datasource.interfaces.GenericoDAO;
import model.ResultadoDiagnostico;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.GlobalSessionFactory;

import java.util.List;

public class ResultadoDiagnosticoDAO implements GenericoDAO<ResultadoDiagnostico> {
    private SessionFactory sessionFactory;

    public ResultadoDiagnosticoDAO(){this.sessionFactory = GlobalSessionFactory.getSessionFactory();}

    @Override
    public ResultadoDiagnostico obtener(Long id){
        Session session = sessionFactory.openSession();
        ResultadoDiagnostico resultadoDiagnostico = session.get(ResultadoDiagnostico.class, id);
        session.close();
        return resultadoDiagnostico;
    }

    @Override
    public List<ResultadoDiagnostico> obtenerTodos() {
        Session session = sessionFactory.openSession();
        String query = "SELECT resultadoDiagnostico FROM ResultadoDiagnopstico resultadoDiagnostico";
        List<ResultadoDiagnostico> resultadosDiagnosticos = session.createQuery(query, ResultadoDiagnostico.class).getResultList();
        session.close();
        return resultadosDiagnosticos;
    }
}
