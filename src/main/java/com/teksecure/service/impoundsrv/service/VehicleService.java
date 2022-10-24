package com.teksecure.service.impoundsrv.service;

import com.teksecure.service.impoundsrv.model.entity.VehicleEntity;
import com.teksecure.service.impoundsrv.model.payload.VehicleCreatePayload;
import com.teksecure.service.impoundsrv.model.payload.VehicleUpdatePayload;
import com.teksecure.service.impoundsrv.repository.OwnerRepository;
import com.teksecure.service.impoundsrv.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService  {

    private VehicleRepository vehicleRepository;

    private OwnerRepository ownerRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, OwnerRepository ownerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.ownerRepository = ownerRepository;
    }

    public VehicleEntity retrieveVehicle(String vehicleId) {
        Integer vehicleIdInt = Integer.parseInt(vehicleId);
        VehicleEntity matchVehicle = vehicleRepository.findById(vehicleIdInt).orElse(null);
        return matchVehicle;
    }

    public VehicleEntity insertVehicle(VehicleCreatePayload payload) {
        VehicleEntity vehicleInsert = new VehicleEntity(payload);
        return vehicleRepository.save(vehicleInsert);
    }

    public VehicleEntity updateVehicle(VehicleUpdatePayload payload) {
        Integer vehicleId = payload.getId();

        // pull the matching vehicle
        VehicleEntity matchVehicle = vehicleRepository.findById(vehicleId).orElse(null);
        if (matchVehicle != null) {
            matchVehicle.updateFieldsFromPayload(payload);
            return vehicleRepository.save(matchVehicle);
        }
        else {
            return null;
        }
    }
}
