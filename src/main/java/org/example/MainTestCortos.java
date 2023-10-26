package org.example;

import datasource.UsuarioDAO;
import model.Enfermero;
import model.Medico;
import util.GlobalSessionFactory;

public class MainTestCortos {

    public static void main(String[] args) {

        GlobalSessionFactory init = new GlobalSessionFactory();
        init.InitGlobalSessionFactory();

        UsuarioDAO dao = new UsuarioDAO();

        var a = dao.obtenerFuncionarioPorIdUsuario(7L);


        if(a.getClass() == Enfermero.class) {
            System.out.println("si");
            Enfermero enfermero = (Enfermero) a;
        }

        if(a.getClass() != Medico.class) System.out.println("no");
        System.out.println(a);


    }
}
