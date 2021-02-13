package ru.ifmo.cmath;

public class LinearSystem {
    private final double[][] extendedMatrix;
    private final int size;

    public LinearSystem(double[][] extendedMatrix, int size) {
        this.extendedMatrix = extendedMatrix;
        this.size = size;

        if (1 > size || size > 20) throw new IllegalArgumentException("Allowed only 1..20 size of system.");
        if (!isValidExtendedMatrix()) {
            throw new IllegalArgumentException("Number of rows or columns does not match the system of equations.");
        }
    }

    private boolean isValidExtendedMatrix() {
        if (extendedMatrix.length != size) return false;
        for (int i = 0; i < size; ++i) {
            if (extendedMatrix[i].length != size + 1) return false;
        }
        return true;
    }

    public int[] getPermutedRowsIfPossible() {
        int[] permutedRows = new int[size];
        boolean[] flag = new boolean[size];

        for (int i = 0; i < size; ++i) {
            double absoluteSum = 0.0d;
            int maxElemIndex = 0;
            for (int j = 0; j < size; ++j) {
                if (Math.abs(extendedMatrix[i][maxElemIndex]) < Math.abs(extendedMatrix[i][j])) {
                    maxElemIndex = j;
                }
                absoluteSum += Math.abs(extendedMatrix[i][j]);
            }
            if (2 * Math.abs(extendedMatrix[i][maxElemIndex]) > absoluteSum && !flag[maxElemIndex]) {
                flag[maxElemIndex] = true;
                permutedRows[maxElemIndex] = i;
            } else {
                return null;
            }
        }
        return permutedRows;
    }
}
