package com.teksecure.service.impoundsrv.model.entity;

import com.teksecure.service.impoundsrv.model.payload.request.ReleaseIdentityPayload;
import com.teksecure.service.impoundsrv.model.type.OwnerIdType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "release_identity")
@Getter @Setter @NoArgsConstructor
public class ReleaseIdentityEntity {

    public ReleaseIdentityEntity(ReleaseIdentityPayload payload) {
        this.firstName = payload.getFirstName();
        this.lastName = payload.getLastName();
        this.idType = payload.getIdType();
        this.idNumber = payload.getIdNumber();
        this.contactNumber = payload.getContactNumber();
        this.emailAddress = payload.getEmailAddress();
        this.nationality = payload.getNationality();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "ID_TYPE")
    private OwnerIdType idType;

    @Column(name = "ID_NUM")
    private String idNumber;

    @Column(name = "CONTACT_NUM")
    private String contactNumber;

    @Column(name = "EMAIL_ADD")
    private String emailAddress;

    @Column(name = "NATIONALITY")
    private String nationality;
}
