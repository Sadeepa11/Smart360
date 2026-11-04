package com.smartcity360.model;

import jakarta.persistence.*;

@Entity
@Table(name = "zone_nodes")
public class ZoneNodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 30)
    private String zoneType; // CITY, DISTRICT, STREET, BUILDING

    private Long parentId;

    private boolean powerOn = true;

    private double baseUsageKwh = 0.0;

    public ZoneNodeEntity() {}

    public ZoneNodeEntity(String name, String zoneType, Long parentId, double baseUsageKwh) {
        this.name = name;
        this.zoneType = zoneType;
        this.parentId = parentId;
        this.baseUsageKwh = baseUsageKwh;
        this.powerOn = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getZoneType() { return zoneType; }
    public void setZoneType(String zoneType) { this.zoneType = zoneType; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public boolean isPowerOn() { return powerOn; }
    public void setPowerOn(boolean powerOn) { this.powerOn = powerOn; }
    public double getBaseUsageKwh() { return baseUsageKwh; }
    public void setBaseUsageKwh(double baseUsageKwh) { this.baseUsageKwh = baseUsageKwh; }
}
