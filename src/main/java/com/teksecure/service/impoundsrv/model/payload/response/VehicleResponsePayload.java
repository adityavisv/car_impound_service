package com.teksecure.service.impoundsrv.model.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.teksecure.service.impoundsrv.model.entity.OwnerEntity;
import com.teksecure.service.impoundsrv.model.entity.ReleaseIdentityEntity;
import com.teksecure.service.impoundsrv.model.entity.VehicleEntity;
import com.teksecure.service.impoundsrv.model.type.Department;
import com.teksecure.service.impoundsrv.model.type.Emirate;
import com.teksecure.service.impoundsrv.model.type.VehicleStatus;
import com.teksecure.service.impoundsrv.model.type.VehicleType;
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
        this.type = VehicleType.valueOf(entity.getType());
        this.registrationDateTime = entity.getRegistrationDateTime();
        this.registrationDateTime = entity.getRegistrationDateTime();
        this.isWanted = entity.getIsWanted();
        this.emirate = Emirate.valueOf(entity.getEmirate());
        this.category = entity.getCategory();
        this.code = entity.getCode();
        this.caseNumber = entity.getCaseNumber();
        this.chassisNumber = entity.getChassisNumber();
        this.parkingSlot = entity.getParkingSlot();
        this.numberPlate = entity.getNumberPlate();
        this.owner = entity.getOwner();
        this.releaseIdentity = entity.getReleaseIdentity();
        this.estimatedReleaseDate = entity.getEstimatedReleaseDate();
        if (entity.getDepartment() != null)
            this.department = Department.valueOf(entity.getDepartment());
        this.images = new ArrayList<>();
        this.vehicleStatus = VehicleStatus.valueOf(entity.getVehicleStatus());
        if (entity.getReleaseDocument() != null) {
            byte[] bytes = entity.getReleaseDocument();
            this.releaseDocument = Base64.getEncoder().encodeToString(bytes);
        }

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

    @JsonProperty(value = "type")
    private VehicleType type;

    @JsonProperty(value = "vehicleStatus")
    private VehicleStatus vehicleStatus;

    @JsonProperty(value = "registrationDateTime", required = true)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm")
    private Date registrationDateTime;

    @JsonProperty(value = "estimatedReleaseDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date estimatedReleaseDate;

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
    private Department department;

    @JsonProperty(value = "emirate")
    private Emirate emirate;

    @JsonProperty(value = "category")
    private String category;

    @JsonProperty(value = "code")
    private String code;

    @JsonProperty(value = "images")
    private List<String> images;

    @JsonProperty(value ="releaseIdentity")
    private ReleaseIdentityEntity releaseIdentity;

    @JsonProperty(value = "releaseDocument")
    private String releaseDocument;
}
