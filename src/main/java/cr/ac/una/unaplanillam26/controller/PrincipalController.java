package cr.ac.una.unaplanillam26.controller;

import cr.ac.una.unaplanillam26.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * @author Dominique
 */
public class PrincipalController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private Label lblUserNombre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnEmpleados(ActionEvent event) {
        FlowController.getInstance().goView("EmpleadosView", "Center", null);
    }

    @FXML
    private void onActionBtnTiposPlanilla(ActionEvent event) {
        FlowController.getInstance().goView("TiposPlanillaView", "Center", null);
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        FlowController.getInstance().salir();
    }

    @FXML
    private void onActionBtnCerrarSesion(ActionEvent event) {
    }
}