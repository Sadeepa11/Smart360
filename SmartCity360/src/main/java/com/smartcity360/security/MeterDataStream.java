package com.smartcity360.security;

/**
 * Concrete Component representing the primary raw data stream
 * connected to an Energy Meter device.
 */
public class MeterDataStream implements DataStream {
    private final String meterId;
    private String storedPayload = "";

    public MeterDataStream(String meterId) {
        this.meterId = meterId;
    }

    public String getMeterId() {
        return meterId;
    }

    @Override
    public void writeData(String data) {
        this.storedPayload = data;
        System.out.printf("[MeterDataStream:%s] Stored raw payload: %s%n", meterId, data);
    }

    @Override
    public String readData() {
        System.out.printf("[MeterDataStream:%s] Read raw payload: %s%n", meterId, storedPayload);
        return this.storedPayload;
    }
}
