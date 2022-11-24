package com.teksecure.service.impoundsrv.model.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Department {
    CID("CID"),
    DRUGS("DRUGS"),
    ALCOHOL("ALCOHOL"),
    TRAFFIC("TRAFFIC"),
    ACCIDENT_AND_OTHER("ACCIDENT_AND_OTHER");

    private final String idText;

    Department(final String text) {
        this.idText = text;
    }

    @JsonValue
    public String toValue() {
        return idText;
    }

}
