package ru.ifmo.cmath.differention;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Function {
    public final Expression expression;

    public Function(String expression) {
        this.expression = new ExpressionBuilder(expression).variables("x", "y").build();
    }

    public Double apply(Double x, Double y) {
        return expression.setVariable("x", x).setVariable("y", y).evaluate();
    }

    public Double apply(Double x) {
        return expression.setVariable("x", x).evaluate();
    }
}
