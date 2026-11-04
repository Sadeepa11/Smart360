package com.smartcity360.model;

import jakarta.persistence.*;

@Entity
@Table(name = "streetlight_fixtures")
public class StreetLightFixtureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String lightCode;

    @Column(nullable = false, length = 80)
    private String model;

    private int wattage;
    private int lumens;
    private double latitude;
    private double longitude;
    private boolean powerOn = true;
    private int brightnessPercent = 100;

    public StreetLightFixtureEntity() {}

    public StreetLightFixtureEntity(String lightCode, String model, int wattage, int lumens, double latitude, double longitude) {
        this.lightCode = lightCode;
        this.model = model;
        this.wattage = wattage;
        this.lumens = lumens;
        this.latitude = latitude;
        this.longitude = longitude;
        this.powerOn = true;
        this.brightnessPercent = 100;
    }

    public Long getId() { return id; }
    public String getLightCode() { return lightCode; }
    public String getModel() { return model; }
    public int getWattage() { return wattage; }
    public int getLumens() { return lumens; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public boolean isPowerOn() { return powerOn; }
    public void setPowerOn(boolean powerOn) { this.powerOn = powerOn; }
    public int getBrightnessPercent() { return brightnessPercent; }
    public void setBrightnessPercent(int brightnessPercent) { this.brightnessPercent = brightnessPercent; }
}
