package com.teksecure.service.impoundsrv.model.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor
public class VehicleUpdatePayload {

    @JsonProperty(value = "make")
    private String make;

    @JsonProperty(value = "model")
    private String model;

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "registrationDateTime")
    private String registrationDateTime;

    @JsonProperty(value = "estimatedReleaseDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate estimatedReleaseDate;

    @JsonProperty(value = "caseNumber")
    private String caseNumber;

    @JsonProperty(value = "chassisNumber")
    private String chassisNumber;

    @JsonProperty(value = "emirate")
    private String emirate;

    @JsonProperty(value = "category")
    private String category;

    @JsonProperty(value = "code")
    private String code;

    @JsonProperty(value = "color")
    private String color;

    @JsonProperty(value = "parkingSlot")
    private String parkingSlot;

    @JsonProperty(value = "isWanted")
    private Boolean isWanted;

    @JsonProperty(value = "numberPlate")
    private String numberPlate;

    @JsonProperty(value = "owner")
    private OwnerPayload owner;

    @JsonProperty(value = "release")
    private ReleaseIdentityPayload releaseIdentity;

    @JsonProperty(value = "remarks")
    private String remarks;

    @JsonProperty(value = "department")
    private String department;
}
