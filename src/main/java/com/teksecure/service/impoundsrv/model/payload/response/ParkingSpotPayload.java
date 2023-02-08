package com.teksecure.service.impoundsrv.model.payload.response;

import com.teksecure.service.impoundsrv.model.entity.ParkingSpotEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ParkingSpotPayload {

    public ParkingSpotPayload(ParkingSpotEntity entity) {
        this.id = entity.getId();
        this.zoneLabel = entity.getZoneLabel();
        this.slotNumber = entity.getSlotNumber();
        this.occupancyStatus = entity.getOccupancyStatus();
        if (entity.getOccupiedVehicle() != null)
            this.occupiedVehicle = new VehicleResponsePayload(entity.getOccupiedVehicle());
    }
    private Integer id;

    private String zoneLabel;

    private Integer slotNumber;

    private String occupancyStatus;

    private VehicleResponsePayload occupiedVehicle;
}
