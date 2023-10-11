package datasource;
import model.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.GlobalSessionFactory;

public class PacienteDAO {
    private SessionFactory sessionFactory;

    public PacienteDAO(){
        this.sessionFactory = GlobalSessionFactory.getSessionFactory();
    }
    
    public void agregarPaciente(Paciente paciente){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(paciente);
        transaction.commit();
        session.close();
    }

    public Paciente obtenerPaciente(Long id) {
        Session session = sessionFactory.openSession();
        Paciente paciente = session.get(Paciente.class, id);
        session.close();
        return paciente;
    }


}
