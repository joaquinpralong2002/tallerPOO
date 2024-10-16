package datasource.interfaces;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.GlobalSessionFactory;

import java.util.List;

public interface GenericoDAO<T> {


    default void agregar(T entity){
        SessionFactory sessionFactory = GlobalSessionFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
    }

    T obtener(Long id);

    List<T> obtenerTodos();

    default void actualizar(T entity){
        SessionFactory sessionFactory = GlobalSessionFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(entity);
        transaction.commit();
        session.close();
    }

    default void borrar(T entity){
        SessionFactory sessionFactory = GlobalSessionFactory.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(entity);
        transaction.commit();
        session.close();
    }

}
