package com.teksecure.service.impoundsrv.controller;

import com.teksecure.service.impoundsrv.model.entity.ParkingSpotEntity;
import com.teksecure.service.impoundsrv.model.payload.request.ReleaseIdentityPayload;
import com.teksecure.service.impoundsrv.model.payload.request.VehicleCreatePayload;
import com.teksecure.service.impoundsrv.model.payload.response.GenericResponse;
import com.teksecure.service.impoundsrv.model.payload.response.ParkingSpotListPayload;
import com.teksecure.service.impoundsrv.model.payload.response.ParkingZoneListSummary;
import com.teksecure.service.impoundsrv.service.ParkingZoneService;
import com.teksecure.service.impoundsrv.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/zone")
public class ZoneController {
    private ParkingZoneService service;

    @Autowired
    public ZoneController(ParkingZoneService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ParkingSpotListPayload> retrieveZone(
            @RequestParam(required = false) Optional<String> zone,
            @RequestParam(required = false) Optional<String> occupiedStatus) {
        ParkingSpotListPayload responsePayload = service.retrieveParkingZone(
                zone.isPresent() ? zone.get() : null,
                occupiedStatus.isPresent() ? occupiedStatus.get() : null
        );
        return new ResponseEntity<>(responsePayload, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @GetMapping(value="/summary")
    public ResponseEntity<ParkingZoneListSummary> retrieveParkingZoneSummaries(
            @RequestParam(required = false) Optional<String> zone) {
        ParkingZoneListSummary summaries = service.retrieveParkingZoneSummaries(
                zone.isPresent() ? zone.get() : null);
        return new ResponseEntity<>(summaries, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @PutMapping(value="/assign")
    public ResponseEntity<ParkingSpotEntity> assignCarToParking(
            @RequestParam String zone,
            @RequestParam Integer slotNumber,
            @RequestBody VehicleCreatePayload payload) {
        ParkingSpotEntity savedEntity = service.assignCarToParkingSpot(zone, slotNumber, payload);
        return new ResponseEntity<>(savedEntity, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @PutMapping(value = "/release")
    public ResponseEntity<GenericResponse> releaseCar(
            @RequestParam String zone,
            @RequestParam Integer slotNumber,
            @RequestBody ReleaseIdentityPayload ownerPayload) {
        Integer updateRetCode = service.releaseCarFromParking(zone, slotNumber, ownerPayload);
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
