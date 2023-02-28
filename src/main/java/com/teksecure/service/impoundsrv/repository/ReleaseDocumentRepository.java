package com.teksecure.service.impoundsrv.repository;

import com.teksecure.service.impoundsrv.model.entity.ReleaseDocumentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleaseDocumentRepository extends CrudRepository<ReleaseDocumentEntity, Integer> {
    @Query(value =  "select * from releasedocuments r where r.VEHICLE_ID = :vehicleId", nativeQuery = true)
    List<ReleaseDocumentEntity> fetchReleaseDocByVehicleId(Integer vehicleId);
}
