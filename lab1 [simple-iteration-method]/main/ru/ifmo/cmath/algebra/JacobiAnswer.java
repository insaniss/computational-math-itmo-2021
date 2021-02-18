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

    /**
     * Get a string data of table row.
     *
     * @param i A number of row.
     * @param roots An item of root column.
     * @param errors An item of infelicity column.
     * @return Row string: <|| i | root | infelicity ||>
     */
    private String pattern(double i, double roots, double errors) {
        int $ = 17 - String.valueOf((int) this.roots.getAbsMax()).length();
        return String.format("│ %02.0f │ % -20." + $ + "f │ % -20.16f │%n", i, roots, errors);
    }

    /**
     * Draw a result tabular of Jacobi answer.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("+————+——————————————————————+——————————————————————+\n")
               .append("│ №i │  root (x)            │  infelicity (delta)  │\n")
               .append("+————+——————————————————————+——————————————————————+\n");
        for (int i = 0; i < roots.getRowDimension(); ++i) {
            builder.append(pattern(i + 1, roots.get(i, 0), errors.get(i, 0)));
        }
        builder.append("+————+——————————————————————+——————————————————————+\n")
               .append("> Iterations: ").append(iterations).append(" times.");

        return builder.toString();
    }
}
