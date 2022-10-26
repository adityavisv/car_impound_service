package com.teksecure.service.impoundsrv.model.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teksecure.service.impoundsrv.model.entity.ParkingSpotEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ParkingSpotListPayload {
    
    @JsonProperty(value = "parkingSpots")
    private List<ParkingSpotEntity> parkingSpots;
}
