package com.teksecure.service.impoundsrv.model.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ParkingZoneSummary {
    @JsonProperty(value = "zoneLabel")
    private String zoneLabel;

    @JsonProperty(value = "totalCapacity")
    private Integer totalCapacity;

    @JsonProperty(value = "availableCount")
    private Integer availableCount;

    @JsonProperty(value = "occupiedCount")
    private Integer occupiedCount;
}
