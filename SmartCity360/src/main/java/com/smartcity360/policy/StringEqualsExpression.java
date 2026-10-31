package com.smartcity360.policy;

/**
 * Terminal Expression comparing a categorical context property (e.g., zone == 'RESIDENTIAL').
 */
public class StringEqualsExpression implements Expression {
    private final String variableName;
    private final String targetValue;

    public StringEqualsExpression(String variableName, String targetValue) {
        this.variableName = variableName;
        this.targetValue = targetValue.replace("'", "").replace("\"", "");
    }

    @Override
    public boolean interpret(Context context) {
        String val = context.getString(variableName);
        return targetValue.equalsIgnoreCase(val);
    }

    @Override
    public String toString() {
        return String.format("(%s == '%s')", variableName, targetValue);
    }
}
