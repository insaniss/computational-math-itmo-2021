package ru.ifmo.cmath;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.ifmo.cmath.interpolation.ChebyshevPolynomial;

public class ChebyshevTest {
    @BeforeAll
    public static void init() {
    }
    @Test
    public void test0() {
        ChebyshevPolynomial chebyshevPolynomial = new ChebyshevPolynomial(7);
        for (Double node : chebyshevPolynomial.nodesAt(-5, 5)) {
            System.out.println(node);
        }
    }
}
