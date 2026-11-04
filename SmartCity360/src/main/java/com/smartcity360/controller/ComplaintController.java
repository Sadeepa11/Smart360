package com.smartcity360.controller;

import com.smartcity360.complaints.*;
import com.smartcity360.model.CitizenComplaintEntity;
import com.smartcity360.repository.CitizenComplaintRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "*")
public class ComplaintController {

    private final CitizenComplaintRepository complaintRepository;
    private final ComplaintHandler chainRoot;

    public ComplaintController(CitizenComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;

        // Assemble the GoF Chain of Responsibility
        ComplaintHandler localStaff = new LocalStaffHandler();
        ComplaintHandler supervisor = new SupervisorHandler();
        ComplaintHandler cityManager = new CityManagerHandler();
        localStaff.setNext(supervisor).setNext(cityManager);

        this.chainRoot = localStaff;
    }

    public static class SubmitComplaintRequest {
        public String citizenName = "Citizen of Colombo";
        public String title;
        public String description;
        public String severity; // LOW, MEDIUM, CRITICAL / HIGH
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitComplaint(@RequestBody SubmitComplaintRequest req) {
        SeverityLevel level = switch (req.severity.toUpperCase()) {
            case "MEDIUM" -> SeverityLevel.MEDIUM;
            case "HIGH" -> SeverityLevel.HIGH;
            case "CRITICAL" -> SeverityLevel.CRITICAL;
            default -> SeverityLevel.LOW;
        };

        String code = "CMP-" + System.currentTimeMillis() % 10000;
        Complaint complaint = new Complaint(code, req.citizenName, req.title, req.description, level);

        // Process through Chain of Responsibility
        chainRoot.processComplaint(complaint);

        // Persist to MySQL Database
        CitizenComplaintEntity entity = new CitizenComplaintEntity(
                code,
                req.citizenName,
                req.title,
                req.description,
                level.name()
        );
        entity.setStatus(complaint.isResolved() ? "RESOLVED" : "ESCALATED");
        entity.setResolvedBy(complaint.getResolvedBy());
        entity.setResolutionNotes(complaint.getResolutionNotes());
        complaintRepository.save(entity);

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("complaintCode", code);
        res.put("title", req.title);
        res.put("severity", level.name());
        res.put("status", entity.getStatus());
        res.put("resolvedBy", complaint.getResolvedBy());
        res.put("actionTaken", complaint.getResolutionNotes());
        res.put("persistedInMySQL", true);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CitizenComplaintEntity>> listComplaints() {
        return ResponseEntity.ok(complaintRepository.findAllByOrderByIdDesc());
    }
}
