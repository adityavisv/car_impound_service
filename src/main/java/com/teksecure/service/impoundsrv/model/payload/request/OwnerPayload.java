package com.teksecure.service.impoundsrv.model.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teksecure.service.impoundsrv.model.type.OwnerIdType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter @Setter @NoArgsConstructor
public class OwnerPayload {
    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(required = true)
    private OwnerIdType idType;

    @JsonProperty(required = true)
    private String firstName;

    @JsonProperty(required = true)
    private String lastName;

    @JsonProperty(required = false)
    private String emailAddress;

    @JsonProperty(required = true)
    private String idNumber;

    @JsonProperty(required = true)
    private String contactNumber;

    @JsonProperty(required = true)
    private String nationality;
}
