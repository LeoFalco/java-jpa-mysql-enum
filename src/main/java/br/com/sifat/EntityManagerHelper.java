package br.com.sifat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.text.html.parser.Entity;

public class EntityManagerHelper {
    private static EntityManager em;
    private static EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("PU");
        em = emf.createEntityManager();
    }

    private EntityManagerHelper() {
    }


    synchronized public static EntityManager getEntityManager() {
        return em;
    }
}
