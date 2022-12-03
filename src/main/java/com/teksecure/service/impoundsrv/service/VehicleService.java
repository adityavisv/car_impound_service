package com.teksecure.service.impoundsrv.service;

import com.teksecure.service.impoundsrv.model.entity.VehicleEntity;
import com.teksecure.service.impoundsrv.model.payload.request.SearchCriteria;
import com.teksecure.service.impoundsrv.model.payload.request.VehicleCreatePayload;
import com.teksecure.service.impoundsrv.model.payload.request.VehicleUpdatePayload;
import com.teksecure.service.impoundsrv.model.payload.response.VehicleListPayload;
import com.teksecure.service.impoundsrv.model.payload.response.VehicleResponsePayload;
import com.teksecure.service.impoundsrv.model.type.VehicleStatus;
import com.teksecure.service.impoundsrv.model.type.VehicleType;
import com.teksecure.service.impoundsrv.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

    public VehicleEntity assignVehicleImage(Integer vehicleId, List<MultipartFile> files) {
        VehicleEntity matchVehicle = repository.findById(vehicleId).orElse(null);
        if (matchVehicle != null) {
            matchVehicle.updateImage(files);
            return repository.save(matchVehicle);
        }
        else {
            return null;
        }
    }

    public VehicleEntity assignReleaseDocument(Integer vehicleId, MultipartFile file) {
        VehicleEntity matchVehicle = repository.findById(vehicleId).orElse(null);
        if (matchVehicle != null) {
            matchVehicle.updateReleaseDocument(file);
            return repository.save(matchVehicle);
        } else {
            return null;
        }
    }

    public List<VehicleResponsePayload> searchVehicles(SearchCriteria criteria) {
        List<VehicleEntity> allVehicles = repository.fetchAllVehicles();
        if (criteria.getMake()  != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getMake().equals(criteria.getMake())).collect(Collectors.toList());

        if (criteria.getModel() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getModel().equals(criteria.getModel())).collect(Collectors.toList());

        if (criteria.getZoneLabel() != null) {
            if (criteria.getSlot() != null) {
                String slotNumber = String.format("%s%s", criteria.getZoneLabel(), criteria.getSlot());
                allVehicles = allVehicles.stream()
                        .filter(v -> (v.getParkingSlot() != null && v.getParkingSlot().contains(slotNumber)))
                        .collect(Collectors.toList());
            }
            else {
                allVehicles = allVehicles.stream()
                        .filter(v -> (v.getParkingSlot() != null && v.getParkingSlot().startsWith(criteria.getZoneLabel())))
                        .collect(Collectors.toList());
            }
        }

        if (criteria.getSlot() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getParkingSlot().equals(criteria.getSlot())).collect(Collectors.toList());
        if (criteria.getColor() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getColor().equals(criteria.getColor()))
                    .collect(Collectors.toList());
        if (criteria.getNumberPlate() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getNumberPlate().equals(criteria.getNumberPlate()))
                    .collect(Collectors.toList());
        if (criteria.getStartDate() != null && criteria.getEndDate() != null) {
            Date startDate = criteria.getStartDate();
            Date endDate = criteria.getEndDate();
            allVehicles = allVehicles.stream()
                    .filter(v -> (v.getRegistrationDateTime().after(startDate) && v.getRegistrationDateTime().before(endDate)))
                    .collect(Collectors.toList());
        }
        if (criteria.getOwnerFirstname() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getOwner().getFirstName().equals(criteria.getOwnerFirstname()))
                    .collect(Collectors.toList());
        if (criteria.getOwnerLastname() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getOwner().getLastName().equals(criteria.getOwnerLastname()))
                    .collect(Collectors.toList());
        if (criteria.getCaseNumber() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getCaseNumber().equals(criteria.getCaseNumber()))
                    .collect(Collectors.toList());
        if (criteria.getIsWanted() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getIsWanted().equals(criteria.getIsWanted()))
                    .collect(Collectors.toList());
        if (criteria.getChassisNumber() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getChassisNumber().equals(criteria.getChassisNumber()))
                    .collect(Collectors.toList());
        if (criteria.getType() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getType().equals(criteria.getType()))
                    .collect(Collectors.toList());
        if (criteria.getEmirate() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getEmirate().equals(criteria.getEmirate()))
                    .collect(Collectors.toList());
        if (criteria.getCategory() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getCategory().equals(criteria.getCategory()))
                    .collect(Collectors.toList());
        if (criteria.getCode() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getCode().equals(criteria.getCode()))
                    .collect(Collectors.toList());
        if (criteria.getReleaseDate() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getEstimatedReleaseDate().equals(criteria.getReleaseDate()))
                    .collect(Collectors.toList());
        if (criteria.getReleaseFirstname() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getReleaseIdentity().getFirstName().equals(criteria.getReleaseFirstname()))
                    .collect(Collectors.toList());
        if (criteria.getReleaseLastname() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getReleaseIdentity().getLastName().equals(criteria.getReleaseLastname()))
                    .collect(Collectors.toList());
        if (criteria.getOwnerNationality() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getOwner().getNationality().equals(criteria.getOwnerNationality()))
                    .collect(Collectors.toList());
        if (criteria.getEstimatedReleaseDate() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getEstimatedReleaseDate().equals(criteria.getEstimatedReleaseDate()))
                    .collect(Collectors.toList());
        if (criteria.getRemarksKeyword() != null) {
            allVehicles = allVehicles.stream()
                    .filter(v -> (v.getRemarks() != null &&
                            v.getRemarks().contains(criteria.getRemarksKeyword())))
                    .collect(Collectors.toList());
        }
        List<VehicleResponsePayload> finalPayloadList = new ArrayList<>();
        for (VehicleEntity entity :allVehicles) {
            finalPayloadList.add(new VehicleResponsePayload(entity));
        }
        return finalPayloadList;
    }

    public VehicleListPayload retrieveReleaseQueue() {
        List<VehicleEntity> allVehicles = repository.fetchAllVehicles();
        List<VehicleEntity> vehiclesInReleaseStatus =
                allVehicles.stream().filter(v -> v.getVehicleStatus().equals(VehicleStatus.PRE_RELEASE.toValue()))
                        .collect(Collectors.toList());
        List<VehicleResponsePayload> payloadList = new ArrayList<>();
        for (VehicleEntity entity : vehiclesInReleaseStatus) {
            payloadList.add(new VehicleResponsePayload(entity));
        }
        return new VehicleListPayload(payloadList);
    }

    public VehicleResponsePayload doFinalRelease(Integer vehicleId) {
        VehicleEntity entity = repository.findById(vehicleId).orElse(null);
        if (entity.getVehicleStatus().equals(VehicleStatus.PRE_RELEASE.toValue())) {
            entity.setVehicleStatus(VehicleStatus.RELEASE.toValue());
            entity = repository.save(entity);
            return new VehicleResponsePayload(entity);
        }
        return null;
    }

}
