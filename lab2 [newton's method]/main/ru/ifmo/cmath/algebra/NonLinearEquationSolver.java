package ru.ifmo.cmath.algebra;

/**
 * Non linear equation solver implementation. It solves a nonlinear equations
 * (nonlinear system of equations) by three methods.
 *
 * @since 13 March, 2021
 * @author Bobur Zakirov
 */
public class NonLinearEquationSolver implements EquationSolver {
    private final int LIMIT = 1_000_000;

    private double ACCURACY = 1E-5;

    @Override
    public void setAccuracy(double accuracy) {
        if (0 > accuracy && accuracy > 1) {
            throw new IllegalArgumentException("Accuracy: allowed only 0 < double < 1");
        }
        this.ACCURACY = accuracy;
    }


    @Override
    public Object[] solveByBisection(Function function, double a, double b) {
        if (a > b) a = a + b - (b = a);
        if (function.apply(a) * function.apply(b) > 0) {
            throw new RuntimeException("Function doesn't have any roots at [" + a + ", " + b + "]");
        }
        int iterations = 0;
        double root = 0;
        while (b - a > 2 * ACCURACY && iterations < LIMIT) {
            root = (a + b) / 2;
            if (function.apply(a) * function.apply(root) > 0) a = root; else b = root;
            iterations++;
        }
        double delta = (b - a) / 2;
        return new Object[] { root, delta, iterations };
    }


    @Override
    public Object[] solveByIteration(Function function, double a, double b) {
        if (a > b) a = a + b - (b = a);
        double q = derivativeSeriesMax(function, a, b);
        if (q >= 1) {
            throw new RuntimeException("Necessary condition for the convergence is not satisfied");
        }
        int iterations = 0;
        double delta, k = (1 - q) / q, prev, root = (a + b) / 2;
        do {
            prev = root; root = function.apply(root);
            delta = root > prev ? root - prev : prev - root;
            iterations++;
        } while (delta > k * ACCURACY && iterations < LIMIT);
        if (iterations == LIMIT) {
            throw new RuntimeException("Specified accuracy is not achieved");
        }
        return new Object[] { root, delta / k, iterations };
    }

    private double derivativeSeriesMax(Function function, double a, double b) {
        double max = 0, delta = (b - a) / 100000;

        for (double point = a; point <= b; point += delta) {
             max = Math.max(max, Math.abs(function.derivative(point, 1e-9)));
        }
        return max;
    }
}
