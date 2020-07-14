package com.piotr.nbp.database;

import com.piotr.nbp.entities.EurEntity;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DbAdapter {

    private EntityManager entityManager;

    public DbAdapter() {
        EntityManagerFactory factory = EntityMenagerFactoryBuilder.getEntityManagerFactory();
        try {
            entityManager = factory.createEntityManager();
        } catch (PersistenceException e) {
            System.out.println("[DB] ERROR");
            System.out.println(e.getMessage());
        }
    }

    public <T> void insert(T entity) {
        try {
            System.out.println("[DB] INSERTING");
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("[DB] Errom while inserting: " + e.getMessage());
        }
    }

    public boolean contain(Date date) {
        Query query = entityManager.createQuery("SELECT t FROM EurEntity t WHERE t.datetime = :datetim").setParameter("datetim", date);
        List<Object> result = query.getResultList();

        return (result.size() > 0) ? true : false;

       /* if (result.size() > 0)
            return true;
        else
            return false;*/
    }

}
