package com.teksecure.service.impoundsrv.service;

import com.teksecure.service.impoundsrv.model.entity.ParkingSpotEntity;
import com.teksecure.service.impoundsrv.model.payload.ParkingSpotListPayload;
import com.teksecure.service.impoundsrv.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
}
