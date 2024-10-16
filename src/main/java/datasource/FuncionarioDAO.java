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

    /**
     * Obtiene un funcionario por su número de DNI.
     *
     * @param dni El número de DNI del funcionario a buscar.
     * @return El funcionario correspondiente al número de DNI proporcionado, o null si no se encuentra ningún funcionario con ese DNI.
     */
    public Funcionario obtenerPorDni(String dni){
        Session session = sessionFactory.openSession();
        String query = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.DNI = :dni";
        Funcionario funcionario = session.createQuery(query, Funcionario.class).setParameter("dni",dni).getSingleResultOrNull();
        return funcionario;
    }

}
