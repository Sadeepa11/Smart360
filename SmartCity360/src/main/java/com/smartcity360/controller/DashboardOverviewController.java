package com.smartcity360.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardOverviewController {

    @GetMapping("/overview")
    public ResponseEntity<Map<String, Object>> getOverview() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("platformName", "SmartCity360 Enterprise Management Platform");
        data.put("status", "All systems operational");
        data.put("activeSubsystems", 5);
        data.put("databaseConnected", true);
        data.put("databaseEngine", "MySQL 8.0");

        List<Map<String, String>> patterns = List.of(
                Map.of("part", "Part A", "name", "Dynamic Security Wrappers", "pattern", "Decorator Pattern", "status", "Operational"),
                Map.of("part", "Part B", "name", "Smart Zone Hierarchy", "pattern", "Composite Pattern", "status", "Operational"),
                Map.of("part", "Part C", "name", "Complaint Escalation", "pattern", "Chain of Responsibility", "status", "Operational"),
                Map.of("part", "Part D", "name", "Smart Street Lights", "pattern", "Flyweight Pattern", "status", "Operational"),
                Map.of("part", "Part E", "name", "City Policy Interpreter", "pattern", "Interpreter Pattern", "status", "Operational")
        );
        data.put("patterns", patterns);

        return ResponseEntity.ok(data);
    }
}
