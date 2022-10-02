package ru.itmo.mpi.hospital.security;

import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;
import ru.itmo.mpi.hospital.entity.Request;

@RowLevelRole(name = "HelperRowLevelRole", code = "helper-row-level-role")
public interface HelperRowLevelRole {

    @JpqlRowLevelPolicy(entityClass = Request.class,
            where = "{E}.helper.id = :current_user_id")
    void requests();
}