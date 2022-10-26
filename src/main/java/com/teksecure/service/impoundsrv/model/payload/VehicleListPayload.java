package com.teksecure.service.impoundsrv.model.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teksecure.service.impoundsrv.model.entity.VehicleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VehicleListPayload {

    @JsonProperty(value = "vehicles")
    private List<VehicleEntity> vehicles;
}
