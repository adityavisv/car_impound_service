package com.teksecure.service.impoundsrv.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Table(name = "images")
@Getter @Setter @NoArgsConstructor
public class ImageEntity {

    public ImageEntity(MultipartFile multipartFile, Integer vehicleId) {
        this.vehicleId = vehicleId;
        this.filetype = multipartFile.getContentType();
        try {
            this.bytes = multipartFile.getBytes();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new InternalError();
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "VEHICLE_ID")
    private Integer vehicleId;

    @Column(name = "FILETYPE")
    private String filetype;

    @Column(name = "BYTES")
    @Lob
    private byte[] bytes;
}
