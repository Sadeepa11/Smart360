package com.smartcity360.zones;

import java.util.List;

/**
 * Component interface declaring common operations for both leaf nodes (Building)
 * and composite nodes (Street, District, City) in the SmartCity360 hierarchy.
 */
public interface ZoneComponent {
    /**
     * @return Descriptive name of the zone component
     */
    String getName();

    /**
     * @return Classification (e.g., CITY, DISTRICT, STREET, BUILDING)
     */
    String getZoneType();

    /**
     * Shuts down power across this zone and any nested sub-zones.
     */
    void shutdownPower();

    /**
     * Restores power across this zone and any nested sub-zones.
     */
    void restorePower();

    /**
     * Calculates and aggregates total active power usage in kilowatt-hours (kWh).
     * @return Aggregated usage
     */
    double reportUsage();

    /**
     * Indicates whether power is active for this component.
     */
    boolean isPowerOn();

    /**
     * Visualizes the structural hierarchy.
     * @param indent Indentation prefix for tree formatting
     */
    void displayHierarchy(String indent);

    // Composite management operations
    default void add(ZoneComponent component) {
        throw new UnsupportedOperationException("Leaf nodes do not support adding child components.");
    }

    default void remove(ZoneComponent component) {
        throw new UnsupportedOperationException("Leaf nodes do not support removing child components.");
    }

    default List<ZoneComponent> getChildren() {
        return List.of();
    }
}
