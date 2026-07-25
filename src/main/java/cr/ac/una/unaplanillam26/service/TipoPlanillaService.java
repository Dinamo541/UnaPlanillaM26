package cr.ac.una.unaplanillam26.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import cr.ac.una.unaplanillam26.model.Empleado;
import cr.ac.una.unaplanillam26.model.EmpleadoDto;
import cr.ac.una.unaplanillam26.model.TipoPlanilla;
import cr.ac.una.unaplanillam26.model.TiposPlanillaDto;
import cr.ac.una.unaplanillam26.util.EntityManagerHelper;
import cr.ac.una.unaplanillam26.util.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

/**
 * @author Dominique
 */
public class TipoPlanillaService {

    private EntityManager em = EntityManagerHelper.getManager(); 
    private EntityTransaction et;

    public Respuesta getTipoPlanilla(Long id) {
        try {
            TypedQuery<TipoPlanilla> qryTipoPlanilla = em.createNamedQuery("TipoPlanilla.findByTplaId",TipoPlanilla.class);
            qryTipoPlanilla.setParameter("tplaId", id);

            TipoPlanilla tipoPlanilla = (TipoPlanilla) qryTipoPlanilla.getSingleResult();
            TiposPlanillaDto tipoPlanillaDto = new TiposPlanillaDto(tipoPlanilla);
 
            for (Empleado empleado : tipoPlanilla.getEmpleados()) {
                tipoPlanillaDto.getEmpleados().add(new EmpleadoDto(empleado));
            }
            
            return new Respuesta(true, "", "", "TipoPlanilla", tipoPlanillaDto);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe un TipoPlanilla con el id ingresado.", "getTipoPlanilla NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Ocurrió un error al consultar el TipoPlanilla.", ex);
            return new Respuesta(false, "Ocurrió un error al consultar el TipoPlanilla.", "getTipoPlanilla NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error obteniendo el TipoPlanilla [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el TipoPlanilla.", "getTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta guardarTipoPlanilla(TiposPlanillaDto tipoPlanillaDto){
        try {
            et = em.getTransaction();
            et.begin();
            TipoPlanilla tipoPlanilla;
            
            if (tipoPlanillaDto.getId() != null && tipoPlanillaDto.getId() > 0) {
                tipoPlanilla = em.find(TipoPlanilla.class, tipoPlanillaDto.getId());
                
                if(tipoPlanilla == null) {
                    return new Respuesta(false, "No se encontró una planilla a modificar.", "guardarTipoPlanilla NoResultException");
                }
                
                tipoPlanilla.actualizar(tipoPlanillaDto);
                
                for (EmpleadoDto empleadoEliminado : tipoPlanillaDto.getEmpleadosEliminados()) {
                    tipoPlanilla.getEmpleados().removeIf((e) -> e.getId().equals(empleadoEliminado.getId()));
                }
      
                if(!tipoPlanillaDto.getEmpleados().isEmpty()) {
                    for (EmpleadoDto empleadoDto : tipoPlanillaDto.getEmpleados()) {
                        if(empleadoDto.getModificado()){
                            Empleado empleado = em.find(Empleado.class, empleadoDto.getId());
                            tipoPlanilla.getEmpleados().add(empleado);
                        }
                    }
                }
                
                tipoPlanilla = em.merge(tipoPlanilla);
            } else {
                if (tipoPlanillaDto.getVersion() == null) {
                    tipoPlanillaDto.setVersion(1L);
                }
                if (tipoPlanillaDto.getId() == null) {
                    Number nuevoId = em.createQuery(
                            "SELECT COALESCE(MAX(t.id), 0) + 1 FROM TipoPlanilla t", Number.class)
                            .getSingleResult();
                    tipoPlanillaDto.setId(nuevoId.longValue());
                }
                tipoPlanilla = new TipoPlanilla(tipoPlanillaDto);
                em.persist(tipoPlanilla);
            }
            et.commit();
            return new Respuesta(true,"","","TipoPlanilla",new TiposPlanillaDto(tipoPlanilla));
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error guardando la planilla", ex);
            return new Respuesta(false, "Error guardando la planilla.", "guardarTipoPlanilla" + ex.getMessage());
        }
    }

    public Respuesta eliminarTipoPlanilla (Long id){
        try {
            et = em.getTransaction();
            et.begin();
            TipoPlanilla tipoPlanilla;

            if (id != null && id > 0) {
                tipoPlanilla= em.find(TipoPlanilla.class,id);

                if (tipoPlanilla==null) {
                    return new Respuesta(false, "No se encontró la planilla a eliminar.", "eliminarTipoPlanilla NoResultException");
                }
                em.remove(tipoPlanilla);
            } else {
                return new Respuesta(false,"Favor consultar la planilla a eliminar.","");
            }

            et.commit();
            return new Respuesta(true,"","");
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error eliminando la planilla", ex);
            return new Respuesta(false, "Error eliminando la planilla.", "eliminarTipoPlanilla" + ex.getMessage());
        }
    }

}
