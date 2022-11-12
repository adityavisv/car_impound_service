package com.teksecure.service.impoundsrv.model.entity;

import com.teksecure.service.impoundsrv.model.payload.request.VehicleCreatePayload;
import com.teksecure.service.impoundsrv.model.payload.request.VehicleUpdatePayload;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="vehicles")
@Getter @Setter @NoArgsConstructor
public class VehicleEntity {
    public VehicleEntity(VehicleCreatePayload payload) throws IOException {
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
        this.owner = new OwnerEntity(payload.getOwner());
        this.image1 = this.image2  = this.image3 = this.image4 = this.image5 = null;
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

    @Column(name = "RELEASE_DT")
    private Date releaseDate;

    @Column(name = "CASE_NUM")
    private String caseNumber;

    @Column(name = "MULKIA_NUM")
    private String mulkiaNumber;

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

    @Column(name = "IMAGE1")
    @Lob
    private byte[] image1;

    @Column(name = "IMAGE2")
    @Lob
    private byte[] image2;

    @Column(name = "IMAGE3")
    @Lob
    private byte[] image3;

    @Column(name = "IMAGE4")
    @Lob
    private byte[] image4;

    @Column(name = "IMAGE5")
    @Lob
    private byte[] image5;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RELEASE_ID", referencedColumnName = "ID")
    private ReleaseIdentityEntity releaseIdentity;

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
        if (payload.getReleaseIdentity() != null)
            this.releaseIdentity = new ReleaseIdentityEntity(payload.getReleaseIdentity());
        if (payload.getReleaseDate() != null)
            this.releaseDate = payload.getReleaseDate();
    }

    public void updateImage(List<MultipartFile> files) {

        try {
            switch(files.size()) {
                case 5:
                    this.image5 = files.get(4).getBytes();
                case 4:
                    this.image4 = files.get(3).getBytes();
                case 3:
                    this.image3 = files.get(2).getBytes();
                case 2:
                    this.image2 = files.get(1).getBytes();
                case 1:
                    this.image1 = files.get(0).getBytes();
                    break;
            }
        } catch (IOException ex) {

        }
    }
}
