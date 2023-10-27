package controllers.Singletons;

import lombok.Setter;
import lombok.Getter;
import model.Enfermero;
import model.Login.AdministradorSistemas;
import model.Login.Rol;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SingletonAdministradorSistema {
    private static SingletonAdministradorSistema instance;
    private AdministradorSistemas administradorSistemas;
    private List<Rol> roles;

    private SingletonAdministradorSistema() {
        administradorSistemas = new AdministradorSistemas();
        roles = new ArrayList<>();
    }

    public static SingletonAdministradorSistema getInstance() {
        if (instance == null) {
            instance = new SingletonAdministradorSistema();
        }
        return instance;
    }
}
