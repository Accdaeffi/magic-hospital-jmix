package ru.itmo.mpi.hospital.config;

import org.springframework.boot.CommandLineRunner;
import org.eclipse.persistence.config.PersistenceUnitProperties;

public class EclipseLinkConfig implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        PersistenceUnitProperties persistenceUnitProperties = new PersistenceUnitProperties();

    }
}
