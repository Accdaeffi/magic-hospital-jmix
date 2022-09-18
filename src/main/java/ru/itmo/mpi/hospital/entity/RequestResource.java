package ru.itmo.mpi.hospital.entity;

import io.jmix.core.Messages;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@JmixEntity
@Table(name = "REQUEST_RESOURCE")
@Entity
@Data
public class RequestResource {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private Request request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    private Resource resource;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "received_amount")
    private Integer receivedAmount;

    @InstanceName
    @DependsOnProperties({"resource", "request"})
    public String getDisplayName(Messages messages) {
        return messages.formatMessage(getClass(), "RequestResource.instanceName", resource.getName(), request.getId().toString());
    }

}