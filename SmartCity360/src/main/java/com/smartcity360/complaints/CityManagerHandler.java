package com.smartcity360.complaints;

/**
 * Concrete Handler: City Manager Executive Tier.
 * Resolves HIGH and CRITICAL severity emergencies affecting city safety or widespread infrastructure.
 */
public class CityManagerHandler extends ComplaintHandler {

    public CityManagerHandler() {
        super("City General Manager");
    }

    @Override
    protected boolean canHandle(Complaint complaint) {
        return complaint.getSeverity() == SeverityLevel.HIGH || complaint.getSeverity() == SeverityLevel.CRITICAL;
    }

    @Override
    protected void executeResolution(Complaint complaint) {
        String action = complaint.getSeverity() == SeverityLevel.CRITICAL
                ? "Initiated City Emergency Protocol, deployed emergency services, notified municipal council."
                : "Issued executive directive for expedited high-priority municipal repair.";
        complaint.markResolved(handlerName, action);
        System.out.printf("    ==> [RESOLVED by %s]: %s (Action: %s)%n",
                handlerName, complaint.getTitle(), complaint.getResolutionNotes());
    }
}
