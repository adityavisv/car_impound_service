package com.teksecure.service.impoundsrv.model.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.teksecure.service.impoundsrv.model.entity.OwnerEntity;
import com.teksecure.service.impoundsrv.model.entity.ReleaseIdentityEntity;
import com.teksecure.service.impoundsrv.model.entity.VehicleEntity;
import com.teksecure.service.impoundsrv.model.type.File;
import com.teksecure.service.impoundsrv.model.type.VehicleStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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
        this.images = new ArrayList<>();
        this.vehicleStatus = VehicleStatus.valueOf(entity.getVehicleStatus());
        if (entity.getReleaseDocument() != null) {
            byte[] bytes = entity.getReleaseDocument();
            this.releaseDocument = new File(Base64.getEncoder().encodeToString(bytes), entity.getReleaseDocumentType());
        }

        if (entity.getImage1() != null) {
            byte[] bytes = entity.getImage1();
            File file = new File(Base64.getEncoder().encodeToString(bytes), entity.getImageType1());
            images.add(file);
        }
        if (entity.getImage2() != null) {
            byte[] bytes = entity.getImage2();
            File file = new File(Base64.getEncoder().encodeToString(bytes), entity.getImageType2());
            images.add(file);
        }
        if (entity.getImage3() != null) {
            byte[] bytes = entity.getImage3();
            File file = new File(Base64.getEncoder().encodeToString(bytes), entity.getImageType3());
            images.add(file);
        }
        if (entity.getImage4() != null) {
            byte[] bytes = entity.getImage4();
            File file = new File(Base64.getEncoder().encodeToString(bytes), entity.getImageType4());
            images.add(file);
        }
        if (entity.getImage5() != null) {
            byte[] bytes = entity.getImage5();
            File file = new File(Base64.getEncoder().encodeToString(bytes), entity.getImageType5());
            images.add(file);
        }

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

    @JsonProperty(value = "images")
    private List<File> images;

    @JsonProperty(value ="releaseIdentity")
    private ReleaseIdentityEntity releaseIdentity;

    @JsonProperty(value = "releaseDocument")
    private File releaseDocument;

    @JsonProperty(value = "remarks")
    private String remarks;
}
