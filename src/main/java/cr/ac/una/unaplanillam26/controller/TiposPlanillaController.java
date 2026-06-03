package cr.ac.una.unaplanillam26.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import cr.ac.una.unaplanillam26.model.EmpleadoDto;
import cr.ac.una.unaplanillam26.model.TiposPlanillaDto;
import cr.ac.una.unaplanillam26.service.EmpleadoService;
import cr.ac.una.unaplanillam26.service.TipoPlanillaService;
import cr.ac.una.unaplanillam26.util.AppContext;
import cr.ac.una.unaplanillam26.util.FlowController;
import cr.ac.una.unaplanillam26.util.Formato;
import cr.ac.una.unaplanillam26.util.Mensaje;
import cr.ac.una.unaplanillam26.util.Respuesta;
import javafx.beans.property.SimpleBooleanProperty;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author Dominique
 */
@SuppressWarnings("unused")
public class TiposPlanillaController extends Controller implements Initializable {

    // ----------------- FXML ----------------
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
    private Button btnAgregarEmpleado;
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
    private MFXTextField txtIdEmpleado;
    @FXML
    private MFXTextField txtNombreEmpleado;
    @FXML
    private MFXCheckbox chkActivo;
    @FXML
    private TableView<EmpleadoDto> tbListaEmpleados;
    @FXML
    private TableColumn<EmpleadoDto, String> colId;
    @FXML
    private TableColumn<EmpleadoDto, String> colName;
    @FXML
    private TableColumn<EmpleadoDto, Boolean> colEliminar;

    // ----------------- Variables ----------------
    private EmpleadoDto empleadoDto;
    private TiposPlanillaDto tipoPlanillaDto;

    private final ObjectProperty<EmpleadoDto> empleadoProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<TiposPlanillaDto> tipoPlanillaProperty = new SimpleObjectProperty<>();

    private final List<Node> requeridos = new ArrayList<>();

    // ----------------- Métodos de Inicialización ----------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.tipoPlanillaDto = new TiposPlanillaDto();
        this.empleadoDto = new EmpleadoDto();

        bindTipoPlanilla();
        bindEmpleado();

        configurarFormato();
        configurarTablaEmpleados();

        prepararVista();
        cargarValoresDefecto();
        indicarRequeridos();
        
    }

    @Override
    public void initialize() {
    }

    // ---------------- Bindings ----------------
    private void bindTipoPlanilla() {
        try {
            tipoPlanillaProperty.addListener((ov, oldVal, newVal) -> {
                if (oldVal != null) {
                    txtId.textProperty().unbind();
                    txtCodigo.textProperty().unbindBidirectional(oldVal.getCodigoProperty());
                    txtDescripcion.textProperty().unbindBidirectional(oldVal.getDescripcionProperty());
                    txtPlanillaXMes.textProperty().unbindBidirectional(oldVal.getPlanillasXMesProperty());
                    chkActivo.selectedProperty().unbindBidirectional(oldVal.getActivoProperty());
                    tbListaEmpleados.setItems(null);
                }
                if (newVal != null) {
                    if (newVal.getIdProperty().get() != null && !newVal.getIdProperty().get().isBlank()) {
                        txtId.textProperty().bind(newVal.getIdProperty());
                    }
                    txtCodigo.textProperty().bindBidirectional(newVal.getCodigoProperty());
                    txtDescripcion.textProperty().bindBidirectional(newVal.getDescripcionProperty());
                    txtPlanillaXMes.textProperty().bindBidirectional(newVal.getPlanillasXMesProperty());
                    chkActivo.selectedProperty().bindBidirectional(newVal.getActivoProperty());
                    tbListaEmpleados.setItems(newVal.getEmpleados());
                }
            });
        } catch (Exception e) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE,
                    "Error al bindear el tipo de planilla", e);
        }
    }

    private void bindEmpleado() {
        try {
            empleadoProperty.addListener((ov, oldVal, newVal) -> {
                if (oldVal != null) {
                    txtIdEmpleado.textProperty().unbind();
                    txtNombreEmpleado.textProperty().unbindBidirectional(oldVal.getNombreProperty());
                }
                if (newVal != null) {
                    if (newVal.getIdProperty().get() != null
                            && !newVal.getIdProperty().get().isBlank()) {
                        txtIdEmpleado.textProperty().bind(newVal.getIdProperty());
                    }
                    txtNombreEmpleado.textProperty().bindBidirectional(newVal.getNombreProperty());
                }
            });
        } catch (Exception e) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al realizar el bindeo", getStage(),
                    "Ocurrió un error al realizar el bindeo.");
        }
    }

    // ---------------- Configuración de Formatos ----------------
    private void configurarFormato() {
        txtId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtCodigo.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(4));
        txtDescripcion.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(40));
        txtPlanillaXMes.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtPlanillaXMes.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(2));
        txtIdEmpleado.delegateSetTextFormatter(Formato.getInstance().integerFormat());
    }

    private void configurarTablaEmpleados() {
        colId.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        colName.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
        colEliminar.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue() != null));
        colEliminar.setCellFactory(cellData -> new ButtonCell());
        tbListaEmpleados.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                this.empleadoDto = newValue;
                this.empleadoProperty.setValue(this.empleadoDto);
            }
        });
    }

    // ---------------- Métodos Privados ----------------
    private void prepararVista() {
        VBox.setVgrow(root, Priority.ALWAYS);
        HBox.setHgrow(root, Priority.ALWAYS);
        vbInclusionEmpleados.setVisible(false);
        vbInclusionEmpleados.setManaged(false);
        vbTiposPlanilla.setVisible(true);
        vbTiposPlanilla.setManaged(true);
    }

    private void cargarValoresDefecto() {
        try {
            tipoPlanillaDto = new TiposPlanillaDto();
            tipoPlanillaDto.setCodigo("");
            tipoPlanillaDto.setDescripcion("");
            tipoPlanillaDto.setPlanillasXMes("");
            tipoPlanillaDto.setActivo(true);
            tipoPlanillaProperty.set(tipoPlanillaDto);

            txtId.clear();
            txtId.requestFocus();
            this.empleadoDto = new EmpleadoDto();
            this.empleadoProperty.set(this.empleadoDto);

            limpiarEmpleados();
            refreshEmpleadosTb();
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

    // ---------------- Métodos para manejo de empleados en el tipo de planilla ----------------
    private void limpiarEmpleados() {
        tbListaEmpleados.getSelectionModel().select(null);
        this.empleadoDto = new EmpleadoDto();
        this.empleadoProperty.setValue(this.empleadoDto);
        txtIdEmpleado.clear();
        txtIdEmpleado.requestFocus();
    }

    private void refreshEmpleadosTb() {
        limpiarEmpleados();
        tbListaEmpleados.setItems(this.tipoPlanillaDto.getEmpleados());
        tbListaEmpleados.refresh();
    }

    private void cargarEmpleado(Long id){
        try {
            EmpleadoService empleadoService = new EmpleadoService();
            Respuesta respuesta = empleadoService.getEmpleado(id);

            if (respuesta.getEstado()) {
                this.empleadoDto = (EmpleadoDto) respuesta.getResultado("Empleado");
                this.empleadoProperty.set(this.empleadoDto);
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar Empleado", getStage(), respuesta.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE, "Error consultando el empleado",ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Consultar Empleado", getStage(),
                    "Ocurrió un error consultando el empleado.");
        }
    }

    private void cargarTipoPlanilla(Long id) {
        try{
            TipoPlanillaService tipoPlanillaService = new TipoPlanillaService();
            Respuesta respuesta = tipoPlanillaService.getTipoPlanilla(id);

            if (respuesta.getEstado()) {
                this.tipoPlanillaDto = (TiposPlanillaDto) respuesta.getResultado("TipoPlanilla");
                this.tipoPlanillaProperty.set(this.tipoPlanillaDto);

                refreshEmpleadosTb();
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar Tipo Planilla", getStage(), respuesta.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, "Error consultando el tipo de planilla",ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Consultar Tipo Planilla", getStage(),
            "Ocurrió un error consultando el tipo de planilla.");
        }
    }

    private boolean mismoEmpleado(EmpleadoDto primero, EmpleadoDto segundo) {
        if (primero == null || segundo == null) {
            return false;
        }
        return Objects.equals(primero.getId(), segundo.getId());
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : this.requeridos) {
            if (node instanceof MFXTextField && (((MFXTextField) node).getText() == null || ((MFXTextField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((MFXTextField) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXTextField) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXPasswordField && (((MFXPasswordField) node).getText() == null || ((MFXPasswordField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((MFXPasswordField) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXPasswordField) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXDatePicker && ((MFXDatePicker) node).getValue() == null) {
                if (validos) {
                    invalidos += ((MFXDatePicker) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXDatePicker) node).getFloatingText();
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

    // ---------------- Métodos de Acción ----------------
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
        if (new Mensaje().showConfirmation("Limpiar Tipo Planilla", getStage(),
                "¿Está seguro de que desea limpiar el registro?")) {
            
            cargarValoresDefecto();
        }
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
        AppContext.getInstance().set("Vista", "TiposPlanilla");
        FlowController.getInstance().goViewInWindowModal("FiltradoEmpleadoView", getStage(), true);

        if (AppContext.getInstance().get("Filtro") != null && AppContext.getInstance().get("Filtro") instanceof TiposPlanillaDto) {
            this.tipoPlanillaDto = (TiposPlanillaDto) AppContext.getInstance().get("Filtro");
            AppContext.getInstance().delete("Filtro");

            cargarTipoPlanilla(Long.valueOf(this.tipoPlanillaDto.getId()));
        }
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();

            if (this.tipoPlanillaDto.getId() == null) {
                new Mensaje().showModal(Alert.AlertType.WARNING,"Eliminar Planilla", getStage(),"Favor consultar la planilla a eliminar");
            } else {
                TipoPlanillaService tipoPlanillaService = new TipoPlanillaService();
                Respuesta respuesta = tipoPlanillaService.eliminarTipoPlanilla(this.tipoPlanillaDto.getId());

                if (respuesta.getEstado()) {
                    cargarValoresDefecto();
                    new Mensaje().showModal(Alert.AlertType.INFORMATION,"Eliminar planilla", getStage(), "La planilla se eliminó correctamente");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Planilla", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, "Error eliminando la planilla",ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminando planilla", getStage(),
                    "Ocurrió un error eliminando la planilla.");
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();

            if (!invalidos.isBlank()) {
                new Mensaje().showModal(Alert.AlertType.WARNING,"Guardar Planilla", getStage(), invalidos);
            } else {
                TipoPlanillaService tipoPlanillaService = new TipoPlanillaService();
                Respuesta respuesta = tipoPlanillaService.guardarTipoPlanilla(this.tipoPlanillaDto);

                if (respuesta.getEstado()) {
                    this.tipoPlanillaDto = (TiposPlanillaDto) respuesta.getResultado("TipoPlanilla");
                    this.tipoPlanillaProperty.set(this.tipoPlanillaDto);
                    validarRequeridos();
                    new Mensaje().showModal(Alert.AlertType.INFORMATION,"Guardar Planilla", getStage(), "La planilla se guardó correctamente");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Planilla", getStage(), respuesta.getMensaje());
                }
            }
            cargarTipoPlanilla(Long.valueOf(this.tipoPlanillaDto.getId()));
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, "Error guardando la planilla",ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar planilla", getStage(),
                    "Ocurrió un error guardando la planilla.");
        }
    }
    
    @FXML
    private void onActionBtnAgregarEmpleado(ActionEvent event) {
        if (this.empleadoDto == null || this.empleadoDto.getId() == null || this.empleadoDto.getNombre() == null
        || this.empleadoDto.getNombre().isBlank()) {
            new Mensaje().showModal(Alert.AlertType.WARNING, "Agregar Empleado", getStage(),
                    "Debe consultar un empleado válido antes de agregarlo a la planilla.");
                    return;
        }
        
        if (tbListaEmpleados.getItems() == null) {
            tbListaEmpleados.setItems(this.tipoPlanillaDto.getEmpleados());
        }

        if (tbListaEmpleados.getItems().stream().anyMatch(emp -> mismoEmpleado(emp, this.empleadoDto))) {
            new Mensaje().showModal(Alert.AlertType.WARNING, "Agregar Empleado", getStage(),
            "El empleado ya fue agregado a la planilla.");
            return;
        }
        
        this.empleadoDto.setModificado(true);
        tbListaEmpleados.getItems().add(this.empleadoDto);
        tbListaEmpleados.refresh();
        limpiarEmpleados();
    }
    
    @FXML
    private void onKeyPressedTxtIdEmpleado(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER
                && txtIdEmpleado.getText() != null
                && !txtIdEmpleado.getText().isBlank()) {
            cargarEmpleado(Long.valueOf(txtIdEmpleado.getText()));
        }
    }

    @FXML
    private void onKeyPressedTxtId(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER
                && txtId.getText() != null
                && !txtId.getText().isBlank()) {
            cargarTipoPlanilla(Long.valueOf(txtId.getText()));
        }
    }

    /*
     * Clase interna para el manejo del botón de eliminación en la tabla de empleados
     */
    private class ButtonCell extends TableCell<EmpleadoDto, Boolean> {
        final Button cellButton = new Button();

        public ButtonCell() {
            cellButton.setPrefWidth(500);
            cellButton.getStyleClass().add("jfx-btnimg-tbveliminar");

            cellButton.setOnAction(event -> {
                EmpleadoDto emp = ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                tipoPlanillaDto.getEmpleadosEliminados().add(emp);
                tbListaEmpleados.getItems().remove(emp);
                tbListaEmpleados.refresh();
            });
        }

        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }

    }

}
