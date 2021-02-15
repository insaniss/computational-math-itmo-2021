package ru.ifmo.cmath.algebra;

import lombok.Getter;

/**
 * An linear system of equations implementation.
 *
 * @since 14 February, 2021
 * @see IllegalArgumentException
 * @author Bobur Zakirov
 */
public class LinearSystem {

    /**
     * An extended matrix of the system of equations.
     */
    @Getter private Matrix extendedMatrix;

    /**
     * Size of the system.
     */
    @Getter private final int size;

    /**
     * Construct a linear system of equations.
     *
     * @param extendedMatrix An extended matrix of the system.
     * @param size Size of the system.
     * @exception IllegalArgumentException 1.Allowed only natural numbers: 1..20.
     *                                     2.Extended matrix dimension doesn't match.
     */
    public LinearSystem(Matrix extendedMatrix, int size) {
        this.extendedMatrix = extendedMatrix;
        this.size = size;
        if (1 > size || size > 20) {
            throw new IllegalArgumentException("Allowed only natural numbers: 1..20.");
        }
        if (extendedMatrix.getRowDimension() != size || extendedMatrix.getColumnDimension() != size + 1) {
            throw new IllegalArgumentException("Extended matrix dimension doesn't match.");
        }
    }

    /**
     * Get an array of permuted row indices — if it's possible to achieve
     * diagonally dominant matrix of coefficients with row permutations,
     * else return null.
     *
     * @return an array or null.
     */
    public int[] getPermutedRowsIfPossible() {
        int[] rowsIndices = new int[size];
        boolean[] flag = new boolean[size];

        for (int i = 0; i < size; ++i) {
            double absoluteSum = 0D;
            int maxItemIndex = 0;
            for (int j = 0; j < size; ++j) {
                if (Math.abs(extendedMatrix.get(i, maxItemIndex)) < Math.abs(extendedMatrix.get(i, j))) {
                    maxItemIndex = j;
                }
                absoluteSum += Math.abs(extendedMatrix.get(i, j));
            }
            if (2 * Math.abs(extendedMatrix.get(i, maxItemIndex)) > absoluteSum && !flag[maxItemIndex]) {
                flag[maxItemIndex] = true;
                rowsIndices[maxItemIndex] = i;
            } else {
                return null;
            }
        }
        return rowsIndices;
    }

    /**
     * Permute the row by array of indices.
     *
     * @param rowIndices An array of row indices.
     * @exception IllegalArgumentException Row dimension must agree.
     */
    public void doRowPermutation(int[] rowIndices) {
        if (rowIndices.length != size) {
            throw new IllegalArgumentException("Row dimension must agree.");
        }
        this.extendedMatrix = extendedMatrix.subMatrix(rowIndices, 0, size);
    }

    /**
     * Get a matrix of coefficients of the system.
     *
     * @return A submatrix of extended matrix: A[n][n].
     */
    public Matrix getMatrixCoefficients() {
        return this.extendedMatrix.subMatrix(0, 0, size - 1, size - 1);
    }

    /**
     * Get a matrix of free members of the system.
     *
     * @return A submatrix of extended matrix: A[n][1].
     */
    public Matrix getMatrixFreeMembers() {
        return this.extendedMatrix.subMatrix(0, size, size - 1, size);
    }
}
