package com.teksecure.service.impoundsrv.model.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teksecure.service.impoundsrv.model.entity.ReleaseDocumentEntity;
import com.teksecure.service.impoundsrv.model.type.File;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Base64;

@Getter @Setter @NoArgsConstructor
public class ReleaseDocumentPayload {

    @JsonProperty(value = "vehicleId")
    private Integer vehicleId;

    @JsonProperty(value = "file")
    private File file;

    public ReleaseDocumentPayload(ReleaseDocumentEntity releaseDocumentEntity) {
        this.vehicleId = releaseDocumentEntity.getVehicleId();
        this.file = new File(Base64.getEncoder().encodeToString(releaseDocumentEntity.getBytes()), releaseDocumentEntity.getFiletype());
    }
}
