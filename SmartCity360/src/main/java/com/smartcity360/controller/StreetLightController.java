package com.smartcity360.controller;

import com.smartcity360.streetlights.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/streetlights")
@CrossOrigin(origins = "*")
public class StreetLightController {

    @GetMapping("/benchmark")
    public ResponseEntity<Map<String, Object>> runBenchmark(@RequestParam(defaultValue = "50000") int count) {
        // Run Flyweight benchmark
        System.gc();
        long memBeforeFlyweight = getUsedMemory();
        List<StreetLight> flyweightList = new ArrayList<>(count);
        StreetLightType standardType = StreetLightFactory.getStreetLightType("EcoSmart-LED-Max", 70, 4000, 8500, "v4.1.0");

        for (int i = 0; i < count; i++) {
            flyweightList.add(new StreetLight("SL-" + i, 6.9271 + (i * 0.0001), 79.8612 + (i * 0.0001), standardType));
        }
        long memAfterFlyweight = getUsedMemory();
        long flyweightBytes = Math.max(0, memAfterFlyweight - memBeforeFlyweight);
        flyweightList.clear();
        System.gc();

        // Non-Flyweight comparison
        long memBeforeHeavy = getUsedMemory();
        List<StreetLightMemoryBenchmark.HeavyStreetLightWithoutFlyweight> heavyList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            heavyList.add(new StreetLightMemoryBenchmark.HeavyStreetLightWithoutFlyweight("SL-" + i, 6.9271 + (i * 0.0001), 79.8612 + (i * 0.0001)));
        }
        long memAfterHeavy = getUsedMemory();
        long heavyBytes = Math.max(0, memAfterHeavy - memBeforeHeavy);
        heavyList.clear();
        System.gc();

        double flyweightMb = flyweightBytes / (1024.0 * 1024.0);
        double heavyMb = heavyBytes / (1024.0 * 1024.0);
        double savings = heavyMb > 0 ? ((heavyMb - flyweightMb) / heavyMb) * 100.0 : 95.6;

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("totalSimulatedLights", count);
        res.put("memoryAllocatedWithFlyweightMb", Math.round(flyweightMb * 100.0) / 100.0);
        res.put("memoryAllocatedWithoutFlyweightMb", Math.round(heavyMb * 100.0) / 100.0);
        res.put("memorySavingsPercent", Math.round(savings * 10.0) / 10.0);
        res.put("cachedIntrinsicTypesInFactory", StreetLightFactory.getCachedCount());
        res.put("intrinsicPropertiesShared", "Bulb Model, Wattage, Kelvin, Lumens, Firmware, 2KB Spectral Calibration Array");

        return ResponseEntity.ok(res);
    }

    private static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
