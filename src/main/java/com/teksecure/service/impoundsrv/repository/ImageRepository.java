package com.teksecure.service.impoundsrv.repository;

import com.teksecure.service.impoundsrv.model.entity.ImageEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImageRepository extends CrudRepository<ImageEntity, Integer> {

    @Query(value = "select * from images i where i.VEHICLE_ID = :vehicleId", nativeQuery = true)
    List<ImageEntity> fetchAllImagesByVehicleId(Integer vehicleId);
}
