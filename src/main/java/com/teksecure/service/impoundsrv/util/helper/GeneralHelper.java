package com.teksecure.service.impoundsrv.util.helper;

import com.teksecure.service.impoundsrv.model.entity.ParkingSpotEntity;
import com.teksecure.service.impoundsrv.model.payload.response.ParkingSpotPayload;

import java.util.ArrayList;
import java.util.List;

public class GeneralHelper {
    public static List<ParkingSpotPayload> convertParkingSpotEntityToPayload(List<ParkingSpotEntity> entityList) {
        List<ParkingSpotPayload> payloadList = new ArrayList<>();
        for (ParkingSpotEntity entity : entityList) {
            payloadList.add(new ParkingSpotPayload(entity));
        }
        return payloadList;
    }
}
