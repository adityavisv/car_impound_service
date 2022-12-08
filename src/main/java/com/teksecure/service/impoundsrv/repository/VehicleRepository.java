package com.teksecure.service.impoundsrv.repository;

import com.teksecure.service.impoundsrv.model.entity.VehicleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends CrudRepository<VehicleEntity, Integer> {
    @Query(value = "SELECT * FROM VEHICLES V", nativeQuery = true)
    List<VehicleEntity> fetchAllVehicles();

    @Query(value = "SELECT * FROM VEHICLES V WHERE V.PARKING_SLOT LIKE ':zone%'", nativeQuery = true)
    List<VehicleEntity> fetchAllVehiclesByZone();

    @Query(value = "SELECT * FROM VEHICLES V WHERE V.STATUS LIKE ':status'", nativeQuery = true)
    List<VehicleEntity> fetchAllVehiclesByStatus(String status);

    @Query(value = "SELECT * FROM VEHICLES V WHERE V.STATUS = 'REGISTERED'", nativeQuery = true)
    List<VehicleEntity> fetchAllRegisteredVehicles();

    @Query(value = "SELECT * FROM VEHICLES V WHERE V.STATUS = 'REGISTERED' AND V.RELEASE_DT IS NOT NULL AND V.RELEASE_DT >= CURDATE() AND V.RELEASE_DT < CURDATE() + INTERVAL 2 DAY", nativeQuery = true)
    List<VehicleEntity> fetchUpcomingReleasesVehicles();
}
