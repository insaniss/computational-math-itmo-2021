package ru.ifmo.cmath.algebra;

public class MatrixJacobi {

    private final Function[][] function;

    public MatrixJacobi(Function[][] functions) {
        this.function = functions;
        this.checkDimension(functions);
    }

    private void checkDimension(Function[][] functions) {
        if (functions.length != 2) throw new IllegalArgumentException("Dimension must agree");
        for (Function[] function : functions) {
            if (function.length != 3) throw new IllegalArgumentException("Dimension must agree");
        }
    }

    private Function member(int i, int j) {
        return this.function[i][j];
    }

    public double getJacobian(double x, double y) {
        return member(0, 0).apply(x, y) * member(1, 1).apply(x, y) - member(0, 1).apply(x, y) * member(1, 0).apply(x, y);
    }

    public double getDeltaX(double x, double y) {
        return member(0, 2).apply(x, y) * member(1, 1).apply(x, y) - member(0, 1).apply(x, y) * member(1, 2).apply(x, y);
    }

    public double getDeltaY(double x, double y) {
        return member(0, 0).apply(x, y) * member(1, 2).apply(x, y) - member(0, 2).apply(x, y) * member(1, 0).apply(x, y);
    }
}
