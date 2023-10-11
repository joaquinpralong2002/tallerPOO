package datasource;

import model.*;
import org.hibernate.Session;
import util.GlobalSessionFactory;

import jakarta.persistence.*;
import org.hibernate.SessionFactory;

public class FuncionarioDAO {

    private SessionFactory sf;

    public FuncionarioDAO(){
        sf = GlobalSessionFactory.getSessionFactory();
    }

    public Funcionario obtener(Long idFuncionario){
        try(Session session = sf.openSession()){
            Funcionario f = session.get(Funcionario.class, idFuncionario);
            return f;
        }
    }

    public void actualizar(Funcionario f){

    }

}
