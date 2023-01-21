package com.teksecure.service.impoundsrv.model.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teksecure.service.impoundsrv.model.entity.VehicleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class UpcomingReleaseResponse {

    @JsonProperty(value = "upcomingReleases")
    private List<VehicleResponsePayload> upcomingReleases;

    @JsonProperty(value = "missedReleases")
    private List<VehicleResponsePayload> missedReleases;


    public UpcomingReleaseResponse(List<VehicleEntity> upcomingReleaseEntities, List<VehicleEntity> missedReleaseEntities) {
        this.upcomingReleases = new ArrayList<>();
        this.missedReleases = new ArrayList<>();
        for (VehicleEntity entity : upcomingReleaseEntities) {
            upcomingReleases.add(new VehicleResponsePayload(entity));
        }

        for (VehicleEntity entity : missedReleaseEntities) {
            missedReleases.add(new VehicleResponsePayload(entity));
        }
    }

}
