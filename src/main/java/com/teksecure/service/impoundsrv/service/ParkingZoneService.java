package com.teksecure.service.impoundsrv.service;

import com.teksecure.service.impoundsrv.model.entity.ParkingSpotEntity;
import com.teksecure.service.impoundsrv.model.entity.ReleaseIdentityEntity;
import com.teksecure.service.impoundsrv.model.entity.VehicleEntity;
import com.teksecure.service.impoundsrv.model.payload.request.ReleaseIdentityPayload;
import com.teksecure.service.impoundsrv.model.payload.request.VehicleCreatePayload;
import com.teksecure.service.impoundsrv.model.payload.response.*;
import com.teksecure.service.impoundsrv.model.type.VehicleStatus;
import com.teksecure.service.impoundsrv.repository.VehicleRepository;
import com.teksecure.service.impoundsrv.repository.ZoneRepository;
import com.teksecure.service.impoundsrv.util.helper.GeneralHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.teksecure.service.impoundsrv.util.Constants.*;

@Service
public class ParkingZoneService {
    private ZoneRepository repository;

    private VehicleRepository vehicleRepository;


    @Autowired
    public ParkingZoneService(ZoneRepository zoneRepository, VehicleRepository vehicleRepository) {
        this.repository = zoneRepository;
        this.vehicleRepository= vehicleRepository;
    }

    public ParkingSpotListPayload retrieveParkingZone(String zone, String ocStatus) {
        if (zone != null) {
            List<ParkingSpotEntity> allSpotsInZone = repository.retrieveParkingByZone(zone);

            if (ocStatus != null) {
                List<ParkingSpotEntity> spotsByOCStatus = allSpotsInZone
                        .stream()
                        .filter(p -> p.getOccupancyStatus().equals(ocStatus))
                        .collect(Collectors.toList());
                List<ParkingSpotPayload> spotsByOCStatusPayload =
                        GeneralHelper.convertParkingSpotEntityToPayload(spotsByOCStatus);
                return new ParkingSpotListPayload(spotsByOCStatusPayload);
            }
            List<ParkingSpotPayload> allSpotsInZonePayload = GeneralHelper
                    .convertParkingSpotEntityToPayload(allSpotsInZone);
            return new ParkingSpotListPayload(allSpotsInZonePayload);
        }


        else if (ocStatus != null) {
            List<ParkingSpotEntity> spotsByOcStatus = repository.retrieveParkingByOccupiedStatus(ocStatus);
            return new ParkingSpotListPayload(GeneralHelper.convertParkingSpotEntityToPayload(spotsByOcStatus));
        }
        else {
            List<ParkingSpotEntity> allSpots = new ArrayList<>();
            repository.findAll().forEach(allSpots::add);
            return new ParkingSpotListPayload(GeneralHelper.convertParkingSpotEntityToPayload(allSpots));
        }
    }

    public ParkingSpotEntity retrievParkingSpotBySlotIdentifier(String zone, Integer slotNumber) {
        ParkingSpotEntity matchParkingSpot = repository.retrieveParkingSpotBySlotIdentifier(zone, slotNumber);
        return matchParkingSpot;
    }

    public ParkingZoneListSummary retrieveParkingZoneSummaries(String zone) {
        if (zone != null) {
            List<ParkingSpotEntity> allSpotsInZone = repository.retrieveParkingByZone(zone);
            int totalCapacity = allSpotsInZone.size();
            int occupiedCount = (int) allSpotsInZone.stream()
                    .filter(s -> s.getOccupancyStatus().equals("OCCUPIED"))
                    .count();
            int availableCount = totalCapacity - occupiedCount;
            ParkingZoneSummary summary = new ParkingZoneSummary(zone, totalCapacity, availableCount, occupiedCount);
            ParkingZoneListSummary summaries  = new ParkingZoneListSummary(Collections.singletonList(summary));
            return summaries;
        }
        else {
            List<ParkingZoneSummary> summariesList = new ArrayList<>();

            List<ParkingSpotEntity> allSpotsInLot = new ArrayList<>();
            repository.findAll().forEach(allSpotsInLot::add);

            for (char zoneChar : ZONE_LABELS) {
                List<ParkingSpotEntity> spotsInZone = allSpotsInLot.stream()
                        .filter(s -> s.getZoneLabel().equals(String.valueOf(zoneChar))).collect(Collectors.toList());
                int totalCapacity = spotsInZone.size();
                int occupiedCount = (int) spotsInZone.stream()
                        .filter(s -> s.getOccupancyStatus().equals("OCCUPIED"))
                        .count();
                int availableCount = totalCapacity - occupiedCount;
                ParkingZoneSummary summary = new ParkingZoneSummary(String.valueOf(zoneChar), totalCapacity, availableCount, occupiedCount);
                summariesList.add(summary);
            }

            return new ParkingZoneListSummary(summariesList);
        }
    }

    public ParkingSpotListPayload assignCarToParkingSpot(List<String> parkingSpots, VehicleCreatePayload payload) {
        VehicleEntity savedVehicle = null;
        List<ParkingSpotPayload> savedPayloads = new ArrayList<>();
        for (String spot : parkingSpots) {
            String zone = spot.substring(0,1);
            Integer slotNumber = Integer.parseInt(spot.substring(1));
            if (savedVehicle == null) {
                try {
                    savedVehicle = vehicleRepository.save(new VehicleEntity(payload));
                } catch (IOException ex) {}
            }
            ParkingSpotEntity entityToSave = repository.retrieveParkingSpotBySlotIdentifier(zone, slotNumber);
            entityToSave.setOccupiedVehicle(savedVehicle);
            entityToSave.setOccupancyStatus(OCCUPIED);
            ParkingSpotEntity savedEntity = repository.save(entityToSave);
            savedPayloads.add(new ParkingSpotPayload(savedEntity));
        }
        return new ParkingSpotListPayload(savedPayloads);
    }

    public VehicleResponsePayload releaseCarFromParking(String zone, Integer slotNumber, ReleaseIdentityPayload releaseIdentityPayload) {
        ParkingSpotEntity occupiedParkingSpot = repository.retrieveParkingSpotBySlotIdentifier(zone, slotNumber);
        VehicleResponsePayload vehicleResponsePayload = null;
        if (occupiedParkingSpot.getOccupancyStatus().equals(OCCUPIED)) {
            VehicleEntity vehicle = occupiedParkingSpot.getOccupiedVehicle();
            String[] parkingSlots = vehicle.getParkingSlot().split(",");

            vehicle.setReleaseIdentity(new ReleaseIdentityEntity(releaseIdentityPayload));
            vehicle.setVehicleStatus(VehicleStatus.APPROVED_FOR_RELEASE.toValue());
            vehicleResponsePayload = new VehicleResponsePayload(vehicleRepository.save(vehicle));

            for (String slot : parkingSlots) {
                String subZone = String.valueOf(slot.charAt(0));
                Integer subSlotNumber = Integer.parseInt(slot.substring(1));
                ParkingSpotEntity spotEntity = repository.retrieveParkingSpotBySlotIdentifier(subZone, subSlotNumber);
                spotEntity.setOccupancyStatus(AVAILABLE);
                spotEntity.setOccupiedVehicle(null);
                repository.save(spotEntity);
            }
        }
        return vehicleResponsePayload;
    }

    public UpcomingReleaseResponse getUpcomingReleases() {
        List<VehicleEntity> allRegisteredVehicles = vehicleRepository.fetchAllRegisteredVehicles();

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate dayAfterTomorrow = today.plusDays(2);

        List<VehicleEntity> upcomingReleaseEntities = allRegisteredVehicles.stream()
                .filter(v -> (v.getEstimatedReleaseDate() != null && v.getEstimatedReleaseDate().isAfter(yesterday) &&
                        v.getEstimatedReleaseDate().isBefore(dayAfterTomorrow)))
                .collect(Collectors.toList());

        List<VehicleEntity> missedReleaseEntities = allRegisteredVehicles.stream()
                .filter(v -> (v.getEstimatedReleaseDate() != null && v.getEstimatedReleaseDate().isBefore(today)))
                .collect(Collectors.toList());

        UpcomingReleaseResponse response = new UpcomingReleaseResponse(upcomingReleaseEntities, missedReleaseEntities);
        return response;
    }

    public VehicleResponsePayload reassignVehicle(Integer vehicleId, String zoneLabel, List<Integer> slotNumbers) {
        VehicleEntity vehicle = vehicleRepository.findById(vehicleId).orElse(null);


        if (vehicle != null) {
            String parkingSlot = vehicle.getParkingSlot();
//            if (slotNumbers.size() > 1) {
                String[] oldSlots = parkingSlot.split(",");
                for (String oldSlot : oldSlots) {
                    String oldSlotZone = oldSlot.substring(0, 1);
                    Integer oldSlotNumber = Integer.parseInt(oldSlot.substring(1));
                    ParkingSpotEntity matchParkingSpotEntity = repository.retrieveParkingSpotBySlotIdentifier(oldSlotZone, oldSlotNumber);
                    matchParkingSpotEntity.setOccupiedVehicle(null);
                    matchParkingSpotEntity.setOccupancyStatus(AVAILABLE);
                    repository.save(matchParkingSpotEntity);
                }

                for (Integer newSlotNumber : slotNumbers) {
                    ParkingSpotEntity matchParkingSpotEntity = repository.retrieveParkingSpotBySlotIdentifier(zoneLabel, newSlotNumber);
                    matchParkingSpotEntity.setOccupiedVehicle(vehicle);
                    matchParkingSpotEntity.setOccupancyStatus(OCCUPIED);
                    repository.save(matchParkingSpotEntity);
                }
//            }

            List<String> newSlotNumberStrings = new ArrayList<>();
            for (Integer newSlotNum : slotNumbers) {
                newSlotNumberStrings.add(zoneLabel + newSlotNum);
            }
            String newSlotJoinedString = String.join(",", newSlotNumberStrings);
            vehicle.setParkingSlot(newSlotJoinedString);
            vehicleRepository.save(vehicle);
        }
        return new VehicleResponsePayload(vehicle);
    }
}
