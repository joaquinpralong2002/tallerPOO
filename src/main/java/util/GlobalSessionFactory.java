package util;

import lombok.Getter;
import model.String;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import model.*;
import org.hibernate.tool.schema.Action;

import static java.lang.Boolean.TRUE;


@Getter
public class GlobalSessionFactory {
    private SessionFactory sessionFactory;
    public void InitGlobalSessionFactory(String usuario, String contraseña, String url){
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
                .addAnnotatedClass(String.class)
                .addAnnotatedClass(Registro.class)
                .addAnnotatedClass(RegistroEntrada.class)
                .addAnnotatedClass(ResultadoDiagnostico.class)
                .addAnnotatedClass(Sector.class)
                .addAnnotatedClass(Triage.class)
                .addAnnotatedClass(Universidad.class)
                //url
                .setProperty(AvailableSettings.JAKARTA_JDBC_URL, url)
                // Credenciales
                .setProperty(AvailableSettings.JAKARTA_JDBC_USER, usuario)
                .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, contraseña)
                // Automatic schema export
                .setProperty(AvailableSettings.JAKARTA_HBM2DDL_DATABASE_ACTION,
                        Action.SPEC_ACTION_DROP_AND_CREATE)
                // SQL logging
                .setProperty(AvailableSettings.SHOW_SQL, TRUE.toString())
                .setProperty(AvailableSettings.FORMAT_SQL, TRUE.toString())
                .setProperty(AvailableSettings.HIGHLIGHT_SQL, TRUE.toString())
                // Creación de SessionFactory
                .buildSessionFactory();
    }
}
