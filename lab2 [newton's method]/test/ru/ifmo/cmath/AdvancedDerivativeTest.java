package ru.ifmo.cmath;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.ifmo.cmath.algebra.Function;

public class AdvancedDerivativeTest {

    private boolean almostEqual(double a, double b) {
        return Math.abs(a - b) < 1e-4;
    }

    @Test
    public void test0() {
        Function f1 = new Function("sin(x)+sin(y)");
        Function f2 = new Function("cos(x)");
        Assertions.assertTrue(
                almostEqual(f1.derivativeByX(0.21, -0.23, 1e-9), f2.apply(0.21, -0.23))
        );
    }

    @Test
    public void test1() {
        Function f1 = new Function("log(x)+xy^2+x*sin(y^2)");
        Function f2 = new Function("1/x+y^2+sin(y^2)");
        Assertions.assertTrue(
                almostEqual(f1.derivativeByX(0.21, -9.0, 1e-9), f2.apply(0.21, -9.0))
        );
    }

    @Test
    public void test2() {
        Function f1 = new Function("log(x)+xy^2+x*sin(y^2)");
        Function f2 = new Function("2xy+2xy*cos(y^2)");
        Assertions.assertTrue(
                almostEqual(f1.derivativeByY(1.987, 2.09, 1e-9), f2.apply(1.987, 2.09))
        );
    }
}
