package ru.itmo.mpi.hospital.entity;

import io.jmix.core.Messages;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

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
@Table(name = "REQUEST")
@Entity
@Data
public class Request {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "helper_id")
    private Helper helper;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disease_case_id", referencedColumnName = "id")
    private DiseaseCase diseaseCase;

    @Column(name = "required_penta_help")
    private Boolean requiredPentaHelp;

    @Column(name = "additional_info")
    private String additionalInfo;

    @Column(name = "request_status")
    private String requestStatus;

    @Column(name = "dust_amount_required")
    private Integer dustAmountRequired;

    @Column(name = "water_required")
    private Integer waterRequired;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public RequestStatus getRequestStatus() {
        return requestStatus == null ? null : RequestStatus.fromId(requestStatus);
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus == null ? null : requestStatus.getId();
    }

    @InstanceName
    @DependsOnProperties({"id"})
    public String getDisplayName(Messages messages) {
        return messages.formatMessage(getClass(), "Request.instanceName", id.toString());
    }

}