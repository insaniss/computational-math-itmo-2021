package ru.ifmo.cmath;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.ifmo.cmath.differention.Function;

public class FunctionTest {


    @BeforeAll
    public static void init() {
    }

    @Test
    public void test0() {
        Function f = new Function("sin(x)", "x");
        Assertions.assertEquals(
                f.apply(3.14), Math.sin(3.14)
        );
    }

    @Test
    public void test1() {
        Function f = new Function("log(xy)", "x", "y");
        Assertions.assertEquals(
                f.apply(-0.56, -1.54), Math.log(-0.56 * -1.54)
        );
    }

    @Test
    public void test2() {
        Function f = new Function("sin(x) + cos(y)", "x", "y");
        Assertions.assertEquals(
                f.apply(4.5, -9.54), Math.sin(4.5) + Math.cos(-9.54)
        );
    }

    @Test
    public void test3() {
        Function f = new Function("x^2y", "x", "y");
        Assertions.assertEquals(
                f.apply(4.5, 0.3), Math.pow(4.5, 2) * 0.3
        );
    }

    @Test
    public void test4() {
        Function f = new Function("log(x)", "x");
        Assertions.assertEquals(
                f.apply(-3.0), Double.NaN
        );
    }
}
