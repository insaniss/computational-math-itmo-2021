package ru.ifmo.cmath;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.ifmo.cmath.algebra.Function;

public class FunctionTest {
    @BeforeAll
    public static void init() {
    }

    @Test
    public void test0() {
        Function f = new Function("sin(x) / x");
        Assertions.assertEquals(
                1, f.apply(1e-9)
        );
    }

    @Test
    public void test1() {
        Function f = new Function("sin(x) ^ 2 + cos(x) ^ 2");
        Assertions.assertEquals(
                1, f.apply(1.57)
        );
    }

    @Test
    public void test2() {
        Function f = new Function("sin(log(x)) ^ (-1) * x - atan(x)");
        Assertions.assertEquals(
                10.595327274410854, f.apply(9.43)
        );
    }
}
