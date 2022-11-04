package com.teksecure.service.impoundsrv.service;

import com.teksecure.service.impoundsrv.model.entity.VehicleEntity;
import com.teksecure.service.impoundsrv.model.payload.request.VehicleCreatePayload;
import com.teksecure.service.impoundsrv.model.payload.request.VehicleUpdatePayload;
import com.teksecure.service.impoundsrv.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService  {

    private VehicleRepository repository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.repository = vehicleRepository;
    }

    public VehicleEntity retrieveVehicle(String vehicleId) {
        Integer vehicleIdInt = Integer.parseInt(vehicleId);
        VehicleEntity matchVehicle = repository.findById(vehicleIdInt).orElse(null);
        return matchVehicle;
    }

    public VehicleEntity insertVehicle(VehicleCreatePayload payload) {
        VehicleEntity vehicleInsert = null;
        try {
            vehicleInsert = new VehicleEntity(payload);
        } catch (IOException ex) {

        }
        return repository.save(vehicleInsert);
    }

    public VehicleEntity updateVehicle(Integer vehicleId, VehicleUpdatePayload payload) {
        // pull the matching vehicle
        VehicleEntity matchVehicle = repository.findById(vehicleId).orElse(null);
        if (matchVehicle != null) {
            matchVehicle.updateFieldsFromPayload(payload);
            return repository.save(matchVehicle);
        }
        else {
            return null;
        }
    }

    public VehicleEntity assignVehicleImage(Integer vehicleId, MultipartFile file) {
        VehicleEntity matchVehicle = repository.findById(vehicleId).orElse(null);
        if (matchVehicle != null) {
            matchVehicle.updateImage(file);
            return repository.save(matchVehicle);
        }
        else {
            return null;
        }
    }

    public List<VehicleEntity> searchVehicles(
            String make,
            String model,
            String slot,
            String color,
            String numberPlate
    ) {
        List<VehicleEntity> allVehicles = repository.fetchAllVehicles();
        if (make != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getMake().equals(make)).collect(Collectors.toList());

        if (model != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getModel().equals(model)).collect(Collectors.toList());

        if (slot != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getParkingSlot().equals(slot)).collect(Collectors.toList());
        if (color != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getColor().equals(color))
                    .collect(Collectors.toList());
        if (numberPlate != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getNumberPlate().equals(numberPlate))
                    .collect(Collectors.toList());
        return allVehicles;
    }
}
