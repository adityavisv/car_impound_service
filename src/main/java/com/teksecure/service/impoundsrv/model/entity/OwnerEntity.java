package com.teksecure.service.impoundsrv.model.entity;


import com.teksecure.service.impoundsrv.model.payload.request.OwnerPayload;
import com.teksecure.service.impoundsrv.model.type.OwnerIdType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "owner")
@Getter @Setter @NoArgsConstructor
public class OwnerEntity {

    public OwnerEntity(OwnerPayload payload) {
        this.idType = payload.getIdType();
        this.firstName = payload.getFirstName();
        this.lastName = payload.getLastName();
        this.emailAddress = payload.getEmailAddress();
        this.idNumber = payload.getIdNumber();
        this.contactNumber = payload.getContactNumber();
        this.nationality = payload.getNationality();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ID_TYPE")
    private OwnerIdType idType;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL_ADD")
    private String emailAddress;

    @Column(name = "ID_NUM")
    private String idNumber;

    @Column(name = "CONTACT_NUM")
    private String contactNumber;

    @Column(name = "NATIONALITY")
    private String nationality;
}
