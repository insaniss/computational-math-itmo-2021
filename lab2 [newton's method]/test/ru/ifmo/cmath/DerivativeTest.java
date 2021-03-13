package ru.ifmo.cmath;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.ifmo.cmath.algebra.Function;

public class DerivativeTest {

    private boolean almostEqual(double a, double b) {
        return Math.abs(a - b) < 1e-6;
    }

    @Test
    public void test0() {
        Function f1 = new Function("sin(x) + cos(x)");
        Function f2 = new Function("cos(x) - sin(x)");
        Assertions.assertTrue(
                almostEqual(f1.derivative(0.3, 1e-9), f2.apply(0.3))
        );
    }

    @Test
    public void test1() {
        Function f1 = new Function("e^(sin(x))");
        Function f2 = new Function("cos(x) * e^(sin(x))");
        Assertions.assertTrue(
                almostEqual(f1.derivative(1.3, 1e-9), f2.apply(1.3))
        );
    }

    @Test
    public void test2() {
        Function f1 = new Function("x");
        Function f2 = new Function("1");
        Assertions.assertTrue(
                almostEqual(f1.derivative(0.3, 1e-9), f2.apply(0.3))
        );
    }

    @Test
    public void test3() {
        Function f1 = new Function("99");
        Function f2 = new Function("0");
        Assertions.assertTrue(
                almostEqual(f1.derivative(56, 1e-9), f2.apply(0))
        );
    }

    @Test
    public void test4() {
        Function f1 = new Function("log(x) + sin(x) + e^(log(x))");
        Function f2 = new Function("1 / x + cos(x) + 1 / x * e^(log(x))");
        Assertions.assertTrue(
                almostEqual(f1.derivative(1.46, 1e-9), f2.apply(1.46))
        );
    }

    @Test
    public void test5() {
        Function f1 = new Function("1 + x + x^2 + x^3 + x^4 + x^5");
        Function f2 = new Function("1 + 2x + 3x^2 + 4x^3 + 5x^4");
        Assertions.assertTrue(
                almostEqual(f1.derivative(-4.53, 1e-5), f2.apply(-4.53))
        );
    }
}
