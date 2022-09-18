package ru.itmo.mpi.hospital.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import java.util.Collection;
import java.util.UUID;

@JmixEntity
@Entity
@NoArgsConstructor
public class God extends User {

    @Builder
    public God(UUID id, Integer version, String username, String password, String firstName, String lastName, Boolean isMale, String email, Boolean active, String timeZoneId, Collection<? extends GrantedAuthority> authorities) {
        super(id, version, username, password, firstName, lastName, isMale, email, active, timeZoneId, authorities);
    }
}