package ru.ifmo.cmath.algebra;

public interface TrapezoidalRule {
    default Double area(Integral integral, Double step) {
        return 0.0;
    }
}
