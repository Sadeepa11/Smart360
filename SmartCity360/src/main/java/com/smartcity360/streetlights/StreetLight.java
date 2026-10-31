package com.smartcity360.streetlights;

/**
 * Context object holding Extrinsic State (unique per physical street fixture)
 * and delegating shared operations to the Intrinsic Flyweight.
 */
public class StreetLight {
    private final String lightId;
    private final double latitude;
    private final double longitude;
    private boolean powerOn;
    private int brightnessPercent;
    private final StreetLightType type; // Shared flyweight reference

    public StreetLight(String lightId, double latitude, double longitude, StreetLightType type) {
        this.lightId = lightId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.powerOn = true;
        this.brightnessPercent = 100;
    }

    public void setPower(boolean on) {
        this.powerOn = on;
    }

    public void setBrightness(int percent) {
        this.brightnessPercent = Math.max(0, Math.min(100, percent));
    }

    public void operate() {
        type.renderOperation(lightId, latitude, longitude, powerOn, brightnessPercent);
    }

    public String getLightId() {
        return lightId;
    }

    public StreetLightType getType() {
        return type;
    }
}
