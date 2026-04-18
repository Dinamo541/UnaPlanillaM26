package cr.ac.una.unaplanillam26.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Dominique
 */
@SuppressWarnings("unused")
public class TiposPlanillaDto {

    // Variables
    private final StringProperty id;
    private final StringProperty codigo;
    private final StringProperty descripcion;
    private final StringProperty planillasXMes;
    private final BooleanProperty activo;
    private final ObservableList<EmpleadoDto> empleados;

    // Declaraciones
    public TiposPlanillaDto() {
        this.id = new SimpleStringProperty("");
        this.codigo = new SimpleStringProperty("");
        this.descripcion = new SimpleStringProperty("");
        this.planillasXMes = new SimpleStringProperty("");
        this.activo = new SimpleBooleanProperty(true);
        this.empleados = FXCollections.observableArrayList();
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

    public String getPlanillasXMes() {
        return planillasXMes.get();
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

    public void setPlanillasXMes(String planillasXMes) {
        this.planillasXMes.set(planillasXMes);
    }

    public void setActivo(Boolean activo) {
        this.activo.set(activo);
    }
}
