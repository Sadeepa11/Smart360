package com.smartcity360.complaints;

import java.time.LocalDateTime;

/**
 * Request object passed through the Chain of Responsibility handlers.
 */
public class Complaint {
    private final String complaintId;
    private final String citizenName;
    private final String title;
    private final String description;
    private final SeverityLevel severity;
    private final LocalDateTime submittedTime;
    
    private boolean resolved;
    private String resolvedBy;
    private String resolutionNotes;

    public Complaint(String complaintId, String citizenName, String title, String description, SeverityLevel severity) {
        this.complaintId = complaintId;
        this.citizenName = citizenName;
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.submittedTime = LocalDateTime.now();
        this.resolved = false;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public String getCitizenName() {
        return citizenName;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public SeverityLevel getSeverity() {
        return severity;
    }

    public LocalDateTime getSubmittedTime() {
        return submittedTime;
    }

    public boolean isResolved() {
        return resolved;
    }

    public String getResolvedBy() {
        return resolvedBy;
    }

    public String getResolutionNotes() {
        return resolutionNotes;
    }

    public void markResolved(String handlerName, String notes) {
        this.resolved = true;
        this.resolvedBy = handlerName;
        this.resolutionNotes = notes;
    }

    @Override
    public String toString() {
        return String.format("[%s] (Severity: %s) Citizen: %s | Issue: '%s' | Status: %s",
                complaintId, severity, citizenName, title, (resolved ? "RESOLVED by " + resolvedBy : "UNRESOLVED"));
    }
}
