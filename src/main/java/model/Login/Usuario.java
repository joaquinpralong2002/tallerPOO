package model.Login;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Funcionario;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Clase que representa los usuarios de la aplicación.
 */
@Getter
@Setter
@NoArgsConstructor

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idUsuario;

    private String nombreUsuario;
    private String contrasenia;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(name = "Usuarios_roles",
            joinColumns = @JoinColumn(name = "idUsuario"),
            inverseJoinColumns = @JoinColumn(name = "idRol"))
    private List<Rol> roles = new LinkedList<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;


    public Usuario(String nombreUsuario, String contrasenia) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
    }

    public Usuario(String nombreUsuario, String contrasenia, Funcionario funcionario, List<Rol> roles) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.funcionario = funcionario;
    }

    public Usuario(String nombreUsuario, String contrasenia, List<Rol> roles) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
    }

    public Usuario(String nombreUsuario, String contrasenia, Funcionario funcionario) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.funcionario = funcionario;
    }


    /**
     * Establece la contraseña del usuario
     * @param contraseña La nueva contraseña del usuario.
     * @return True si la contraseña se estableció correctamente, false en caso contrario.
     */
    public boolean setContrasenia(String contraseña) {
        if (contraseña.length() >= 8 &&
                contieneLetraMayuscula(contraseña) &&
                contieneLetraMinuscula(contraseña) &&
                contieneSimbolo(contraseña)) {
            this.contrasenia = contraseña;
            return true;
        } else {
            return false;
        }
    }
    /**
     * Controla que la contraseña pasada por parámetro contenga mayúsculas
     * @param contraseña
     * @return
     */
    private boolean contieneLetraMayuscula(String contraseña) {
        for (char c : contraseña.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Controla que la contraseña pasada por parámetro contenga minúsculas
     * @param contraseña
     * @return
     */
    private boolean contieneLetraMinuscula(String contraseña) {
        for (char c : contraseña.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Controla que la contraseña pasada por parámetro contenga símbolos
     * @param contraseña
     * @return
     */
    private boolean contieneSimbolo(String contraseña) {
        String simbolos = "!@#$%^&*()_+-=[]{}|;:,.<>?";
        for (char c : contraseña.toCharArray()) {
            if (simbolos.contains(String.valueOf(c))) {
                return true;
            }
        }
        return false;
    }


    /**
     * Establece el nombre de usuario del médico.
     * @param nombreUsuario El nuevo nombre de usuario del médico.
     * @return True si el nombre de usuario se estableció correctamente, false en caso contrario.
     */
    public boolean setNombreUsuario(String nombreUsuario) {
        if(nombreUsuario.length() >= 10){
            this.nombreUsuario = nombreUsuario;
            return true;
        } else return false;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(nombreUsuario, usuario.nombreUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreUsuario);
    }

    void setRol(Rol l){
        if(!roles.contains(l)) roles.add(l);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombreUsuario='" + nombreUsuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }
}
