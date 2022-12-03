package com.teksecure.service.impoundsrv.model.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.teksecure.service.impoundsrv.model.entity.OwnerEntity;
import com.teksecure.service.impoundsrv.model.type.VehicleType;
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

    @JsonProperty(value = "type")
    private VehicleType type;

    @JsonProperty(value = "registrationDateTime")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss")
    private Date registrationDateTime;

    @JsonProperty(value = "estimatedReleaseDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date estimatedReleaseDate;

    @JsonProperty(value = "caseNumber")
    private String caseNumber;

    @JsonProperty(value = "chassisNumber")
    private String chassisNumber;

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

    @JsonProperty(value = "release")
    private ReleaseIdentityPayload releaseIdentity;

    @JsonProperty(value = "department")
    private String department;
}
