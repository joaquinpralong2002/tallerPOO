package datasource;

import datasource.interfaces.GenericoDAO;
import model.Especialidad;
import model.Login.Usuario;
import model.Universidad;
import datasource.interfaces.GenericoDAO;
import org.hibernate.*;
import util.GlobalSessionFactory;

import java.time.LocalDate;
import java.util.List;

public class EspecialidadDAO implements GenericoDAO<Especialidad> {
    private SessionFactory sessionFactory;

    public EspecialidadDAO(){
        this.sessionFactory = GlobalSessionFactory.getSessionFactory();
    }
    //************** BASICOS DE ESPECIALIDAD *******************
    @Override
    public Especialidad obtener(Long id) {
        Session session = sessionFactory.openSession();
        Especialidad especialidad = session.get(Especialidad.class, id);
        session.close();
        return especialidad;
    }

    @Override
    public List<Especialidad> obtenerTodos() {
        Session session = sessionFactory.openSession();
        String query = "SELECT especialidad FROM Especialidad especialidad";
        List<Especialidad> especialidad = session.createQuery(query, Especialidad.class).getResultList();
        session.close();
        return especialidad;
    }
    //**************************************************************************

    public Especialidad obtenerEspecialidadNombreYFecha(String nombre, LocalDate fecha){
        Session session = sessionFactory.openSession();
        String query = "SELECT especialidad FROM Especialidad especialidad WHERE especialidad.fecha = :fecha AND especialidad.nombre = :nombre";
        Especialidad especialidad = session.createQuery(query, Especialidad.class)
                .setParameter("fecha", fecha)
                .setParameter("nombre", nombre)
                .getSingleResult();
        session.close();
        return especialidad;
    }


}
