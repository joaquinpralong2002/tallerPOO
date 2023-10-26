package datasource;

import datasource.interfaces.GenericoDAO;
import model.*;
import model.Login.*;
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

    public Medico obtenerMedicoPorIdUsuario(Long id) {
        Session session = sessionFactory.openSession();
        String query = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.usuario.id = :id";
        Medico medico = session.createQuery(query, Medico.class)
                .setParameter("id", id)
                .getSingleResultOrNull();
        session.close();
        return medico;
    }

    public Enfermero obtenerEnfermeroPorIdUsuario(Long id) {
        Session session = sessionFactory.openSession();
        String query = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.usuario.id = :id";
        Enfermero enfermero = session.createQuery(query, Enfermero.class)
                .setParameter("id", id)
                .getSingleResultOrNull();
        session.close();
        return enfermero;
    }

    public AdministradorSistemas obtenerAdministradorPorIdUsuario(Long id) {
        Session session = sessionFactory.openSession();
        String query = "SELECT admin FROM AdministradorSistemas admin WHERE admin.usuario.id = :id";
        AdministradorSistemas administradorSistemas = session.createQuery(query, AdministradorSistemas.class)
                .setParameter("id", id)
                .getSingleResultOrNull();
        session.close();
        return administradorSistemas;
    }

    public Funcionario obtenerFuncionarioAdministrativoPorIdUsuario(Long id) {
        Session session = sessionFactory.openSession();
        String query = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.usuario.idUsuario = :id";
        Funcionario funcionario = session.createQuery(query, Funcionario.class).setParameter("id", id)
                .getSingleResultOrNull();
        session.close();
        return funcionario;
    }


    public Object obtenerFuncionarioPorIdUsuario(Long idUsuario) {
        Session session = sessionFactory.openSession();
        String query = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.usuario.idUsuario = :id";
        Object funcionario = session.createQuery(query).setParameter("id", idUsuario)
                .getSingleResultOrNull();
        session.close();

        return funcionario;
    }
}
