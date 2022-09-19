package ru.itmo.mpi.hospital.entity;

import io.jmix.core.Messages;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;

@JmixEntity
@Table(name = "DISEASE_CASE")
@Entity
@Getter
@Setter
public class DiseaseCase {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "patient_complaints")
    private String patientComplaints;

    @Column(name = "actions")
    private String actions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disease_id")
    private Disease disease;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrator_id")
    private Administrator registrator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "healer_id")
    private Healer healer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @OneToOne(mappedBy = "diseaseCase", fetch = FetchType.LAZY)
    private Prayer prayer;

    @OneToOne(mappedBy = "diseaseCase", fetch = FetchType.LAZY)
    private Request request;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @InstanceName
    @DependsOnProperties({"patient", "createdDate"})
    public String getDisplayName(Messages messages) {
        return messages.formatMessage(getClass(), "DiseaseCase.instanceName", patient.getDisplayName(), createdDate.toString());
    }

}