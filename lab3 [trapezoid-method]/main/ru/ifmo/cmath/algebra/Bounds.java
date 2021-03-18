package ru.ifmo.cmath.algebra;

public class Bounds {
    protected Double lower;
    protected Double upper;

    public Boolean equal() {
        return this.lower.equals(this.upper);
    }

    public Double difference() {
        return this.upper - this.lower;
    }

    public Double lower() {
        return this.lower;
    }

    public Double upper() {
        return this.upper;
    }
}
