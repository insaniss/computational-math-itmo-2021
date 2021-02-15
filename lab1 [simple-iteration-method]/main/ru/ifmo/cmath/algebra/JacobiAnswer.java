package ru.ifmo.cmath.algebra;

import lombok.Getter;

/**
 * An answer of Jacobi method implementation.
 *
 * @since 14 February, 2021
 * @see LinearSystemSolver
 * @author Bobur Zakirov
 */
public class JacobiAnswer {

    /**
     * Matrix of answers of the linear system.
     */
    @Getter private final Matrix roots;

    /**
     * Matrix of errors of the linear system.
     */
    @Getter private final Matrix errors;

    /**
     * Count of iterations.
     */
    @Getter private final int iterations;

    /**
     * Construct an answer of the linear system of equations.
     *
     * @param roots Matrix of answers. (x_i, i=1,2,...,n)
     * @param errors Matrix of errors.
     * @param iterations Iteration counts.
     */
    public JacobiAnswer(Matrix roots, Matrix errors, int iterations) {
        this.roots = roots;
        this.errors = errors;
        this.iterations = iterations;
    }
}
