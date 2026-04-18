package cr.ac.una.unaplanillam26.controller;

import cr.ac.una.unaplanillam26.model.EmpleadoDto;
import cr.ac.una.unaplanillam26.model.TiposPlanillaDto;
import cr.ac.una.unaplanillam26.util.Formato;
import cr.ac.una.unaplanillam26.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Dominique
 */
@SuppressWarnings("unused")
public class TiposPlanillaController extends Controller implements Initializable {

    // Variables FXML
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
    private TableView<EmpleadoDto> tbListaEmpleados;
    @FXML
    private TableColumn<EmpleadoDto, String> colId;
    @FXML
    private TableColumn<EmpleadoDto, String> colName;
    @FXML
    private TableColumn<EmpleadoDto, EmpleadoDto> colEliminar;
    @FXML
    private Button btnAgregarEmpleado;

    // Variables
    private TiposPlanillaDto tipoPlanilla;
    private final ObjectProperty<TiposPlanillaDto> tipoPlanillaProperty = new SimpleObjectProperty<>();
    private final List<Node> requeridos = new ArrayList<>();
    private final Map<String, TiposPlanillaDto> tiposPlanillaPorCodigo = new HashMap<>();
    private final AtomicLong secuenciaIdTipoPlanilla = new AtomicLong(1);
    private final AtomicLong secuenciaIdEmpleado = new AtomicLong(1);
    private MFXTextField txtIdEmpleado;
    private MFXTextField txtNombreEmpleado;

    // Declaraciones
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prepararVista();
        configurarFormatoCampos();
        bindTipoPlanilla();
        configurarTablaEmpleados();
        detectarCamposInclusionEmpleados();
        configurarAccionAgregarEmpleado();
        cargarValoresDefecto();
        indicarRequeridos();
    }

    @Override
    public void initialize() {
    }

    // Funciones extra
    private void prepararVista() {
        vbInclusionEmpleados.setVisible(false);
        vbInclusionEmpleados.setManaged(false);
        vbTiposPlanilla.setVisible(true);
        vbTiposPlanilla.setManaged(true);
    }

    private void configurarFormatoCampos() {
        txtId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtCodigo.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txtDescripcion.delegateSetTextFormatter(Formato.getInstance().letrasFormat(50));
        txtPlanillaXMes.delegateSetTextFormatter(Formato.getInstance().integerFormat());
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

    private void configurarTablaEmpleados() {
        colId.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(
                construirNombreCompleto(cellData.getValue())));
        colEliminar.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        colEliminar.setCellFactory(param -> new TableCell<>() {
            private final Button btnEliminarEmpleado = new Button("Quitar");

            {
                btnEliminarEmpleado.setOnAction(event -> {
                    EmpleadoDto empleado = getItem();
                    if (empleado != null && tipoPlanillaProperty.get() != null) {
                        tipoPlanillaProperty.get().getEmpleados().remove(empleado);
                    }
                });
            }

            @Override
            protected void updateItem(EmpleadoDto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEliminarEmpleado);
                }
            }
        });
    }

    private String construirNombreCompleto(EmpleadoDto empleado) {
        List<String> partes = new ArrayList<>();
        if (empleado.getNombre() != null && !empleado.getNombre().isBlank()) {
            partes.add(empleado.getNombre());
        }
        if (empleado.getPrimerApellido() != null && !empleado.getPrimerApellido().isBlank()) {
            partes.add(empleado.getPrimerApellido());
        }
        if (empleado.getSegundoApellido() != null && !empleado.getSegundoApellido().isBlank()) {
            partes.add(empleado.getSegundoApellido());
        }
        return String.join(" ", partes);
    }

    private void detectarCamposInclusionEmpleados() {
        List<MFXTextField> campos = obtenerNodosPorTipo(vbInclusionEmpleados, MFXTextField.class);
        Optional<MFXTextField> idEmpleado = campos.stream()
                .filter(campo -> "Id Empleados".equalsIgnoreCase(campo.getFloatingText()))
                .findFirst();
        Optional<MFXTextField> nombreEmpleado = campos.stream()
                .filter(campo -> "Nombre".equalsIgnoreCase(campo.getFloatingText()))
                .findFirst();

        txtIdEmpleado = idEmpleado.orElse(null);
        txtNombreEmpleado = nombreEmpleado.orElse(null);

        if (txtIdEmpleado != null) {
            txtIdEmpleado.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        }
        if (txtNombreEmpleado != null) {
            txtNombreEmpleado.delegateSetTextFormatter(Formato.getInstance().letrasFormat(30));
        }
    }

    private <T extends Node> List<T> obtenerNodosPorTipo(Node rootNode, Class<T> claseNodo) {
        List<T> nodos = new ArrayList<>();
        if (claseNodo.isInstance(rootNode)) {
            nodos.add(claseNodo.cast(rootNode));
        }
        if (rootNode instanceof VBox) {
            for (Node node : ((VBox) rootNode).getChildren()) {
                nodos.addAll(obtenerNodosPorTipo(node, claseNodo));
            }
        } else if (rootNode instanceof javafx.scene.layout.HBox) {
            for (Node node : ((javafx.scene.layout.HBox) rootNode).getChildren()) {
                nodos.addAll(obtenerNodosPorTipo(node, claseNodo));
            }
        }
        return nodos;
    }

    private void configurarAccionAgregarEmpleado() {
        btnAgregarEmpleado.setOnAction(event -> agregarEmpleadoATipoPlanilla());
    }

    private void agregarEmpleadoATipoPlanilla() {
        try {
            if (tipoPlanillaProperty.get() == null) {
                return;
            }
            if (txtNombreEmpleado == null || txtIdEmpleado == null) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Incluir Empleado", getStage(),
                        "No fue posible encontrar los campos de inclusión de empleados en la vista.");
                return;
            }
            String idEmpleadoTexto = txtIdEmpleado.getText() == null ? "" : txtIdEmpleado.getText().trim();
            String nombreEmpleadoTexto = txtNombreEmpleado.getText() == null ? "" : txtNombreEmpleado.getText().trim();

            if (nombreEmpleadoTexto.isBlank()) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Incluir Empleado", getStage(),
                        "Debe indicar el nombre del empleado.");
                return;
            }

            EmpleadoDto nuevoEmpleado = new EmpleadoDto();
            if (!idEmpleadoTexto.isBlank()) {
                nuevoEmpleado.setId(Long.valueOf(idEmpleadoTexto));
            } else {
                nuevoEmpleado.setId(secuenciaIdEmpleado.getAndIncrement());
            }
            nuevoEmpleado.setNombre(nombreEmpleadoTexto);
            nuevoEmpleado.setActivo(Boolean.TRUE);

            tipoPlanillaProperty.get().getEmpleados().add(nuevoEmpleado);
            txtIdEmpleado.clear();
            txtNombreEmpleado.clear();
            txtNombreEmpleado.requestFocus();
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE,
                    "Error al incluir empleado en tipo de planilla", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Incluir Empleado", getStage(),
                    "Ocurrió un error al incluir el empleado en el tipo de planilla.");
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
            txtId.clear();
            txtCodigo.requestFocus();
            limpiarCamposInclusionEmpleados();
        } catch (Exception e) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE,
                    "Error al cargar valores por defecto", e);
        }
    }

    private void limpiarCamposInclusionEmpleados() {
        if (txtIdEmpleado != null) {
            txtIdEmpleado.clear();
        }
        if (txtNombreEmpleado != null) {
            txtNombreEmpleado.clear();
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

    private String validarRequeridos() {
        List<String> invalidos = new ArrayList<>();
        for (Node node : requeridos) {
            if (node instanceof MFXTextField textField
                    && (textField.getText() == null || textField.getText().isBlank())) {
                invalidos.add(textField.getFloatingText());
            }
        }
        if (invalidos.isEmpty()) {
            return "";
        }
        return "Campos requeridos o con problemas de formato [" + String.join(",", invalidos) + "].";
    }

    private TiposPlanillaDto clonarTipoPlanilla(TiposPlanillaDto origen) {
        TiposPlanillaDto copia = new TiposPlanillaDto();
        copia.setId(origen.getId());
        copia.setCodigo(origen.getCodigo());
        copia.setDescripcion(origen.getDescripcion());
        copia.setPlanillasXMes(origen.getPlanillasXMes());
        copia.setActivo(origen.getActivo());
        copia.setEmpleados(origen.getEmpleados());
        return copia;
    }

    // OnActions
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
                "¿Esta seguro que desea limpiar el registro?")) {
            cargarValoresDefecto();
        }
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
        try {
            String codigo = txtCodigo.getText() == null ? "" : txtCodigo.getText().trim();
            if (codigo.isBlank()) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Buscar Tipo Planilla", getStage(),
                        "Debe indicar el código para realizar la búsqueda.");
                return;
            }
            TiposPlanillaDto encontrado = tiposPlanillaPorCodigo.get(codigo);
            if (encontrado == null) {
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Buscar Tipo Planilla", getStage(),
                        "No se encontró un tipo de planilla registrado con ese código.");
                return;
            }
            tipoPlanillaProperty.set(clonarTipoPlanilla(encontrado));
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Buscar Tipo Planilla", getStage(),
                    "El tipo de planilla se consultó correctamente.");
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName())
                    .log(Level.SEVERE, "Error consultando el tipo de planilla", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar Tipo Planilla", getStage(),
                    "Ocurrió un error consultando el tipo de planilla.");
        }
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
        try {
            if (tipoPlanillaProperty.get() == null || tipoPlanillaProperty.get().getCodigo() == null
                    || tipoPlanillaProperty.get().getCodigo().isBlank()) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Eliminar Tipo Planilla", getStage(),
                        "Favor consultar el tipo de planilla a eliminar.");
                return;
            }
            tiposPlanillaPorCodigo.remove(tipoPlanillaProperty.get().getCodigo().trim());
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar Tipo Planilla", getStage(),
                    "El tipo de planilla se eliminó correctamente.");
            cargarValoresDefecto();
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName())
                    .log(Level.SEVERE, "Error eliminando el tipo de planilla", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Tipo Planilla", getStage(),
                    "Ocurrió un error eliminando el tipo de planilla.");
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();
            if (!invalidos.isBlank()) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Guardar Tipo Planilla", getStage(), invalidos);
                return;
            }

            TiposPlanillaDto actual = tipoPlanillaProperty.get();
            if (actual.getId() == null) {
                actual.setId(secuenciaIdTipoPlanilla.getAndIncrement());
            }

            TiposPlanillaDto respaldo = clonarTipoPlanilla(actual);
            tiposPlanillaPorCodigo.put(actual.getCodigo().trim(), respaldo);
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Tipo Planilla", getStage(),
                    "El tipo de planilla se guardó correctamente.");
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName())
                    .log(Level.SEVERE, "Error guardando el tipo de planilla", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Tipo Planilla", getStage(),
                    "Ocurrió un error guardando el tipo de planilla.");
        }
    }
}
