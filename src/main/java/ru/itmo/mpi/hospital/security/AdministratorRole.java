package ru.itmo.mpi.hospital.security;

import io.jmix.security.role.annotation.ResourceRole;

@ResourceRole(name = "Administrator", code = "administrator")
public interface AdministratorRole {
}