package controllers.Administracion;

import controllers.FuncionarioProController;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import lombok.Setter;

public class EstadisticasController {

    @FXML
    private AnchorPane paneFondo;

    @Setter
    private FuncionarioProController controllerPrincipal;

    @FXML
    public void initialize(){
        controllerPrincipal = FuncionarioProController.getControladorPrimario();
    }

}
