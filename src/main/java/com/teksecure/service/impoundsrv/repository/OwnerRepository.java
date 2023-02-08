package com.teksecure.service.impoundsrv.repository;

import com.teksecure.service.impoundsrv.model.entity.OwnerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends CrudRepository<OwnerEntity, Integer> {
//
//    @Query("SELECT * FROM OWNER O WHERE O.FIRST_NAME = :firstName AND O.LAST_NAME = :lastName")
//    public List<OwnerEntity> findByName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
