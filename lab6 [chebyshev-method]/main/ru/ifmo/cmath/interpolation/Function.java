package ru.ifmo.cmath.interpolation;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Function {
    private final Expression expression;

    public Function(String expression) {
        this.expression = new ExpressionBuilder(expression).variable("x").build();
    }

    public Double apply(double x) {
        try {
            return this.expression.setVariable("x", x).evaluate();
        } catch (RuntimeException exception) {
            return Double.NaN;
        }
    }
}
