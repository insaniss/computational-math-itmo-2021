package ru.ifmo.cmath.algebra;

public class Bounds {
    protected Double lower;
    protected Double upper;

    public Boolean equal() {
        return lower.equals(upper);
    }

    public Double difference() {
        return Math.abs(lower - upper);
    }

    public Double lower() {
        return this.lower;
    }

    public Double upper() {
        return this.upper;
    }
}
