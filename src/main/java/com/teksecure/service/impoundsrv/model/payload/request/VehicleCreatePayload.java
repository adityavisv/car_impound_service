package com.teksecure.service.impoundsrv.model.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.teksecure.service.impoundsrv.model.type.Emirate;
import com.teksecure.service.impoundsrv.model.type.VehicleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @NoArgsConstructor
public class VehicleCreatePayload {
    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "make", required = true)
    private String make;

    @JsonProperty(value = "model", required = true)
    private String model;

    @JsonProperty(value = "type", required = true)
    private VehicleType type;

    @JsonProperty(value = "registrationDateTime", required = true)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm")
    private Date registrationDateTime;

    @JsonProperty(value = "estimatedReleaseDate", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date estimatedReleaseDate;

    @JsonProperty(value = "caseNumber", required = false)
    private String caseNumber;

    @JsonProperty(value = "chassisNumber", required = false)
    private String chassisNumber;

    @JsonProperty(value = "emirate", required = false)
    private Emirate emirate;

    @JsonProperty(value = "category", required = false)
    private String category;

    @JsonProperty(value = "code", required = false)
    private String code;

    @JsonProperty(value = "color", required = true)
    private String color;

    @JsonProperty(value = "parkingSlot", required = true)
    private String parkingSlot;

    @JsonProperty(value = "isWanted", required = true)
    private Boolean isWanted;

    @JsonProperty(value = "numberPlate", required = false)
    private String numberPlate;

    @JsonProperty(value = "owner")
    private OwnerPayload owner;

    @JsonProperty(value = "department", required = true)
    private String department;

    @JsonProperty(value = "remarks")
    private String remarks;
}
