package com.smartcity360.policy;

/**
 * Non-Terminal Expression performing logical AND between two sub-expressions.
 */
public class AndExpression implements Expression {
    private final Expression left;
    private final Expression right;

    public AndExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean interpret(Context context) {
        return left.interpret(context) && right.interpret(context);
    }

    @Override
    public String toString() {
        return String.format("[%s AND %s]", left, right);
    }
}
