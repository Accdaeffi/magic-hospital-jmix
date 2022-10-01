package ru.itmo.mpi.hospital.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum PatientState implements EnumClass<String> {

    HEALTHY("healthy"),
    SICK("sick"),
    DISEASED("diseased");

    private String id;

    PatientState(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PatientState fromId(String id) {
        for (PatientState at : PatientState.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}