package datasource;

import datasource.interfaces.GenericoDAO;
import model.FuncionarioAdministrativo;
import model.Paciente;
import model.RegistroEntrada;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.GlobalSessionFactory;

import java.time.LocalDate;
import java.util.List;

public class RegistroEntradaDAO implements GenericoDAO<RegistroEntrada> {
        private SessionFactory sessionFactory;

        public RegistroEntradaDAO(){
                this.sessionFactory = GlobalSessionFactory.getSessionFactory();
        }

        @Override
        public RegistroEntrada obtener(Long id) {
                Session session = sessionFactory.openSession();
                RegistroEntrada registroEntrada = session.get(RegistroEntrada.class, id);
                session.close();
                return registroEntrada;
        }

        @Override
        public List<RegistroEntrada> obtenerTodos() {
                Session session = sessionFactory.openSession();
                String query = "SELECT registroEntrada FROM RegistroEntrada registroEntrada";
                List<RegistroEntrada> registrosEntrada = session.createQuery(query, RegistroEntrada.class).getResultList();
                session.close();
                return registrosEntrada;
        }

        public List<RegistroEntrada> obtenerPorFecha(LocalDate fecha){
                Session session = sessionFactory.openSession();
                String query = "SELECT registroEntrada FROM RegistroEntrada registroEntrada WHERE registroEntrada.fecha = :fecha";
                List<RegistroEntrada> registrosEntrada = session.createQuery(query,RegistroEntrada.class)
                        .setParameter("fecha", fecha).getResultList();
                session.close();
                return registrosEntrada;
        }

}
