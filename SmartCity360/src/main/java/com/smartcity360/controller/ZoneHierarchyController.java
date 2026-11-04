package com.smartcity360.controller;

import com.smartcity360.zones.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/zones")
@CrossOrigin(origins = "*")
public class ZoneHierarchyController {

    private ZoneComposite rootCity;
    private ZoneComposite district01;
    private ZoneComposite district05;

    public ZoneHierarchyController() {
        initializeHierarchy();
    }

    private void initializeHierarchy() {
        Building bldA1 = new Building("Lotus Tower Tech Center", 450.0);
        Building bldA2 = new Building("World Trade Center Tower 1", 620.0);
        Building bldB1 = new Building("Galle Face Residences", 280.0);
        Building bldB2 = new Building("Shangri-La Mall Complex", 510.0);
        Building bldC1 = new Building("Havelock City Block D", 340.0);

        ZoneComposite streetFort = new ZoneComposite("York Street", "STREET");
        streetFort.add(bldA1);
        streetFort.add(bldA2);

        ZoneComposite streetGalle = new ZoneComposite("Galle Road Coastal Strip", "STREET");
        streetGalle.add(bldB1);
        streetGalle.add(bldB2);

        ZoneComposite streetHavelock = new ZoneComposite("Dickmans Road", "STREET");
        streetHavelock.add(bldC1);

        district01 = new ZoneComposite("Colombo 01 - Business District", "DISTRICT");
        district01.add(streetFort);
        district01.add(streetGalle);

        district05 = new ZoneComposite("Colombo 05 - Residential District", "DISTRICT");
        district05.add(streetHavelock);

        rootCity = new ZoneComposite("Greater Colombo Metropolitan Smart Area", "CITY");
        rootCity.add(district01);
        rootCity.add(district05);
    }

    @GetMapping("/tree")
    public ResponseEntity<Map<String, Object>> getHierarchy() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("city", rootCity.getName());
        response.put("totalPowerUsageKwh", rootCity.reportUsage());
        response.put("powerStatus", rootCity.isPowerOn() ? "ACTIVE" : "SHUTDOWN");
        response.put("hierarchyTree", buildTreeData(rootCity));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/power-shutdown")
    public ResponseEntity<Map<String, Object>> shutdownPower(@RequestParam(defaultValue = "district01") String target) {
        if ("district01".equalsIgnoreCase(target)) {
            district01.shutdownPower();
        } else {
            rootCity.shutdownPower();
        }

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("action", "SHUTDOWN_EXECUTED");
        res.put("target", target);
        res.put("remainingCityUsageKwh", rootCity.reportUsage());
        res.put("colombo01UsageKwh", district01.reportUsage());
        res.put("colombo05UsageKwh", district05.reportUsage());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/power-restore")
    public ResponseEntity<Map<String, Object>> restorePower() {
        rootCity.restorePower();
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("action", "RESTORE_EXECUTED");
        res.put("totalCityUsageKwh", rootCity.reportUsage());
        return ResponseEntity.ok(res);
    }

    private Map<String, Object> buildTreeData(ZoneComponent component) {
        Map<String, Object> node = new LinkedHashMap<>();
        node.put("name", component.getName());
        node.put("type", component.getZoneType());
        node.put("usageKwh", component.reportUsage());
        node.put("powerOn", component.isPowerOn());

        if (component instanceof ZoneComposite composite) {
            List<Map<String, Object>> childrenList = new ArrayList<>();
            for (ZoneComponent child : composite.getChildren()) {
                childrenList.add(buildTreeData(child));
            }
            node.put("children", childrenList);
        }
        return node;
    }
}
