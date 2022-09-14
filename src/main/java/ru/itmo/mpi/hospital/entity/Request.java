package ru.itmo.mpi.hospital.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disease_case_id")
    private DiseaseCase diseaseCase;

    @Column(name = "required_penta_help")
    private Boolean requiredPentaHelp;

    @Column(name = "additional_info")
    private String additionalInfo;

    @OneToMany(mappedBy = "request")
    private List<RequestResource> requestResources;

    @Column(name = "request_status")
    private String requestStatus;

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

}