package ru.ifmo.cmath;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.ifmo.cmath.algebra.EquationSolver;
import ru.ifmo.cmath.algebra.Function;
import ru.ifmo.cmath.algebra.NonLinearEquationSolver;

public class SolverTest {

    private final EquationSolver solver;
    {
        solver = new NonLinearEquationSolver();
    }

    @Test
    public void test0a() {
        Function func = new Function("sin(x)^2-x^2+1"); // sin(x)^2-x^2+1=0
        solver.setAccuracy(0.000123);
        Object[] results = solver.solveByBisection(func, -0.1, 1.5);
        Assertions.assertTrue(
                almostEqual(func.apply((double) results[0]), 0, 0.000123)
        );
    }

    @Test
    public void test0b() {
        Function func = new Function("(sin(x)^2+1)^0.5"); // x=(sin(x)^2+1)^0.5
        solver.setAccuracy(0.000123);
        Object[] results = solver.solveByIteration(func, -0.1, 1.5);
        Assertions.assertTrue(
                almostEqual(func.apply((double) results[0]), (double) results[0], 0.000123)
        );
    }

    @Test
    public void test1a() {
        Function func = new Function("x^2-e^x-3x+2"); // x^2-e^x-3x+2=0
        solver.setAccuracy(0.01);
        Object[] results = solver.solveByBisection(func, 0.9, -0.1);
        Assertions.assertTrue(
                almostEqual(func.apply((double) results[0]), 0, 0.01)
        );
    }
    
    @Test
    public void test1b() {
        Function func = new Function("log(x^2-3x+2)"); // x=log(x^2-3x+2)
        solver.setAccuracy(0.01);
        Object[] results = solver.solveByIteration(func, 0.9, -0.1);
        Assertions.assertTrue(
                almostEqual(func.apply((double) results[0]), (double) results[0], 0.01)
        );
    }

    @Test
    public void test2a() {
        Function func = new Function("xe^{x^2}-sin(x)^2+3cos(x)+5"); // xe^(x^2)-sin(x)^2+3cos(x)+5=0
        solver.setAccuracy(0.000123);
        Object[] results = solver.solveByBisection(func, -1.0, -3.3);
        Assertions.assertTrue(
                almostEqual(func.apply((double) results[0]), 0, 0.0123)
        );
    }

    @Test
    public void test2b() {
        Function func = new Function("(sin(x)^2-3cos(x)-5)/e^(x^2)"); // x=(sin(x)^2-3cos(x)-5)/e^(x^2)
        solver.setAccuracy(0.0123);
        Object[] results = solver.solveByIteration(func, -1.9, -3.3);
        Assertions.assertTrue(
                almostEqual(func.apply((double) results[0]), (double) results[0], 0.0123)
        );
    }

    @Test
    public void test3a() {
        Function func = new Function("x^3-17"); // x^3-17=0
        solver.setAccuracy(1e-7);
        Object[] results = solver.solveByBisection(func, -2.0, 4.089);
        Assertions.assertTrue(
                almostEqual(func.apply((double) results[0]), 0, 1e-5)
        );
    }

    @Test
    public void test3b() {
        Function func = new Function("17^(1/3)"); // x=17^(1/3)
        solver.setAccuracy(1e-7);
        Object[] results = solver.solveByIteration(func, -2.0, 4.089);
        Assertions.assertTrue(
                almostEqual(func.apply((double) results[0]), (double) results[0], 1e-5)
        );
    }

    private boolean almostEqual(double a, double b, double accuracy) {
        return Math.abs(a - b) <= accuracy;
    }
}
