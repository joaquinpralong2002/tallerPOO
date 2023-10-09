package util;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import model.*;
import org.hibernate.tool.schema.Action;

import static java.lang.Boolean.TRUE;


@Getter
public class GlobalSessionFactory {
    private SessionFactory sessionFactory;
    public void InitGlobalSessionFactory(String usuario, String contraseña, String url, String dialecto){
        this.sessionFactory = new Configuration()
                //Clases mapeadas
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
                .setProperty(AvailableSettings.URL, url)
                // Credenciales
                .setProperty(AvailableSettings.USER, usuario)
                .setProperty(AvailableSettings.PASS, contraseña)
                .setProperty(AvailableSettings.DIALECT, dialecto)
                .setProperty(AvailableSettings.DRIVER, "com.mysql.cj.jdbc.Driver")
                // Automatic schema export
                // SQL logging
                .setProperty(AvailableSettings.SHOW_SQL, TRUE.toString())
                .setProperty(AvailableSettings.FORMAT_SQL, TRUE.toString())
                .setProperty(AvailableSettings.HIGHLIGHT_SQL, TRUE.toString())
                // Creación de SessionFactory
                .buildSessionFactory();
    }
}
