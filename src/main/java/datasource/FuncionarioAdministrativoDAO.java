package datasource;

import datasource.interfaces.GenericoDAO;
import model.FuncionarioAdministrativo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.GlobalSessionFactory;

import java.util.List;

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
}
