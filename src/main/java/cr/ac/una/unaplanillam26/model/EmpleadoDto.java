package cr.ac.una.unaplanillam26.model;

import java.time.LocalDate;
import java.util.Objects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Dominique
 */
public class EmpleadoDto {
    
    private StringProperty id;
    private StringProperty nombre;
    private StringProperty primerApellido;
    private StringProperty segundoApellido;
    private StringProperty cedula;
    private ObjectProperty<String> genero;
    private StringProperty correo;
    private StringProperty usuario;
    private StringProperty clave;
    private ObjectProperty<LocalDate> fechaIngreso;
    private ObjectProperty<LocalDate> fechaSalida;
    private BooleanProperty administrador;
    private BooleanProperty activo;
    private Long version;
    private Boolean modificado;

    public EmpleadoDto() {
        this.id = new SimpleStringProperty("");
        this.nombre = new SimpleStringProperty("");
        this.primerApellido = new SimpleStringProperty("");
        this.segundoApellido = new SimpleStringProperty("");
        this.cedula = new SimpleStringProperty("");
        this.genero = new SimpleObjectProperty<>("M");
        this.correo = new SimpleStringProperty("");
        this.usuario = new SimpleStringProperty("");
        this.clave = new SimpleStringProperty("");
        this.fechaIngreso = new SimpleObjectProperty<>(LocalDate.now());
        this.fechaSalida = new SimpleObjectProperty<>();
        this.activo = new SimpleBooleanProperty(true);
        this.administrador = new SimpleBooleanProperty(false);
        this.modificado = false;
    }

    public EmpleadoDto(Empleado empleado) {
        this();
        this.id.set(empleado.getId().toString());
        this.nombre.set(empleado.getNombre() == null ? "" : empleado.getNombre());
        this.primerApellido.set(empleado.getPrimerApellido() == null ? "" : empleado.getPrimerApellido());
        this.segundoApellido.set(empleado.getSegundoApellido() == null ? "" : empleado.getSegundoApellido());
        this.cedula.set(empleado.getCedula() == null ? "" : empleado.getCedula());
        this.genero.set(empleado.getGenero() == null ? "M" : empleado.getGenero());
        this.correo.set(empleado.getCorreo() == null ? "" : empleado.getCorreo());
        this.usuario.set(empleado.getUsuario() == null ? "" : empleado.getUsuario());
        this.clave.set(empleado.getClave() == null ? "" : empleado.getClave());
        this.fechaIngreso.set(empleado.getFechaIngreso());
        this.fechaSalida.set(empleado.getFechaSalida());
        this.administrador.set("S".equals(empleado.getAdministrador()));
        this.activo.set("A".equals(empleado.getEstado()));
        this.version = empleado.getVersion();
    }

    public Long getId() {
        if (this.id.get() != null && !this.id.get().isBlank()) {
            return Long.valueOf(this.id.get());
        }
        return null;
    }

    public void setId(Long id) {
        this.id.set(id == null ? "" : id.toString());
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre == null ? "" : nombre);
    }

    public String getPrimerApellido() {
        return primerApellido.get();
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido.set(primerApellido == null ? "" : primerApellido);
    }

    public String getSegundoApellido() {
        return segundoApellido.get();
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido.set(segundoApellido == null ? "" : segundoApellido);
    }

    public String getCedula() {
        return cedula.get();
    }

    public void setCedula(String cedula) {
        this.cedula.set(cedula == null ? "" : cedula);
    }

    public String getGenero() {
        return genero.get();
    }

    public void setGenero(String genero) {
        this.genero.set(genero == null ? "M" : genero);
    }

    public String getCorreo() {
        return correo.get();
    }

    public void setCorreo(String correo) {
        this.correo.set(correo == null ? "" : correo);
    }

    public Boolean getAdministrador() {
        return this.administrador.get();
    }

    public void setAdministrador(Boolean administrador) {
        this.administrador.set(administrador);
    }

    public String getUsuario() {
        return usuario.get();
    }

    public void setUsuario(String usuario) {
        this.usuario.set(usuario == null ? "" : usuario);
    }

    public String getClave() {
        return clave.get();
    }

    public void setClave(String clave) {
        this.clave.set(clave == null ? "" : clave);
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso.get();
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso.set(fechaIngreso);
    }

    public LocalDate getFechaSalida() {
        return fechaSalida.get();
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida.set(fechaSalida);
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

    public StringProperty getPrimerApellidoProperty() {
        return primerApellido;
    }

    public StringProperty getSegundoApellidoProperty() {
        return segundoApellido;
    }

    public StringProperty getCedulaProperty() {
        return cedula;
    }

    public ObjectProperty<String> getGeneroProperty() {
        return genero;
    }

    public StringProperty getCorreoProperty() {
        return correo;
    }

    public StringProperty getUsuarioProperty() {
        return usuario;
    }

    public StringProperty getClaveProperty() {
        return clave;
    }

    public ObjectProperty<LocalDate> getFechaIngresoProperty() {
        return fechaIngreso;
    }

    public ObjectProperty<LocalDate> getFechaSalidaProperty() {
        return fechaSalida;
    }

    public BooleanProperty getActivoProperty() {
        return activo;
    }

    public BooleanProperty getAdministratorProperty() {
        return administrador;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final EmpleadoDto other = (EmpleadoDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "EmpleadoDto{" + "id=" + id + ", nombre=" + nombre + ", primerApellido=" + primerApellido + ", cedula=" + cedula + '}';
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }
    
}
