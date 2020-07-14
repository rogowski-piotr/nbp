package com.piotr.nbp.database;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.config.TargetServer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceUnitTransactionType;
import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.eclipse.persistence.config.PersistenceUnitProperties.*;

public class EntityMenagerFactoryBuilder {

    private static EntityManagerFactory factory;

    private EntityMenagerFactoryBuilder() { }

    static EntityManagerFactory getEntityManagerFactory() {
        return factory == null ? buildFactory() : factory;
    }

    private static EntityManagerFactory buildFactory() {
        factory = Persistence.createEntityManagerFactory("persistenceUnit");
        return factory;
    }

}
