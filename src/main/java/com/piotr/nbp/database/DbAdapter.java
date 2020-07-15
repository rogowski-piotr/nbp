package com.piotr.nbp.database;

import com.piotr.nbp.entities.Currency;
import com.piotr.nbp.enums.CurrencyType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
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

    public Currency getEntity(Date date, CurrencyType currency) {
        try {
            Query query = entityManager.createQuery("SELECT t FROM " + currency.type.getSimpleName() + " t WHERE t.datetime = :datetim").setParameter("datetim", date);
            Object result = query.getSingleResult();
            return (result == null) ? null : (Currency) result;
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Date> getMissingElements(Date dateFrom, Date dateTo, CurrencyType currency) {
        Query query = entityManager.createQuery("SELECT t FROM " + currency.type.getSimpleName() + " t WHERE t.datetime >= :dateFrom AND t.datetime <= :dateTo")
                                    .setParameter("dateFrom", dateFrom)
                                    .setParameter("dateTo", dateTo);

        List<Object> resultFromDatabase = query.getResultList();
        List<Date> dateList = new ArrayList<>();

        while (dateFrom.before(dateTo) || dateFrom.equals(dateTo)) {
            dateList.add(dateFrom);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFrom);
            calendar.add(Calendar.DATE, 1);
            dateFrom = calendar.getTime();
        }

        for (Object el : resultFromDatabase) {
            Date date = ((Currency)el).getDatetime();
            dateList.remove(date);
        }
        return dateList;
    }

}
