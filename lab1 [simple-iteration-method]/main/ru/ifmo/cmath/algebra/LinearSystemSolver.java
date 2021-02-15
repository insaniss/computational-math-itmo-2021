package ru.ifmo.cmath.algebra;

/**
 * A linear system of equations solver implementation.
 *
 * @since 14 February, 2021
 * @author Bobur Zakirov
 */
public class LinearSystemSolver {

    /**
     * The default accuracy.
     */
    private final double DEFAULT_ACCURACY;

    /**
     * Construct a solver with default accuracy.
     */
    public LinearSystemSolver() {
        this.DEFAULT_ACCURACY = 1E-5; // 0.00001D
    }

    /**
     * Construct a solver with accuracy.
     *
     * @param accuracy System root accuracy.
     */
    public LinearSystemSolver(double accuracy) {
        this.DEFAULT_ACCURACY = accuracy;
    }

    /**
     * Solve a linear system of equations.
     *
     * @param system A linear system of equations.
     * @param accuracy System root accuracy.
     * @return An answer of the system: (Root, error, iteration).
     */
    public JacobiAnswer solveByJacobi(LinearSystem system, double accuracy) {
        int[] rowIndices = system.getPermutedRowsIfPossible();

        if (rowIndices == null) {
            throw new RuntimeException("Impossible to achieve diagonally dominant matrix with row permutations. Iterations diverge.");
        } else {
            system.doRowPermutation(rowIndices);
        }
        Matrix coefficients = system.getMatrixCoefficients();
        Matrix freeMembers = system.getMatrixFreeMembers();

        return iterate(coefficients, freeMembers, accuracy);
    }

    /**
     * Solve a linear system of equations.
     *
     * @param system A linear system of equations.
     * @return An answer of the system: (Root, error, iteration).
     */
    public JacobiAnswer solveByJacobi(LinearSystem system) {
        return this.solveByJacobi(system, DEFAULT_ACCURACY);
    }

    /**
     * Determine the root and error matrices.
     *
     * @param coefficients A matrix of coefficients of the system.
     * @param freeMembers A matrix of free members of the system.
     * @param accuracy The root accuracy
     * @return An answer of the system: (Root, error, iteration).
     */
    private JacobiAnswer iterate(Matrix coefficients, Matrix freeMembers, double accuracy) {
        double[] diagonal = coefficients.getDiagonalArray();

        Matrix a = coefficients.div(diagonal).minus(Matrix.identity(diagonal.length));
        Matrix b = freeMembers.div(diagonal);

        Matrix prev, next = b.copy();
        int iters = 0;
        do {
           prev = next.copy();
           next = b.minus(a.mult(prev)); // X = B - A * X
           iters++;
        } while (next.minus(prev).getAbsMax() > Math.abs(accuracy));

        Matrix errors = next.minus(prev);

        return new JacobiAnswer(next, errors, iters);
    }
}
