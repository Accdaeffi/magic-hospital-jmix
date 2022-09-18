package ru.itmo.mpi.hospital.testsupport.testdata;

public interface TestDataProvisioning<Entity, Builder> {

    Builder defaultData();

    Entity save(Entity dto);
    Entity saveDefault();

    Entity create(Entity dto);
    Entity createDefault();
}
