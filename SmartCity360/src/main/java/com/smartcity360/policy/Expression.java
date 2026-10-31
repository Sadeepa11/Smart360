package com.smartcity360.policy;

/**
 * Abstract Expression interface declaring the interpret method for
 * validating municipal policies against runtime environmental context.
 */
public interface Expression {
    /**
     * Interprets and evaluates this expression tree against the given environmental context.
     * @param context Active telemetry and municipal sensor parameters
     * @return true if policy rule criteria are met/violated, false otherwise
     */
    boolean interpret(Context context);
}
