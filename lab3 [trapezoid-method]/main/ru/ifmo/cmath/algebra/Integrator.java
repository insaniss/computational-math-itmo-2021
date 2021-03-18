package ru.ifmo.cmath.algebra;

public class Integrator implements TrapezoidalRule {
    public IntegralAnswer integrate(Integral integral, Double accuracy) {
        if (0 >= accuracy || accuracy >= 1) {
            throw new IllegalArgumentException("accuracy: allowed only positive numbers 0..1");
        }
        return null;
    }

    public IntegralAnswer approximate(Integral integral, Double accuracy) {

        return null;
    }
}
