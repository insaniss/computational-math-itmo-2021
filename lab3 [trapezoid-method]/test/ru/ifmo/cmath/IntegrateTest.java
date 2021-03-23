package ru.ifmo.cmath;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.ifmo.cmath.algebra.Function;
import ru.ifmo.cmath.algebra.Integral;
import ru.ifmo.cmath.algebra.IntegralAnswer;
import ru.ifmo.cmath.algebra.Integrator;

public class IntegrateTest {
    private static Integrator integrator;

    @BeforeAll
    public static void init() {
        integrator = new Integrator();
    }

    @Test
    public void test0() {
        Integral integral = new Integral(new Function("sin(x) / x")).from(-1.3645).to(2.321);
        IntegralAnswer ans = integrator.integrate(integral, 0.00001);
        Assertions.assertTrue(
                almostEqual(ans.area(), 2.959920645245537)
        );
    }

    @Test
    public void test1() {
        Integral integral = new Integral(new Function("x^2")).from(-8.97).to(1.987);
        IntegralAnswer ans = integrator.integrate(integral, 0.00001);
        Assertions.assertTrue(
                almostEqual(ans.area(), 243.1930949343334)
        );
    }

    @Test
    public void test2() {
        Integral integral = new Integral(new Function("log(abs(x))")).from(-0.34).to(3.4);
        IntegralAnswer ans = integrator.integrate(integral, 0.00001);
        Assertions.assertTrue(
                almostEqual(ans.area(), 0.05404118264873708)
        );
    }

    @Test
    public void test3() {
        Integral integral = new Integral(new Function("log(x)")).from(0.1).to(3.4);
        IntegralAnswer ans = integrator.integrate(integral, 0.00001);
        Assertions.assertTrue(
                almostEqual(ans.area(), 1.091094976814598)
        );
    }

    private boolean almostEqual(Double a, Double b) {
        return Math.abs(a - b) < 1e-3;
    }
}
