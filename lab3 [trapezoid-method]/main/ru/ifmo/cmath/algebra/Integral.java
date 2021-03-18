package ru.ifmo.cmath.algebra;

public class Integral {
    public final Function function;

    public Double lowerBound;
    public Double upperBound;

    public Integral(Function function) {
        this.function = function;
    }

    public Integral from(Double lowerBound) {
        this.lowerBound = lowerBound;
        return this;
    }

    public Integral to(Double upperBound) {
        this.upperBound = upperBound;
        return this;
    }
}
