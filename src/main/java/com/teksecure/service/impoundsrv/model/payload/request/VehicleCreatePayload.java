package com.teksecure.service.impoundsrv.model.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.teksecure.service.impoundsrv.model.type.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter @Setter @NoArgsConstructor
public class VehicleCreatePayload {
    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "make", required = true)
    private String make;

    @JsonProperty(value = "model", required = true)
    private String model;

    @JsonProperty(value = "registrationDateTime", required = true)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm")
    private Date registrationDateTime;

    @JsonProperty(value = "caseNumber", required = true)
    private Integer caseNumber;

    @JsonProperty(value = "mulkiaNumber", required = true)
    private Integer mulkiaNumber;

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
    private OwnerPayload owner;

    @JsonProperty(value = "department")
    private Department department;
}
