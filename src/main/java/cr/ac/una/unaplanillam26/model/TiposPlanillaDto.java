package cr.ac.una.unaplanillam26.model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Dominique
 */
@SuppressWarnings("unused")
public class TiposPlanillaDto {

    // Variables
    private StringProperty id;
    private StringProperty codigo;
    private StringProperty descripcion;
    private StringProperty planillasXMes;
    private BooleanProperty activo;
    private Long version;
    private ObservableList<EmpleadoDto> empleados;
    private List<EmpleadoDto> empleadosEliminados;

    // Declaraciones
    public TiposPlanillaDto() {
        this.id = new SimpleStringProperty("");
        this.codigo = new SimpleStringProperty("");
        this.descripcion = new SimpleStringProperty("");
        this.planillasXMes = new SimpleStringProperty("");
        this.activo = new SimpleBooleanProperty(true);
        this.empleados = FXCollections.observableArrayList();
        this.empleadosEliminados = new ArrayList<>();
        this.version = 1L;
    }

    public TiposPlanillaDto(TipoPlanilla tipoPlanilla) {
        this();
        this.id = new SimpleStringProperty(tipoPlanilla.getId() != null ? tipoPlanilla.getId().toString() : "");
        this.codigo = new SimpleStringProperty(tipoPlanilla.getCodigo() != null ? tipoPlanilla.getCodigo() : "");
        this.descripcion = new SimpleStringProperty(tipoPlanilla.getDescripcion() != null ? tipoPlanilla.getDescripcion() : "");
        this.planillasXMes = new SimpleStringProperty(tipoPlanilla.getPlaxmes() != null ? tipoPlanilla.getPlaxmes().toString() : "");
        this.activo = new SimpleBooleanProperty("A".equals(tipoPlanilla.getEstado()));
        this.empleados = FXCollections.observableArrayList();
        this.version = tipoPlanilla.getVersion();
    }

    // Funciones extra
    public void setEmpleados(ObservableList<EmpleadoDto> empleados) {
        this.empleados.clear();
        if (empleados != null) {
            this.empleados.addAll(empleados);
        }
    }

    public ObservableList<EmpleadoDto> getEmpleados() {
        return empleados;
    }
    
    public void setEmpleadosEliminados(List<EmpleadoDto> empleadosEliminados) {
        this.empleados.clear();
        if (empleados != null) {
            this.empleadosEliminados = new ArrayList<>();
        }
    }
    
    public List<EmpleadoDto> getEmpleadosEliminados() {
        return empleadosEliminados;
    }

    // Getters
    public Long getId() {
        if (this.id.get() != null && !this.id.get().isBlank()) {
            return Long.valueOf(this.id.get());
        }
        return null;
    }

    public String getCodigo() {
        return codigo.get();
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public Integer getPlanillasXMes() {
        try {
            String val = planillasXMes.get();
            if (val == null || val.isBlank()) {
                return null;
            }
            return Integer.valueOf(val);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Boolean getActivo() {
        return activo.get();
    }

    public StringProperty getIdProperty() {
        return id;
    }

    public StringProperty getCodigoProperty() {
        return codigo;
    }

    public StringProperty getDescripcionProperty() {
        return descripcion;
    }

    public StringProperty getPlanillasXMesProperty() {
        return planillasXMes;
    }

    public BooleanProperty getActivoProperty() {
        return activo;
    }

    public Long getVersion() {
        return version;
    }

    // Setters
    public void setId(Long id) {
        if (id == null) {
            this.id.set("");
            return;
        }
        this.id.set(id.toString());
    }

    public void setCodigo(String codigo) {
        this.codigo.set(codigo);
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public void setPlanillasXMes(Integer planillasXMes) {
        if (planillasXMes == null) {
            this.planillasXMes.set("");
        } else {
            this.planillasXMes.set(planillasXMes.toString());
        }
    }

    public void setPlanillasXMes(String planillasXMes) {
        if (planillasXMes == null) {
            this.planillasXMes.set("");
        } else {
            this.planillasXMes.set(planillasXMes);
        }
    }

    public void setActivo(Boolean activo) {
        this.activo.set(activo);
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    
}
