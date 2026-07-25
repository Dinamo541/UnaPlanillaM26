package cr.ac.una.unaplanillam26.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Dominique
 */
@Entity
@Table(name = "PLAM_TIPOPLANILLAS", schema = "una")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPlanilla.findAll", query = "SELECT t FROM TipoPlanilla t"),
    @NamedQuery(name = "TipoPlanilla.findByTplaId", query = "SELECT t FROM TipoPlanilla t WHERE t.id = :tplaId")})
public class TipoPlanilla implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "TPLA_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "TPLA_CODIGO")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "TPLA_DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "TPLA_PLAXMES")
    private Integer plaXMes;
    @Column(name = "TPLA_ANOULTPLA")
    private BigInteger anoultpla;
    @Column(name = "TPLA_MESULTPLA")
    private BigInteger mesultpla;
    @Column(name = "TPLA_NUMULTPLA")
    private BigInteger numultpla;
    @Basic(optional = false)
    @Column(name = "TPLA_ESTADO")
    private String estado;
    @Basic(optional = false)
    @Column(name = "TPLA_VERSION")
    private Long version;
    @JoinTable(name = "PLAM_EMPLEADOSPLANILLA", joinColumns = {
        @JoinColumn(name = "EXP_IDTPLA", referencedColumnName = "TPLA_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "EXP_IDEMP", referencedColumnName = "EMP_ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Empleado> empleados;

    public TipoPlanilla() {
    }

    public TipoPlanilla(TiposPlanillaDto tiposPlanillaDto) {
        this.id = tiposPlanillaDto.getId();
        actualizar(tiposPlanillaDto);
    }
    
    public void actualizar(TiposPlanillaDto tiposPlanillaDto) {
        this.codigo = tiposPlanillaDto.getCodigo();
        this.descripcion = tiposPlanillaDto.getDescripcion();
        this.plaXMes = tiposPlanillaDto.getPlanillasXMes();
        this.estado = tiposPlanillaDto.getActivo() ? "A" : "I";
        this.version = tiposPlanillaDto.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPlaxmes() {
        return plaXMes;
    }

    public void setPlaxmes(Integer plaxmes) {
        this.plaXMes = plaxmes;
    }

    public BigInteger getAnoultpla() {
        return anoultpla;
    }

    public void setAnoultpla(BigInteger anoultpla) {
        this.anoultpla = anoultpla;
    }

    public BigInteger getMesultpla() {
        return mesultpla;
    }

    public void setMesultpla(BigInteger mesultpla) {
        this.mesultpla = mesultpla;
    }

    public BigInteger getNumultpla() {
        return numultpla;
    }

    public void setNumultpla(BigInteger numultpla) {
        this.numultpla = numultpla;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @XmlTransient
    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TipoPlanilla)) {
            return false;
        }
        TipoPlanilla other = (TipoPlanilla) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanillam26.model.TipoPlanilla[ id=" + id + " ]";
    }

}
