package com.teksecure.service.impoundsrv.controller;

import com.teksecure.service.impoundsrv.model.entity.VehicleEntity;
import com.teksecure.service.impoundsrv.model.payload.VehicleCreatePayload;
import com.teksecure.service.impoundsrv.model.payload.VehicleListPayload;
import com.teksecure.service.impoundsrv.model.payload.VehicleUpdatePayload;
import com.teksecure.service.impoundsrv.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/api/v1/vehicle")
public class VehicleController {

    private VehicleService service;

    @Autowired
    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<VehicleEntity> retrieveVehicle(@RequestParam String vehicleId) {
        VehicleEntity matchVehicle = service.retrieveVehicle(vehicleId);
        if (matchVehicle != null)
            return new ResponseEntity<>(matchVehicle, HttpStatus.OK);
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<VehicleEntity> addNewVehicle(@RequestBody VehicleCreatePayload payload) {
        VehicleEntity savedVehicle = service.insertVehicle(payload);
        return new ResponseEntity<>(savedVehicle, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<VehicleEntity> updateVehicle(@RequestBody VehicleUpdatePayload payload) {
        VehicleEntity updatedVehicle = service.updateVehicle(payload);
        return new ResponseEntity<>(updatedVehicle, HttpStatus.CREATED);
    }
}
