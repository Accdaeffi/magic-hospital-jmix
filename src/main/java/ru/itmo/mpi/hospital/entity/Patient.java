package ru.itmo.mpi.hospital.entity;

import io.jmix.core.Messages;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;

@JmixEntity
@Table(name = "PATIENT")
@Entity
@Data
@Builder
public class Patient {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "is_male")
    private Boolean isMale;

    @Column(name = "is_mage")
    private Boolean isMage;

    @Column(name = "social_status")
    private String socialStatus;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public SocialStatus getSocialStatus() {
        return socialStatus == null ? null : SocialStatus.fromId(socialStatus);
    }

    public void setSocialStatus(SocialStatus socialStatus) {
        this.socialStatus = socialStatus == null ? null : socialStatus.getId();
    }

    @InstanceName
    @DependsOnProperties({"name", "surname"})
    public String getDisplayName(Messages messages) {
        return messages.formatMessage(
                getClass(), "Patient.instanceName", this.name, this.surname);
    }

}