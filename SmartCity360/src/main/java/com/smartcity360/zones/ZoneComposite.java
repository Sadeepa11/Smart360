package com.smartcity360.zones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Composite class in the Composite Pattern.
 * Represents grouping nodes such as Street, District, or entire City.
 * Delegates operations uniformly across all contained children.
 */
public class ZoneComposite implements ZoneComponent {
    private final String name;
    private final String zoneType;
    private final List<ZoneComponent> children = new ArrayList<>();

    public ZoneComposite(String name, String zoneType) {
        this.name = name;
        this.zoneType = zoneType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getZoneType() {
        return zoneType;
    }

    @Override
    public void add(ZoneComponent component) {
        children.add(component);
    }

    @Override
    public void remove(ZoneComponent component) {
        children.remove(component);
    }

    @Override
    public List<ZoneComponent> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void shutdownPower() {
        System.out.printf("[%s: %s] Cascading 'Shutdown Power' command to all %d sub-elements...%n",
                zoneType, name, children.size());
        for (ZoneComponent child : children) {
            child.shutdownPower();
        }
    }

    @Override
    public void restorePower() {
        System.out.printf("[%s: %s] Cascading 'Restore Power' command to all %d sub-elements...%n",
                zoneType, name, children.size());
        for (ZoneComponent child : children) {
            child.restorePower();
        }
    }

    @Override
    public double reportUsage() {
        double total = 0.0;
        for (ZoneComponent child : children) {
            total += child.reportUsage();
        }
        return total;
    }

    @Override
    public boolean isPowerOn() {
        // Considered ON if at least one subcomponent has active power
        return children.stream().anyMatch(ZoneComponent::isPowerOn);
    }

    @Override
    public void displayHierarchy(String indent) {
        double totalUsage = reportUsage();
        System.out.printf("%s+ [%s] %s (Aggregated Usage: %.2f kWh)%n", indent, zoneType, name, totalUsage);
        for (ZoneComponent child : children) {
            child.displayHierarchy(indent + "   ");
        }
    }
}
