package cr.ac.una.unaplanillam26.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 *
 * @author Dominique
 */
@Entity
@Table(name = "PLA_HISSALARIOS")
@NamedQueries({
    @NamedQuery(name = "HistoricoSalario.findAll", query = "SELECT h FROM HistoricoSalario h"),
    @NamedQuery(name = "HistoricoSalario.findById", query = "SELECT h FROM HistoricoSalario h WHERE h.id = :id"),
    @NamedQuery(name = "HistoricoSalario.findBySalAno", query = "SELECT h FROM HistoricoSalario h WHERE h.salAno = :salAno"),
    @NamedQuery(name = "HistoricoSalario.findBySalMes", query = "SELECT h FROM HistoricoSalario h WHERE h.salMes = :salMes"),
    @NamedQuery(name = "HistoricoSalario.findBySalMonto", query = "SELECT h FROM HistoricoSalario h WHERE h.salMonto = :salMonto"),
    @NamedQuery(name = "HistoricoSalario.findBySalVersion", query = "SELECT h FROM HistoricoSalario h WHERE h.salVersion = :salVersion")})
public class HistoricoSalario implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "SAL_ANO")
    private short salAno;
    @Basic(optional = false)
    @Column(name = "SAL_MES")
    private short salMes;
    @Basic(optional = false)
    @Column(name = "SAL_MONTO")
    private BigDecimal salMonto;
    @Basic(optional = false)
    @Column(name = "SAL_VERSION")
    private BigInteger salVersion;
    @JoinColumn(name = "SAL_EMPID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmpleadoPractica salEmpid;

    public HistoricoSalario() {
    }

    public HistoricoSalario(BigDecimal id) {
        this.id = id;
    }

    public HistoricoSalario(BigDecimal id, short salAno, short salMes, BigDecimal salMonto, BigInteger salVersion) {
        this.id = id;
        this.salAno = salAno;
        this.salMes = salMes;
        this.salMonto = salMonto;
        this.salVersion = salVersion;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public short getSalAno() {
        return salAno;
    }

    public void setSalAno(short salAno) {
        this.salAno = salAno;
    }

    public short getSalMes() {
        return salMes;
    }

    public void setSalMes(short salMes) {
        this.salMes = salMes;
    }

    public BigDecimal getSalMonto() {
        return salMonto;
    }

    public void setSalMonto(BigDecimal salMonto) {
        this.salMonto = salMonto;
    }

    public BigInteger getSalVersion() {
        return salVersion;
    }

    public void setSalVersion(BigInteger salVersion) {
        this.salVersion = salVersion;
    }

    public EmpleadoPractica getSalEmpid() {
        return salEmpid;
    }

    public void setSalEmpid(EmpleadoPractica salEmpid) {
        this.salEmpid = salEmpid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof HistoricoSalario)) {
            return false;
        }
        HistoricoSalario other = (HistoricoSalario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanillam26.model.HistoricoSalario[ id=" + id + " ]";
    }
    
}
