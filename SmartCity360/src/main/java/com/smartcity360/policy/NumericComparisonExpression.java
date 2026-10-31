package com.smartcity360.policy;

/**
 * Terminal Expression comparing a numeric telemetry variable from the context
 * against a static numeric threshold using an operator (<, <=, >, >=, ==, !=).
 */
public class NumericComparisonExpression implements Expression {
    private final String variableName;
    private final String operator;
    private final double threshold;

    public NumericComparisonExpression(String variableName, String operator, double threshold) {
        this.variableName = variableName;
        this.operator = operator;
        this.threshold = threshold;
    }

    @Override
    public boolean interpret(Context context) {
        double currentValue = context.getDouble(variableName);
        return switch (operator) {
            case ">" -> currentValue > threshold;
            case ">=" -> currentValue >= threshold;
            case "<" -> currentValue < threshold;
            case "<=" -> currentValue <= threshold;
            case "==" -> Math.abs(currentValue - threshold) < 0.00001;
            case "!=" -> Math.abs(currentValue - threshold) >= 0.00001;
            default -> throw new IllegalArgumentException("Unsupported comparison operator: " + operator);
        };
    }

    @Override
    public String toString() {
        return String.format("(%s %s %.2f)", variableName, operator, threshold);
    }
}
