package cr.ac.una.unaplanillam26.controller;

import cr.ac.una.unaplanillam26.model.TiposPlanillaDto;
import cr.ac.una.unaplanillam26.util.Formato;
import cr.ac.una.unaplanillam26.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Dominique
 */
@SuppressWarnings("unused")
public class TiposPlanillaController extends Controller implements Initializable {

    // Variables
    @FXML
    private VBox raiz;
    @FXML
    private MFXButton btnNuevo;
    @FXML
    private MFXButton btnBuscar;
    @FXML
    private MFXButton btnEliminar;
    @FXML
    private MFXButton btnGuardar;
    @FXML
    private MFXTextField txtId;
    @FXML
    private MFXTextField txtNombre;
    @FXML
    private MFXTextField txtDescripcion;
    @FXML
    private MFXCheckbox chkActivo;

    private TiposPlanillaDto tiposPlanilla;
    private ObjectProperty<TiposPlanillaDto> tiposPlanillaProperty = new SimpleObjectProperty<>();
    private List<Node> requeridos = new ArrayList<>();

    // Declaraciones
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtNombre.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(40));
        txtDescripcion.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(120));
        tiposPlanilla = new TiposPlanillaDto();
        enlazarTiposPlanilla();
        cargarValoresDefecto();
        indicarRequeridos();
    }

    @Override
    public void initialize() {
    }

    // Funciones extra
    private void enlazarTiposPlanilla() {
        try {
            tiposPlanillaProperty.addListener((ov, oldVal, newVal) -> {
                if (oldVal != null) {
                    txtId.textProperty().unbind();
                    txtNombre.textProperty().unbindBidirectional(oldVal.getNombreProperty());
                    txtDescripcion.textProperty().unbindBidirectional(oldVal.getDescripcionProperty());
                    chkActivo.selectedProperty().unbindBidirectional(oldVal.getActivoProperty());
                }
                if (newVal != null) {
                    if (newVal.getIdProperty().get() != null
                            && !newVal.getIdProperty().get().isBlank()) {
                        txtId.textProperty().bind(newVal.getIdProperty());
                    }
                    txtNombre.textProperty().bindBidirectional(newVal.getNombreProperty());
                    txtDescripcion.textProperty().bindBidirectional(newVal.getDescripcionProperty());
                    chkActivo.selectedProperty().bindBidirectional(newVal.getActivoProperty());
                }
            });
        } catch (Exception ex) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al realizar el bindeo", getStage(),
                    "Ocurrió un error al realizar el bindeo.");
        }
    }

    private void cargarValoresDefecto() {
        this.tiposPlanilla = new TiposPlanillaDto();
        this.tiposPlanilla.setActivo(Boolean.TRUE);
        tiposPlanillaProperty.setValue(this.tiposPlanilla);
        txtId.clear();
        txtId.requestFocus();
    }

    private void indicarRequeridos() {
        this.requeridos.clear();
        this.requeridos.addAll(Arrays.asList(txtNombre));
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof MFXTextField
                    && (((MFXTextField) node).getText() == null || ((MFXTextField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((MFXTextField) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXTextField) node).getFloatingText();
                }
                validos = false;
            }
        }
        if (validos) {
            return "";
        } else {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }
    }

    private void cargarTiposPlanilla(Long id) {
        try {
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Consultar Tipo de Planilla",
                    getStage(), "El tipo de planilla se consultó correctamente.");
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName())
                    .log(Level.SEVERE, "Error consultando el tipo de planilla", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Consultar Tipo de Planilla",
                    getStage(), "Ocurrió un error consultando el tipo de planilla.");
        }
    }

    // Acciones
    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
        if (new Mensaje().showConfirmation("Limpiar Tipo de Planilla", getStage(),
                "¿Esta seguro que desea limpiar el registro?")) {
            cargarValoresDefecto();
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();
            if (!invalidos.isBlank()) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Guardar Tipo de Planilla",
                        getStage(), invalidos);
            } else {
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Tipo de Planilla",
                        getStage(), "El tipo de planilla se guardó correctamente.");
            }

        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName())
                    .log(Level.SEVERE, "Error guardando el tipo de planilla", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Tipo de Planilla",
                    getStage(), "Ocurrió un error guardando el tipo de planilla.");
        }
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
        try {
            if (this.tiposPlanilla.getId() == null) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Eliminar Tipo de Planilla",
                        getStage(), "Favor consultar el tipo de planilla a eliminar.");
            } else {
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar Tipo de Planilla",
                        getStage(), "El tipo de planilla se eliminó correctamente.");
            }

        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName())
                    .log(Level.SEVERE, "Error eliminando el tipo de planilla", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Tipo de Planilla",
                    getStage(), "Ocurrió un error eliminando el tipo de planilla.");
        }
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
    }

    @FXML
    private void onKeyPressedTxtId(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER
                && !txtId.getText().isBlank()) {
            cargarTiposPlanilla(Long.valueOf(txtId.getText()));
        }
    }
}
