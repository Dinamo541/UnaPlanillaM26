package cr.ac.una.unaplanillam26.service;

import java.util.logging.Level;
import java.util.logging.Logger;

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
 *
 * @author Dominique
 */
public class TipoPlanillaService {

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public Respuesta getTipoPlanilla(Long id) {
        try {
            TypedQuery<TipoPlanilla> qryTipoPlanilla = em.createNamedQuery("TipoPlanilla.findById", TipoPlanilla.class);
            qryTipoPlanilla.setParameter("id", id);
            TiposPlanillaDto TiposPlanillaDto = new TiposPlanillaDto(qryTipoPlanilla.getSingleResult());
            return new Respuesta(true, "", "", "TipoPlanilla", TiposPlanillaDto); // Hacer el constructor para cada caso
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

    public Respuesta guardarTipoPlanilla(TiposPlanillaDto TiposPlanillaDto) {
        try {
            et = em.getTransaction();
            et.begin();
            
            TipoPlanilla TipoPlanilla;
            if (TiposPlanillaDto.getId() != null && TiposPlanillaDto.getId() > 0) {
                TipoPlanilla = em.find(TipoPlanilla.class, TiposPlanillaDto.getId());
                if (TipoPlanilla == null) {
                    return new Respuesta(false, "No se encontró un TipoPlanilla a modificar.", "guardarTipoPlanilla NoResultException");
                }
                TipoPlanilla.actualizar(TiposPlanillaDto);
                TipoPlanilla = em.merge(TipoPlanilla);
            } else {
                TipoPlanilla = new TipoPlanilla(TiposPlanillaDto);
                em.persist(TipoPlanilla);
            }
            et.commit();
            return new Respuesta(true, "", "", "TipoPlanilla", new TiposPlanillaDto(TipoPlanilla));
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error guardando el TipoPlanilla.", ex);
            return new Respuesta(false, "Error guardando el TipoPlanilla.", "guardarTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta eliminarTipoPlanilla(Long id) {
        try {
            et = em.getTransaction();
            et.begin();
            
            TipoPlanilla TipoPlanilla;
            if (id != null && id > 0) {
                TipoPlanilla = em.find(TipoPlanilla.class, id);
                if (TipoPlanilla == null) {
                    return new Respuesta(false, "No se encontró el TipoPlanilla a eliminar.", "eliminarTipoPlanilla NoResultException");
                }
                em.remove(TipoPlanilla);
            } else {
                return new Respuesta(false, "Favor consultar el TipoPlanilla a eliminar.", "");
            }
            et.commit();
            return new Respuesta(true, "", "", "TipoPlanilla", new TiposPlanillaDto(TipoPlanilla));
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error guardando el TipoPlanilla.", ex);
            return new Respuesta(false, "Error guardando el TipoPlanilla.", "guardarTipoPlanilla " + ex.getMessage());
        }
    }

}
