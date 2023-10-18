package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Enum.EstadoCivil;
import model.Paciente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegistroEntradaController {

    
    public TextField txtNomPac;
    public TextField txtApePac;
    public TextField txtDNIPac;
    public TextField txtTelFijoPac;
    public TextField txtTelCelPac;
    public DatePicker dateFechaNaciPac;
    public ComboBox cboxEstCivilPac;
    public TextField txtCorreoPac;
    public TextField txtNumPerContPac;
    public TextField txtDomiPac;

    @FXML
    public void initialize(){
        this.cboxEstCivilPac.getItems().addAll(EstadoCivil.values());
    }
    public void CrearRegistro(ActionEvent actionEvent) {
        String nombrePac = this.txtNomPac.getText();
        String apellidoPac = this.txtApePac.getText();
        LocalDate fechaNaciPac = this.dateFechaNaciPac.getValue();
        String domicilioPac = this.txtDomiPac.getText();
        int dniPac = Integer.parseInt(this.txtDNIPac.getText());
        int telefonoFijoPac = Integer.parseInt(this.txtTelFijoPac.getText());
        long telefonoCelPac = Long.parseLong(this.txtTelCelPac.getText());
        String estadoCivilPac = this.cboxEstCivilPac.getValue().toString();
        String correoPac = this.txtCorreoPac.getText();
        String teleonoPersonaContactoPac = this.txtNumPerContPac.getText();

        List valores = new ArrayList<>();
        valores.add(nombrePac);
        valores.add(apellidoPac);
        valores.add(fechaNaciPac);
        valores.add(domicilioPac);
        valores.add(dniPac);
        valores.add(estadoCivilPac);
        if(this.comprobarValores(valores)){}
        //Paciente paciente = new Paciente(txtNomPac.getText(),txtApePac.getText(),dateFechaNaciPac.getValue(),txtDomiPac.getText(),txtDNIPac.getText());

    }

    public boolean comprobarValores(List valores){
       //implementar para que los valores pasados no esten vacios y que otros sean del tipo indicado
        return false;
    }
}
