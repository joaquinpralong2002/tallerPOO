package controllers.Singletons;

import lombok.Getter;
import lombok.Setter;
import model.Login.Usuario;

@Setter
@Getter
public class SingletonUsuario {
    private static SingletonUsuario instance;
    private Usuario usuario;

    public SingletonUsuario() {
        usuario = new Usuario();
    }

    public static SingletonUsuario getInstance(){
        if(instance == null){
            instance = new SingletonUsuario();
        }
        return  instance;
    }
}
