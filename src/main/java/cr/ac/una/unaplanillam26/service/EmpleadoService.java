package cr.ac.una.unaplanillam26.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import cr.ac.una.unaplanillam26.model.Empleado;
import cr.ac.una.unaplanillam26.model.EmpleadoDto;
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
public class EmpleadoService {
    
    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
    
    public Respuesta getEmpleado(Long id) {
        try {
            TypedQuery<Empleado> qryEmpleado = em.createNamedQuery("Empleado.findById", Empleado.class);
            qryEmpleado.setParameter("id", id);
            EmpleadoDto empleadoDto = new EmpleadoDto(qryEmpleado.getSingleResult());
            return new Respuesta(true, "", "", "Empleado", empleadoDto); // Hacer el constructor para cada caso
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe un empleado con el id ingresado.", "getEmpleado NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Ocurrió un error al consultar el empleado.", ex);
            return new Respuesta(false, "Ocurrió un error al consultar el empleado.", "getEmpleado NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error obteniendo el empleado [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el empleado.", "getEmpleado " + ex.getMessage());
        }
    }
    
    public Respuesta guardarEmpleado(EmpleadoDto empleadoDto) {
        try {
            et = em.getTransaction();
            et.begin();
            
            Empleado empleado;
            if (empleadoDto.getId() != null && empleadoDto.getId() > 0) {
                empleado = em.find(Empleado.class, empleadoDto.getId());
                if (empleado == null) {
                    return new Respuesta(false, "No se encontró un empleado a modificar.", "guardarEmpleado NoResultException");
                }
                empleado.actualizar(empleadoDto);
                empleado = em.merge(empleado);
            } else {
                empleado = new Empleado(empleadoDto);
                em.persist(empleado);
            }
            et.commit();
            return new Respuesta(true, "", "", "Empleado", new EmpleadoDto(empleado));
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error guardando el empleado.", ex);
            return new Respuesta(false, "Error guardando el empleado.", "guardarEmpleado " + ex.getMessage());
        }
    }
    
    public Respuesta eliminarEmpleado(Long id) {
        try {
            et = em.getTransaction();
            et.begin();
            
            Empleado empleado;
            if (id != null && id > 0) {
                empleado = em.find(Empleado.class, id);
                if (empleado == null) {
                    return new Respuesta(false, "No se encontró el empleado a eliminar.", "eliminarEmpleado NoResultException");
                }
                em.remove(empleado);
            } else {
                return new Respuesta(false, "Favor consultar el empleado a eliminar.", "");
            }
            et.commit();
            return new Respuesta(true, "", "", "Empleado", new EmpleadoDto(empleado));
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error guardando el empleado.", ex);
            return new Respuesta(false, "Error guardando el empleado.", "guardarEmpleado " + ex.getMessage());
        }
    }

}
