package com.smartcity360.streetlights;

import java.util.HashMap;
import java.util.Map;

/**
 * Flyweight Factory managing the pool of reusable StreetLightType flyweights.
 * Ensures that identical intrinsic configurations reuse the existing cached instance.
 */
public class StreetLightFactory {
    private static final Map<String, StreetLightType> TYPE_REGISTRY = new HashMap<>();

    /**
     * Retrieves an existing StreetLightType or creates and caches a new one if not present.
     */
    public static synchronized StreetLightType getStreetLightType(
            String model, int wattage, int colorTemp, int lumens, String firmware) {

        String cacheKey = String.format("%s_%d_%d_%d_%s", model, wattage, colorTemp, lumens, firmware);
        StreetLightType type = TYPE_REGISTRY.get(cacheKey);

        if (type == null) {
            type = new StreetLightType(model, wattage, colorTemp, lumens, firmware);
            TYPE_REGISTRY.put(cacheKey, type);
            System.out.printf("[FlyweightFactory] CREATED new intrinsic type: %s (Total cached types: %d)%n",
                    cacheKey, TYPE_REGISTRY.size());
        }

        return type;
    }

    public static int getCachedCount() {
        return TYPE_REGISTRY.size();
    }

    public static void clearCache() {
        TYPE_REGISTRY.clear();
    }
}
