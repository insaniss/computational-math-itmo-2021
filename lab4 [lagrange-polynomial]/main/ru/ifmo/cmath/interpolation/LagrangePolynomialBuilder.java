package ru.ifmo.cmath.interpolation;

import java.util.ArrayList;
import java.util.List;

public class LagrangePolynomialBuilder {
    private final Function function;
    private final String lagrangePolynomial;
    private final List<Double> xData;
    private final List<Double> yData;

    public LagrangePolynomialBuilder(Function function) {
        if (function == null) {
            throw new IllegalArgumentException("Function can not be null.");
        }
        this.function = function;
        this.lagrangePolynomial = new String();
        this.xData = new ArrayList<>(4);
        this.yData = new ArrayList<>(4);
    }

    public LagrangePolynomialBuilder experimentalData(Double... data) {
        for (Double point : data) {
            this.xData.add(point);
            this.yData.add(function.apply(point));
        }
        return this;
    }

    public Function build() {
        return null;
    }
}
