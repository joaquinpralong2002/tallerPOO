package controllers.Singletons;

import lombok.Getter;
import lombok.Setter;
import model.Funcionario;
import model.Login.Rol;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SingletonFuncionario {
    private static SingletonFuncionario instance;
    private Funcionario funcionario;
    private List<Rol> roles;

    private SingletonFuncionario() {
        funcionario = new Funcionario();
        roles = new ArrayList<>();
    }

    public static SingletonFuncionario getInstance() {
        if (instance == null) {
            instance = new SingletonFuncionario();
        }
        return instance;
    }
}
