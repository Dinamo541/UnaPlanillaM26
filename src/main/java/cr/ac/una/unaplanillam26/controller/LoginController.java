package cr.ac.una.unaplanillam26.controller;

import cr.ac.una.unaplanillam26.model.Empleado;
import cr.ac.una.unaplanillam26.model.EmpleadoDto;
import cr.ac.una.unaplanillam26.service.EmpleadoService;
import cr.ac.una.unaplanillam26.util.AppContext;
import cr.ac.una.unaplanillam26.util.EntityManagerHelper;
import cr.ac.una.unaplanillam26.util.FlowController;
import cr.ac.una.unaplanillam26.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    private Boolean consultarUsuario() {
        try{
            String nombreUsuario = txfUsuario.getText();
            String clave = pswClave.getText();

            if (nombreUsuario == null || nombreUsuario.isBlank() || clave == null || clave.isBlank()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar Empleado", getStage(), "El nombre de usuario o la contraseña no pueden estar vacíos.");
                return false;
            }

            EntityManager em = EntityManagerHelper.getManager();
            TypedQuery<Empleado> qryEmpleado = em.createQuery("SELECT e FROM Empleado e WHERE e.nombre = :nombre AND e.clave = :clave", Empleado.class);
            qryEmpleado.setParameter("nombre", nombreUsuario);
            qryEmpleado.setParameter("clave", clave);

            if (qryEmpleado.getResultList().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar Empleado", getStage(), "No existe un empleado con el nombre de usuario y contraseña ingresados.");
                return false;
            }

            AppContext.getInstance().set("Empleado", new EmpleadoDto(qryEmpleado.getSingleResult()));
            return true;
        } catch (NonUniqueResultException ex) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar Empleado", getStage(), "Ocurrió un error al consultar el empleado: se encontraron múltiples resultados.");
        } catch (Exception ex) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar Empleado", getStage(), "Error consultando el empleado: " + ex.getMessage());
        }
        return false;
    }

    @FXML
    private void onActionBtnCancelar(javafx.event.ActionEvent event) {
        ((Stage)btnCancelar.getScene().getWindow()).close();
    }

    @FXML
    private void onActionBtnIngresar(ActionEvent event) {
        FlowController.getInstance().goMain();
        onActionBtnCancelar(null);
        return;


        /*if (consultarUsuario()) {
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Login", getStage(), "Bienvenido " + AppContext.getInstance().get("Empleado").toString());

            PauseTransition pauseOne = new PauseTransition(Duration.millis(2000));
            pauseOne.setOnFinished(e -> {
                FlowController.getInstance().goMain();
                onActionBtnCancelar(null);
            });
            pauseOne.play();
        }*/
    }

    @FXML
    private void onKeyPressedTxfUsuario(javafx.scene.input.KeyEvent event) {
        if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
            onActionBtnIngresar(null);
        }
    }

    @FXML
    private void onKeyPressedPswClave(javafx.scene.input.KeyEvent event) {
        if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
            onActionBtnIngresar(null);
        }
    }

}