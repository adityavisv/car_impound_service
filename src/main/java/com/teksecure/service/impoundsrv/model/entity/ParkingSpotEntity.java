package com.teksecure.service.impoundsrv.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PARKING_ZONE")
@Getter @Setter @NoArgsConstructor
public class ParkingSpotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ZONE_LABEL")
    private String zoneLabel;

    @Column(name = "SLOT_NUM")
    private Integer slotNumber;

    @Column(name = "OC_STATUS")
    private String occupancyStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "VEHICLE_ID", referencedColumnName = "ID")
    private VehicleEntity occupiedVehicle;
}
