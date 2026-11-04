package com.smartcity360.controller;

import com.smartcity360.model.EnergyTelemetryEntity;
import com.smartcity360.repository.EnergyTelemetryRepository;
import com.smartcity360.security.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/security")
@CrossOrigin(origins = "*")
public class SecurityStreamController {

    private final EnergyTelemetryRepository telemetryRepository;

    public SecurityStreamController(EnergyTelemetryRepository telemetryRepository) {
        this.telemetryRepository = telemetryRepository;
    }

    public static class ProcessStreamRequest {
        public String meterId = "EM-COLOMBO-4401";
        public String rawPayload = "VOLTAGE: 230V, CURRENT: 12.5A, KWH: 450.2";
        public boolean applyEncryption;
        public boolean applyCompression;
        public boolean applyAuthentication;
    }

    @PostMapping("/process-stream")
    public ResponseEntity<Map<String, Object>> processStream(@RequestBody ProcessStreamRequest request) {
        // Step 1: Base Concrete Component
        DataStream stream = new MeterDataStream(request.meterId);
        List<String> layerList = new ArrayList<>();

        // Step 2: Apply Decorators Dynamically (Open/Closed Principle)
        if (request.applyAuthentication) {
            stream = new AuthenticationDecorator(stream);
            layerList.add("Authentication");
        }
        if (request.applyCompression) {
            stream = new CompressionDecorator(stream);
            layerList.add("Compression");
        }
        if (request.applyEncryption) {
            stream = new EncryptionDecorator(stream);
            layerList.add("Encryption");
        }

        // Transmission
        stream.writeData(request.rawPayload);
        String finalPayload = stream.readData();
        boolean verified = request.rawPayload.equals(finalPayload);

        String layersSummary = layerList.isEmpty() ? "RAW (No security layers)" : String.join(" -> ", layerList);

        // Step 3: Persist Audit in MySQL
        EnergyTelemetryEntity entity = new EnergyTelemetryEntity(
                request.meterId,
                request.rawPayload,
                layersSummary,
                finalPayload,
                verified
        );
        telemetryRepository.save(entity);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "SUCCESS");
        response.put("meterId", request.meterId);
        response.put("originalPayload", request.rawPayload);
        response.put("appliedLayers", layersSummary);
        response.put("recoveredPayload", finalPayload);
        response.put("isIntegrityVerified", verified);
        response.put("persistedInMySQL", true);

        return ResponseEntity.ok(response);
    }
}
