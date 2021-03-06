package ru.ifmo.cmath.algebra;

/**
 * A simple double array matrix implementation.
 *
 * @since 14 February, 2021
 * @see IllegalArgumentException
 * @author Bobur Zakirov
 */
public class Matrix {

    /**
     * Array for internal storage of elements.
     */
    private final double[][] elements;

    /**
     * Row and column dimensions.
     */
    private final int row, column;

    /**
     * Construct an [row]x[column] matrix of zeros.
     *
     * @param row Number of rows.
     * @param column Number of columns.
     * @exception NumberFormatException For invalid dimensions.
     */
    public Matrix(int row, int column) {
        this.elements = new double[row][column];
        this.row = row;
        this.column = column;

        if (row <= 0 || column <= 0) {
            throw new NumberFormatException("For negative(or zero) dimension.");
        }
    }

    /**
     * Construct a matrix from two-dimensional array.
     *
     * @param elements Two-dimensional array of doubles.
     * @param row Number of rows.
     * @param column Number of columns.
     * @exception IllegalArgumentException For invalid dimensions.
     */
    public Matrix(double[][] elements, int row, int column) {
        this.elements = elements;
        this.row = row;
        this.column = column;

        if (row <= 0 || column <= 0) {
            throw new NumberFormatException("For negative(or zero) dimension.");
        }
        this.checkMatrixDimensions(elements);
    }

    /**
     * Check a two-dimensional array for validity.
     *
     * @param elements Two-dimensional array of doubles.
     * @exception IllegalArgumentException The dimension doesn't match.
     */
    private void checkMatrixDimensions(double[][] elements) {
        if (elements.length != row) {
            throw new IllegalArgumentException("Number of rows doesn't match.");
        }
        for (int i = 0; i < row; ++i) {
            if (elements[i].length != column) {
                throw new IllegalArgumentException("Number of columns doesn't match.");
            }
        }
    }

    /**
     * Get a submatrix.
     *
     * @param i0 Initial row index.
     * @param j0 Initial column index.
     * @param i1 Final row index.
     * @param j1 Final column index.
     * @return A new matrix: M(i0:i1,j0:j1).
     * @exception ArrayIndexOutOfBoundsException Submatrix indices out of range.
     */
    public Matrix subMatrix(int i0, int j0, int i1, int j1) {
        Matrix matrix = new Matrix(i1 - i0 + 1, j1 - j0 + 1);
        double[][] elements = matrix.getAsArray();
        try {
            for (int i = i0; i <= i1; ++i) {
                for (int j = j0; j <= j1; ++j) {
                    elements[i-i0][j-j0] = this.elements[i][j];
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices out of range.");
        }
        return matrix;
    }

    /**
     * Get a submatrix.
     *
     * @param rowIndices Array of row indices.
     * @param j0 Initial column index.
     * @param j1 Final column index.
     * @return A new matrix: M(rowIndices[:],j0:j1).
     * @exception ArrayIndexOutOfBoundsException Submatrix indices out of range.
     */
    public Matrix subMatrix(int[] rowIndices, int j0, int j1) {
        Matrix matrix = new Matrix(rowIndices.length, j1 - j0 + 1);
        double[][] elements = matrix.getAsArray();
        try {
            for (int i = 0; i < rowIndices.length; ++i) {
                for (int j = j0; j <= j1; ++j) {
                    elements[i][j-j0] = this.elements[rowIndices[i]][j];
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices out of range.");
        }
        return matrix;
    }

    /**
     * Get an element of matrix.
     *
     * @param i Row index.
     * @param j Column index.
     * @return An element of matrix.
     */
    public double get(int i, int j) {
        return this.elements[i][j];
    }

    /**
     * Get a norm of matrix.
     *
     * @return Maximum row's absolute sum.
     */
    public double getNorm() {
        double norm = 0D;
        for (int i = 0; i < row; ++i) {
            double absoluteSum = 0D;
            for (int j = 0; j < column; ++j) {
                absoluteSum += Math.abs(this.elements[i][j]);
            }
            if (norm < absoluteSum) norm = absoluteSum;
        }
        return norm;
    }

    /**
     * Get an absolutely max element.
     *
     * @return Absolutely max element.
     */
    public double getAbsMax() {
        double maxItem = 0D;
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                if (maxItem < Math.abs(this.elements[i][j]))
                    maxItem = Math.abs(this.elements[i][j]);
            }
        }
        return maxItem;
    }

    /**
     * Multiple matrix to scalar.
     *
     * @param lambda A scalar value.
     * @return A new matrix: lambda * M(row,column).
     */
    public Matrix mult(double lambda) {
        Matrix matrix = new Matrix(row, column);
        double[][] elements = matrix.getAsArray();

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                elements[i][j] = lambda * this.elements[i][j];
            }
        }
        return matrix;
    }

    /**
     * Multiple matrix to matrix.
     *
     * @param matrix An another matrix.
     * @return A new matrix: M(i,j)=A(i,k)*B(k,j).
     * @exception IllegalArgumentException Matrix inner dimensions must agree.
     */
    public Matrix mult(Matrix matrix) {
        if (column != matrix.row) {
            throw new IllegalArgumentException("Matrix inner dimensions must agree.");
        }
        Matrix matrix0 = new Matrix(row, matrix.column);
        double[][] elements = matrix0.getAsArray();

        for (int i = 0; i < matrix0.row; ++i) {

            for (int j = 0; j < matrix0.column; ++j) {
                for (int k = 0; k < column; ++k) {
                    elements[i][j] += this.elements[i][k] * matrix.elements[k][j];
                }
            }
        }
        return matrix0;
    }

    /**
     * Add matrix to matrix
     *
     * @param matrix An another matrix.
     * @return A new matrix: M(i,j)=A(i,j)+B(i,j).
     * @exception IllegalArgumentException Matrix dimensions must agree.
     */
    public Matrix plus(Matrix matrix) {
        if (row != matrix.row || column != matrix.column) {
            throw new IllegalArgumentException("Matrix dimensions must agree.");
        }
        Matrix matrix0 = new Matrix(row, column);
        double[][] elements = matrix0.getAsArray();

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                elements[i][j] = this.elements[i][j] + matrix.elements[i][j];
            }
        }
        return matrix0;
    }

    /**
     * Subtract matrix from matrix.
     *
     * @param matrix An another matrix.
     * @return A new matrix: M(i,j)=A(i,j)-B(i,j).
     * @exception IllegalArgumentException Matrix dimensions must agree.
     */
    public Matrix minus(Matrix matrix) {
        if (row != matrix.row || column != matrix.column) {
            throw new IllegalArgumentException("Matrix dimensions must agree.");
        }
        Matrix matrix0 = new Matrix(row, column);
        double[][] elements = matrix0.getAsArray();

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                elements[i][j] = this.elements[i][j] - matrix.elements[i][j];
            }
        }
        return matrix0;
    }

    /**
     * Get an array of diagonal elements.
     *
     * @return An array of diagonal elements.
     */
    public double[] getDiagonalArray() {
        double[] diagonal = new double[Math.min(row, column)];

        for (int i = 0; i < row && i < column; ++i) {
            diagonal[i] = this.elements[i][i];
        }
        return diagonal;
    }

    /**
     * Divide each row of matrix to array of scalars, responsively.
     *
     * @param divisor An array of row divisors
     * @return A new matrix: A(row,size) / array[:].
     * @exception IllegalArgumentException Dimension must agree.
     */
    public Matrix div(double[] divisor) {
        if (divisor.length != row) {
            throw new IllegalArgumentException("Array dimension must agree with row's.");
        }
        Matrix matrix = new Matrix(row, column);
        double[][] elements = matrix.getAsArray();

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                elements[i][j] = this.elements[i][j] / divisor[i];
            }
        }
        return matrix;
    }

    /**
     * @return An array of elements.
     */
    public double[][] getAsArray() {
        return this.elements;
    }

    /**
     * Copy a matrix.
     *
     * @return A new copy of the matrix.
     */
    public Matrix copy() {
        Matrix matrix = new Matrix(row, column);
        double[][] elements = matrix.getAsArray();

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                elements[i][j] = this.elements[i][j];
            }
        }
        return matrix;
    }

    /**
     * @return Row dimension.
     */
    public int getRowDimension() {
        return this.row;
    }

    /**
     * @return Column dimension.
     */
    public int getColumnDimension() {
        return this.column;
    }

    /**
     * Identity matrix.
     *
     * @param size The dimension of the matrix.
     * @return An identity matrix: I(i,i).
     */
    public static Matrix identity(int size) {
        Matrix matrix = new Matrix(size, size);
        double[][] elements = matrix.getAsArray();

        for (int i = 0; i < size; ++i) elements[i][i] = 1D;
        return matrix;
    }
}
