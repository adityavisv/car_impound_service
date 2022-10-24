package com.teksecure.service.impoundsrv.controller;

import com.teksecure.service.impoundsrv.model.entity.ParkingSpotEntity;
import com.teksecure.service.impoundsrv.model.payload.ParkingSpotListPayload;
import com.teksecure.service.impoundsrv.service.ParkingZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/zone")
public class ZoneController {
    private ParkingZoneService parkingZoneService;

    @Autowired
    public ZoneController(ParkingZoneService parkingZoneService) {
        this.parkingZoneService = parkingZoneService;
    }

    @GetMapping
    public ResponseEntity<ParkingSpotListPayload> retrieveZone(
            @RequestParam(required = false) Optional<String> zone,
            @RequestParam(required = false) Optional<String> occupiedStatus) {
        ParkingSpotListPayload responsePayload = parkingZoneService.retrieveParkingZone(
                zone.isPresent() ? zone.get() : null,
                occupiedStatus.isPresent() ? occupiedStatus.get() : null
        );
        return new ResponseEntity<>(responsePayload, HttpStatus.OK);
    }

    @GetMapping(value="/parkingSpot")
    public ResponseEntity<ParkingSpotEntity> retrieveParkingSpotBySlotNum(@RequestParam String zone,
                                                                          @RequestParam Integer slotNumber) {
        ParkingSpotEntity matchParking = parkingZoneService.retrievParkingSpotBySlotIdentifier(zone, slotNumber);
        if (matchParking != null) {
            return new ResponseEntity<>(parkingZoneService.retrievParkingSpotBySlotIdentifier(zone, slotNumber),
                    HttpStatus.OK);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
