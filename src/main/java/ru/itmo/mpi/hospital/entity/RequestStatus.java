package ru.itmo.mpi.hospital.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum RequestStatus implements EnumClass<String> {

    INITIALISED("initialised"),
    PROCESSING("processing"),
    COMPLETED("completed");

    private String id;

    RequestStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static RequestStatus fromId(String id) {
        for (RequestStatus at : RequestStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}