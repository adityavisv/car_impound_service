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
}
