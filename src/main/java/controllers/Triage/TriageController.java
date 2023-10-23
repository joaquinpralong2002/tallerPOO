package controllers.Triage;

import controllers.MedicoController;
import datasource.PacienteDAO;
import datasource.RegistroEntradaDAO;
import datasource.TriageDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Setter;
import model.Enum.ColorTriage;
import model.EnumeracionesVariablesTriage.*;
import model.Medico;
import model.RegistroEntrada;
import model.Triage;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

public class TriageController {
    private ColorTriage colorTriageAsignado = ColorTriage.Ninguno;
    private RegistroEntrada registroEntrada;
    private Medico medico;
    @Setter
    private DatosTriage datosTriage;
    @FXML
    private ComboBox<Conciencia> concienciaComboBox;
    @FXML
    private ComboBox<DolorAbdominal> dolorAbdominalComboBox;
    @FXML
    private ComboBox<DolorPecho> dolorPechoComboBox;
    @FXML
    private ComboBox<EstadoMental> estadoMentalComboBox;
    @FXML
    private ComboBox<LecionesGraves> lesionGraveComboBox;
    @FXML
    private ComboBox<LesionLeve> lesionLeveComboBox;
    @FXML
    private ComboBox<Respiracion> respiracionComboBox;
    @FXML
    private ComboBox<Sangrado> sangradoComboBox;
    @FXML
    private ComboBox<SignoShock> signoShockComboBox;
    @FXML
    private ComboBox<Vomitos> vomitosComboBox;
    @FXML
    private TextField pulsoTextField;
    @FXML
    private TextField temperaturaTextField;
    @FXML
    private Label colorRecomendadoLabel;


    /**
     * Inicializa los componentes de la interfaz de usuario asignando los valores posibles
     * a las listas desplegables y estableciendo el texto en la etiqueta de color recomendado.
     */
    public void initialize() {
        concienciaComboBox.getItems().addAll(Conciencia.values());
        dolorAbdominalComboBox.getItems().addAll(DolorAbdominal.values());
        dolorPechoComboBox.getItems().addAll(DolorPecho.values());
        estadoMentalComboBox.getItems().addAll(EstadoMental.values());
        lesionGraveComboBox.getItems().addAll(LecionesGraves.values());
        lesionLeveComboBox.getItems().addAll(LesionLeve.values());
        respiracionComboBox.getItems().addAll(Respiracion.values());
        sangradoComboBox.getItems().addAll(Sangrado.values());
        signoShockComboBox.getItems().addAll(SignoShock.values());
        vomitosComboBox.getItems().addAll(Vomitos.values());
        colorRecomendadoLabel.setText(ColorTriage.Ninguno.toString());
    }

    /**
     * Recibe el paciente y el registro de entrada de la escena de médico
     * @param registroEntrada
     * @param medico
     */
    @FXML
    public void recibirDatos(RegistroEntrada registroEntrada, Medico medico) {
        this.registroEntrada = registroEntrada;
        this.medico = medico;
    }


    /**
     * Maneja la acción del botón "Atrás" en la interfaz de usuario.
     * Este método carga la vista inicial del médico y cambia la escena actual
     * para volver a la pantalla principal del médico.
     * @param event El evento de acción que desencadenó esta función.
     * @throws Exception Si ocurre un error al cargar la vista o cambiar la escena.
     */
    public void handleAtrasButtonAction(ActionEvent event) throws Exception {
        // Obtiene la vista inicial
        Parent root = FXMLLoader.load(getClass().getResource("/views/MedicoViews/Medico.fxml"));
        // Cambia a la escena
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Maneja la acción del botón "Calcular Color" en la interfaz de triage.
     * Este método calcula el color de triage recomendado basado en una serie de datos
     * proporcionados por el usuario, incluyendo la respiración, el pulso cardiaco,
     * el estado mental, la conciencia, el dolor en el pecho, las lesiones graves,
     * la edad, la fiebre, la temperatura, los vómitos, el dolor abdominal, el signo de shock,
     * las lesiones leves y el sangrado. Luego, muestra el color de triaje recomendado
     * en la etiqueta correspondiente y almacena los datos en un objeto DatosTriage.
     *
     * @param event El evento de acción que desencadenó esta función.
     * @throws Exception Si ocurre un error al calcular el triaje o al actualizar la interfaz.
     */
    public void handleCalcularColorButtonAction(ActionEvent event) throws Exception {
        Respiracion respiracion = respiracionComboBox.getSelectionModel().getSelectedItem();
        int pulsoCardiaco = Integer.parseInt(pulsoTextField.getText());
        Pulso pulso = calcularPulso(pulsoCardiaco);
        EstadoMental estadoMental = estadoMentalComboBox.getSelectionModel().getSelectedItem();
        Conciencia conciencia = concienciaComboBox.getSelectionModel().getSelectedItem();
        DolorPecho dolorPecho = dolorPechoComboBox.getSelectionModel().getSelectedItem();
        LecionesGraves lecionesGraves = lesionGraveComboBox.getSelectionModel().getSelectedItem();
        Edad edad = calcularEdadEnum();
        int edadAños = calcularEdad();
        float temperatura = Float.parseFloat(temperaturaTextField.getText());
        Fiebre fiebre = calcularFiebre(temperatura);
        Vomitos vomitos = vomitosComboBox.getSelectionModel().getSelectedItem();
        DolorAbdominal dolorAbdominal = dolorAbdominalComboBox.getSelectionModel().getSelectedItem();
        SignoShock signoShock = signoShockComboBox.getSelectionModel().getSelectedItem();
        LesionLeve lesionLeve = lesionLeveComboBox.getSelectionModel().getSelectedItem();
        Sangrado sangrado = sangradoComboBox.getSelectionModel().getSelectedItem();


        ColorTriage colorRecomendado = Triage.calcularColorTriageRecomendado(respiracion, pulso, pulsoCardiaco, estadoMental, conciencia, dolorPecho, lecionesGraves,
                edad, edadAños, fiebre, temperatura, vomitos, dolorAbdominal, signoShock, lesionLeve, sangrado);
        this.datosTriage = new DatosTriage(respiracion, pulsoCardiaco, pulso, estadoMental, conciencia, dolorPecho, lecionesGraves,
                edad, edadAños, temperatura, fiebre, vomitos, dolorAbdominal, signoShock, lesionLeve, sangrado, colorRecomendado, this.registroEntrada, this.medico);
        colorRecomendadoLabel.setText(colorRecomendado.toString());
        this.colorTriageAsignado = colorRecomendado;
    }

    /**
     * Maneja la acción del botón "Modificar Color" en la interfaz de triaje médico.
     *
     * Este método carga la vista de modificación de triaje y permite al médico
     * modificar los datos de triaje para un paciente en función del color de triaje
     * previamente asignado. Si el paciente no tiene ningún color asignado, se muestra
     * un mensaje de error.
     *
     * @param event El evento de acción que desencadenó esta función.
     * @throws Exception Si ocurre un error al cargar la vista de modificación o al mostrar
     * el mensaje de error.
     */
    public void handleModificarColorButtonAction(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MedicoViews/Triage/ModificarTriage.fxml"));
        Parent root = loader.load();
        ModificarTriageController controller = loader.getController();
        if(this.colorTriageAsignado != ColorTriage.Ninguno){
            controller.setDatosTriage(this.datosTriage);
            // Cambia a la nueva escena
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("El paciente no tiene ningún color asignado.");
            alert.showAndWait();
            return;
        }
    }

    /**
     * Maneja la acción del botón "Confirmar Triage" en la interfaz de triaje médico.
     *
     * Este método permite al médico confirmar y registrar el triaje realizado para un paciente,
     * basándose en los datos previamente configurados. Realiza las siguientes acciones:
     * 1. Pregunta al médico si desea confirmar el triaje mediante un cuadro de diálogo.
     * 2. Si se confirma el triaje, se obtienen los datos de triaje previamente configurados.
     * 3. El médico realiza el triaje y actualiza los datos del paciente y el registro de entrada.
     * 4. Si el color del triaje ha cambiado, se registra la modificación.
     * 5. Se actualiza la información en la base de datos.
     * 6. La interfaz se restablece a la vista inicial del médico con una tabla actualizada de triajes.
     *
     * @param event El evento de acción que desencadenó esta función.
     * @throws Exception Si ocurre un error al confirmar el triaje o actualizar la interfaz.
     */
    public void handleConfirmarTriageButtonAction(ActionEvent event) throws Exception {
        // Cierra la sesión del médico

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Realizar triage");
        alert.setContentText("¿Confirmar el triage?");
        Optional<ButtonType> resultado = alert.showAndWait();

        if(resultado.get() == ButtonType.OK){
            Respiracion respiracion = this.datosTriage.getRespiracion();
            int pulsoCardiaco = this.datosTriage.getPulsoCardiaco();
            Pulso pulso = this.datosTriage.getPulso();
            EstadoMental estadoMental = this.datosTriage.getEstadoMental();
            Conciencia conciencia = this.datosTriage.getConciencia();
            DolorPecho dolorPecho = this.datosTriage.getDolorPecho();
            LecionesGraves lecionesGraves = this.datosTriage.getLecionesGraves();
            Edad edad = this.datosTriage.getEdad();
            System.out.println(edad);
            int edadAños = this.datosTriage.getEdadAños();
            float temperatura = this.datosTriage.getTemperatura();
            Fiebre fiebre = this.datosTriage.getFiebre();
            Vomitos vomitos = this.datosTriage.getVomitos();
            DolorAbdominal dolorAbdominal = this.datosTriage.getDolorAbdominal();
            SignoShock signoShock = this.datosTriage.getSignoShock();
            LesionLeve lesionLeve = this.datosTriage.getLesionLeve();
            Sangrado sangrado = this.datosTriage.getSangrado();

            //El médico realiza el triage
            this.medico.realizarTriage(respiracion, pulso, pulsoCardiaco,  estadoMental, conciencia, dolorPecho, lecionesGraves,
                    edad, edadAños, fiebre, temperatura, vomitos, dolorAbdominal, signoShock, lesionLeve, sangrado);

            Triage triageMedico = this.medico.getTriagesRealizados().get(medico.getTriagesRealizados().size() - 1);

            //Se realizan cambios si se ha modificado el color del triage
            if(this.datosTriage.getColorTriageCambiado() != null){
                this.medico.cambiarColorTriage(triageMedico,this.datosTriage.getColorTriageCambiado(), this.datosTriage.getMotivoCambioTriage());
            }

            TriageDAO triageDAO = new TriageDAO();
            triageMedico.setMedico(medico);
            triageDAO.actualizar(triageMedico);

            registroEntrada.setTriage(triageMedico);

            registroEntrada.getPaciente().setTriagiado(true);
            PacienteDAO pacienteDAO = new PacienteDAO();
            pacienteDAO.actualizar(registroEntrada.getPaciente());

            //Una vez realizado el triage, se vuelve a la escena inicial de médico
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/MedicoViews/Medico.fxml"));
            Parent root = loader.load();
            MedicoController medicoController = loader.getController();
            medicoController.iniciarTablaDesdeTriage();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Establece los datos de triaje para su visualización y posible modificación en la interfaz.
     * Luego, restaura el estado de la interfaz con estos datos, lo que implica la actualización
     * de los campos y elementos visuales según los datos proporcionados.
     *
     * @param datosTriage Los datos de triaje que se deben establecer en la interfaz.
     */
    public void setDatosTriage(DatosTriage datosTriage){
        this.datosTriage = datosTriage;
        restaurarEstado();
    }

    /**
     * Restaura el estado de la interfaz con los datos de triaje proporcionados.
     * Esto implica la actualización de los campos y elementos visuales con los valores
     * contenidos en los datos de triaje dados.
     */
    private void restaurarEstado(){
        this.colorTriageAsignado = datosTriage.getColorTriageAsignado();
        this.registroEntrada = datosTriage.getRegistroEntrada();
        this.medico = datosTriage.getMedico();
        this.concienciaComboBox.setValue(datosTriage.getConciencia());
        this.dolorAbdominalComboBox.setValue(datosTriage.getDolorAbdominal());
        this.dolorPechoComboBox.setValue(datosTriage.getDolorPecho());
        this.estadoMentalComboBox.setValue(datosTriage.getEstadoMental());
        this.lesionGraveComboBox.setValue(datosTriage.getLecionesGraves());
        this.lesionLeveComboBox.setValue(datosTriage.getLesionLeve());
        this.respiracionComboBox.setValue(datosTriage.getRespiracion());
        this.sangradoComboBox.setValue(datosTriage.getSangrado());
        this.signoShockComboBox.setValue(datosTriage.getSignoShock());
        this.vomitosComboBox.setValue(datosTriage.getVomitos());
        this.pulsoTextField.setText(Integer.toString(datosTriage.getPulsoCardiaco()));
        this.temperaturaTextField.setText(Float.toString(datosTriage.getTemperatura()));
        if(datosTriage.getColorTriageCambiado() != null){
            this.colorRecomendadoLabel.setText(datosTriage.getColorTriageCambiado().toString());
        } else {
            this.colorRecomendadoLabel.setText(datosTriage.getColorTriageAsignado().toString());
        }
    }

    /**
     * Calcula el estado del pulso del paciente basado en su edad y el valor del pulso proporcionado.
     *
     * Este método determina el estado del pulso del paciente, que puede ser Normal o Anormal, en función
     * de la edad del paciente y el valor del pulso registrado. Se establecen diferentes rangos de
     * referencia para el pulso en función de la edad del paciente.
     *
     * @param valorPulso El valor del pulso del paciente que se va a evaluar.
     * @return El estado del pulso del paciente (Normal o Anormal) según los rangos de referencia.
     */
    private Pulso calcularPulso(int valorPulso) {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaNacimiento = this.registroEntrada.getPaciente().getFechaNacimiento();
        Period periodo = Period.between(fechaActual, fechaNacimiento);
        int mesesEdad = periodo.getMonths();
        Pulso pulso = Pulso.Normal;

        if (mesesEdad <= 1) {
            if (valorPulso < 70 || valorPulso > 190) pulso = Pulso.Anormal;
        } else if (mesesEdad > 1 && mesesEdad <= 12) {
            if (valorPulso < 80 || valorPulso > 160) pulso = Pulso.Anormal;
        } else if (mesesEdad > 12 && mesesEdad <= 36) {
            if (valorPulso < 98 || valorPulso > 140) pulso = Pulso.Anormal;
        } else if (mesesEdad > 36 && mesesEdad <= 60) {
            if (valorPulso < 98 || valorPulso > 140) pulso = Pulso.Anormal;
        } else if (mesesEdad > 60 && mesesEdad <= 144) {
            if (valorPulso < 98 || valorPulso > 140) pulso = Pulso.Anormal;
        } else if (valorPulso < 60 || valorPulso > 100) pulso = Pulso.Anormal;
        return pulso;
    }

    /**
     * Calcula la categoría de edad del paciente basada en su fecha de nacimiento.
     *
     * Este método determina la categoría de edad del paciente, que puede ser Adulto o Niño/Anciano, en función
     * de la diferencia en años entre la fecha de nacimiento y la fecha actual.
     *
     * @return La categoría de edad del paciente (Adulto o Niño/Anciano) según la diferencia en años.
     */
    private Edad calcularEdadEnum(){
        int añosEdad = calcularEdad();
        Edad edad = Edad.Adulto;
        if(añosEdad <= 12 || añosEdad >= 60) edad = Edad.NinioAnciano;
        return edad;
    }

    /**
     * Calcula la edad del paciente en años, basada en su fecha de nacimiento.
     *
     * Este método calcula la edad del paciente en años completos, utilizando la diferencia entre
     * la fecha de nacimiento y la fecha actual.
     *
     * @return La edad del paciente en años.
     */
    private int calcularEdad(){
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaNacimiento = this.registroEntrada.getPaciente().getFechaNacimiento();
        Period periodo = Period.between(fechaNacimiento, fechaActual);
        int añosEdad = periodo.getYears();
        return añosEdad;
    }

    /**
     * Calcula el estado de fiebre del paciente basado en su temperatura corporal y edad.
     *
     * Este método determina el estado de fiebre del paciente, que puede ser SinFiebre, Moderada o Alta,
     * en función de la temperatura corporal y la edad del paciente. Se establecen diferentes rangos de
     * temperatura y categorías de edad para determinar el estado de fiebre.
     *
     * @param temperatura La temperatura corporal del paciente que se va a evaluar.
     * @return El estado de fiebre del paciente (SinFiebre, Moderada o Alta) según los criterios especificados.
     */
    private Fiebre calcularFiebre(float temperatura){
        Fiebre fiebre = Fiebre.SinFiebre;
        int edad = calcularEdad();
        if(edad >= 0 && edad <= 12){
            if(temperatura > 37.2 && temperatura < 39.4) fiebre = Fiebre.Moderada;
            else if(temperatura >= 39.4) fiebre = Fiebre.Alta;
        } else if(edad > 12 && edad <= 65){
            if(temperatura > 37.2 && temperatura < 39.4) fiebre = Fiebre.Moderada;
            else if(temperatura >= 39.4) fiebre = Fiebre.Alta;
        } else {
            if(temperatura > 36.2 && temperatura < 39.4) fiebre = Fiebre.Moderada;
            else if(temperatura >= 39.4) fiebre = Fiebre.Alta;
        }
        return fiebre;
    }
}
