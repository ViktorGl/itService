package com.company.itservice.app;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum MagicType implements EnumClass<String> {

    NEW_VALUE("A");

    private String id;

    MagicType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static MagicType fromId(String id) {
        for (MagicType at : MagicType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}