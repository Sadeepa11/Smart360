package com.smartcity360.complaints;

/**
 * Base Handler in the Chain of Responsibility Pattern.
 * Defines the successor link and default request forwarding mechanism.
 */
public abstract class ComplaintHandler {
    protected ComplaintHandler nextHandler;
    protected final String handlerName;

    public ComplaintHandler(String handlerName) {
        this.handlerName = handlerName;
    }

    /**
     * Sets the next successor in the processing pipeline.
     * @return nextHandler for fluent chaining
     */
    public ComplaintHandler setNext(ComplaintHandler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    /**
     * Handles the complaint if within this tier's authority;
     * otherwise escalates down the chain to the successor.
     */
    public void processComplaint(Complaint complaint) {
        if (canHandle(complaint)) {
            System.out.printf(">>> [%s] Accepted complaint #%s. Resolving issue...%n", handlerName, complaint.getComplaintId());
            executeResolution(complaint);
        } else if (nextHandler != null) {
            System.out.printf("--- [%s] Severity '%s' exceeds local authority. Escalating to [%s]...%n",
                    handlerName, complaint.getSeverity(), nextHandler.getHandlerName());
            nextHandler.processComplaint(complaint);
        } else {
            System.out.printf("!!! [UNHANDLED] Complaint #%s could not be processed by any handler in the escalation chain!%n",
                    complaint.getComplaintId());
        }
    }

    public String getHandlerName() {
        return handlerName;
    }

    protected abstract boolean canHandle(Complaint complaint);
    protected abstract void executeResolution(Complaint complaint);
}
