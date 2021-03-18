package ru.ifmo.cmath.algebra;

public class Integral {
    private final Function function;
    private final Bounds bounds;

    public Integral(Function function) {
        this.function = function;
        this.bounds = new Bounds();
    }

    public Integral from(Double lowerBound) {
        bounds.lower = lowerBound;
        return this;
    }

    public Integral to(Double upperBound) {
        bounds.upper = upperBound;
        return this;
    }

    public Function function() {
        return this.function;
    }

    public Bounds bounds() {
        return this.bounds;
    }
}
