package com.piotr.nbp.database;

import com.piotr.nbp.entities.Currency;
import com.piotr.nbp.entities.EurEntity;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.exceptions.DatabaseException;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DbAdapter {

    private EntityManager entityManager;

    public DbAdapter() {
        try {
            EntityManagerFactory factory = EntityMenagerFactoryBuilder.getEntityManagerFactory();
            entityManager = factory.createEntityManager();
        } catch (PersistenceException e) {
            System.out.println("[DB] ERROR");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public <T> void insert(T entity) {
        try {
            System.out.println("[DB] INSERTING INTO " + entity.getClass().getSimpleName());
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("[DB] Errom while inserting: " + e.getMessage());
        }
    }

    public Currency getEntity(Date date) {
        try {
            Query query = entityManager.createQuery("SELECT t FROM EurEntity t WHERE t.datetime = :datetim").setParameter("datetim", date);
            Object result = query.getSingleResult();
            return (result == null) ? null : (Currency) result;
        } catch (NoResultException e) {
            return null;
        }
    }

    public void getEntities(Date dateFrom, Date dateTo) {
        Query query = entityManager.createQuery("SELECT t FROM EurEntity t WHERE t.datetime >= :dateFrom AND t.datetime <= :dateTo")
                                    .setParameter("dateFrom", dateFrom)
                                    .setParameter("dateTo", dateTo);
        List<Object> result = query.getResultList();
        System.out.println("RESULT:");
        for (Object i : result) {
            System.out.println(i);
        }
    }

}
