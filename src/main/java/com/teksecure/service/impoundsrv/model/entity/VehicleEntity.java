package com.teksecure.service.impoundsrv.model.entity;

import com.teksecure.service.impoundsrv.model.payload.VehicleCreatePayload;
import com.teksecure.service.impoundsrv.model.payload.VehicleUpdatePayload;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="vehicles")
@Getter @Setter @NoArgsConstructor
public class VehicleEntity {
    public VehicleEntity(VehicleCreatePayload payload) {
        this.make = payload.getMake();
        this.model = payload.getModel();
        this.registrationDateTime = payload.getRegistrationDateTime();
        this.caseNumber = payload.getCaseNumber();
        this.mulkiaNumber = payload.getMulkiaNumber();
        this.color = payload.getColor();
        this.parkingSlot = payload.getParkingSlot();
        this.isCaseInCourt = payload.getIsCaseInCourt();
        this.isCarToBeAuctioned = payload.getIsCarToBeAuctioned();
        this.numberPlate = payload.getNumberPlate();
        this.owner = payload.getOwner();
    }

    public VehicleEntity(VehicleUpdatePayload payload) {
        this.id = payload.getId();
        if (payload.getMake() != null && (!payload.getMake().isEmpty())) {
            this.make = payload.getMake();
        }
        this.make = payload.getMake();
        this.model = payload.getModel();
        this.registrationDateTime = payload.getRegistrationDateTime();
        this.caseNumber = payload.getCaseNumber();
        this.mulkiaNumber = payload.getMulkiaNumber();
        this.color = payload.getColor();
        this.parkingSlot = payload.getParkingSlot();
        this.isCaseInCourt = payload.getIsCaseInCourt();
        this.isCarToBeAuctioned = payload.getIsCarToBeAuctioned();
        this.numberPlate = payload.getNumberPlate();
        this.owner = payload.getOwner();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="MAKE")
    private String make;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "REG_DT_TIME")
    private Date registrationDateTime;

    @Column(name = "CASE_NUM")
    private Integer caseNumber;

    @Column(name = "MULKIA_NUM")
    private Integer mulkiaNumber;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "PARKING_SLOT")
    private String parkingSlot;

    @Column(name = "IS_CASE_IN_COURT")
    private Boolean isCaseInCourt;

    @Column(name = "IS_CAR_AUCTION")
    private Boolean isCarToBeAuctioned;

    @Column(name = "NUMBER_PLATE")
    private String numberPlate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "OWNER_ID", referencedColumnName = "ID")
    private OwnerEntity owner;

    @Column(name = "DEPT")
    private String department;

    public void updateFieldsFromPayload(VehicleUpdatePayload payload) {
        if (payload.getMake() != null) {
            this.make = payload.getMake();
        }
        if (payload.getModel() != null)
            this.model = payload.getModel();
        if (payload.getRegistrationDateTime() != null)
            this.registrationDateTime = payload.getRegistrationDateTime();
        if (payload.getCaseNumber() != null)
            this.caseNumber = payload.getCaseNumber();
        if (payload.getMulkiaNumber() != null)
            this.mulkiaNumber = payload.getMulkiaNumber();
        if (payload.getColor() != null)
            this.color = payload.getColor();
        if (payload.getParkingSlot() != null)
            this.parkingSlot = payload.getParkingSlot();
        if (payload.getIsCaseInCourt() != null)
            this.isCaseInCourt = payload.getIsCaseInCourt();
        if (payload.getIsCarToBeAuctioned() != null)
            this.isCarToBeAuctioned = payload.getIsCarToBeAuctioned();
        if (payload.getOwner() != null)
            this.owner = payload.getOwner();
    }
}
