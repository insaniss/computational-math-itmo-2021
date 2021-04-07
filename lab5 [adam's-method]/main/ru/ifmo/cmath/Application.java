package ru.ifmo.cmath;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import ru.ifmo.cmath.differention.DifferentialEquationSolver;
import ru.ifmo.cmath.differention.Function;
import ru.ifmo.cmath.differention.LagrangianPolynomialBuilder;
import ru.ifmo.cmath.utils.Point;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Application extends javafx.application.Application {
    private Properties props;
    private Function lagrangianPolynomial;
    private Function rightHandSide;
    private double x0;
    private double xN;
    private double y0;
    private double epsilon;
    private List<Point> axisData;

    @Override
    public void init() throws Exception {
        try (InputStream data = new FileInputStream("lab5 [adam's-method]/main/ru/ifmo/cmath/differential.properties")) {
            this.props = new Properties();
            this.props.load(data);
            /* Create a right hand side expression */
            this.rightHandSide = new Function(props.getProperty("differential.function"), "x", "y");
            /* Read axis data */
            this.x0 = Double.parseDouble(props.getProperty("differential.equation.x0"));
            this.xN = Double.parseDouble(props.getProperty("differential.equation.xN"));
            this.y0 = Double.parseDouble(props.getProperty("differential.equation.y0"));
            /* Read an accuracy */
            this.epsilon = Double.parseDouble(props.getProperty("differential.equation.epsilon"));
            {
                this.solve();
            }
        } catch (Exception exception) {
            this.error(exception.getMessage(), 1);
        }
    }

    private void solve() {
        DifferentialEquationSolver solver = new DifferentialEquationSolver();
        try {
            /* Solve an ordinary differential equation */
            axisData = solver.solveByAdams(rightHandSide, new Point(x0, y0), xN, epsilon);
            /* Print an answer table */
            this.print(axisData);
            /* Build a lagrangian polynomial */
            lagrangianPolynomial = new LagrangianPolynomialBuilder().setAxisData(axisData).build();
        } catch (IllegalArgumentException exception) {
            this.error(exception.getMessage(), 1);
        } catch (RuntimeException exception) {
            this.warn(exception.getMessage());
        }
    }

    private void print(List<Point> points) {
        int size = String.valueOf(points.size()).length();
        String line = String.join("", Collections.nCopies(size + 44, "-"));
        /* Build a header of the tabular */
        StringBuilder builder = new StringBuilder(
                String.format("+%s+\n| %" + size + "s |       Axis X       |       Axis Y       |\n+%s+\n", line, "№i", line)
        );
        /* Build a result tabular */
        for (int i = 1; i <= points.size(); i++) {
            builder.append(String.format("| %" + size + "s | %018.9f | %018.9f |\n", i,  points.get(i - 1).getX(), points.get(i - 1).getY()));
        }
        /* Print a result */
        System.out.println(builder.append(String.format("+%s+\n", line)));
    }

    private void error(String message, int status) {
        System.err.printf("[error] %s\n", message.toLowerCase());
        /* Terminate */
        System.exit(status);
    }

    private void warn(String message) {
        System.err.printf("[warn] %s\n", message.toLowerCase());
    }

    @Override
    public void start(Stage stage) throws Exception {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        /*
         * Build a line chart.
         */
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        /*
         * Create an observable data list.
         */
        ObservableList data = FXCollections.observableArrayList();
        /*
         * Build a series.
         */
        XYChart.Series series = new XYChart.Series<>(data);
        series.setName("Final Function");
        /*
         * Some chart settings.
         */
        chart.getData().add(series);
        chart.getStylesheets().add("ru/ifmo/cmath/chart-styles.css");
        chart.setCursor(Cursor.HAND);
        /*
         * Set a chart as root to scene.
         */
        Scene scene = new Scene(chart, 900, 600);
        stage.setTitle("Ordinary Differential Equation Solver (Adam's Method)");
        stage.setScene(scene);
        stage.show();
        /*
         * Draw a graphic of final function.
         */
        if (lagrangianPolynomial == null) {
            drawWithoutLagrangianPolynomial(data);
        } else {
            drawWithLagrangianPolynomial(data);
        }
    }

    private void drawWithoutLagrangianPolynomial(ObservableList data) {
        XYChart.Data tmp;
        for (Point point : axisData) {
            data.add(tmp = new XYChart.Data<>(point.getX(), point.getY()));
            /* Delete a point symbol */
            tmp.getNode().setVisible(false);
        }
    }

    private void drawWithLagrangianPolynomial(ObservableList data) {
        double step = (xN - x0) / 1024;

        XYChart.Data tmp;
        for (double point = x0; point <= xN; point += step) {
            data.add(tmp = new XYChart.Data<>(point, lagrangianPolynomial.apply(point)));
            /* Delete a point symbol */
            tmp.getNode().setVisible(false);
        }
        for (Point point : axisData) {
            data.add(tmp = new XYChart.Data<>(point.getX(), point.getY()));
        }
    }
}
