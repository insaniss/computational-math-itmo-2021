package ru.ifmo.cmath.algebra;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * Basic function implementation.
 *
 * @since 11 March, 2021
 * @author Bobur Zakirov
 */
public class Function {


    private Expression expression;

    /**
     * Construct a function.
     *
     * @param function String value of a function.
     */
    public Function(String function) {
        this.expression = new ExpressionBuilder(function).variables("x", "y").build();
    }

    /**
     * Apply a value of the function at point x.
     */
    public double apply(double x) {
        return expression.setVariable("x", x).evaluate();
    }

    /**
     * Apply a value of the function at points x and y.
     */
    public double apply(double x, double y) {
        return expression.setVariable("x", x).setVariable("y", y).evaluate();
    }

    /**
     * Calculate a derivative of the function at point x.
     */
    public double derivative(double x, double delta) {
        return (this.apply(x + delta) - this.apply(x - delta)) / (2 * delta);
    }

    /**
     * Just a toString method for current class =).
     */
    @Override
    public String toString() {
        return null;
    }
}
