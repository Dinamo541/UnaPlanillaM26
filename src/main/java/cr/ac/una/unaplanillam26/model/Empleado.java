package cr.ac.una.unaplanillam26.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Dominique
 */
@Entity
@Table(name = "PLA_EMPLEADOS")
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findById", query = "SELECT e FROM Empleado e WHERE e.id = :id"),
    @NamedQuery(name = "Empleado.findByEmpNombre", query = "SELECT e FROM Empleado e WHERE e.empNombre = :empNombre"),
    @NamedQuery(name = "Empleado.findByEmpApellidos", query = "SELECT e FROM Empleado e WHERE e.empApellidos = :empApellidos"),
    @NamedQuery(name = "Empleado.findByEmpFechaing", query = "SELECT e FROM Empleado e WHERE e.empFechaing = :empFechaing"),
    @NamedQuery(name = "Empleado.findByEmpEstado", query = "SELECT e FROM Empleado e WHERE e.empEstado = :empEstado"),
    @NamedQuery(name = "Empleado.findByEmpVersion", query = "SELECT e FROM Empleado e WHERE e.empVersion = :empVersion")})
public class Empleado implements Serializable {

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

    public Empleado() {
    }

    public Empleado(BigDecimal id) {
        this.id = id;
    }

    public Empleado(BigDecimal id, String empNombre, String empApellidos, Date empFechaing, String empEstado, BigInteger empVersion) {
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
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
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
