package ru.itmo.mpi.hospital.security;

import ru.itmo.mpi.hospital.entity.Prayer;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "GodRowLevelRole", code = "god-row-level-role")
public interface GodRowLevelRole {

    @JpqlRowLevelPolicy(entityClass = Prayer.class,
            where = "{E}.god.id = :current_user_id")
    void prayer();
}