package ru.ifmo.cmath.interpolation;

import java.util.ArrayList;
import java.util.List;

public class ChebyshevPolynomial {
    private final int order;

    public ChebyshevPolynomial(int order) {
        this.order = order;
    }

    public List<Double> nodesAt(double a, double b) {
        List<Double> nodes = new ArrayList<>(order);
        /*
         * Nodes affine transformation over an arbitrary interval [a,b]
         */
        for (Double root : roots()) {
            nodes.add(0.5 * (a + b) + 0.5 * (a - b) * root);
        }
        return nodes;
    }

    private Double root(int i) {
        /*
         * i-th root of Chebyshev polynomial, where i = 1,2,...,n
         */
        return Math.cos(Math.PI * (i - 0.5) / order);
    }

    public List<Double> roots() {
        List<Double> roots = new ArrayList<>(order);
        /*
         * Chebyshev polynomial roots at (-1,1)
         */
        for (int i = 1; i <= order; i++) {
            roots.add(root(i));
        }
        return roots;
    }
}
