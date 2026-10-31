package com.smartcity360.policy;

/**
 * Non-Terminal Expression performing logical OR between two sub-expressions.
 */
public class OrExpression implements Expression {
    private final Expression left;
    private final Expression right;

    public OrExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean interpret(Context context) {
        return left.interpret(context) || right.interpret(context);
    }

    @Override
    public String toString() {
        return String.format("[%s OR %s]", left, right);
    }
}
