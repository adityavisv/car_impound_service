package com.teksecure.service.impoundsrv.service;

import com.teksecure.service.impoundsrv.model.entity.ImageEntity;
import com.teksecure.service.impoundsrv.model.entity.ReleaseDocumentEntity;
import com.teksecure.service.impoundsrv.model.entity.VehicleEntity;
import com.teksecure.service.impoundsrv.model.payload.request.SearchCriteria;
import com.teksecure.service.impoundsrv.model.payload.request.VehicleCreatePayload;
import com.teksecure.service.impoundsrv.model.payload.request.VehicleUpdatePayload;
import com.teksecure.service.impoundsrv.model.payload.response.ImageListPayload;
import com.teksecure.service.impoundsrv.model.payload.response.ReleaseDocumentPayload;
import com.teksecure.service.impoundsrv.model.payload.response.VehicleListPayload;
import com.teksecure.service.impoundsrv.model.payload.response.VehicleResponsePayload;
import com.teksecure.service.impoundsrv.model.type.VehicleStatus;
import com.teksecure.service.impoundsrv.repository.ImageRepository;
import com.teksecure.service.impoundsrv.repository.ReleaseDocumentRepository;
import com.teksecure.service.impoundsrv.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService  {

    private ReleaseDocumentRepository releaseDocumentRepository;

    private VehicleRepository repository;

    private ImageRepository imageRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, ImageRepository imageRepository, ReleaseDocumentRepository releaseDocumentRepository) {
        this.repository = vehicleRepository;
        this.imageRepository = imageRepository;
        this.releaseDocumentRepository = releaseDocumentRepository;
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

    public List<ImageEntity> assignVehicleImage(Integer vehicleId, List<MultipartFile> files) {
        List<ImageEntity> savedEntities = new ArrayList<>();
        for (MultipartFile file : files) {
            ImageEntity imageEntity = new ImageEntity(file, vehicleId);
            imageRepository.save(imageEntity);
            savedEntities.add(imageEntity);
        }
        return savedEntities;
    }

    public ReleaseDocumentEntity assignReleaseDocument(Integer vehicleId, MultipartFile file) {
        ReleaseDocumentEntity newReleaseDocEntity = new ReleaseDocumentEntity(file, vehicleId);
        newReleaseDocEntity = releaseDocumentRepository.save(newReleaseDocEntity);
        if (newReleaseDocEntity != null) {
            return newReleaseDocEntity;
        } else {
            return null;
        }
    }

    public List<VehicleResponsePayload> searchVehicles(SearchCriteria criteria) {
        List<VehicleEntity> allVehicles = repository.fetchAllVehicles();
        if (criteria.getMake()  != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getMake().contains(criteria.getMake())).collect(Collectors.toList());

        if (criteria.getModel() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getModel().contains(criteria.getModel())).collect(Collectors.toList());

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

        if (criteria.getColor() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getColor().contains(criteria.getColor()))
                    .collect(Collectors.toList());
        if (criteria.getNumberPlate() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> (v.getNumberPlate() != null & v.getNumberPlate().contains(criteria.getNumberPlate())))
                    .collect(Collectors.toList());
        if (criteria.getStatusAction() != null) {
            if (criteria.getStartDate() != null && criteria.getEndDate() != null) {
                LocalDate startDate = criteria.getStartDate();
                LocalDate endDate = criteria.getEndDate();
                if (criteria.getStatusAction().equals(VehicleStatus.REGISTERED.toValue())) {
                    allVehicles = allVehicles.stream()
                            .filter(v -> (
                                    (v.getRegistrationDateTime().toLocalDate().isAfter(startDate) || v.getRegistrationDateTime().toLocalDate().isEqual(startDate)) &&
                                            (v.getRegistrationDateTime().toLocalDate().isEqual(endDate) || v.getRegistrationDateTime().toLocalDate().isBefore(endDate))))
                            .collect(Collectors.toList());
                }
                else if (criteria.getStatusAction().equals(VehicleStatus.APPROVED_FOR_RELEASE.toValue())) {
                    allVehicles = allVehicles.stream()
                            .filter(v -> v.getReleaseIdentity() != null)
                            .collect(Collectors.toList());
                    allVehicles = allVehicles.stream()
                            .filter(v -> (
                                    (v.getReleaseIdentity().getReleaseDateTime().toLocalDate().isAfter(startDate)
                                            || v.getReleaseIdentity().getReleaseDateTime().toLocalDate().isEqual(startDate))
                                            && (v.getReleaseIdentity().getReleaseDateTime().toLocalDate().isEqual(endDate)
                                            || v.getReleaseIdentity().getReleaseDateTime().toLocalDate().isBefore(endDate))))
                            .collect(Collectors.toList());
                }

            }
        }

        if (criteria.getOwnerFirstname() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> (v.getOwner() != null && v.getOwner().getFirstName() != null && v.getOwner().getFirstName().contains(criteria.getOwnerFirstname())))
                    .collect(Collectors.toList());
        if (criteria.getOwnerLastname() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> (v.getOwner() != null && v.getOwner().getLastName() != null && v.getOwner().getLastName().equals(criteria.getOwnerLastname())))
                    .collect(Collectors.toList());
        if (criteria.getCaseNumber() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> (v.getCaseNumber() != null && v.getCaseNumber().contains(criteria.getCaseNumber())))
                    .collect(Collectors.toList());
        if (criteria.getIsWanted() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getIsWanted().equals(criteria.getIsWanted()))
                    .collect(Collectors.toList());
        if (criteria.getChassisNumber() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> (v.getChassisNumber() != null && v.getChassisNumber().contains(criteria.getChassisNumber())))
                    .collect(Collectors.toList());
        if (criteria.getType() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getType().equals(criteria.getType().toValue()))
                    .collect(Collectors.toList());
        if (criteria.getEmirate() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> (v.getEmirate() != null && v.getEmirate().equals(criteria.getEmirate().toValue())))
                    .collect(Collectors.toList());
        if (criteria.getCategory() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> (v.getCategory() != null && v.getCategory().contains(criteria.getCategory())))
                    .collect(Collectors.toList());
        if (criteria.getCode() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> (v.getCode() != null && v.getCode().contains(criteria.getCode())))
                    .collect(Collectors.toList());
        if (criteria.getReleaseDate() != null)
            allVehicles = allVehicles.stream()
                    .filter(v ->
                            (v.getReleaseIdentity() != null && v.getReleaseIdentity().getReleaseDateTime() != null && v.getReleaseIdentity().getReleaseDateTime().toLocalDate().isEqual(criteria.getReleaseDate())))
                    .collect(Collectors.toList());
        if (criteria.getReleaseFirstname() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> (v.getReleaseIdentity() != null && v.getReleaseIdentity().getFirstName() != null && v.getReleaseIdentity().getFirstName().contains(criteria.getReleaseFirstname())))
                    .collect(Collectors.toList());
        if (criteria.getReleaseLastname() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> (v.getReleaseIdentity() != null && v.getReleaseIdentity().getLastName() != null && v.getReleaseIdentity().getLastName().contains(criteria.getReleaseLastname())))
                    .collect(Collectors.toList());
        if (criteria.getOwnerNationality() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> (v.getOwner() != null && v.getOwner().getNationality() != null &&  v.getOwner().getNationality().contains(criteria.getOwnerNationality())))
                    .collect(Collectors.toList());
        if (criteria.getEstimatedReleaseDate() != null)
            allVehicles = allVehicles.stream()
                    .filter(v -> (v.getEstimatedReleaseDate() != null && v.getEstimatedReleaseDate().isEqual(criteria.getEstimatedReleaseDate())))
                    .collect(Collectors.toList());
        if (criteria.getRemarksKeyword() != null) {
            allVehicles = allVehicles.stream()
                    .filter(v -> (v.getRemarks() != null &&
                            v.getRemarks().contains(criteria.getRemarksKeyword())))
                    .collect(Collectors.toList());
        }
        if (criteria.getStatus() != null) {
            allVehicles = allVehicles.stream()
                    .filter(v -> v.getVehicleStatus().equals(criteria.getStatus()))
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
                allVehicles.stream().filter(v -> v.getVehicleStatus().equals(VehicleStatus.APPROVED_FOR_RELEASE.toValue()))
                        .collect(Collectors.toList());
        List<VehicleResponsePayload> payloadList = new ArrayList<>();
        for (VehicleEntity entity : vehiclesInReleaseStatus) {
            payloadList.add(new VehicleResponsePayload(entity));
        }
        return new VehicleListPayload(payloadList);
    }

    public VehicleResponsePayload doFinalRelease(Integer vehicleId) {
        VehicleEntity entity = repository.findById(vehicleId).orElse(null);
        if (entity.getVehicleStatus().equals(VehicleStatus.APPROVED_FOR_RELEASE.toValue())) {
            entity.setVehicleStatus(VehicleStatus.RELEASED.toValue());
            entity = repository.save(entity);
            return new VehicleResponsePayload(entity);
        }
        return null;
    }

    public ImageListPayload fetchAllImagesOfVehicle(Integer vehicleId) {
        List<ImageEntity> allImagesOfVehicle = imageRepository.fetchAllImagesByVehicleId(vehicleId);
        return new ImageListPayload(allImagesOfVehicle);
    }

    public ReleaseDocumentPayload fetchReleaseDocByVehicle(Integer vehicleId) {
        List<ReleaseDocumentEntity> releaseDocEntities = releaseDocumentRepository.fetchReleaseDocByVehicleId(vehicleId);
        if (releaseDocEntities != null && releaseDocEntities.size() > 0)
            return new ReleaseDocumentPayload(releaseDocEntities.get(0));
        else
            return null;
    }

}
