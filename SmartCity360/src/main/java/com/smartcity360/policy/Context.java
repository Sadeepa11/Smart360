package com.smartcity360.policy;

import java.util.HashMap;
import java.util.Map;

/**
 * Context class maintaining current runtime values of environmental telemetry,
 * municipal sensor readings, and city zone properties.
 */
public class Context {
    private final Map<String, Object> data = new HashMap<>();

    public void set(String key, Object value) {
        data.put(key.toLowerCase(), value);
    }

    public Object get(String key) {
        return data.get(key.toLowerCase());
    }

    public double getDouble(String key) {
        Object val = data.get(key.toLowerCase());
        if (val instanceof Number num) {
            return num.doubleValue();
        }
        if (val != null) {
            try {
                return Double.parseDouble(val.toString());
            } catch (NumberFormatException ignored) {}
        }
        return 0.0;
    }

    public String getString(String key) {
        Object val = data.get(key.toLowerCase());
        return val != null ? val.toString() : "";
    }

    public boolean has(String key) {
        return data.containsKey(key.toLowerCase());
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
