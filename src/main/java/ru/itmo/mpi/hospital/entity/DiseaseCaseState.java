package ru.itmo.mpi.hospital.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum DiseaseCaseState implements EnumClass<String> {

    AT_WORK("at_work"),
    DEATH("death"),
    RECOVERY("recovery");

    private String id;

    DiseaseCaseState(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static DiseaseCaseState fromId(String id) {
        for (DiseaseCaseState at : DiseaseCaseState.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}