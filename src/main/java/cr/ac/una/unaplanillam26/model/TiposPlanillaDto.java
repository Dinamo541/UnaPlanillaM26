package cr.ac.una.unaplanillam26.model;

import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Dominique
 */
public class TiposPlanillaDto {

    // Variables
    private StringProperty id;
    private StringProperty nombre;
    private StringProperty descripcion;
    private BooleanProperty activo;

    // Declaraciones
    public TiposPlanillaDto() {
        this.id = new SimpleStringProperty("");
        this.nombre = new SimpleStringProperty("");
        this.descripcion = new SimpleStringProperty("");
        this.activo = new SimpleBooleanProperty(true);
    }

    // Funciones extra
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TiposPlanillaDto other = (TiposPlanillaDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "TiposPlanillaDto{" + "id=" + id + ", nombre=" + nombre + '}';
    }

    // Funciones de acceso
    public Long getId() {
        if (this.id.get() != null && !this.id.get().isBlank()) {
            return Long.valueOf(this.id.get());
        }
        return null;
    }

    public void setId(Long id) {
        this.id.set(id.toString());
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
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

    public StringProperty getNombreProperty() {
        return nombre;
    }

    public StringProperty getDescripcionProperty() {
        return descripcion;
    }

    public BooleanProperty getActivoProperty() {
        return activo;
    }
}
