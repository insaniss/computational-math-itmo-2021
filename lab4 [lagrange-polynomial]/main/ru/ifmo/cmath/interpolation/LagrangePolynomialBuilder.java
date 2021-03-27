package ru.ifmo.cmath.interpolation;

import java.util.ArrayList;
import java.util.List;

public class LagrangePolynomialBuilder {
    private final Function function;
    private final StringBuilder lagrangePolynomial;
    private final List<Double> xData;
    private final List<Double> yData;

    public LagrangePolynomialBuilder(Function function) {
        if (function == null) {
            throw new IllegalArgumentException("Function can not be null.");
        }
        this.function = function;
        this.lagrangePolynomial = new StringBuilder();
        this.xData = new ArrayList<>(4);
        this.yData = new ArrayList<>(4);
    }

    public LagrangePolynomialBuilder experimentalData(Double... data) {
        if (data.length < 2) {
            throw new IllegalArgumentException("Experimental points can not be less than 2.");
        }
        /* Calculate an experimental data */
        for (Double point : data) {
            this.xData.add(point);
            this.yData.add(function.apply(point));
        }
        return this;
    }

    private StringBuilder lagrangeMultiplier(int i) {
        StringBuilder numerator = new StringBuilder();
        Double dominator = 1.0D;

        for (int j = 0; j < xData.size(); j++) {
            if (i == j) continue;
            /* Create a numerator of lagrange multiplier */
            numerator.append("(x-").append(xData.get(j)).append(")");
            /* Calculate a dominator value */
            dominator *= (xData.get(i) - xData.get(j));

            if (-1E-6 < dominator && dominator < 1E-6) {
                throw new IllegalArgumentException("Too small steps!");
            }
        }
        return numerator.append("/").append(dominator);
    }

    public Function build() {
        if (xData.isEmpty() || yData.isEmpty()) {
            throw new IllegalArgumentException("Experimental data is empty!");
        }
        for (int i = 0; i < yData.size(); i++) {
            if (yData.get(i).isNaN() || yData.get(i).isInfinite()) {
                throw new IllegalArgumentException("Function undefined at x=" + xData.get(i) + ".");
            }
            /* Create a Lagrange Polynomial */
            lagrangePolynomial.append("+").append(yData.get(i))
                    .append("(")
                    .append(lagrangeMultiplier(i))
                    .append(")");
        }
        return new Function(lagrangePolynomial.toString());
    }
}
