package com.teksecure.service.impoundsrv.model.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Department {
    CID("CID"),
    DRUGS("Drugs"),
    ALCOHOL("Alcohol"),
    TRAFFIC("Traffic"),
    ACCIDENT_AND_OTHER("Accident and other");

    private final String idText;

    Department(final String text) {
        this.idText = text;
    }

    @JsonValue
    public String toValue() {
        return idText;
    }

}
