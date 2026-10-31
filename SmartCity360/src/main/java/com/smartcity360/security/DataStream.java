package com.smartcity360.security;

/**
 * Component interface defining the contract for reading and writing data streams
 * within the SmartCity360 Energy Monitoring Subsystem.
 */
public interface DataStream {
    /**
     * Writes (transmits) data payload through the stream.
     * @param data Raw or processed string payload (e.g. JSON meter telemetry)
     */
    void writeData(String data);

    /**
     * Reads (receives) data payload from the stream.
     * @return Transmitted payload after reversing stream transformations
     */
    String readData();
}
