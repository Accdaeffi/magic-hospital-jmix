package ru.itmo.mpi.hospital.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import lombok.Getter;
import lombok.Setter;
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
@Table(name = "PRAYER")
@Entity
@Getter
@Setter
public class Prayer {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "god_id")
    private God god;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disease_case_id", referencedColumnName = "id")
    private DiseaseCase diseaseCase;

    @InstanceName
    @Column(name = "pray_text")
    private String prayText;

    @Column(name = "status")
    private String prayerStatus;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public PrayerStatus getPrayerStatus() {
        return prayerStatus == null ? null : PrayerStatus.fromId(prayerStatus);
    }

    public void setPrayerStatus(PrayerStatus prayerStatus) {
        this.prayerStatus = prayerStatus == null ? null : prayerStatus.getId();
    }

}