package com.teksecure.service.impoundsrv.service;

import com.teksecure.service.impoundsrv.model.entity.ParkingSpotEntity;
import com.teksecure.service.impoundsrv.model.entity.VehicleEntity;
import com.teksecure.service.impoundsrv.model.payload.*;
import com.teksecure.service.impoundsrv.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.teksecure.service.impoundsrv.util.Constants.*;

@Service
public class ParkingZoneService {
    private ZoneRepository zoneRepository;

    @Autowired
    public ParkingZoneService(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public ParkingSpotListPayload retrieveParkingZone(String zone, String ocStatus) {
        if (zone != null) {
            List<ParkingSpotEntity> allSpotsInZone = zoneRepository.retrieveParkingByZone(zone);
            if (ocStatus != null) {
                List<ParkingSpotEntity> spotsByOCStatus = allSpotsInZone
                        .stream()
                        .filter(p -> p.getOccupancyStatus().equals(ocStatus))
                        .collect(Collectors.toList());
                return new ParkingSpotListPayload(spotsByOCStatus);
            }
            return new ParkingSpotListPayload(allSpotsInZone);
        }
        else if (ocStatus != null) {
            List<ParkingSpotEntity> spotsByOcStatus = zoneRepository.retrieveParkingByOccupiedStatus(ocStatus);
            return new ParkingSpotListPayload(spotsByOcStatus);
        }
        else {
            List<ParkingSpotEntity> allSpots = new ArrayList<>();
            zoneRepository.findAll().forEach(allSpots::add);
            return new ParkingSpotListPayload(allSpots);
        }
    }

    public ParkingSpotEntity retrievParkingSpotBySlotIdentifier(String zone, Integer slotNumber) {
        ParkingSpotEntity matchParkingSpot = zoneRepository.retrieveParkingSpotBySlotIdentifier(zone, slotNumber);
        return matchParkingSpot;
    }

    public ParkingZoneListSummary retrieveParkingZoneSummaries(String zone) {
        if (zone != null) {
            List<ParkingSpotEntity> allSpotsInZone = zoneRepository.retrieveParkingByZone(zone);
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
            zoneRepository.findAll().forEach(allSpotsInLot::add);

            for (char zoneChar : ZONE_LABELS) {
                List<ParkingSpotEntity> spotsInZone = allSpotsInLot.stream()
                        .filter(s -> s.getZoneLabel().equals(zoneChar)).collect(Collectors.toList());
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

    public ParkingSpotEntity assignCarToParkingSpot(String zone, Integer slotNumber, VehicleCreatePayload payload) {
        ParkingSpotEntity entityToSave = new ParkingSpotEntity();
        entityToSave.setZoneLabel(zone);
        entityToSave.setSlotNumber(slotNumber);
        entityToSave.setOccupancyStatus("OCCUPIED");
        entityToSave.setOccupiedVehicle(new VehicleEntity(payload));

        ParkingSpotEntity savedEntity = zoneRepository.save(entityToSave);
        return savedEntity;
    }

    public Integer releaseCarFromParking(String zone, Integer slotNumber) {
        ParkingSpotEntity occupiedParkingSpot = zoneRepository.retrieveParkingSpotBySlotIdentifier(zone, slotNumber);
        if (occupiedParkingSpot.getOccupancyStatus().equals(OCCUPIED)) {
            occupiedParkingSpot.setOccupiedVehicle(null);
            occupiedParkingSpot.setOccupancyStatus(AVAILABLE);
            zoneRepository.save(occupiedParkingSpot);
            return 0;
        }
        return 1;
    }
}
