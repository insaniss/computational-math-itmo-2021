package ru.ifmo.cmath.algebra;

public class Integrator implements TrapezoidalRule {
    public IntegralAnswer integrate(Integral integral, Double accuracy) {
        return this.approximate(integral, accuracy);
    }

    public IntegralAnswer approximate(Integral integral, Double accuracy) {
        Integer parts = 1;
        Double integral1n, integral2n = area(integral, parts);
        Double delta;
        do {
            integral1n = integral2n;
            parts <<= 1;
            integral2n = area(integral, parts);
            delta = Math.abs(integral1n - integral2n) / 3;
        }
        while (delta > accuracy);
        return new IntegralAnswer(integral2n, delta, parts);
    }

    private Double area(Integral integral, Integer parts) {
        Double area = 0.0;
        Double step = integral.bounds().difference() / parts;
        for (int i = 1; i <= parts; i++) {
            area += this.trapezoid(
                    integral.function(),
                    integral.bounds().lower() + (i - 1) * step,
                    integral.bounds().lower() + i * step
            );
            if (area.isNaN() || area.isInfinite()) {
                throw new DivergeException("Integral is diverge!");
            }
        }
        return area;
    }
}
