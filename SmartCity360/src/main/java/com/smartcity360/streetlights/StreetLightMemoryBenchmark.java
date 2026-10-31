package com.smartcity360.streetlights;

import java.util.ArrayList;
import java.util.List;

/**
 * Benchmark utility to empirically measure and demonstrate the memory reduction
 * achieved by applying the Flyweight Pattern across tens of thousands of streetlight fixtures.
 */
public class StreetLightMemoryBenchmark {

    /**
     * Non-Flyweight comparison fixture that duplicates all intrinsic specifications
     * inside every single instance.
     */
    public static class HeavyStreetLightWithoutFlyweight {
        private final String lightId;
        private final double latitude;
        private final double longitude;
        private final String model;
        private final int wattage;
        private final int colorTemperatureKelvin;
        private final int lumenOutput;
        private final String firmwareVersion;
        private final byte[] calibrationCurve; // 2KB duplicated per object!

        public HeavyStreetLightWithoutFlyweight(String lightId, double latitude, double longitude) {
            this.lightId = lightId;
            this.latitude = latitude;
            this.longitude = longitude;
            this.model = "EcoSmart-LED-Max";
            this.wattage = 70;
            this.colorTemperatureKelvin = 4000;
            this.lumenOutput = 8500;
            this.firmwareVersion = "v4.1.0";
            this.calibrationCurve = new byte[2048];
        }
    }

    public static void runBenchmark(int count) {
        System.out.println("======================================================================");
        System.out.printf("  FLYWEIGHT PERFORMANCE BENCHMARK: Scaling to %,d Streetlights%n", count);
        System.out.println("======================================================================");

        // 1. Benchmark WITH Flyweight Pattern
        System.gc();
        long memBeforeFlyweight = getUsedMemory();
        List<StreetLight> flyweightList = new ArrayList<>(count);
        StreetLightType standardType = StreetLightFactory.getStreetLightType("EcoSmart-LED-Max", 70, 4000, 8500, "v4.1.0");

        for (int i = 0; i < count; i++) {
            flyweightList.add(new StreetLight("SL-" + i, 6.9271 + (i * 0.0001), 79.8612 + (i * 0.0001), standardType));
        }
        long memAfterFlyweight = getUsedMemory();
        long flyweightMemoryUsedBytes = Math.max(0, memAfterFlyweight - memBeforeFlyweight);

        // 2. Clear Flyweight list & GC
        flyweightList.clear();
        System.gc();

        // 3. Benchmark WITHOUT Flyweight Pattern
        long memBeforeHeavy = getUsedMemory();
        List<HeavyStreetLightWithoutFlyweight> heavyList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            heavyList.add(new HeavyStreetLightWithoutFlyweight("SL-" + i, 6.9271 + (i * 0.0001), 79.8612 + (i * 0.0001)));
        }
        long memAfterHeavy = getUsedMemory();
        long heavyMemoryUsedBytes = Math.max(0, memAfterHeavy - memBeforeHeavy);
        heavyList.clear();
        System.gc();

        // Results
        double flyweightMb = flyweightMemoryUsedBytes / (1024.0 * 1024.0);
        double heavyMb = heavyMemoryUsedBytes / (1024.0 * 1024.0);
        double savings = heavyMb > 0 ? ((heavyMb - flyweightMb) / heavyMb) * 100.0 : 0.0;

        System.out.printf("  [WITH Flyweight]    Memory Allocated: %8.2f MB (Shared 1 Flyweight Type)%n", flyweightMb);
        System.out.printf("  [WITHOUT Flyweight] Memory Allocated: %8.2f MB (Duplicates heavy data %d times)%n", heavyMb, count);
        System.out.printf("  >>> MEMORY SAVINGS: ~%.1f%% reduction!%n", savings);
        System.out.println("======================================================================\n");
    }

    private static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
