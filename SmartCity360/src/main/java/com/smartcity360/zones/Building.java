package com.smartcity360.zones;

/**
 * Leaf component representing an individual Building in the SmartCity360 infrastructure.
 * Contains direct state and power telemetry but has no child components.
 */
public class Building implements ZoneComponent {
    private final String name;
    private final double basePowerUsageKwh;
    private boolean powerOn;

    public Building(String name, double basePowerUsageKwh) {
        this.name = name;
        this.basePowerUsageKwh = basePowerUsageKwh;
        this.powerOn = true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getZoneType() {
        return "BUILDING";
    }

    @Override
    public void shutdownPower() {
        this.powerOn = false;
        System.out.printf("  [Building: %s] Power grid disconnected. Status: OFF.%n", name);
    }

    @Override
    public void restorePower() {
        this.powerOn = true;
        System.out.printf("  [Building: %s] Power grid energized. Status: ON.%n", name);
    }

    @Override
    public double reportUsage() {
        // If power is shut down, consumption drops to 0 (or backup battery 0.0)
        double currentUsage = powerOn ? basePowerUsageKwh : 0.0;
        return currentUsage;
    }

    @Override
    public boolean isPowerOn() {
        return powerOn;
    }

    @Override
    public void displayHierarchy(String indent) {
        String status = powerOn ? "[ACTIVE - " + basePowerUsageKwh + " kWh]" : "[SHUTDOWN - 0.0 kWh]";
        System.out.printf("%s-- [Building] %s %s%n", indent, name, status);
    }
}
