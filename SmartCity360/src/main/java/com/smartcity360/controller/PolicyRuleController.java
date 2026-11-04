package com.smartcity360.controller;

import com.smartcity360.model.CityPolicyRuleEntity;
import com.smartcity360.policy.*;
import com.smartcity360.repository.CityPolicyRuleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/policy")
@CrossOrigin(origins = "*")
public class PolicyRuleController {

    private final CityPolicyRuleRepository policyRepository;

    public PolicyRuleController(CityPolicyRuleRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    public static class EvaluatePolicyRequest {
        public String ruleString = "noise_level > 70 AND hour >= 22";
        public Map<String, Object> telemetry = new HashMap<>();
    }

    @PostMapping("/evaluate")
    public ResponseEntity<Map<String, Object>> evaluatePolicy(@RequestBody EvaluatePolicyRequest req) {
        // Step 1: Parse DSL into Abstract Syntax Tree (AST) using Interpreter Pattern
        Expression expression = PolicyParser.parse(req.ruleString);

        // Step 2: Populate Context with dynamic telemetry
        Context context = new Context();
        if (req.telemetry != null) {
            req.telemetry.forEach(context::set);
        }

        // Step 3: Interpret expression tree against context
        boolean isViolated = expression.interpret(context);

        String recommendedAction;
        if (isViolated) {
            if (req.ruleString.toLowerCase().contains("noise")) {
                recommendedAction = "DISPATCH POLICE WARNING / NOISE CITATION";
            } else if (req.ruleString.toLowerCase().contains("parking")) {
                recommendedAction = "ISSUE OVERTIME PARKING TICKET";
            } else {
                recommendedAction = "TRIGGER PUBLIC ADVISORY & EMERGENCY PROTOCOL";
            }
        } else {
            recommendedAction = "ALL PARAMETERS WITHIN CIVIC LIMITS (NORMAL)";
        }

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("dslRule", req.ruleString);
        res.put("abstractSyntaxTree", expression.toString());
        res.put("evaluatedTelemetry", context.toString());
        res.put("policyTriggeredOrViolated", isViolated);
        res.put("systemAutomatedAction", recommendedAction);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/rules")
    public ResponseEntity<List<Map<String, String>>> getPredefinedRules() {
        List<Map<String, String>> rules = new ArrayList<>();
        rules.add(Map.of(
                "name", "Night Noise Ordinance",
                "dsl", "noise_level > 70 AND hour >= 22",
                "sampleQuery", "noise_level: 78.5, hour: 23"
        ));
        rules.add(Map.of(
                "name", "Environmental Hazard Alert",
                "dsl", "co2_ppm > 800 OR temperature > 38",
                "sampleQuery", "co2_ppm: 920, temperature: 31.0"
        ));
        rules.add(Map.of(
                "name", "Commercial Parking Time Limit",
                "dsl", "parking_minutes > 120 AND zone == COMMERCIAL",
                "sampleQuery", "parking_minutes: 180, zone: COMMERCIAL"
        ));
        return ResponseEntity.ok(rules);
    }
}
