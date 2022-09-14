package ru.itmo.mpi.hospital.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum SocialStatus implements EnumClass<String> {

    COMMONER("commoner"),
    ARISTOCRAT("aristocrat"),
    SLAVE("slave");

    private String id;

    SocialStatus(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static SocialStatus fromId(String id) {
        for (SocialStatus at : SocialStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}