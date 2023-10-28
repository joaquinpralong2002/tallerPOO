package model.Login;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.Funcionario;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
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
    @Column(unique = true)
    private String nombreUsuario;
    private String contrasenia;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(name = "Usuarios_roles",
            joinColumns = @JoinColumn(name = "idUsuario"),
            inverseJoinColumns = @JoinColumn(name = "idRol"))
    private List<Rol> roles = new LinkedList<>();

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
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
        this.roles = roles;
    }

    public Usuario(String nombreUsuario, String contrasenia, Funcionario funcionario) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.funcionario = funcionario;
    }

    public List<String> getNombreRoles(){
        List<String> listaNombres = new ArrayList<>();
        for(Rol rol: roles){
            listaNombres.add(rol.getNombre());
        }
        return listaNombres;
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
