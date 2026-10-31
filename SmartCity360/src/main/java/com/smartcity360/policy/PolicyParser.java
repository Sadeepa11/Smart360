package com.smartcity360.policy;

import java.util.ArrayList;
import java.util.List;

/**
 * Lightweight Domain Specific Language (DSL) Parser for Municipal Operational Rules.
 * Parses textual city policies into an Abstract Syntax Tree (AST) of Expression objects.
 *
 * Supported Grammar:
 *   Rule       ::= OrClause
 *   OrClause   ::= AndClause ("OR" AndClause)*
 *   AndClause  ::= Condition ("AND" Condition)*
 *   Condition  ::= Variable Op Constant | "NOT" Condition
 *   Op         ::= ">" | ">=" | "<" | "<=" | "==" | "!="
 */
public class PolicyParser {

    /**
     * Parses a policy DSL string into an executable AST Expression.
     * Example: "noise_level > 70 AND hour >= 22"
     */
    public static Expression parse(String ruleString) {
        if (ruleString == null || ruleString.trim().isEmpty()) {
            throw new IllegalArgumentException("Rule string cannot be empty.");
        }

        // Split top-level OR clauses
        String[] orTokens = ruleString.split("(?i)\\s+OR\\s+");
        List<Expression> orExpressions = new ArrayList<>();

        for (String orToken : orTokens) {
            // Split AND clauses within each OR term
            String[] andTokens = orToken.split("(?i)\\s+AND\\s+");
            List<Expression> andExpressions = new ArrayList<>();

            for (String andToken : andTokens) {
                andExpressions.add(parseAtomicCondition(andToken.trim()));
            }

            // Chain AND expressions together
            Expression combinedAnd = andExpressions.get(0);
            for (int i = 1; i < andExpressions.size(); i++) {
                combinedAnd = new AndExpression(combinedAnd, andExpressions.get(i));
            }
            orExpressions.add(combinedAnd);
        }

        // Chain OR expressions together
        Expression combinedOr = orExpressions.get(0);
        for (int i = 1; i < orExpressions.size(); i++) {
            combinedOr = new OrExpression(combinedOr, orExpressions.get(i));
        }

        return combinedOr;
    }

    private static Expression parseAtomicCondition(String conditionStr) {
        conditionStr = conditionStr.replace("(", "").replace(")", "").trim();

        // Check for NOT prefix
        if (conditionStr.toUpperCase().startsWith("NOT ")) {
            String sub = conditionStr.substring(4).trim();
            return new NotExpression(parseAtomicCondition(sub));
        }

        // Recognized binary comparison operators
        String[] operators = {">=", "<=", "!=", "==", ">", "<"};
        for (String op : operators) {
            int opIdx = conditionStr.indexOf(op);
            if (opIdx != -1) {
                String varName = conditionStr.substring(0, opIdx).trim();
                String rightSide = conditionStr.substring(opIdx + op.length()).trim();

                // Check if right side is a numeric constant or a string literal
                try {
                    double numVal = Double.parseDouble(rightSide);
                    return new NumericComparisonExpression(varName, op, numVal);
                } catch (NumberFormatException e) {
                    // String literal comparison (typically for "==" or "!=")
                    return new StringEqualsExpression(varName, rightSide);
                }
            }
        }

        throw new IllegalArgumentException("Invalid policy DSL syntax in condition: '" + conditionStr + "'");
    }
}
