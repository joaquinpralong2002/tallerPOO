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

    /**
     * Obtiene un objeto Usuario por su nombre de usuario.
     *
     * @param nombreUsuario El nombre de usuario del Usuario que se desea obtener.
     * @return El Usuario correspondiente al nombre de usuario proporcionado o null si no se encuentra.
     */
    public Usuario obtenerUsuarioPorNombre(String nombreUsuario){
        Session session = sessionFactory.openSession();
        String query = "SELECT usuario FROM Usuario usuario WHERE usuario.nombreUsuario = :nombreUsuario";
        Usuario usuario = session.createQuery(query, Usuario.class)
                .setParameter("nombreUsuario", nombreUsuario)
                .getSingleResultOrNull();
        session.close();
        return usuario;
    }

    /**
     * Obtiene un objeto Medico asociado a un Usuario por su ID.
     *
     * @param id El ID del Usuario cuyo Medico se desea obtener.
     * @return El Medico asociado al Usuario con el ID proporcionado o null si no se encuentra.
     */
    public Medico obtenerMedicoPorIdUsuario(Long id) {
        Session session = sessionFactory.openSession();
        String query = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.usuario.id = :id";
        Medico medico = session.createQuery(query, Medico.class)
                .setParameter("id", id)
                .getSingleResultOrNull();
        session.close();
        return medico;
    }

    /**
     * Obtiene un objeto Enfermero asociado a un Usuario por su ID.
     *
     * @param id El ID del Usuario cuyo Enfermero se desea obtener.
     * @return El Enfermero asociado al Usuario con el ID proporcionado o null si no se encuentra.
     */
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

    /**
     * Obtiene un objeto Funcionario por el ID de su Usuario asociado.
     *
     * @param idUsuario El ID del Usuario asociado al Funcionario que se desea obtener.
     * @return El objeto Funcionario asociado al Usuario con el ID proporcionado o null si no se encuentra.
     */
    public Object obtenerFuncionarioPorIdUsuario(Long idUsuario) {
        Session session = sessionFactory.openSession();
        String query = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.usuario.idUsuario = :id";
        Object funcionario = session.createQuery(query).setParameter("id", idUsuario)
                .getSingleResultOrNull();
        session.close();

        return funcionario;
    }
}
