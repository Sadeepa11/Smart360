package com.smartcity360.complaints;

/**
 * Concrete Handler: Local Staff Tier.
 * Resolves LOW severity routine municipal complaints (e.g., street trash, minor light flicker).
 */
public class LocalStaffHandler extends ComplaintHandler {

    public LocalStaffHandler() {
        super("Local Staff Field Unit");
    }

    @Override
    protected boolean canHandle(Complaint complaint) {
        return complaint.getSeverity() == SeverityLevel.LOW;
    }

    @Override
    protected void executeResolution(Complaint complaint) {
        complaint.markResolved(handlerName, "Dispatched field technician to resolve routine maintenance.");
        System.out.printf("    ==> [RESOLVED by %s]: %s (Action: %s)%n",
                handlerName, complaint.getTitle(), complaint.getResolutionNotes());
    }
}
