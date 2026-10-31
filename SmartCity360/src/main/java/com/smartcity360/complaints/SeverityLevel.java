package com.smartcity360.complaints;

/**
 * Enumeration representing the severity tier of a citizen complaint.
 */
public enum SeverityLevel {
    LOW(1, "Routine issue handled by Local Ground Staff"),
    MEDIUM(2, "Operational issue requiring Supervisor authorization"),
    HIGH(3, "Critical service disruption requiring City Manager intervention"),
    CRITICAL(4, "Emergency situation requiring Executive City Command");

    private final int level;
    private final String description;

    SeverityLevel(int level, String description) {
        this.level = level;
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }
}
