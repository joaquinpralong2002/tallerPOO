package util;

import lombok.Getter;
import model.Login.AdministradorSistemas;
import model.Login.Usuario;
import model.Login.Rol;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import model.*;

import static java.lang.Boolean.TRUE;

/**
 * Configuración de Hibernate
 */

public class GlobalSessionFactory {

    @Getter
    private static SessionFactory sessionFactory;
    /**
     * Inicializa la SessionFactory global de Hibernate.
     */
    public void InitGlobalSessionFactory(){
        sessionFactory = new Configuration()
                //Clases mapeadas
                .addAnnotatedClass(AdministradorSistemas.class)
                .addAnnotatedClass(Rol.class)
                .addAnnotatedClass(Usuario.class)
                .addAnnotatedClass(Asignacion.class)
                .addAnnotatedClass(BoxAtencion.class)
                .addAnnotatedClass(Enfermero.class)
                .addAnnotatedClass(Especialidad.class)
                .addAnnotatedClass(Funcionario.class)
                .addAnnotatedClass(FuncionarioAdministrativo.class)
                .addAnnotatedClass(Medico.class)
                .addAnnotatedClass(Paciente.class)
                .addAnnotatedClass(Persona.class)
                .addAnnotatedClass(Registro.class)
                .addAnnotatedClass(RegistroEntrada.class)
                .addAnnotatedClass(ResultadoDiagnostico.class)
                .addAnnotatedClass(Sector.class)
                .addAnnotatedClass(Triage.class)
                .addAnnotatedClass(Universidad.class)
                //url
                .setProperty(AvailableSettings.URL, "jdbc:mysql://localhost:3306/tallerdb")
                // Credenciales
                .setProperty(AvailableSettings.USER, "usuario")
                .setProperty(AvailableSettings.PASS, "basededatostallerpoo123")
                .setProperty(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQLDialect")
                .setProperty(AvailableSettings.DRIVER, "com.mysql.cj.jdbc.Driver")
                // Automatic schema export
                // SQL logging
                .setProperty(AvailableSettings.SHOW_SQL, TRUE.toString())
                .setProperty(AvailableSettings.FORMAT_SQL, TRUE.toString())
                .setProperty(AvailableSettings.HIGHLIGHT_SQL, TRUE.toString())
                .setProperty(AvailableSettings.HBM2DDL_AUTO, "update")
                // Creación de SessionFactory
                .buildSessionFactory();
    }

}
