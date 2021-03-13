package ru.ifmo.cmath.algebra;

/**
 * Equation solver
 *
 * @since 13 March, 2021
 * @author Bobur Zakirov
 */
public interface EquationSolver {

    Object solveByBisection(Function function, double a, double b);

    Object solveByIteration(Function function, double a, double b);

    void setAccuracy(double accuracy);
}
