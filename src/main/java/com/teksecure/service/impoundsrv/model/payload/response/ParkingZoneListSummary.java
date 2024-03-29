package com.teksecure.service.impoundsrv.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ParkingZoneListSummary {
    private List<ParkingZoneSummary> zoneSummaries;
}
