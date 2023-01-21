package com.teksecure.service.impoundsrv.model.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teksecure.service.impoundsrv.model.type.OwnerIdType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @NoArgsConstructor
public class ReleaseIdentityPayload {
    @JsonProperty(value = "id", required = true)
    private Integer id;

    @JsonProperty(value = "firstName", required = true)
    private String firstName;

    @JsonProperty(value = "lastName", required = true)
    private String lastName;

    @JsonProperty(value = "idType", required = true)
    private OwnerIdType idType;

    @JsonProperty(value = "idNumber", required = true)
    private String idNumber;

    @JsonProperty(value = "contactNumber", required = true)
    private String contactNumber;

    @JsonProperty(value = "emailAddress", required = true)
    private String emailAddress;

    @JsonProperty(value = "nationality", required = true)
    private String nationality;

    @JsonProperty(value = "releaseDocumentNumber", required = false)
    private String releaseDocumentNumber;
}
