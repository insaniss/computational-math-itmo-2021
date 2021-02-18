package ru.ifmo.cmath;

import ru.ifmo.cmath.algebra.JacobiAnswer;
import ru.ifmo.cmath.algebra.LinearSystem;
import ru.ifmo.cmath.algebra.LinearSystemSolver;
import ru.ifmo.cmath.algebra.Matrix;

import java.io.*;
import java.util.*;

/**
 * Basic application implementation.
 *
 * @since 14 February, 2021
 * @author Bobur Zakirov
 */
public class Application {

    private final String RESET  = "\u001B[0m";

    private final String RED    = "\u001B[31m";
    private final String GREEN  = "\u001B[32m";
    private final String YELLOW = "\u001B[33m";
    private final String BLUE   = "\u001B[34m";
    private final String CYAN   = "\u001B[36m";

    /**
     * The application enter point.
     */
    public static void main(String[] args) {
        new Application().run();
    }

    /**
     * Print a command execute errors.
     *
     * @param arg A help string.
     * @param space Count of left side spaces.
     */
    private void error(String arg, int space) {
        printf("%serror: %s" + (arg) + "%n", RED, RESET, RED, RESET);
        printf("%s%" + (space) + "s%n", RED, "^");
    }

    /**
     * Print a command usage info.
     *
     * @param arg A usage info string.
     */
    private void usage(String arg) {
        printf("%susage: %s" + (arg) + "%n", YELLOW, RESET);
    }

    /**
     * Print a warning info.
     *
     * @param args A warning info string.
     */
    private void warning(int args) {
        printf("%swarning: %sallowed %s" + (args) + "%s argument(s)%n", YELLOW, RESET, BLUE, RESET);
    }

    /**
     * Print a string using by format.
     *
     * @param s A string pattern.
     * @param objects Objects.
     */
    private void printf(String s, Object... objects) {
        System.out.printf(s, objects);
    }

    /**
     * Application status.
     */
    private boolean __RUNNING__ = true;

    /**
     * Parse a user inputs.
     */
    public void run(){
        Scanner in = new Scanner(System.in);
        String[] args;

        printf("+-------------------------------------------+%n" +
               "| %sSIMPLE ITERATION METHOD (JACOBI'S METHOD)%s |%n" +
               "+-------------------------------------------+%n", RED, RESET);

        while (__RUNNING__) {
            printf("%ssimple-iteration-method%s$ ", CYAN, RESET);

            args = in.nextLine().trim().split("(\\s++)");

            switch (args[0].toLowerCase(Locale.ROOT)) {
                case "accuracy":
                    parseAccuracy(args);
                    break;
                case "matrix":
                    parseMatrix(args, in);
                    break;
                case "solve":
                    parseSolve(args);
                    break;
                case "show":
                    parseShow(args);
                    break;
                case "help":
                    parseHelp();
                    break;
                case "exit":
                    parseExit();
                    break;
                default:
                    parseNothing(args);
                    break;
            }
        }
    }

    /**
     * The ACCURACY.
     */
    private double accuracy;

    /**
     * The SIZE of equation.
     */
    private int size;

    /**
     * An array of elements of the EXTENDED MATRIX of linear system.
     */
    private double[][] elements;

    /**
     * Command ACCURACY.
     *
     * @param args An array of command args.
     */
    private void parseAccuracy(String[] args) {
        try {
            if (args.length > 3) {
                warning(2);
            } else if (args[1].equals("=")) {
                accuracy = Double.parseDouble(args[2]);
            } else {
                error("accuracy %s=%s [double]", 17);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            usage("accuracy = [double]");
        } catch (NumberFormatException e) {
            error("accuracy = [%sdouble%s]", 22);
        }
    }

    /**
     * Command MATRIX.
     *
     * @param args An array of command args.
     * @param in A standard input stream.
     */
    private void parseMatrix(String[] args, Scanner in) {
        if (args.length == 1) {
            parseMatrixC(in, false);
        } else {
            parseMatrixF(args);
        }
    }

    /**
     * Command MATRIX (console).
     *
     * @param in A standard input stream.
     * @param isFile Read a file.
     */
    private void parseMatrixC(Scanner in, boolean isFile) {
        int j = 0, size = -1;
        double[][] elements;
        String row, num[] = new String[0];
        try {
            size = Integer.parseInt(in.nextLine().trim());
            if (1 > size || size > 20) {
                throw new RuntimeException();
            }
            elements = new double[size][size + 1];
            int i = 0;
            while (i < size) {
                row = in.nextLine().trim().replaceAll(",", ".");
                if (row.isEmpty() && !isFile) {
                    continue;
                }
                num = row.split("(\\s++)");
                if (num.length != size + 1) {
                    throw new RuntimeException();
                } else {
                    for (j = 0; j <= size; ++j) {
                        elements[i][j] = Double.parseDouble(num[j]);
                    }
                    i++;
                }
            }
            this.size = size;
            this.elements = elements;
            printf("matrix: %ssuccessfully read%s%n", GREEN, RESET);
        } catch (NoSuchElementException e) {
            printf("matrix: %sinvalid file data%n", YELLOW);
        } catch (NumberFormatException e) {
            if (size == -1) {
                printf("matrix: invalid format [size=%sint%s]%n", BLUE, RESET);
            } else {
                printf("matrix: invalid format - %s%s%n", RED, num[j]);
            }
        } catch (RuntimeException e) {
            if (1 <= size && size <= 20) {
                printf("matrix: dimensions must agree%n");
            } else {
                printf("matrix: allowed only [1..20]%n");
            }
        }
    }

    /**
     * Command MATRIX (file).
     *
     * @param args An array of command args.
     */
    private void parseMatrixF(String[] args) {
        try {
            if (args.length > 3) {
                warning(2);
            } else if (args[1].equals("-f")) {
                Scanner file = new Scanner(new File(args[2]));
                parseMatrixC(file, true);
            } else if (args[1].equals("-g")) {
                parseMatrixG(args);
            } else {
                error("matrix %s-f%s [path]", 16);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            error("matrix -f [%spath%s]", 20);
        } catch (FileNotFoundException e) {
            printf("matrix: %sfile not found%n", RED);
        }
    }

    /**
     * Command MATRIX (generate).
     *
     * @param args An array of command args.
     */
    private void parseMatrixG(String[] args) {
        try {
            size = Integer.parseInt(args[2]);
            if (1 > size || size > 20) {
                throw new RuntimeException();
            }
            Integer[] indices = new Integer[size];
            double[] sums = new double[size];
            elements = new double[size][size + 1];
            for (int i = 0; i < size; ++i) {
                indices[i] = i;
                for (int j = 0; j < size + 1; ++j) {
                    elements[i][j] = Math.random() * 10D - 5D;
                    sums[i] += Math.abs(elements[i][j]);
                }
                sums[i] -= Math.abs(elements[i][size]);
            }
            List<Integer> list = Arrays.asList(indices);
            Collections.shuffle(list);
            list.toArray(indices);

            for (int i = 0; i < size; ++i) {
                elements[indices[i]][i] = sums[indices[i]] + Math.random() + 1.0;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            usage("matrix -g [int]");
        } catch (NumberFormatException e) {
            printf("matrix: invalid format [size=%sint%s]%n", BLUE, RESET);
        } catch (RuntimeException e) {
            printf("matrix: allowed only [1..20]%n");
        }
    }

    /**
     * Linear system solver.
     */
    private final LinearSystemSolver solver = new LinearSystemSolver();

    /**
     * Command SOLVE.
     *
     * @param args An array of command args.
     */
    private void parseSolve(String[] args) {
        if (args.length > 1) {
            warning(0);
        } else if (elements != null) {
            Matrix matrix = new Matrix(elements, size, size + 1);
            LinearSystem system = new LinearSystem(matrix, size);
            JacobiAnswer answer;
            try {
                answer = solver.solveByJacobi(system, accuracy);
                System.out.println(answer);
            } catch (RuntimeException e) {
                printf("%s%n", e.getMessage());
            }
        } else {
            printf("solve: firstly enter a matrix%n");
        }
    }

    /**
     * Command SHOW.
     *
     * @param args An array of command args.
     */
    private void parseShow(String[] args) {
        if (args.length == 1) {
            parseShowA();
            parseShowM();
        } else if (args.length > 2) {
            warning(1);
        } else if (args[1].equals("-a")) {
            parseShowA();
        } else if (args[1].equals("-m")) {
            parseShowM();
        } else {
            usage("show -a[ccuracy]");
            usage("show -m[atrix]");
        }
    }

    /**
     * Command SHOW (accuracy).
     */
    private void parseShowA() {
        printf("accuracy=%.12f%n", accuracy);
    }

    /**
     * Command SHOW (matrix).
     */
    private void parseShowM() {
        if (elements != null) {
            for (int i = 0; i < size; ++i) {
                printf("[");
                for (int j = 0; j < size - 1; ++j) {
                    printf("%s, ", elements[i][j]);
                }
                printf("%s] * %sx_%s%s = %s%n", elements[i][size-1], RED, i + 1, RESET, elements[i][size]);
            }
        } else {
            printf("show: firstly enter a matrix%n");
        }
    }

    /**
     * Command EXIT.
     */
    private void parseExit() {
        this.__RUNNING__ = false;
    }

    /**
     * Command NOT found.
     *
     * @param args An array of args.
     */
    private void parseNothing(String[] args) {
        if(!args[0].isEmpty()) {
            printf("%s%s: %scommand not found%n", RESET, args[0], RED);
        }
    }

    /**
     * Command HELP.
     */
    private void parseHelp() {
        printf("" +
                " accuracy  =  [value]    Set an accuracy of the algebraic linear system of equations%n" +
                "                         solver. Usage:                                             %n" +
                "                           > accuracy = 0.0001D                                  (1)%n" +
                "                           > accuracy = 1E-5                                     (2)%n" +
                "                           > accuracy = 0.000134532234                           (3)%n" +
                " matrix        [ ][ ]    Read a data of extended matrix of a algebraic linear system%n" +
                "                         of equations from console.                                 %n" +
                "         -f    [path]    Read a data from a file. Usage:                            %n" +
                "         -g    [size]    Generate   a  new  diagonally   dominant  matrix   size  of%n" +
                "                         M[size]x[size+1].                                          %n" +
                " exit                    terminate the application.                                 %n" +
                " solve                   Solve the last read algebraic linear system of equations by%n" +
                "                         simple iteration method (Jacobi's method).                 %n" +
                " show             [ ]    Show  an  accuracy  value  and  active  matrix  of  system.%n" +
                "         -a [ccuracy]    Show only accuracy value. Example:                         %n" +
                "         -m   [atrix]    Show only and only active matrix of system. Example        %n" +
                " help                    display a help information on console.                     %n"
        );
    }
}
