package ru.ifmo.cmath.differention;

import ru.ifmo.cmath.utils.Point;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.*;

public class OrdinaryDifferentialEquationSolver {

    public List<Point> solveByAdams(Function function, Point start, double end, double epsilon) {
        List<Point> points = new ArrayList<>();
        points.add(start);
        /* Calculate a parts */
        int parts = (int) ceil((end - start.getX()) / pow(epsilon, 0.25));
        /* . */
        double step = (end - start.getX()) / parts;

        for (int i = 1; i <= parts; i++) {
            if (i <= 3) {
                // todo
            } else {
                int k = 5; // fixme
            }
        }
        return points;
    }
}
