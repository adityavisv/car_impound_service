package com.teksecure.service.impoundsrv.controller;

import com.teksecure.service.impoundsrv.model.entity.ParkingSpotEntity;
import com.teksecure.service.impoundsrv.model.payload.*;
import com.teksecure.service.impoundsrv.service.ParkingZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/zone")
public class ZoneController {
    private ParkingZoneService service;

    @Autowired
    public ZoneController(ParkingZoneService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ParkingSpotListPayload> retrieveZone(
            @RequestParam(required = false) Optional<String> zone,
            @RequestParam(required = false) Optional<String> occupiedStatus) {
        ParkingSpotListPayload responsePayload = service.retrieveParkingZone(
                zone.isPresent() ? zone.get() : null,
                occupiedStatus.isPresent() ? occupiedStatus.get() : null
        );
        return new ResponseEntity<>(responsePayload, HttpStatus.OK);
    }

    @GetMapping(value="/parkingSpot")
    public ResponseEntity<ParkingSpotEntity> retrieveParkingSpotBySlotNum(@RequestParam String zone,
                                                                          @RequestParam Integer slotNumber) {
        ParkingSpotEntity matchParking = service.retrievParkingSpotBySlotIdentifier(zone, slotNumber);
        if (matchParking != null) {
            return new ResponseEntity<>(service.retrievParkingSpotBySlotIdentifier(zone, slotNumber),
                    HttpStatus.OK);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/summary")
    public ResponseEntity<ParkingZoneListSummary> retrieveParkingZoneSummaries(
            @RequestParam(required = false) Optional<String> zone) {
        ParkingZoneListSummary summaries = service.retrieveParkingZoneSummaries(
                zone.isPresent() ? zone.get() : null);
        return new ResponseEntity<>(summaries, HttpStatus.OK);
    }

    @PutMapping(value="/assign")
    public ResponseEntity<ParkingSpotEntity> assignCarToParking(
            @RequestParam String zone,
            @RequestParam Integer slotNumber,
            @RequestBody VehicleCreatePayload payload) {
        ParkingSpotEntity savedEntity = service.assignCarToParkingSpot(zone, slotNumber, payload);
        return new ResponseEntity<>(savedEntity, HttpStatus.CREATED);
    }

    @PutMapping(value = "/release")
    public ResponseEntity<GenericResponse> releaseCar(
            @RequestParam String zone,
            @RequestParam Integer slotNumber) {
        Integer updateRetCode = service.releaseCarFromParking(zone, slotNumber);
        if (updateRetCode == 0) {
            return new ResponseEntity<>(
                    new GenericResponse("Successfully released", 201),
                    HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(
                    new GenericResponse("Invalid Parking Slot Number", 400)
            , HttpStatus.BAD_REQUEST);
        }
    }
}
