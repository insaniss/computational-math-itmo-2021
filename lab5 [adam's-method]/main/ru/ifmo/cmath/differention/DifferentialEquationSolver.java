package ru.ifmo.cmath.differention;

import lombok.NonNull;
import ru.ifmo.cmath.utils.Point;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;


public class DifferentialEquationSolver {

    public List<Point> solveByAdams(@NonNull Function function,
                                    @NonNull Point initialPoint, double end, double epsilon) {

        List<Point> points = new ArrayList<>();
        points.add(initialPoint);

        if (0 >= epsilon || epsilon >= 1) {
            throw new IllegalArgumentException("accuracy: allowed only decimal point numbers between 0 and 1");
        }
        /* Find a count of segments using specified inequality. */
        int count = (int) ceil((end - initialPoint.getX()) / pow(epsilon, 0.25));
        /* Calculate a segments' length. */
        double step = (end - initialPoint.getX()) / count;

        List<Double> values = new ArrayList<>();
        for (int idx = 0; idx < count; idx++) {
            /* Calculate function value at every point */
            values.add(function.apply(points.get(idx).getX(), points.get(idx).getY()));

            if (idx < 3) {
                points.add(pointByRungeKutta(function, points, idx + 1, step));
            } else {
                points.add(pointByAdams(values, points, idx + 1, step));
            }
        }
        return points;
    }


    private Point pointByRungeKutta(Function func, List<Point> points, int i, double step) {
        Point point = points.get(i - 1);

        double[] k = new double[4];

        k[0] = func.apply(point.getX(), point.getX()) * step;
        k[1] = func.apply(point.getX() + 0.5 * step, point.getY() + 0.5 * k[0]) * step;
        k[2] = func.apply(point.getX() + 0.5 * step, point.getY() + 0.5 * k[1]) * step;
        k[3] = func.apply(point.getX() + step, point.getY() + k[2]) * step;

        double delta = (k[0] + 2 * k[1] + 2 * k[2] + k[3]) / 6;

        return new Point(points.get(0).getX() + i * step, point.getY() + delta);
    }



    private Point pointByAdams(List<Double> values, List<Point> points, int i, double step) {
        Point point = points.get(i - 1);

        double[] q = new double[4];

        q[0] = values.get(i - 1) * step;
        q[1] = values.get(i - 2) * step;
        q[2] = values.get(i - 3) * step;
        q[3] = values.get(i - 4) * step;

        double delta = (55 * q[0] - 59 * q[1] + 37 * q[2] - 9 * q[3]) / 24;

        return new Point(points.get(0).getX() + i * step, point.getY() + delta);
    }
}
