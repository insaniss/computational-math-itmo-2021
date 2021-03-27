package ru.ifmo.cmath;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.ifmo.cmath.interpolation.Function;
import ru.ifmo.cmath.interpolation.LagrangePolynomialBuilder;

public class LagrangePolynomialTest {
    private static Double[] data;

    @BeforeAll
    public static void init() {
        data = new Double[] { 0.3, -4.5, 2.35, 23.4565 };
    }

    @Test
    public void test0() {
        Function func = new Function("sin(x)");
        Function lagrange = new LagrangePolynomialBuilder(func)
                .experimentalData(data)
                .build();
        for (Double val : data) {
            Assertions.assertEquals(func.apply(val), lagrange.apply(val)); // OK
        }
    }

    @Test
    public void test1() {
        Function func = new Function("1/((x-1)(x-3)(x-5))");
        Function lagrange = new LagrangePolynomialBuilder(func)
                .experimentalData(data)
                .build();
        for (Double val : data) {
            Assertions.assertEquals(func.apply(val), lagrange.apply(val)); // OK
        }
    }

    @Test
    public void test2() {
        Function func = new Function("log(abs(x))");
        Function lagrange = new LagrangePolynomialBuilder(func)
                .experimentalData(data)
                .build();
        for (Double val : data) {
            Assertions.assertEquals(func.apply(val), lagrange.apply(val)); // OK
        }
    }

    @Test
    public void test3() {
        Function func = new Function("sin(x)^2+cos(x)^2");
        Function lagrange = new LagrangePolynomialBuilder(func)
                .experimentalData(data)
                .build();
        for (Double val : data) {
            Assertions.assertEquals(func.apply(val), lagrange.apply(val)); // OK
        }
    }
}
