package com.smartcity360.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "energy_telemetry_logs")
public class EnergyTelemetryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String meterId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String rawPayload;

    @Column(length = 100)
    private String appliedLayers; // e.g. "AUTH, COMPRESSION, ENCRYPTION"

    @Column(columnDefinition = "TEXT")
    private String transformedPayload;

    private boolean verified;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    public EnergyTelemetryEntity() {}

    public EnergyTelemetryEntity(String meterId, String rawPayload, String appliedLayers, String transformedPayload, boolean verified) {
        this.meterId = meterId;
        this.rawPayload = rawPayload;
        this.appliedLayers = appliedLayers;
        this.transformedPayload = transformedPayload;
        this.verified = verified;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getMeterId() { return meterId; }
    public String getRawPayload() { return rawPayload; }
    public String getAppliedLayers() { return appliedLayers; }
    public String getTransformedPayload() { return transformedPayload; }
    public boolean isVerified() { return verified; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
