package com.teksecure.service.impoundsrv.model.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.teksecure.service.impoundsrv.model.entity.OwnerEntity;
import com.teksecure.service.impoundsrv.model.entity.ReleaseIdentityEntity;
import com.teksecure.service.impoundsrv.model.entity.VehicleEntity;
import com.teksecure.service.impoundsrv.model.type.VehicleStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
public class VehicleResponsePayload {

    public VehicleResponsePayload(VehicleEntity  entity) {
        this.id = entity.getId();
        this.make = entity.getMake();
        this.model = entity.getModel();
        this.color = entity.getColor();
        this.type = entity.getType();
        this.registrationDateTime = entity.getRegistrationDateTime();
        this.isWanted = entity.getIsWanted();
        this.emirate = entity.getEmirate();
        this.category = entity.getCategory();
        this.code = entity.getCode();
        this.caseNumber = entity.getCaseNumber();
        this.chassisNumber = entity.getChassisNumber();
        this.parkingSlot = entity.getParkingSlot();
        this.numberPlate = entity.getNumberPlate();
        this.owner = entity.getOwner();
        this.releaseIdentity = entity.getReleaseIdentity();
        this.estimatedReleaseDate = entity.getEstimatedReleaseDate();
        this.remarks = entity.getRemarks();
        if (entity.getDepartment() != null)
            this.department = entity.getDepartment();
        this.vehicleStatus = VehicleStatus.valueOf(entity.getVehicleStatus());

    }

    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "make", required = true)
    private String make;

    @JsonProperty(value = "model", required = true)
    private String model;

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "vehicleStatus")
    private VehicleStatus vehicleStatus;

    @JsonProperty(value = "registrationDateTime", required = true)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm")
    private LocalDateTime registrationDateTime;

    @JsonProperty(value = "estimatedReleaseDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate estimatedReleaseDate;

    @JsonProperty(value = "caseNumber", required = true)
    private String caseNumber;

    @JsonProperty(value = "chassisNumber", required = true)
    private String chassisNumber;

    @JsonProperty(value = "color", required = true)
    private String color;

    @JsonProperty(value = "parkingSlot", required = true)
    private String parkingSlot;

    @JsonProperty(value = "isWanted", required = true)
    private Boolean isWanted;

    @JsonProperty(value = "numberPlate")
    private String numberPlate;

    @JsonProperty(value = "owner")
    private OwnerEntity owner;

    @JsonProperty(value = "department")
    private String department;

    @JsonProperty(value = "emirate")
    private String emirate;

    @JsonProperty(value = "category")
    private String category;

    @JsonProperty(value = "code")
    private String code;

    @JsonProperty(value ="releaseIdentity")
    private ReleaseIdentityEntity releaseIdentity;

    @JsonProperty(value = "remarks")
    private String remarks;
}
