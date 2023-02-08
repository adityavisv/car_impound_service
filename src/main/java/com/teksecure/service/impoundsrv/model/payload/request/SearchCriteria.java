package com.teksecure.service.impoundsrv.model.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teksecure.service.impoundsrv.model.type.Emirate;
import com.teksecure.service.impoundsrv.model.type.VehicleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter @Setter @NoArgsConstructor
public class SearchCriteria {
    private String make;
    private String model;
    private String color;
    private String slot;
    private String numberPlate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String ownerFirstname;
    private String ownerLastname;
    private String caseNumber;
    private Boolean isWanted;
    private String chassisNumber;
    private VehicleType type;
    private Emirate emirate;
    private String category;
    private String code;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private String releaseFirstname;
    private String releaseLastname;
    private String ownerNationality;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate estimatedReleaseDate;
    private String remarksKeyword;
    private String zoneLabel;
    private String status;
}
