package com.teksecure.service.impoundsrv.model.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class VehicleListPayload {
    private List<VehicleCreatePayload> vehicles;

    public VehicleListPayload(List<VehicleCreatePayload> vehicles) {
        this.vehicles = vehicles;
    }
}
