package com.smartcity360.complaints;

/**
 * Concrete Handler: Operations Supervisor Tier.
 * Resolves MEDIUM severity complaints requiring inter-departmental coordination or budget approval.
 */
public class SupervisorHandler extends ComplaintHandler {

    public SupervisorHandler() {
        super("District Operations Supervisor");
    }

    @Override
    protected boolean canHandle(Complaint complaint) {
        return complaint.getSeverity() == SeverityLevel.MEDIUM;
    }

    @Override
    protected void executeResolution(Complaint complaint) {
        complaint.markResolved(handlerName, "Authorized contractor deployment and replacement parts requisition.");
        System.out.printf("    ==> [RESOLVED by %s]: %s (Action: %s)%n",
                handlerName, complaint.getTitle(), complaint.getResolutionNotes());
    }
}
