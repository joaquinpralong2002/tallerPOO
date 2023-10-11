package datasource;

import datasource.interfaces.GenericoDAO;
import model.Funcionario;
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
        return null;
    }

    @Override
    public List<Funcionario> obtenerTodos() {
        return null;
    }
}
