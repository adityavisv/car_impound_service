package com.teksecure.service.impoundsrv.repository;

import com.teksecure.service.impoundsrv.model.entity.ParkingSpotEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends CrudRepository<ParkingSpotEntity, Integer> {

    @Query(value = "SELECT * FROM parking_zone P WHERE P.ZONE_LABEL = :zone", nativeQuery = true)
    public List<ParkingSpotEntity> retrieveParkingByZone(@Param("zone") String zone);

    @Query(value = "SELECT * FROM parking_zone P WHERE P.OC_STATUS = :status", nativeQuery = true)
    public List<ParkingSpotEntity> retrieveParkingByOccupiedStatus(@Param("status") String status);

    @Query(value = "SELECT * FROM parking_zone P WHERE P.ZONE_LABEL = :zone AND P.SLOT_NUM = :slotNumber", nativeQuery = true)
    public ParkingSpotEntity retrieveParkingSpotBySlotIdentifier(@Param("zone") String zone, @Param("slotNumber") Integer slotNumber);
}
