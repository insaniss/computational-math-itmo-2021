package ru.ifmo.cmath.algebra;

public interface TrapezoidalRule {
    default Double trapezoid(Function function, Double a, Double b) {
        Double val1 = function.apply(a);
        if (val1.isNaN() || val1.isInfinite() && !a.equals(b)) {
            a += 0.000000001D;
        }
        Double val2 = function.apply(b);
        if (val2.isNaN() || val2.isInfinite() && !a.equals(b)) {
            b -= 0.000000001D;
        }
        return 0.5 * (b - a) * (function.apply(a) + function.apply(b));
    }
}

