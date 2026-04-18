package cr.ac.una.unaplanillam26.model;

import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Dominique
 */
@SuppressWarnings("unused")
public class TiposPlanillaDto {

    private StringProperty id;
    private StringProperty codigo;
    private StringProperty descripcion;
    private StringProperty planillasXMes;
    private BooleanProperty activo;

    public TiposPlanillaDto() {
        this.id = new SimpleStringProperty("");
        this.codigo = new SimpleStringProperty("");
        this.descripcion = new SimpleStringProperty("");
        this.planillasXMes = new SimpleStringProperty("");
        this.activo = new SimpleBooleanProperty(true);
    }

    public Long getId() {
        if (this.id.get() != null && !this.id.get().isBlank()) {
            return Long.valueOf(this.id.get());
        }
        return null;
    }

    public void setId(Long id) {
        this.id.set(id.toString());
    }

    public String getCodigo() {
        return codigo.get();
    }

    public void setCodigo(String codigo) {
        this.codigo.set(codigo);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public String getPlanillasXMes() {
        return planillasXMes.get();
    }

    public void setPlanillasXMes(String planillasXMes) {
        this.planillasXMes.set(planillasXMes);
    }

    public Boolean getActivo() {
        return activo.get();
    }

    public void setActivo(Boolean activo) {
        this.activo.set(activo);
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

}
