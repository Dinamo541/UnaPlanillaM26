package cr.ac.una.unaplanillam26.controller;

import cr.ac.una.unaplanillam26.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dominique
 */
public class LoginController extends Controller implements Initializable {
    
    @FXML
    private MFXButton btnCancelar;
    @FXML
    private ImageView imvFondo;
    @FXML
    private AnchorPane root;
    @FXML
    private MFXTextField txfUsuario;
    @FXML
    private MFXPasswordField pswClave;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imvFondo.fitHeightProperty().bind(root.heightProperty());
        imvFondo.fitWidthProperty().bind(root.widthProperty());
    }
    
    @Override
    public void initialize() {
        txfUsuario.clear();
        pswClave.clear();
    }

    @FXML
    private void onActionBtnCancelar(javafx.event.ActionEvent event) {
        ((Stage)btnCancelar.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnIngresar(ActionEvent event) {
        FlowController.getInstance().goMain();
        onActionBtnCancelar(null);
    }
}