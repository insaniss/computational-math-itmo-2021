package ru.ifmo.cmath.gui;

import ru.ifmo.cmath.algebra.EquationSolver;
import ru.ifmo.cmath.algebra.Function;
import ru.ifmo.cmath.algebra.NonLinearEquationSolver;

import java.util.Locale;
import java.util.Scanner;

/**
 * @version 1.1
 * @author Bobur Zakirov
 */
public class Application {
    private final Scanner scanner = new Scanner(System.in);

    // Run...

    public static void main(String[] args) {
        {
            {
                {
                    try { new Application().run(); } catch (Exception e) { print("Oops...%n"); exit(); }
                }
            }
        }
        new Graphic().run();
    }

    private final EquationSolver solver;
    {
        solver = new NonLinearEquationSolver();
    }

    private void run() {

        print("[1] nonlinear equation%n");
        print("[2] system of nonlinear equations%n");

        switch (input()) {
            case "1": nonlinearEquation();
                break;
            case "2": systemOfEquations();
                break;
            default: throw new RuntimeException();
        }
    }

    private void setAccuracy() {

        print("accuracy(0..1): ");

        double accuracy = Double.parseDouble(scanner.nextLine());
        if (0 >= accuracy || accuracy >= 1) {
            setAccuracy();
        }
        solver.setAccuracy(accuracy);
    }

    // Nonlinear equations...

    private void nonlinearEquation() {

        print("[a] %s%n", Math.EQUATION[0]);
        print("[b] %s%n", Math.EQUATION[1]);
        print("[c] %s%n", Math.EQUATION[2]);
        print("[d] %s%n", Math.EQUATION[3]);

        switch (input()) {
            case "a": solveNonlinearEquation(0);
                break;
            case "b": solveNonlinearEquation(1);
                break;
            case "c": solveNonlinearEquation(2);
                break;
            case "d": solveNonlinearEquation(3);
                break;
            default: throw new RuntimeException();
        }
    }

    private void solveNonlinearEquation(int equations) {
        Function[] functions = Math.EQUATIONS[equations];

        setAccuracy();

        print("a: "); double a = scanner.nextDouble();
        print("b: "); double b = scanner.nextDouble();

        Graphic.setData(0, equations);

        print("\u001B[33mbisection method:\u001B[0m ");
        try
        {
            Object[] result = solver.solveByBisection(functions[0], a, b);

            print("[x = %.18f, Δx = %.18f, iters = %d times]%n", result[0], result[1], result[2]);
        }
        catch (RuntimeException e)
        {
            print("%s%n", e.getMessage().toLowerCase(Locale.ROOT));
        }

        print("\u001B[33miterative method:\u001B[0m ");
        try
        {
            Object[] result = solver.solveByIteration(functions[1], a, b);

            print("[x = %.18f, Δx = %.18f, iters = %d times]%n", result[0], result[1], result[2]);
        }
        catch (RuntimeException e)
        {
            print("%s%n", e.getMessage().toLowerCase(Locale.ROOT));
        }
    }


    // System of nonlinear equations...

    private void systemOfEquations() {

        print("[a] %s%n    %s%n", Math.SYSTEM[0][0], Math.SYSTEM[0][1]);
        print("%n");
        print("[b] %s%n    %s%n", Math.SYSTEM[1][0], Math.SYSTEM[1][1]);

        switch (input()) {
            case "a": solveSystemOfEquations(0);
                break;
            case "b": solveSystemOfEquations(1);
                break;
            default: throw new RuntimeException();
        }
    }

    private void solveSystemOfEquations(int system) {
        Function[] systems = Math.SYSTEMS[system];

        setAccuracy();

        Graphic.setData(1, system);

        print("\u001B[33mnewton's method:\u001B[0m ");

        Object[][] result = solver.solveByNewton(0.5, 0.5, systems[0], systems[1]);

        print("[x = %.18f, Δx = %.18f]%n", result[0][0], result[0][1]);
        print("                 ");
        print("[y = %.18f, Δy = %.18f]%n", result[1][0], result[1][1]);

        print("                 [iters = %d]%n", result[2][0]);
    }

    // Utilities...

    private static void print(String pattern, Object... args) {
        System.out.printf(pattern, args);
    }

    private static void exit() {
        System.exit(0);
    }

    // Read a new line...

    private String input() {
        print("> ");
        return scanner.nextLine().trim().toLowerCase(Locale.ROOT);
    }
}
