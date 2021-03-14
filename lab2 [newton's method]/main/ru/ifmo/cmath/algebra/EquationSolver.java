package ru.ifmo.cmath.algebra;

/**
 * Equation solver.
 *
 * @since 13 March, 2021
 * @author Bobur Zakirov
 */
public interface EquationSolver {

    /**
     * Solve an equation by Bisection method.
     */
    Object[] solveByBisection(Function function, double a, double b);

    /**
     * Solve an equation by Iterative method.
     */
    Object[] solveByIteration(Function function, double a, double b);

    /**
     * Set an accuracy.
     */
    void setAccuracy(double accuracy);

    /**
     * Solve a system of equations by Newton's method.
     */
    Object[][] solveByNewton(double x, double y, Function... functions);
}
