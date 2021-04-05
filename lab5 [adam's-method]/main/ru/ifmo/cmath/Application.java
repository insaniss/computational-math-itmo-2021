package ru.ifmo.cmath;

import ru.ifmo.cmath.differention.OrdinaryDifferentialEquationSolver;
import ru.ifmo.cmath.differention.Function;
import ru.ifmo.cmath.utils.Point;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        OrdinaryDifferentialEquationSolver solver = new OrdinaryDifferentialEquationSolver();
        List list  = solver.solveByAdams(
                new Function("1+x+y/(1-x^2)", "x", "y"),
                new Point(0.0, 0.0),
                1,
                0.001);
    }
}
