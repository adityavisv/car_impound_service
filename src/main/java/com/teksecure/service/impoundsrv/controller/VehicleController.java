package com.teksecure.service.impoundsrv.controller;

import com.teksecure.service.impoundsrv.model.entity.VehicleEntity;
import com.teksecure.service.impoundsrv.model.payload.VehicleCreatePayload;
import com.teksecure.service.impoundsrv.model.payload.VehicleListPayload;
import com.teksecure.service.impoundsrv.model.payload.VehicleUpdatePayload;
import com.teksecure.service.impoundsrv.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/vehicle")
public class VehicleController {

    private VehicleService service;

    @Autowired
    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<VehicleEntity> retrieveVehicle(@RequestParam String vehicleId) {
        VehicleEntity matchVehicle = service.retrieveVehicle(vehicleId);
        if (matchVehicle != null)
            return new ResponseEntity<>(matchVehicle, HttpStatus.OK);
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<VehicleEntity> addNewVehicle(@RequestBody VehicleCreatePayload payload) {
        VehicleEntity savedVehicle = service.insertVehicle(payload);
        return new ResponseEntity<>(savedVehicle, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<VehicleEntity> updateVehicle(@RequestBody VehicleUpdatePayload payload) {
        VehicleEntity updatedVehicle = service.updateVehicle(payload);
        return new ResponseEntity<>(updatedVehicle, HttpStatus.CREATED);
    }

    @GetMapping(path="/search")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<VehicleListPayload> searchVehicles(
            @RequestParam(required = false) String zone,
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String ownerFirstName,
            @RequestParam(required = false) String ownerLastName) {

        List<VehicleEntity> matchingResults = service.searchVehicles(
                make, model, zone, ownerFirstName, ownerLastName);
        return new ResponseEntity<>(
                new VehicleListPayload(matchingResults),
                HttpStatus.OK
        );

    }
}
