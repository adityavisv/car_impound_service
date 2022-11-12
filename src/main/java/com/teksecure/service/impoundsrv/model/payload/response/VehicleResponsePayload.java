package com.teksecure.service.impoundsrv.model.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.teksecure.service.impoundsrv.model.entity.OwnerEntity;
import com.teksecure.service.impoundsrv.model.entity.ReleaseIdentityEntity;
import com.teksecure.service.impoundsrv.model.entity.VehicleEntity;
import com.teksecure.service.impoundsrv.model.type.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class VehicleResponsePayload {

    public VehicleResponsePayload(VehicleEntity  entity) {
        this.id = entity.getId();
        this.make = entity.getMake();
        this.model = entity.getModel();
        this.color = entity.getColor();
        this.registrationDateTime = entity.getRegistrationDateTime();
        this.registrationDateTime = entity.getRegistrationDateTime();
        this.caseNumber = entity.getCaseNumber();
        this.mulkiaNumber = entity.getMulkiaNumber();
        this.parkingSlot = entity.getParkingSlot();
        this.isCaseInCourt = entity.getIsCaseInCourt();
        this.isCarToBeAuctioned = entity.getIsCarToBeAuctioned();
        this.numberPlate = entity.getNumberPlate();
        this.owner = entity.getOwner();
        this.releaseIdentity = entity.getReleaseIdentity();
        this.releaseDate = entity.getReleaseDate();
        this.images = new ArrayList<>();

        if (entity.getImage1() != null) {
            byte[] bytes = entity.getImage1();
            images.add(Base64.getEncoder().encodeToString(bytes));
        }
        if (entity.getImage2() != null) {
            images.add(Base64.getEncoder().encodeToString(entity.getImage2()));
        }
        if (entity.getImage3() != null) {
            images.add(Base64.getEncoder().encodeToString(entity.getImage3()));
        }
        if (entity.getImage4() != null) {
            images.add(Base64.getEncoder().encodeToString(entity.getImage4()));
        }
        if (entity.getImage5() != null) {
            images.add(Base64.getEncoder().encodeToString(entity.getImage5()));
        }

    }

    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "make", required = true)
    private String make;

    @JsonProperty(value = "model", required = true)
    private String model;

    @JsonProperty(value = "registrationDateTime", required = true)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm")
    private Date registrationDateTime;

    @JsonProperty(value = "releaseDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date releaseDate;

    @JsonProperty(value = "caseNumber", required = true)
    private String caseNumber;

    @JsonProperty(value = "mulkiaNumber", required = true)
    private String mulkiaNumber;

    @JsonProperty(value = "color", required = true)
    private String color;

    @JsonProperty(value = "parkingSlot", required = true)
    private String parkingSlot;

    @JsonProperty(value = "isCaseInCourt")
    private Boolean isCaseInCourt = false;

    @JsonProperty(value = "isCarToBeAuctioned")
    private Boolean isCarToBeAuctioned = false;

    @JsonProperty(value = "numberPlate")
    private String numberPlate;

    @JsonProperty(value = "owner")
    private OwnerEntity owner;

    @JsonProperty(value = "department")
    private Department department;

    @JsonProperty(value = "image")
    private List<String> images;

    @JsonProperty(value ="releaseIdentity")
    private ReleaseIdentityEntity releaseIdentity;
}
