package cr.ac.una.unaplanillam26.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author Dominique
 */
@Entity
@Table(name = "PLA_EMPLEADOS")
@NamedQueries({
    @NamedQuery(name = "EmpleadoPractica.findAll", query = "SELECT e FROM EmpleadoPractica e"),
    @NamedQuery(name = "EmpleadoPractica.findById", query = "SELECT e FROM EmpleadoPractica e WHERE e.id = :id"),
    @NamedQuery(name = "EmpleadoPractica.findByEmpNombre", query = "SELECT e FROM EmpleadoPractica e WHERE e.empNombre = :empNombre"),
    @NamedQuery(name = "EmpleadoPractica.findByEmpApellidos", query = "SELECT e FROM EmpleadoPractica e WHERE e.empApellidos = :empApellidos"),
    @NamedQuery(name = "EmpleadoPractica.findByEmpFechaing", query = "SELECT e FROM EmpleadoPractica e WHERE e.empFechaing = :empFechaing"),
    @NamedQuery(name = "EmpleadoPractica.findByEmpEstado", query = "SELECT e FROM EmpleadoPractica e WHERE e.empEstado = :empEstado"),
    @NamedQuery(name = "EmpleadoPractica.findByEmpVersion", query = "SELECT e FROM EmpleadoPractica e WHERE e.empVersion = :empVersion")})
public class EmpleadoPractica implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "EMP_NOMBRE")
    private String empNombre;
    @Basic(optional = false)
    @Column(name = "EMP_APELLIDOS")
    private String empApellidos;
    @Basic(optional = false)
    @Column(name = "EMP_FECHAING")
    @Temporal(TemporalType.TIMESTAMP)
    private Date empFechaing;
    @Basic(optional = false)
    @Column(name = "EMP_ESTADO")
    private String empEstado;
    @Basic(optional = false)
    @Column(name = "EMP_VERSION")
    private BigInteger empVersion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "salEmpid", fetch = FetchType.LAZY)
    private List<HistoricoSalario> historicoSalarioList;

    public EmpleadoPractica() {
    }

    public EmpleadoPractica(BigDecimal id) {
        this.id = id;
    }

    public EmpleadoPractica(BigDecimal id, String empNombre, String empApellidos, Date empFechaing, String empEstado, BigInteger empVersion) {
        this.id = id;
        this.empNombre = empNombre;
        this.empApellidos = empApellidos;
        this.empFechaing = empFechaing;
        this.empEstado = empEstado;
        this.empVersion = empVersion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getEmpNombre() {
        return empNombre;
    }

    public void setEmpNombre(String empNombre) {
        this.empNombre = empNombre;
    }

    public String getEmpApellidos() {
        return empApellidos;
    }

    public void setEmpApellidos(String empApellidos) {
        this.empApellidos = empApellidos;
    }

    public Date getEmpFechaing() {
        return empFechaing;
    }

    public void setEmpFechaing(Date empFechaing) {
        this.empFechaing = empFechaing;
    }

    public String getEmpEstado() {
        return empEstado;
    }

    public void setEmpEstado(String empEstado) {
        this.empEstado = empEstado;
    }

    public BigInteger getEmpVersion() {
        return empVersion;
    }

    public void setEmpVersion(BigInteger empVersion) {
        this.empVersion = empVersion;
    }

    public List<HistoricoSalario> getHistoricoSalarioList() {
        return historicoSalarioList;
    }

    public void setHistoricoSalarioList(List<HistoricoSalario> historicoSalarioList) {
        this.historicoSalarioList = historicoSalarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpleadoPractica)) {
            return false;
        }
        EmpleadoPractica other = (EmpleadoPractica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanillam26.model.Empleado[ id=" + id + " ]";
    }
    
}
