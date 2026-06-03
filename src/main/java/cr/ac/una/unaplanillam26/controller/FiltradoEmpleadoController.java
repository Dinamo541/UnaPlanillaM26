package cr.ac.una.unaplanillam26.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import cr.ac.una.unaplanillam26.model.TipoPlanilla;
import cr.ac.una.unaplanillam26.model.TiposPlanillaDto;
import cr.ac.una.unaplanillam26.util.AppContext;
import cr.ac.una.unaplanillam26.util.EntityManagerHelper;
import cr.ac.una.unaplanillam26.util.Mensaje;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * @author Dominique
 */
public class FiltradoEmpleadoController extends Controller implements Initializable {
    
    // ----------------- FXML ----------------
    @FXML
    private VBox root;
    @FXML
    private MFXButton btnFiltrar;
    @FXML
    private MFXButton btnAceptar;
    @FXML
    private Label lblNombreFiltro;
    @FXML
    private Label lblTituloTabla;
    @FXML
    private VBox vbFiltros;
    @FXML
    private TableView<TiposPlanillaDto> tbResultados;

    // ----------------- Variables ----------------
    private List<MFXTextField> textFieldList = new ArrayList<>();
    private String vistaActual;

    // ----------------- Métodos de Inicialización ----------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initialize();
    }

    @Override
    public void initialize() {
        textFieldList = new ArrayList<>();
        vistaActual = (String) AppContext.getInstance().get("Vista");
        if (vistaActual != null && vistaActual.equals("TiposPlanilla")) {
            lblNombreFiltro.setText("Buscar Tipo Planilla");
            lblTituloTabla.setText("Planilla");

            agregarColumnasTiposPlanilla();
            agregarParametrosTiposPlanilla();
        } else {
            lblNombreFiltro.setText("Buscar Empleado");
            lblTituloTabla.setText("Empleado");

            vbFiltros.getChildren().clear();
            tbResultados.getColumns().clear();
        }
    }

    // ---------------- Métodos Privados ----------------
    private void agregarColumnasTiposPlanilla() {
        TableColumn<TiposPlanillaDto, String> colCodigo = new TableColumn<>("Codigo");
        colCodigo.setPrefWidth(100);
        colCodigo.setCellValueFactory(cd -> cd.getValue().getCodigoProperty());

        TableColumn<TiposPlanillaDto, String> colDescripcion = new TableColumn<>("Descripcion");
        colDescripcion.setPrefWidth(200);
        colDescripcion.setCellValueFactory(cd -> cd.getValue().getDescripcionProperty());

        TableColumn<TiposPlanillaDto, String> colPlanXMes = new TableColumn<>("Planilla X Mes");
        colPlanXMes.setPrefWidth(300);
        colPlanXMes.setCellValueFactory(cd -> cd.getValue().getPlanillasXMesProperty());

        tbResultados.getColumns().clear();
        tbResultados.getColumns().add(colCodigo);
        tbResultados.getColumns().add(colDescripcion);
        tbResultados.getColumns().add(colPlanXMes);
    }

    private void agregarParametrosTiposPlanilla() {
        textFieldList.clear();

        MFXTextField txtId = new MFXTextField();
        HBox.setHgrow(txtId, Priority.ALWAYS);
        txtId.setMaxWidth(Double.MAX_VALUE);
        txtId.setFloatingText("Id");
        txtId.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                onActionBtnFiltrar(null);
            }
        });

        MFXTextField txtCodigo = new MFXTextField();
        HBox.setHgrow(txtCodigo, Priority.ALWAYS);
        txtCodigo.setMaxWidth(Double.MAX_VALUE);
        txtCodigo.setFloatingText("Codigo");
        txtCodigo.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                onActionBtnFiltrar(null);
            }
        });

        MFXTextField txtEmpleado = new MFXTextField();
        HBox.setHgrow(txtEmpleado, Priority.ALWAYS);
        txtEmpleado.setMaxWidth(Double.MAX_VALUE);
        txtEmpleado.setFloatingText("Empleado");
        txtEmpleado.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                onActionBtnFiltrar(null);
            }
        });

        textFieldList.add(txtId);
        textFieldList.add(txtCodigo);
        textFieldList.add(txtEmpleado);

        vbFiltros.getChildren().clear();
        vbFiltros.getChildren().add(txtId);
        vbFiltros.getChildren().add(txtCodigo);
        vbFiltros.getChildren().add(txtEmpleado);
    }

    private void consultarTipoPlanilla() {
        try {
            StringBuilder consulta = new StringBuilder("SELECT DISTINCT t FROM TipoPlanilla t LEFT JOIN t.empleados e WHERE 1=1");
            
            String idValue = textFieldList.get(0).getText() == null ? "" : textFieldList.get(0).getText().trim();
            String codigoValue = textFieldList.get(1).getText() == null ? "" : textFieldList.get(1).getText().trim();
            String empleadoValue = textFieldList.get(2).getText() == null ? "" : textFieldList.get(2).getText().trim();
            
            if (!idValue.isBlank()) {
                consulta.append(" AND t.id = :id");
            }
            if (!codigoValue.isBlank()) {
                consulta.append(" AND UPPER(t.codigo) LIKE :codigo");
            }
            if (!empleadoValue.isBlank()) {
                consulta.append(" AND (UPPER(e.nombre) LIKE :empleado OR UPPER(e.cedula) LIKE :empleado)");
            }
            
            EntityManager em = EntityManagerHelper.getManager();
            TypedQuery<TipoPlanilla> query = em.createQuery(consulta.toString(), TipoPlanilla.class);
            
            if (!idValue.isBlank()) {
                try {
                    query.setParameter("id", Long.parseLong(idValue));
                } catch (NumberFormatException e) {
                    tbResultados.getItems().clear();
                    return;
                }
            }
            if (!codigoValue.isBlank()) {
                query.setParameter("codigo", "%" + codigoValue.toUpperCase() + "%");
            }
            if (!empleadoValue.isBlank()) {
                query.setParameter("empleado", "%" + empleadoValue.toUpperCase() + "%");
            }
            
            List<TipoPlanilla> resultados = query.getResultList();
            
            List<TiposPlanillaDto> tiposPlanillaDtos = new ArrayList<>();
            for (TipoPlanilla tipoPlanilla : resultados) {
                TiposPlanillaDto dto = new TiposPlanillaDto(tipoPlanilla);
                tiposPlanillaDtos.add(dto);
            }
            
            tbResultados.getItems().clear();
            tbResultados.getItems().addAll(tiposPlanillaDtos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ---------------- Métodos de Acción ----------------
    @FXML
    private void onActionBtnFiltrar(ActionEvent event) {
        if (vistaActual.equals("TiposPlanilla")) {
            consultarTipoPlanilla();
        } else {
            // Implementar búsqueda de empleados si es necesario
        }
    }

    @FXML
    private void onActionBtnAceptar(ActionEvent event) {
        if (vistaActual.equals("TiposPlanilla")) {
            TiposPlanillaDto tipoPlanillaDto = tbResultados.getSelectionModel().getSelectedItem();

            if (tipoPlanillaDto != null) {
                AppContext.getInstance().set("Filtro", tipoPlanillaDto);
                tbResultados.getItems().clear();
                getStage().close();
            } else {
                new Mensaje().show(AlertType.INFORMATION, "Filtros", "Tiene que seleccionar un objeto antes de continuar.");
            }
        } else {
            // Implementar búsqueda de empleados si es necesario
        }
    }

}
