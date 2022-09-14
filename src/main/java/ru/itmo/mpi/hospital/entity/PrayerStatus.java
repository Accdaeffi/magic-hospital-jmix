package ru.itmo.mpi.hospital.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum PrayerStatus implements EnumClass<String> {

    UNANSWERED("unanswered"),
    ACCEPTED("accepted"),
    REJECTED("rejected");

    private String id;

    PrayerStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PrayerStatus fromId(String id) {
        for (PrayerStatus at : PrayerStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}