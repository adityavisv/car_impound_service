package com.teksecure.service.impoundsrv.controller;

import com.teksecure.service.impoundsrv.model.entity.ParkingSpotEntity;
import com.teksecure.service.impoundsrv.model.payload.request.ReleaseIdentityPayload;
import com.teksecure.service.impoundsrv.model.payload.request.VehicleCreatePayload;
import com.teksecure.service.impoundsrv.model.payload.response.ParkingSpotListPayload;
import com.teksecure.service.impoundsrv.model.payload.response.ParkingZoneListSummary;
import com.teksecure.service.impoundsrv.model.payload.response.VehicleListPayload;
import com.teksecure.service.impoundsrv.model.payload.response.VehicleResponsePayload;
import com.teksecure.service.impoundsrv.service.ParkingZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
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
    @PreAuthorize("hasRole('SUPERUSER') or hasRole('ADMIN')")
    public ResponseEntity<ParkingSpotListPayload> retrieveZone(
            @RequestParam(required = false) String zone,
            @RequestParam(required = false) String occupiedStatus) {
        ParkingSpotListPayload responsePayload = service.retrieveParkingZone(
                zone,
                occupiedStatus);
        return new ResponseEntity<>(responsePayload, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPERUSER') or hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('SUPERUSER') OR hasRole('ADMIN')")
    @GetMapping(value="/summary")
    public ResponseEntity<ParkingZoneListSummary> retrieveParkingZoneSummaries(
            @RequestParam(required = false) Optional<String> zone) {
        ParkingZoneListSummary summaries = service.retrieveParkingZoneSummaries(
                zone.isPresent() ? zone.get() : null);
        return new ResponseEntity<>(summaries, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SUPERUSER') OR hasRole('ADMIN')")
    @PutMapping(value="/assign")
    public ResponseEntity<ParkingSpotListPayload> assignCarToParking(
            @RequestParam("spot") List<String> parkingSpots,
            @RequestBody VehicleCreatePayload payload) {
        ParkingSpotListPayload savedPayloads = service.assignCarToParkingSpot(parkingSpots, payload);
        return new ResponseEntity<>(savedPayloads, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('SUPERUSER') OR hasRole('ADMIN')")
    @PutMapping(value = "/release")
    public ResponseEntity<VehicleResponsePayload> releaseCar(
            @RequestParam String zone,
            @RequestParam Integer slotNumber,
            @RequestBody ReleaseIdentityPayload ownerPayload) {
        VehicleResponsePayload updatedVehicle = service.releaseCarFromParking(zone, slotNumber, ownerPayload);
        if (updatedVehicle != null) {
            return new ResponseEntity<>(
                   updatedVehicle,
                    HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(
                    updatedVehicle
            , HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('SUPERUSER') or hasRole('ADMIN')")
    @GetMapping(value="/upcomingreleases")
    public ResponseEntity<VehicleListPayload> getUpcomingReleases(
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate) {

        VehicleListPayload upcomingReleaseVehicles = service.getUpcomingReleases(startDate, endDate);
        return new ResponseEntity<>(upcomingReleaseVehicles, HttpStatus.OK);

    }

}
