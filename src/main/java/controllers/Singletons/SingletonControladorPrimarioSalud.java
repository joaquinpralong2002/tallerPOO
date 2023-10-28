package controllers.Singletons;

import controllers.SaludController;
import lombok.Getter;
import lombok.Setter;
import model.Enfermero;
import model.Login.Rol;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SingletonControladorPrimarioSalud {
    private static SingletonControladorPrimarioSalud instance;
    private SaludController controller;

    private SingletonControladorPrimarioSalud() {
        controller = new SaludController();
    }

    public static SingletonControladorPrimarioSalud getInstance() {
        if (instance == null) {
            instance = new SingletonControladorPrimarioSalud();
        }
        return instance;
    }
}
