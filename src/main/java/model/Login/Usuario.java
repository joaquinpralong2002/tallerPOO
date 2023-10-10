package model.Login;

import jakarta.persistence.*;
import lombok.*;
import model.Funcionario;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String nombreUsuario;
    private String contrasenia;

    @OneToOne(mappedBy = "usuario")
    private Funcionario funcionario;

    @ManyToMany
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "idUsuario"),
            inverseJoinColumns = @JoinColumn(name = "idRol")
    )
    private List<Rol> roles;

    public Usuario(String nombreUsuario, String contrasenia) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.roles = new ArrayList<>();
    }

    public Usuario(String nombreUsuario, String contrasenia, Funcionario funcionario, List<Rol> roles) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.funcionario = funcionario;
        this.roles = roles;
    }

    public Usuario(String nombreUsuario, String contrasenia, List<Rol> roles) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.roles = roles;
    }

    public Usuario(String nombreUsuario, String contrasenia, Funcionario funcionario) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.funcionario = funcionario;
        this.roles = new ArrayList<>();
    }


    /**
     * Método que controla que se cumplan ciertos criterios para la asignación de la contraseña a un usuario.
     * Debe tener un largo de mínimo de 8 carácteres, contener minúsculas, mayúsculas y símbolos.
     * @param contraseña
     * @return
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
     * Método que controla que la contraseña pasada por parámetro tenga mayúsculas
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
     * Método que controla que la contraseña pasada por parámetro tenga minúsculas
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
     * Método que controla que la contraseña pasada por parámetro tenga símbolos
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
     * Método que controla que se cumplan ciertos criterios para la asignación del username a un usuario.
     * Debe tener un largo de mínimo de 10 carácteres.
     * (CUANDO SE IMPLEMENTE LA BASE DE DATOS, CONTROLAR QUE NO SEA REPETIDO EL NOMBRE)
     * @param nombreUsuario
     * @return
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
