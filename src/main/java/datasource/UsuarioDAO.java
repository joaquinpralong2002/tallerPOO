package datasource;

import datasource.interfaces.GenericoDAO;
import model.Funcionario;
import model.FuncionarioAdministrativo;
import model.Login.*;
import model.Medico;
import model.Paciente;
import org.hibernate.*;
import util.GlobalSessionFactory;

import java.util.List;
import java.util.Optional;

public class UsuarioDAO implements GenericoDAO<Usuario> {
    private SessionFactory sessionFactory;

    public UsuarioDAO(){
        this.sessionFactory = GlobalSessionFactory.getSessionFactory();
    }

    @Override
    public Usuario obtener(Long id) {
        Session session = sessionFactory.openSession();
        Usuario usuario = session.get(Usuario.class, id);
        session.close();
        return usuario;
    }

    @Override
    public List<Usuario> obtenerTodos() {
        Session session = sessionFactory.openSession();
        String query = "SELECT usuario FROM Usuario usuario";
        List<Usuario> usuario = session.createQuery(query, Usuario.class).getResultList();
        session.close();
        return usuario;
    }

    public Usuario obtenerUsuarioPorNombre(String nombreUsuario){
        Session session = sessionFactory.openSession();
        String query = "SELECT usuario FROM Usuario usuario WHERE usuario.nombreUsuario = :nombreUsuario";
        Usuario usuario = session.createQuery(query, Usuario.class)
                .setParameter("nombreUsuario", nombreUsuario)
                .getSingleResultOrNull();
        session.close();
        return usuario;
    }

    public Boolean existeUsuarioPorNombre(String nombreUsuario){
        Session session = sessionFactory.openSession();
        String query = "SELECT usuario FROM Usuario usuario WHERE usuario.nombreUsuario = :nombreUsuario";
        Usuario usuario = session.createQuery(query, Usuario.class)
                .setParameter("nombreUsuario", nombreUsuario)
                        .getSingleResultOrNull();
        session.close();
        return usuario != null;
    }

    public Medico obtenerMedicoPorNombreUsuario(String nombreUsuario) {
        Session session = sessionFactory.openSession();
        String query = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.correo = :nombreUsuario";
        Medico medico = session.createQuery(query, Medico.class)
                .setParameter("nombreUsuario", nombreUsuario)
                .getSingleResultOrNull();
        session.close();
        return medico;
    }

    public FuncionarioAdministrativo obtenerFuncionarioAdministrativoPorIdUsuario(Long id) {
        Session session = sessionFactory.openSession();
        String query = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.usuario.idUsuario = :id";
        FuncionarioAdministrativo funcionarioAdministrativo = session.createQuery(query, FuncionarioAdministrativo.class).setParameter("id", id)
                .getSingleResultOrNull();
        session.close();
        return funcionarioAdministrativo;
    }
}
