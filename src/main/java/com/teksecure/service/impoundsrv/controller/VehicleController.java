package com.teksecure.service.impoundsrv.controller;

import com.teksecure.service.impoundsrv.model.entity.VehicleEntity;
import com.teksecure.service.impoundsrv.model.payload.request.VehicleCreatePayload;
import com.teksecure.service.impoundsrv.model.payload.response.GenericResponse;
import com.teksecure.service.impoundsrv.model.payload.response.VehicleListPayload;
import com.teksecure.service.impoundsrv.model.payload.request.VehicleUpdatePayload;
import com.teksecure.service.impoundsrv.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
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
    public ResponseEntity<VehicleEntity> updateVehicle(
            @RequestParam Integer vehicleId,
            @RequestBody VehicleUpdatePayload payload) {
        VehicleEntity updatedVehicle = service.updateVehicle(vehicleId, payload);
        return new ResponseEntity<>(updatedVehicle, HttpStatus.CREATED);
    }

    @GetMapping(path="/search")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<VehicleListPayload> searchVehicles(
            @RequestParam(required = false) String slot,
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String numberPlate) {

        List<VehicleEntity> matchingResults = service.searchVehicles(
                make, model, slot, color, numberPlate);
        return new ResponseEntity<>(
                new VehicleListPayload(matchingResults),
                HttpStatus.OK
        );

    }

    @RequestMapping(method = RequestMethod.POST, path = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse> assignImageToVehicle(@RequestParam("vehicleId") String vehicleId,
                                                                @RequestParam("file") List<MultipartFile> files
                                                                ) {
        Integer vehicleIdInt = Integer.parseInt(vehicleId);
        VehicleEntity updatedVehicle = service.assignVehicleImage(vehicleIdInt, files);
        GenericResponse response = null;
        if (updatedVehicle != null) {
            response = new GenericResponse("Image uploaded", 201);
        }
        else {
            response = new GenericResponse("Image upload failed", 500);
        }
        return new ResponseEntity<>(response, response.getStatusCode() == 201 ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
