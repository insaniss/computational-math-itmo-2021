package ru.ifmo.cmath.interpolation;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class SimpleFunction implements Function {
    private final Expression expression;

    public SimpleFunction(String expression) {
        this.expression = new ExpressionBuilder(expression).variable("x").build();
    }

    @Override
    public Double apply(double x) {
        try {
            return this.expression.setVariable("x", x).evaluate();
        } catch (RuntimeException exception) {
            return Double.NaN;
        }
    }
}
