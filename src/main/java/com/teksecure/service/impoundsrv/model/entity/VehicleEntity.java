package com.teksecure.service.impoundsrv.model.entity;

import com.teksecure.service.impoundsrv.model.payload.request.VehicleCreatePayload;
import com.teksecure.service.impoundsrv.model.payload.request.VehicleUpdatePayload;
import com.teksecure.service.impoundsrv.model.type.VehicleStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name="vehicles")
@Getter @Setter @NoArgsConstructor
public class VehicleEntity {
    public VehicleEntity(VehicleCreatePayload payload) throws IOException {
        this.make = payload.getMake();
        this.model = payload.getModel();
        this.type = payload.getType();
        this.isWanted = payload.getIsWanted();
        this.category = payload.getCategory();
        this.emirate = payload.getEmirate();
        this.code = payload.getCode();
        this.estimatedReleaseDate = payload.getEstimatedReleaseDate();
//
//        ZoneId zoneId = ZoneId.of("Asia/Dubai");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.registrationDateTime = LocalDateTime.parse(payload.getRegistrationDateTime(), formatter);

        this.caseNumber = payload.getCaseNumber();
        this.chassisNumber = payload.getChassisNumber();
        this.color = payload.getColor();
        this.parkingSlot = payload.getParkingSlot();
        this.numberPlate = payload.getNumberPlate();
        this.owner = new OwnerEntity(payload.getOwner());
        this.image1 = this.image2  = this.image3 = this.image4 = this.image5 = null;
        this.department = payload.getDepartment();
        this.vehicleStatus = VehicleStatus.REGISTERED.toValue();
        this.remarks = payload.getRemarks();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="MAKE")
    private String make;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "REG_DT_TIME")
    private LocalDateTime registrationDateTime;

    @Column(name = "RELEASE_DT")
    private LocalDate estimatedReleaseDate;

    @Column(name = "CASE_NUM")
    private String caseNumber;

    @Column(name = "CHASSIS_NUM")
    private String chassisNumber;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "PARKING_SLOT")
    private String parkingSlot;

    @Column(name = "EMIRATE")
    private String emirate;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NUMBER_PLATE")
    private String numberPlate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "OWNER_ID", referencedColumnName = "ID")
    private OwnerEntity owner;

    @Column(name = "DEPARTMENT")
    private String department;

    @Column(name = "STATUS")
    private String vehicleStatus;


    @Column(name = "IS_WANTED")
    private Boolean isWanted;

    @Column(name = "RELEASE_DOCUMENT")
    @Lob
    private byte[] releaseDocument;

    @Column(name = "RELEASE_DOCUMENT_TYPE")
    private String releaseDocumentType;

    @Column(name = "IMAGE1")
    @Lob
    private byte[] image1;

    @Column(name = "IMAGE_TYPE_1")
    private String imageType1;

    @Column(name = "IMAGE2")
    @Lob
    private byte[] image2;

    @Column(name = "IMAGE_TYPE_2")
    private String imageType2;

    @Column(name = "IMAGE3")
    @Lob
    private byte[] image3;

    @Column(name = "IMAGE_TYPE_3")
    private String imageType3;

    @Column(name = "IMAGE4")
    @Lob
    private byte[] image4;

    @Column(name = "IMAGE_TYPE_4")
    private String imageType4;

    @Column(name = "IMAGE5")
    @Lob
    private byte[] image5;

    @Column(name = "IMAGE_TYPE_5")
    private String imageType5;

    @Column(name = "REMARKS")
    private String remarks;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RELEASE_ID", referencedColumnName = "ID")
    private ReleaseIdentityEntity releaseIdentity;

    public void updateFieldsFromPayload(VehicleUpdatePayload payload) {
        if (payload.getMake() != null) {
            this.make = payload.getMake();
        }
        if (payload.getModel() != null) {
            this.model = payload.getModel();
        }
        if (payload.getType() != null) {
            this.type = payload.getType();
        }
        if (payload.getRegistrationDateTime() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            this.registrationDateTime = LocalDateTime.parse(payload.getRegistrationDateTime(), formatter);
        }
        if (payload.getEstimatedReleaseDate() != null) {
            this.estimatedReleaseDate = payload.getEstimatedReleaseDate();
        }
        if (payload.getCaseNumber() != null) {
            this.caseNumber = payload.getCaseNumber();
        }
        if (payload.getChassisNumber() != null) {
            this.chassisNumber = payload.getChassisNumber();
        }
        if (payload.getEmirate() != null) {
            this.emirate = payload.getEmirate();
        }
        if (payload.getCategory() != null) {
            this.category = payload.getCategory();
        }
        if (payload.getCode() != null) {
            this.code = payload.getCode();
        }
        if (payload.getColor() != null) {
            this.color = payload.getColor();
        }
        if (payload.getIsWanted() != null) {
            this.isWanted = payload.getIsWanted();
        }
        if (payload.getNumberPlate() != null) {
            this.numberPlate = payload.getNumberPlate();
        }
        if (payload.getOwner() != null) {
            this.owner = new OwnerEntity(payload.getOwner());
        }

        if (payload.getReleaseIdentity() != null) {
            this.releaseIdentity = new ReleaseIdentityEntity(payload.getReleaseIdentity());
        }

        if (payload.getRemarks() != null) {
            this.remarks = payload.getRemarks();
        }

        if (payload.getDepartment() != null) {
            this.department = payload.getDepartment();
        }
    }

    public void updateReleaseDocument(MultipartFile file) {
        try {
            this.releaseDocument = file.getBytes();
            this.releaseDocumentType = file.getContentType();
        } catch (IOException ex) {

        }
    }

    public void updateNewImage(List<MultipartFile> files) {

    }

    public void updateImage(List<MultipartFile> files) {
        try {
            switch(files.size()) {
                case 5:
                    this.image5 = files.get(4).getBytes();
                    this.imageType5 = files.get(4).getContentType();
                case 4:
                    this.image4 = files.get(3).getBytes();
                    this.imageType4 = files.get(4).getContentType();
                case 3:
                    this.image3 = files.get(2).getBytes();
                    this.imageType3 = files.get(2).getContentType();
                case 2:
                    this.image2 = files.get(1).getBytes();
                    this.imageType2 = files.get(1).getContentType();
                case 1:
                    this.image1 = files.get(0).getBytes();
                    this.imageType1 = files.get(0).getContentType();
                    break;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
