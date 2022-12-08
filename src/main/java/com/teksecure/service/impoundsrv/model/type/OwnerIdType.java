package com.teksecure.service.impoundsrv.model.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OwnerIdType {
    NONE(""),
    PASSPORT("Passport"),
    EMIRATES_ID("Emirates ID"),
    NATIONAL_ID("National ID"),
    DRIVING_LICENSE("Driving License");

    private final String idText;

    OwnerIdType(final String text) {
        this.idText = text;
    }

    @JsonValue
    public String toValue() {
        return idText;
    }
}
