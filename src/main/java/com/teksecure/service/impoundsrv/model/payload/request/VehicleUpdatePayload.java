package com.teksecure.service.impoundsrv.model.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.teksecure.service.impoundsrv.model.entity.OwnerEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @NoArgsConstructor
public class VehicleUpdatePayload {

    @JsonProperty(value = "make")
    private String make;

    @JsonProperty(value = "model")
    private String model;

    @JsonProperty(value = "registrationDateTime")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss")
    private Date registrationDateTime;

    @JsonProperty(value = "caseNumber")
    private Integer caseNumber;

    @JsonProperty(value = "mulkiaNumber")
    private Integer mulkiaNumber;

    @JsonProperty(value = "color")
    private String color;

    @JsonProperty(value = "parkingSlot")
    private String parkingSlot;

    @JsonProperty(value = "isCaseInCourt")
    private Boolean isCaseInCourt;

    @JsonProperty(value = "isCarToBeAuctioned")
    private Boolean isCarToBeAuctioned;

    @JsonProperty(value = "numberPlate")
    private String numberPlate;

    @JsonProperty(value = "owner")
    private OwnerEntity owner;
}
