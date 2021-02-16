package ru.ifmo.cmath;

import ru.ifmo.cmath.algebra.JacobiAnswer;
import ru.ifmo.cmath.algebra.LinearSystem;
import ru.ifmo.cmath.algebra.LinearSystemSolver;
import ru.ifmo.cmath.algebra.Matrix;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class Application {

    private boolean RUNNING = true;

    private void run() {
        Scanner in = new Scanner(System.in);
        String[] arguments;

        this.help(); // INFO!!!

        while (RUNNING) {
            System.out.print(">>> ");

            arguments = in.nextLine().trim().split("(\\s++)");

            if (arguments[0].isEmpty()) continue;
            switch (arguments[0].toLowerCase(Locale.ROOT)) {
                case "accuracy":
                    this.accuracy(arguments);
                    break;
                case "matrix":
                    this.parse(arguments, in);
                    break;
                case "help":
                    this.help();
                    break;
                case "solve":
                    this.solve();
                    break;
                case "exit":
                    RUNNING = false;
                    break;
                default:
                    System.out.printf("%s: command not found%n", arguments[0]);
                    break;
            }
        }
    }


    private double accuracy;

    public void accuracy(String[] arguments) {
        if (arguments.length == 1) {
            System.out.printf("accuracy: need an option '='%n");
        } else if (arguments.length == 2) {
            if (!arguments[1].equals("=")) {
                System.out.printf("accuracy: invalid option '%s'%n", arguments[1]);
            } else {
                System.out.printf("accuracy: need a value%n");
            }
        } else try {
            this.accuracy = Double.parseDouble(arguments[2]);
        } catch (NumberFormatException e) {
            System.out.printf("accuracy: invalid format of data%n");
        }
    }

    public void parse(String[] arguments, Scanner in) {
        if (arguments.length == 1) {
            this.read(in);
        } else if (arguments[1].toLowerCase(Locale.ROOT).equals("-f")) {
            this.read(arguments);
        } else {
            System.out.printf("matrix: invalid option '%s'%n", arguments[1]);
        }
    }

    private int size;
    private double[][] elements;


    public void read(Scanner in) {
        try {
            int size = Integer.parseInt(in.nextLine().trim());
            if (1 > size || size > 20) {
                System.out.printf("Allowed only natural numbers: 1..20%n");
                return;
            }
            double[][] elements = new double[size][size+1];

            String row, num[];
            int i = 0;
            while (i < size) {
                row = in.nextLine().trim();
                if (row.isEmpty()) continue;

                num = row.split("(,)");
                if (num.length != size + 1) {
                    System.out.printf("Matrix dimension must agree%n");
                } else {
                    for (int j = 0; j < size + 1; ++j) {
                        elements[i][j] = Double.parseDouble(num[j].trim());
                    }
                    i++;
                }
            }
            this.elements = elements;
            this.size = size;
        } catch (RuntimeException e) {
            System.out.printf("Invalid format of data%n");
        }
    }

    public void read(String[] arguments) {
        try {
            if (arguments.length < 3) {
                System.out.printf("matrix: need a file path%n");
            } else {
                File file = new File(arguments[2]);
                Scanner in = new Scanner(new FileReader(file));
                this.accuracy = Double.parseDouble(in.nextLine().trim());
                this.read(in);
            }
        } catch (FileNotFoundException e) {
            System.out.printf("File not found%n");
        } catch (RuntimeException e) {
            System.out.printf("Invalid format of data%n");
        }
    }


    private final LinearSystemSolver solver = new LinearSystemSolver();

    private void solve() {
        if (elements != null) {
            Matrix matrix = new Matrix(elements, size, size + 1);
            LinearSystem system = new LinearSystem(matrix, size);
            JacobiAnswer answer;
            try {
                if (accuracy == 0) answer = solver.solveByJacobi(system);
                else answer = solver.solveByJacobi(system, accuracy);
                System.out.println(answer);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.printf("Please firstly enter some system%n");
        }
    }

    private void help() {
        String builder =
            "+---------------------+----------------------------------------+\n" +
            "|  command            |  information                           |\n" +
            "+---------------------+----------------------------------------+\n" +
            "|  accuracy [=] [val] |  set an accuracy of system solver.     |\n" +
            "|  help               |  show a list of the available command  |\n" +
            "|                     |  and information about them.           |\n" +
            "|  matrix [-f] [path] |  read a data of  the linear system of  |\n" +
            "|                     |  equations.                            |\n" +
            "|         [  ] [    ] |   - for reading from console.          |\n" +
            "|         [-f] [path] |   - for reading from a file(./file.c)  |\n" +
            "|  solve              |  solve the last read linear system of  |\n" +
            "|                     |  equations by simple-iteration-method  |\n" +
            "|                     |  (Jacobi's method).                    |\n" +
            "|  exit               |  terminate the application.            |\n" +
            "+---------------------+----------------------------------------+\n";
        System.out.print(builder);
    }

    public static void main(String[] args) {
        new Application().run();
    }
}
