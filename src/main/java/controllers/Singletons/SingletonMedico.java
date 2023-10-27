package controllers.Singletons;

import lombok.Getter;
import lombok.Setter;
import model.Login.Rol;
import model.Medico;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class SingletonMedico {
    private static SingletonMedico instance;
    private Medico medico;
    private List<Rol> roles;

    private SingletonMedico() {
        medico = new Medico();
        roles = new ArrayList<>();
    }

    public static SingletonMedico getInstance() {
        if (instance == null) {
            instance = new SingletonMedico();
        }
        return instance;
    }
}
