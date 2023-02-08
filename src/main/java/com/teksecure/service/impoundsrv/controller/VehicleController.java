package com.teksecure.service.impoundsrv.controller;

import com.teksecure.service.impoundsrv.model.entity.VehicleEntity;
import com.teksecure.service.impoundsrv.model.payload.request.SearchCriteria;
import com.teksecure.service.impoundsrv.model.payload.request.VehicleCreatePayload;
import com.teksecure.service.impoundsrv.model.payload.request.VehicleUpdatePayload;
import com.teksecure.service.impoundsrv.model.payload.response.GenericResponse;
import com.teksecure.service.impoundsrv.model.payload.response.VehicleListPayload;
import com.teksecure.service.impoundsrv.model.payload.response.VehicleResponsePayload;
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
    @PreAuthorize("hasRole('SUPERUSER') or hasRole('ADMIN')")
    public ResponseEntity<VehicleEntity> retrieveVehicle(@RequestParam String vehicleId) {
        VehicleEntity matchVehicle = service.retrieveVehicle(vehicleId);
        if (matchVehicle != null)
            return new ResponseEntity<>(matchVehicle, HttpStatus.OK);
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPERUSER') or hasRole('ADMIN')")
    public ResponseEntity<VehicleEntity> addNewVehicle(@RequestBody VehicleCreatePayload payload) {
        VehicleEntity savedVehicle = service.insertVehicle(payload);
        return new ResponseEntity<>(savedVehicle, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('SUPERUSER') or hasRole('ADMIN')")
    public ResponseEntity<VehicleEntity> updateVehicle(
            @RequestParam Integer vehicleId,
            @RequestBody VehicleUpdatePayload payload) {
        VehicleEntity updatedVehicle = service.updateVehicle(vehicleId, payload);
        return new ResponseEntity<>(updatedVehicle, HttpStatus.CREATED);
    }

    @GetMapping(path = "/releasequeue")
    @PreAuthorize("hasRole('SUPERUSER') or hasRole('ADMIN') or hasRole('EXIT_OPERATOR')")
    public ResponseEntity<VehicleListPayload> retrieveReleaseVehicles() {
        VehicleListPayload releaseVehicles = service.retrieveReleaseQueue();
        return new ResponseEntity<>(releaseVehicles, HttpStatus.OK);
    }

    @PutMapping(path="/finalrelease")
    @PreAuthorize("hasRole('SUPERUSER') or hasRole('ADMIN') or hasRole('EXIT_OPERATOR')")
    public ResponseEntity<VehicleResponsePayload> doFinalRelease(@RequestParam Integer vehicleId) {
        VehicleResponsePayload finalReleasedPayload = service.doFinalRelease(vehicleId);
        return new ResponseEntity<>(finalReleasedPayload, HttpStatus.CREATED);
    }

    @PutMapping(path="/search")
    @PreAuthorize("hasRole('SUPERUSER') or hasRole('ADMIN')")
    public ResponseEntity<VehicleListPayload> searchVehicles(@RequestBody SearchCriteria criteria) {

        List<VehicleResponsePayload> matchingResults = service.searchVehicles(criteria);
        return new ResponseEntity<>(
                new VehicleListPayload(matchingResults),
                HttpStatus.OK
        );

    }

    @RequestMapping(method = RequestMethod.POST, path="/releasedocument", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('SUPERUSER') or hasRole('ADMIN')")
    public ResponseEntity<GenericResponse> assignReleaseDocument(@RequestParam("vehicleId") String vehicleId,
                                                                 @RequestParam("file") MultipartFile file) {
        Integer vehicleIdInt = Integer.parseInt(vehicleId);
        VehicleEntity updatedVehicle = service.assignReleaseDocument(vehicleIdInt, file);
        GenericResponse response;
        if (updatedVehicle != null) {
            response = new GenericResponse("Release document uploaded", 201);
        }
        else {
            response = new GenericResponse("Release document failed", 500);
        }
        return new ResponseEntity<>(response, response.getStatusCode() == 201 ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('SUPERUSER') or hasRole('ADMIN')")
    public ResponseEntity<GenericResponse> assignImageToVehicle(@RequestParam("vehicleId") String vehicleId,
                                                                @RequestParam("file") List<MultipartFile> files
                                                                ) {
        Integer vehicleIdInt = Integer.parseInt(vehicleId);
        VehicleEntity updatedVehicle = service.assignVehicleImage(vehicleIdInt, files);
        GenericResponse response;
        if (updatedVehicle != null) {
            response = new GenericResponse("Image uploaded", 201);
        }
        else {
            response = new GenericResponse("Image upload failed", 500);
        }
        return new ResponseEntity<>(response, response.getStatusCode() == 201 ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @RequestMapping(method = RequestMethod.PUT, path="/update")
    @PreAuthorize("hasRole('SUPERUSER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateVehicle(@RequestParam("vehicleId") String vehicleId,
                                                                @RequestBody VehicleUpdatePayload updatePayload) {
        Integer vehicleIdInt = Integer.parseInt(vehicleId);
        VehicleEntity updatedVehicle = service.updateVehicle(vehicleIdInt, updatePayload);
        if (updatedVehicle != null) {
            return new ResponseEntity<VehicleResponsePayload>(new VehicleResponsePayload(updatedVehicle), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<GenericResponse>(new GenericResponse("Error", 404), HttpStatus.NOT_FOUND);
        }
    }


}
