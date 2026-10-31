package com.smartcity360.streetlights;

import java.util.Arrays;

/**
 * Concrete Flyweight object containing Intrinsic State.
 * This state is immutable and safely shared across thousands of streetlight fixtures.
 */
public class StreetLightType {
    private final String model;
    private final int wattage;
    private final int colorTemperatureKelvin;
    private final int lumenOutput;
    private final String firmwareVersion;
    // Simulating internal specification & spectral calibration data array (heavy payload)
    private final byte[] calibrationCurve;

    public StreetLightType(String model, int wattage, int colorTemperatureKelvin, int lumenOutput, String firmwareVersion) {
        this.model = model;
        this.wattage = wattage;
        this.colorTemperatureKelvin = colorTemperatureKelvin;
        this.lumenOutput = lumenOutput;
        this.firmwareVersion = firmwareVersion;
        // 2KB calibration data per type
        this.calibrationCurve = new byte[2048];
        Arrays.fill(this.calibrationCurve, (byte) 0x7F);
    }

    public String getModel() {
        return model;
    }

    public int getWattage() {
        return wattage;
    }

    public int getColorTemperatureKelvin() {
        return colorTemperatureKelvin;
    }

    public int getLumenOutput() {
        return lumenOutput;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    /**
     * Executes operational logic combining intrinsic specifications with extrinsic runtime context.
     * @param lightId Extrinsic unique ID
     * @param latitude Extrinsic GPS coordinate
     * @param longitude Extrinsic GPS coordinate
     * @param powerOn Extrinsic operational state
     * @param brightnessPercent Extrinsic dimming level
     */
    public void renderOperation(String lightId, double latitude, double longitude, boolean powerOn, int brightnessPercent) {
        System.out.printf("  [Light: %s] Model: %s (%dW, %dK) | Coord: (%.4f, %.4f) | State: %s (%d%%)%n",
                lightId, model, wattage, colorTemperatureKelvin, latitude, longitude,
                (powerOn ? "ON" : "OFF"), brightnessPercent);
    }
}
