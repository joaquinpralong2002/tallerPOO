package datasource;

import datasource.interfaces.GenericoDAO;
import model.Login.*;
import model.Paciente;
import org.hibernate.*;
import util.GlobalSessionFactory;

import java.util.List;

public class UsuarioDAO implements GenericoDAO<Usuario> {
    private SessionFactory sessionFactory;

    public UsuarioDAO(){
        this.sessionFactory = GlobalSessionFactory.getSessionFactory();
    }

    //************** BASICOS DE USUARIO*******************
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
    //**************************************************************************

    public Usuario obtenerUsuarioPorNombre(String nombreUsuario){
        Session session = sessionFactory.openSession();
        String query = "SELECT usuario FROM Usuario usuario WHERE usuario.nombreUsuario = :nombreUsuario";
        Usuario usuario = session.createQuery(query, Usuario.class)
                .setParameter("nombreUsuario", nombreUsuario)
                .getSingleResult();
        session.close();
        return usuario;
    }

}
