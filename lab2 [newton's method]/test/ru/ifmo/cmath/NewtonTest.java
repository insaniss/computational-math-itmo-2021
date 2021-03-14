package ru.ifmo.cmath;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.ifmo.cmath.algebra.EquationSolver;
import ru.ifmo.cmath.algebra.Function;
import ru.ifmo.cmath.algebra.NonLinearEquationSolver;

public class NewtonTest {

    private final EquationSolver solver;
    {
        solver = new NonLinearEquationSolver();
    }
    @Test
    public void test0() {
        Function f1 = new Function("0.1x^2+x+0.2y^2-0.3");
        Function f2 = new Function("0.2x^2+y-0.1xy-0.7");
        Object[][] result = solver.solveByNewton(0, 0, f1, f2);
        Assertions.assertTrue(
                almostEqual(f1.apply((double) result[0][0], (double) result[1][0]), 0)
                &&
                almostEqual(f2.apply((double) result[0][0], (double) result[1][0]), 0)
        );
    }

    @Test
    public void test1() {
        Function f1 = new Function("sin(2x-y)-1.2x-0.4");
        Function f2 = new Function("0.8x^2+1.5y^2-1");
        Object[][] result = solver.solveByNewton(0.4, -0.75, f1, f2);
        Assertions.assertTrue(
                almostEqual(f1.apply((double) result[0][0], (double) result[1][0]), 0)
                &&
                almostEqual(f2.apply((double) result[0][0], (double) result[1][0]), 0)
        );
    }

    private boolean almostEqual(double a, double b) {
        return Math.abs(a - b) <= 1e-3;
    }
}
