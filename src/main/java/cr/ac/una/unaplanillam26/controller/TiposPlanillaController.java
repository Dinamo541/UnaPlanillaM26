package cr.ac.una.unaplanillam26.controller;

import cr.ac.una.unaplanillam26.model.TiposPlanillaDto;
import cr.ac.una.unaplanillam26.util.Formato;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Dominique
 */
public class TiposPlanillaController extends Controller implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private VBox vbTiposPlanilla;
    @FXML
    private VBox vbInclusionEmpleados;
    @FXML
    private MFXButton btnTiposPlanilla;
    @FXML
    private MFXButton btnInclusionEmpleados;
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
    private MFXTextField txtCodigo;
    @FXML
    private MFXTextField txtDescripcion;
    @FXML
    private MFXTextField txtPlanillaXMes;
    @FXML
    private MFXCheckbox chkActivo;
    @FXML
    private TableView<?> tbListaEmpleados;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colEliminar;

    private TiposPlanillaDto tipoPlanilla;
    private ObjectProperty<TiposPlanillaDto> tipoPlanillaProperty = new SimpleObjectProperty<>();
    private List<Node> requeridos = new ArrayList();
    @FXML
    private Button btnAgregarEmpleado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vbInclusionEmpleados.setVisible(false);
        vbInclusionEmpleados.setManaged(false);
        vbTiposPlanilla.setVisible(true);
        vbTiposPlanilla.setManaged(true);
        txtId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtCodigo.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txtDescripcion.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txtPlanillaXMes.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        tipoPlanilla = new TiposPlanillaDto();
        bindTipoPlanilla();
        cargarValoresDefecto();
        indicarRequeridos();
    }

    @Override
    public void initialize() {
    }

    private void bindTipoPlanilla() {
        try {
            tipoPlanillaProperty.addListener((ov, oldVal, newVal) -> {
                if (oldVal != null) {
                    txtId.textProperty().unbind();
                    txtCodigo.textProperty().unbindBidirectional(oldVal.getCodigoProperty());
                    txtDescripcion.textProperty().unbindBidirectional(oldVal.getDescripcionProperty());
                    txtPlanillaXMes.textProperty().unbindBidirectional(oldVal.getPlanillasXMesProperty());
                    chkActivo.selectedProperty().unbindBidirectional(oldVal.getActivoProperty());
                }
                if (newVal != null) {
                    if (newVal.getIdProperty().get() != null
                            && !newVal.getIdProperty().get().isBlank()) {
                        txtId.textProperty().bind(newVal.getIdProperty());
                    }
                    txtCodigo.textProperty().bindBidirectional(newVal.getCodigoProperty());
                    txtDescripcion.textProperty().bindBidirectional(newVal.getDescripcionProperty());
                    txtPlanillaXMes.textProperty().bindBidirectional(newVal.getPlanillasXMesProperty());
                    chkActivo.selectedProperty().bindBidirectional(newVal.getActivoProperty());
                }
            });
        } catch (Exception e) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE,
                    "Error al bindear el tipo de planilla", e);
        }
    }

    private void cargarValoresDefecto() {
        try {
            tipoPlanilla = new TiposPlanillaDto();
            tipoPlanilla.setCodigo("");
            tipoPlanilla.setDescripcion("");
            tipoPlanilla.setPlanillasXMes("");
            tipoPlanilla.setActivo(true);
            tipoPlanillaProperty.set(tipoPlanilla);
        } catch (Exception e) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE,
                    "Error al cargar valores por defecto", e);
        }
    }

    private void indicarRequeridos() {
        try {
            requeridos.clear();
            requeridos.add(txtCodigo);
            requeridos.add(txtDescripcion);
            requeridos.add(txtPlanillaXMes);
        } catch (Exception e) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, "Error al indicar requeridos",
                    e);
        }
    }

    @FXML
    private void onActionBtnTiposPlanilla(ActionEvent event) {
        vbInclusionEmpleados.setVisible(false);
        vbInclusionEmpleados.setManaged(false);
        vbTiposPlanilla.setVisible(true);
        vbTiposPlanilla.setManaged(true);
    }

    @FXML
    private void onActionBtnInclusionEmpleados(ActionEvent event) {
        vbInclusionEmpleados.setVisible(true);
        vbInclusionEmpleados.setManaged(true);
        vbTiposPlanilla.setVisible(false);
        vbTiposPlanilla.setManaged(false);
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
    }
}