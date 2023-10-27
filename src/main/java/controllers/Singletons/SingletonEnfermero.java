package controllers.Singletons;

import lombok.Getter;
import lombok.Setter;
import model.Enfermero;
import model.Login.Rol;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SingletonEnfermero {
    private static SingletonEnfermero instance;
    private Enfermero enfermero;
    private List<Rol> roles;

    private SingletonEnfermero() {
        enfermero = new Enfermero();
        roles = new ArrayList<>();
    }

    public static SingletonEnfermero getInstance() {
        if (instance == null) {
            instance = new SingletonEnfermero();
        }
        return instance;
    }
}
