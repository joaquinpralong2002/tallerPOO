package datasource;

import model.RegistroEntrada;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.GlobalSessionFactory;

public class RegistroEntradaDAO {
        private SessionFactory sessionFactory;

        public RegistroEntradaDAO(){
                this.sessionFactory = GlobalSessionFactory.getSessionFactory();
        }

        public void agregar(RegistroEntrada registroEntrada) {
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();
                session.save(registroEntrada);
                transaction.commit();
                session.close();
        }
        public RegistroEntrada obtener(Long id){
                Session session = sessionFactory.openSession();
                RegistroEntrada registroEntrada = session.get(RegistroEntrada.class, id);
                session.close();
                return registroEntrada;
        }

        public RegistroEntrada obtenerPorFecha(Long id){
                Session session = sessionFactory.openSession();
                RegistroEntrada registroEntrada = session.get(RegistroEntrada.class, id);
                session.close();
                return registroEntrada;
        }


        public void actualizar(RegistroEntrada registroEntrada) {
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();
                session.update(registroEntrada);
                transaction.commit();
                session.close();
        }

        public void borrar(RegistroEntrada registroEntrada) {
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();
                session.delete(registroEntrada);
                transaction.commit();
                session.close();
        }

        







}
