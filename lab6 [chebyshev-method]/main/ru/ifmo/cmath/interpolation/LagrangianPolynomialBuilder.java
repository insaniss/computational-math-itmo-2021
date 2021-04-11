package ru.ifmo.cmath.interpolation;

import java.util.ArrayList;
import java.util.List;

public final class LagrangianPolynomialBuilder {
    private final Function initialFunction;
    private final StringBuilder lagrangianPolynomial;
    private final List<Double> xData;
    private final List<Double> yData;

    public LagrangianPolynomialBuilder(Function initialFunction) {
        if (initialFunction == null) {
            throw new IllegalArgumentException("Initial function can not be null.");
        }
        this.initialFunction = initialFunction;
        this.lagrangianPolynomial = new StringBuilder();
        this.xData = new ArrayList<>(4);
        this.yData = new ArrayList<>(4);
    }

    public LagrangianPolynomialBuilder setNodes(List<Double> nodes) {
        if (nodes.size() < 2) {
            throw new IllegalArgumentException("Nodes can not be less than 2");
        }
        /* Calculate a data */
        for (Double node : nodes) {
            this.xData.add(node);
            this.yData.add(initialFunction.apply(node));
        }
        return this;
    }

    private StringBuilder lagrangianMultiplier(int i) {
        StringBuilder numerator = new StringBuilder();
        Double dominator = 1.0;

        for (int j = 0; j < xData.size(); j++) {
            if (i == j) continue;
            /* Create a numerator of lagrangian multiplier */
            numerator.append("(x-").append(xData.get(j)).append(")");
            /* Calculate a dominator */
            dominator *= (xData.get(i) - xData.get(j));
        }
        if (-1e-9 < dominator && dominator < 1e-9) {
            throw new IllegalArgumentException("Too small steps!");
        }
        return numerator.append("/").append(dominator);
    }

    public Function build() {
        if (xData.isEmpty() || yData.isEmpty()) {
            throw new IllegalArgumentException("Nodes didn't set!");
        }
        for (int i = 0; i < yData.size(); i++) {
            if (yData.get(i).isNaN() || yData.get(i).isInfinite()) {
                throw new IllegalArgumentException("Initial function undefined at x=" + xData.get(i));
            }
            /* Create a Lagrangian Polynomial */
            lagrangianPolynomial.append("+").append(yData.get(i))
                    .append("(")
                    .append(lagrangianMultiplier(i))
                    .append(")");
        }
        return new Function(lagrangianPolynomial.toString());
    }
}
