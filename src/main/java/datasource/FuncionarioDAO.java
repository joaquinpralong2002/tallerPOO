package datasource;

import datasource.interfaces.GenericoDAO;
import model.Funcionario;
import model.FuncionarioAdministrativo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.GlobalSessionFactory;

import java.util.List;

public class FuncionarioDAO implements GenericoDAO<Funcionario> {

    private SessionFactory sessionFactory;

    public FuncionarioDAO(){
        this.sessionFactory = GlobalSessionFactory.getSessionFactory();
    }

    @Override
    public Funcionario obtener(Long id) {
        Session session = sessionFactory.openSession();
        Funcionario funcionario = session.get(FuncionarioAdministrativo.class,id);
        session.close();
        return funcionario;
    }

    @Override
    public List<Funcionario> obtenerTodos() {
        Session session = sessionFactory.openSession();
        String query = "SELECT funcionario FROM Funcionario funcionario";
        List<Funcionario> funcionarios = session.createQuery(query, Funcionario.class).getResultList();
        session.close();
        return funcionarios;
    }
}
