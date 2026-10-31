package com.smartcity360.policy;

/**
 * Non-Terminal Expression performing logical NOT inversion on a sub-expression.
 */
public class NotExpression implements Expression {
    private final Expression child;

    public NotExpression(Expression child) {
        this.child = child;
    }

    @Override
    public boolean interpret(Context context) {
        return !child.interpret(context);
    }

    @Override
    public String toString() {
        return String.format("[NOT %s]", child);
    }
}
