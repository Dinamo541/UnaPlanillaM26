package cr.ac.una.unaplanillam26.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
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
public class LoginController implements Initializable {
    
    @FXML
    private MFXButton btnCancelar;
    
    @FXML
    private ImageView imvFondo;

    @FXML
    private AnchorPane root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imvFondo.fitHeightProperty().bind(root.heightProperty());
        imvFondo.fitWidthProperty().bind(root.widthProperty());
    }

    @FXML
    private void onActionBtnCancelar(javafx.event.ActionEvent event) {
        ((Stage)btnCancelar.getScene().getWindow()).close();
    }
    
}
