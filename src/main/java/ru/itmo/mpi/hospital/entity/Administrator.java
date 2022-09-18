package ru.itmo.mpi.hospital.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@JmixEntity
@Entity
@Setter
@Getter

@NoArgsConstructor
public class Administrator extends User {

    @Column(name = "work_start_date")
    private Date workStartDate;

    @Column(name = "social_status")
    private String socialStatus;

    public SocialStatus getSocialStatus() {
        return socialStatus == null ? null : SocialStatus.fromId(socialStatus);
    }

    public void setSocialStatus(SocialStatus socialStatus) {
        this.socialStatus = socialStatus == null ? null : socialStatus.getId();
    }

    @Builder
    public Administrator(UUID id, Integer version, String username, String password, String firstName, String lastName, Boolean isMale, String email, Boolean active, String timeZoneId, Collection<? extends GrantedAuthority> authorities, Date workStartDate, String socialStatus) {
        super(id, version, username, password, firstName, lastName, isMale, email, active, timeZoneId, authorities);
        this.workStartDate = workStartDate;
        this.socialStatus = socialStatus;
    }

}