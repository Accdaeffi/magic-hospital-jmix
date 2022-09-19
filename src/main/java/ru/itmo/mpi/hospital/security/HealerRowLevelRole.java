package ru.itmo.mpi.hospital.security;

import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;
import ru.itmo.mpi.hospital.entity.DiseaseCase;
import ru.itmo.mpi.hospital.entity.Prayer;
import ru.itmo.mpi.hospital.entity.Request;

@RowLevelRole(name = "HealerRowLevelRole", code = "healer-row-level-role")
public interface HealerRowLevelRole {

    @JpqlRowLevelPolicy(entityClass = Prayer.class,
            where = "{E}.diseaseCase.healer.id = :current_user_id")
    void prayer();

    @JpqlRowLevelPolicy(entityClass = DiseaseCase.class,
            where = "{E}.healer.id = :current_user_id")
    void diseaseCase();

    @JpqlRowLevelPolicy(entityClass = Request.class,
            where = "{E}.diseaseCase.healer.id = :current_user_id")
    void request();
}