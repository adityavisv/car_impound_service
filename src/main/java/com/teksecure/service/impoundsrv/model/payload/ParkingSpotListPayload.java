package com.teksecure.service.impoundsrv.model.payload;

import com.teksecure.service.impoundsrv.model.entity.ParkingSpotEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ParkingSpotListPayload {
    private List<ParkingSpotEntity> parkingSpots;
}
