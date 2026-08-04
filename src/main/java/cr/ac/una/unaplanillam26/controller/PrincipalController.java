package cr.ac.una.unaplanillam26.controller;

import cr.ac.una.unaplanillam26.util.AppContext;
import cr.ac.una.unaplanillam26.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Dominique
 */
public class PrincipalController extends Controller implements Initializable {

    // ----------------- FXML ----------------
    @FXML
    private BorderPane root;
    @FXML
    private Label lblUserNombre;

    private String currentView = "EmpleadosView";

    // ----------------- Métodos de Inicialización ----------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FXMLLoader loader = FlowController.getInstance().getLoader("EmpleadosView");
        Controller controller = loader.getController();
        controller.initialize();
        
        VBox vBox = (VBox) root.getCenter();
        vBox.getChildren().clear();
        vBox.getChildren().add(loader.getRoot());

        initialize();
    }
    
    @Override
    public void initialize() {
        lblUserNombre.setText((String) AppContext.getInstance().get("UserNombre"));
    }

    // ---------------- Métodos de Acción ----------------
    @FXML
    private void onActionBtnEmpleados(ActionEvent event) {
        if (currentView.equals("EmpleadosView")) {
            return;
        }
        currentView = "EmpleadosView";
        FlowController.getInstance().goView("EmpleadosView", "Center", null);
    }

    @FXML
    private void onActionBtnTiposPlanilla(ActionEvent event) {
        if (currentView.equals("TiposPlanillaView")) {
            return;
        }
        currentView = "TiposPlanillaView";
        FlowController.getInstance().goView("TiposPlanillaView", "Center", null);
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        FlowController.getInstance().salir();
    }

    @FXML
    private void onActionBtnCerrarSesion(ActionEvent event) {
        AppContext.getInstance().delete("Empleado");
        AppContext.getInstance().delete("UserNombre");
        FlowController.getInstance().goViewInWindow("LoginView");
        ((Stage) root.getScene().getWindow()).close();
    }

}