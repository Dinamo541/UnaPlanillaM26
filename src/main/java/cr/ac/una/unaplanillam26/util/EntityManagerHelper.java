/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.unaplanillam26.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author ccarranza
 */
public class EntityManagerHelper {

    private static final EntityManagerHelper SINGLENTON = new EntityManagerHelper();
    private static EntityManagerFactory emf;
    private static EntityManager em;
    
    public static EntityManagerHelper getInstance() {

        return SINGLENTON;
    }

    public static EntityManager getManager() {
        if (em == null) {
            try {
                if (emf == null) {
                    emf = Persistence.createEntityManagerFactory("UnaPlanillaPU");
                }
                em = emf.createEntityManager();
            } catch (Exception ex) {
                em = null;
                java.util.logging.Logger.getLogger(EntityManagerHelper.class.getName())
                        .log(java.util.logging.Level.SEVERE, "No fue posible crear el EntityManager.", ex);
            }
        }
        return em;
    }
}
