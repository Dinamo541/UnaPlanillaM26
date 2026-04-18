package cr.ac.una.unaplanillam26.controller;

import cr.ac.una.unaplanillam26.model.EmpleadoDto;
import cr.ac.una.unaplanillam26.util.BindingUtils;
import cr.ac.una.unaplanillam26.util.Formato;
import cr.ac.una.unaplanillam26.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Dominique
 */
@SuppressWarnings("unused")
public class EmpleadosController extends Controller implements Initializable {

    @FXML
    private VBox root;
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
    private MFXTextField txtCedula;
    @FXML
    private MFXTextField txtPApellido;
    @FXML
    private MFXTextField txtSApellido;
    @FXML
    private MFXTextField txtCorreo;
    @FXML
    private MFXTextField txtUsuario;
    @FXML
    private MFXPasswordField txtClave;
    @FXML
    private MFXDatePicker dtpFIngreso;
    @FXML
    private MFXDatePicker dtpFSalida;
    @FXML
    private ToggleGroup tggGender;
    @FXML
    private MFXRadioButton rbMasculino;
    @FXML
    private MFXRadioButton rbFemenino;
    @FXML
    private MFXCheckbox chkAdministrador;
    @FXML
    private MFXCheckbox chkActivo;

    private EmpleadoDto empleado;
    private ObjectProperty<EmpleadoDto> empleadoProperty = new SimpleObjectProperty<>();
    private List<Node> requeridos = new ArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rbFemenino.setUserData("F");
        rbMasculino.setUserData("M");
        txtId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtNombre.delegateSetTextFormatter(Formato.getInstance().letrasFormat(30));
        txtPApellido.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txtSApellido.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txtCedula.delegateSetTextFormatter(Formato.getInstance().cedulaFormat(40));
        txtCedula.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(80));
        txtUsuario.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txtClave.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(8));
        empleado = new EmpleadoDto();
        bindEmpleado();
        cargarValoresDefecto();
        indicarRequeridos();
    }

    @Override
    public void initialize() {
    }

    private void bindEmpleado() {
        try {
            empleadoProperty.addListener((ov, oldVal, newVal) -> {
                if (oldVal != null) {
                    txtId.textProperty().unbind();
                    txtCedula.textProperty().unbindBidirectional(oldVal.getCedulaProperty());
                    txtNombre.textProperty().unbindBidirectional(oldVal.getNombreProperty());
                    txtPApellido.textProperty().unbindBidirectional(oldVal.getPrimerApellidoProperty());
                    txtSApellido.textProperty().unbindBidirectional(oldVal.getSegundoApellidoProperty());
                    txtCorreo.textProperty().unbindBidirectional(oldVal.getCorreoProperty());
                    txtUsuario.textProperty().unbindBidirectional(oldVal.getUsuarioProperty());
                    txtClave.textProperty().unbindBidirectional(oldVal.getClaveProperty());
                    chkAdministrador.selectedProperty().unbindBidirectional(oldVal.getAdministratorProperty());
                    chkActivo.selectedProperty().unbindBidirectional(oldVal.getActivoProperty());
                    dtpFIngreso.valueProperty().unbindBidirectional(oldVal.getFechaIngresoProperty());
                    dtpFSalida.valueProperty().unbindBidirectional(oldVal.getFechaSalidaProperty());
                    BindingUtils.unbindToggleGroupToProperty(tggGender, oldVal.getGeneroProperty());
                }
                if (newVal != null) {
                    if (newVal.getIdProperty().get() != null
                            && !newVal.getIdProperty().get().isBlank()) {
                        txtId.textProperty().bind(newVal.getIdProperty());
                    }
                    txtCedula.textProperty().bindBidirectional(newVal.getCedulaProperty());
                    txtNombre.textProperty().bindBidirectional(newVal.getNombreProperty());
                    txtPApellido.textProperty().bindBidirectional(newVal.getPrimerApellidoProperty());
                    txtSApellido.textProperty().bindBidirectional(newVal.getSegundoApellidoProperty());
                    txtCorreo.textProperty().bindBidirectional(newVal.getCorreoProperty());
                    txtUsuario.textProperty().bindBidirectional(newVal.getUsuarioProperty());
                    txtClave.textProperty().bindBidirectional(newVal.getClaveProperty());
                    chkAdministrador.selectedProperty().bindBidirectional(newVal.getAdministratorProperty());
                    chkActivo.selectedProperty().bindBidirectional(newVal.getActivoProperty());
                    dtpFIngreso.valueProperty().bindBidirectional(newVal.getFechaIngresoProperty());
                    dtpFSalida.valueProperty().bindBidirectional(newVal.getFechaSalidaProperty());
                    BindingUtils.bindToggleGroupToProperty(tggGender, newVal.getGeneroProperty());
                }
            });
        } catch (Exception e) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al realizar el bindeo", getStage(),
                    "Ocurrió un error al realizar el bindeo.");
        }
    }

    private void cargarValoresDefecto() {
        this.empleado = new EmpleadoDto();
        this.empleado.setActivo(Boolean.TRUE);
        this.empleado.setAdministrador(Boolean.FALSE);
        this.empleado.setFechaIngreso(LocalDate.now());
        this.empleado.setGenero("M");
        empleadoProperty.setValue(this.empleado);
        validarAdministrador();
        txtId.clear();
        txtId.requestFocus();
    }

    private void validarAdministrador() {
        if (chkAdministrador.isSelected()) {
            this.requeridos.addAll(Arrays.asList(txtUsuario, txtClave));

            txtUsuario.clear();
            txtClave.clear();

            txtUsuario.setDisable(false);
            txtClave.setDisable(false);
        } else {
            this.requeridos.removeAll(Arrays.asList(txtUsuario, txtClave));

            txtUsuario.clear();
            txtClave.clear();

            txtUsuario.setDisable(true);
            txtClave.setDisable(true);
        }
    }

    private void indicarRequeridos() {
        this.requeridos.clear();
        this.requeridos.addAll(Arrays.asList(txtCedula, txtNombre, txtPApellido, dtpFIngreso));
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
            } else if (node instanceof MFXPasswordField
                    && (((MFXPasswordField) node).getText() == null || ((MFXPasswordField) node).getText().isBlank())) {
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
            } else if (node instanceof MFXComboBox && ((MFXComboBox) node).getSelectionModel().getSelectedIndex() < 0) {
                if (validos) {
                    invalidos += ((MFXComboBox) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXComboBox) node).getFloatingText();
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

    @FXML
    private void onActionChkAdministrador(ActionEvent event) {
        validarAdministrador();
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
        if (new Mensaje().showConfirmation("Limpiar Empleado", getStage(),
                "¿Esta seguro que desea limpiar el registro?")) {
            cargarValoresDefecto();
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();
            if (!invalidos.isBlank()) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Guardar Empleado",
                        getStage(), invalidos);
            } else {
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Empleado",
                        getStage(), "El empleado se guardó correctamente.");
            }

        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName())
                    .log(Level.SEVERE, "Error guardando el empleado", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Empleado",
                    getStage(), "Ocurrió un error guardando el empleado.");
        }
    }

    @FXML
    private void onActionBtnEliminar(ActionEvent event) {
        try {
            if (this.empleado.getId() == null) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Eliminar Empleado",
                        getStage(), "Favor consultar el empleado a eliminar.");
            } else {
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Empleado",
                        getStage(), "El empleado se eliminó correctamente.");
            }

        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName())
                    .log(Level.SEVERE, "Error eliminando el Empleado", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Empleado",
                    getStage(), "Ocurrió un error eliminando el empleado.");
        }
    }

    @FXML
    private void onKeyPressedTxtId(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER
                && !txtId.getText().isBlank()) {
            cargarEmpleado(Long.valueOf(txtId.getText()));
        }
    }

    private void cargarEmpleado(Long id) {
        try {
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Consultar Empleado",
                    getStage(), "El empleado se consultó correctamente.");
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName())
                    .log(Level.SEVERE, "Error consultando el empleado", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Consultar Empleado",
                    getStage(), "Ocurrió un error consultando el empleado.");
        }
    }

    @FXML
    private void onActionBtnBuscar(ActionEvent event) {
    }
}