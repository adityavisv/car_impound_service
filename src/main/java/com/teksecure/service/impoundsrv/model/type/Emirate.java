package com.teksecure.service.impoundsrv.model.type;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Emirate {
    ABU_DHABI("ABU_DHABI"),
    AJMAN("AJMAN"),
    DUBAI("DUBAI"),
    FUJAIRAH("FUJAIRAH"),
    RAS_AL_KHAYMAH("RAS_AL_KHAYMAH"),
    SHARJAH("SHARJAH"),
    UMM_AL_QUWAIN("UMM_AL_QUWAIN");

    private final String display;

    Emirate(final String display) {
        this.display = display;
    }

    @JsonValue
    public String toValue() {
        return display;
    }


}
