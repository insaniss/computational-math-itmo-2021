package ru.ifmo.cmath.interpolation;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Function {
    private final Expression expression;

    public Function(String expr) {
        this.expression = new ExpressionBuilder(expr).variable("x").build();
    }

    public Double apply(Double x) {
        try {
            return expression.setVariable("x", x).evaluate();
        } catch (RuntimeException e) {
            return Double.NaN;
        }
    }
}
