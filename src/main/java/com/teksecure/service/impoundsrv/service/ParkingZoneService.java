package com.teksecure.service.impoundsrv.service;

import com.teksecure.service.impoundsrv.model.entity.ParkingSpotEntity;
import com.teksecure.service.impoundsrv.model.entity.ReleaseIdentityEntity;
import com.teksecure.service.impoundsrv.model.entity.VehicleEntity;
import com.teksecure.service.impoundsrv.model.payload.request.ReleaseIdentityPayload;
import com.teksecure.service.impoundsrv.model.payload.request.VehicleCreatePayload;
import com.teksecure.service.impoundsrv.model.payload.response.ParkingSpotListPayload;
import com.teksecure.service.impoundsrv.model.payload.response.ParkingSpotPayload;
import com.teksecure.service.impoundsrv.model.payload.response.ParkingZoneListSummary;
import com.teksecure.service.impoundsrv.model.payload.response.ParkingZoneSummary;
import com.teksecure.service.impoundsrv.repository.VehicleRepository;
import com.teksecure.service.impoundsrv.repository.ZoneRepository;
import com.teksecure.service.impoundsrv.util.helper.GeneralHelper;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    public Integer releaseCarFromParking(String zone, Integer slotNumber, ReleaseIdentityPayload releaseIdentityPayload) {
        ParkingSpotEntity occupiedParkingSpot = repository.retrieveParkingSpotBySlotIdentifier(zone, slotNumber);
        if (occupiedParkingSpot.getOccupancyStatus().equals(OCCUPIED)) {
            VehicleEntity vehicle = occupiedParkingSpot.getOccupiedVehicle();
            vehicle.setParkingSlot(null);
            vehicle.setReleaseIdentity(new ReleaseIdentityEntity(releaseIdentityPayload));
            vehicleRepository.save(vehicle);
            occupiedParkingSpot.setOccupiedVehicle(null);
            occupiedParkingSpot.setOccupancyStatus(AVAILABLE);
            repository.save(occupiedParkingSpot);
            return 0;
        }
        return 1;
    }
}
