package ru.ifmo.cmath;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import ru.ifmo.cmath.interpolation.ChebyshevPolynomial;
import ru.ifmo.cmath.interpolation.Function;
import ru.ifmo.cmath.interpolation.LagrangianPolynomialBuilder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Application extends javafx.application.Application {
    private Properties props;
    private Function lagrangianPolynomial;
    private Function initialFunction;
    private List<Double> nodes;
    private double a;
    private double b;
    private int numberOfNodes;
    private boolean isChebyshev;

    @Override
    public void init() throws Exception {
        try (InputStream inputStream = new FileInputStream("lab6 [chebyshev-method]/main/ru/ifmo/cmath/application.properties")) {
            this.props = new Properties();
            this.props.load(inputStream);
            /* Create a initial function */
            this.initialFunction = new Function(props.getProperty("interpolation.initial.function"));
            /* Read an interval of interpolation */
            this.a = Double.parseDouble(props.getProperty("interpolation.interval.a"));
            this.b = Double.parseDouble(props.getProperty("interpolation.interval.b"));
            /* Read a number of interpolation nodes */
            this.numberOfNodes = Integer.parseInt(props.getProperty("interpolation.node"));
            /* Read a value of 'isChebyshev' */
            this.isChebyshev = Boolean.parseBoolean(props.getProperty("interpolation.chebyshev"));
            /* Calculate nodes */
            this.nodes = (this.isChebyshev) ? new ChebyshevPolynomial(numberOfNodes).nodesAt(a, b) : nodesAt(a, b);
            /* Create a lagrangian polynomial */
            this.lagrangianPolynomial = new LagrangianPolynomialBuilder(initialFunction)
                    .setNodes(nodes)
                    .build();
            {
                printTable(initialFunction);
            }
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            /* Terminate */
            System.exit(1);
        }
    }

    private List<Double> nodesAt(double a, double b) {
        if (numberOfNodes < 2) {
            throw new IllegalArgumentException("Number of nodes can not be less than 2");
        }
        List<Double> nodes = new ArrayList<>(numberOfNodes);

        if (a > b) a = a + b - (b = a); // swap
        /* Calculate nodes step */
        double step = (b - a) / (numberOfNodes - 1);
        for (int i = 0; i < numberOfNodes; i++) {
            nodes.add(a + i * step);
        }
        return nodes;
    }

    private void printTable(Function function) {
        int size = String.valueOf(nodes.size()).length();

        List<String> xData = new ArrayList<>(nodes.size());
        List<String> yData = new ArrayList<>(nodes.size());

        int xMax = 6, yMax = 6;
        String t;
        for (Double node : nodes) {
            xData.add(t = String.format("% -2.6f", node));
            xMax = Math.max(xMax, t.length());
            yData.add(t = String.format("% -2.6f", function.apply(node)));
            yMax = Math.max(yMax, t.length());
        }

        String pattern = "| %" + size + "s | %" + xMax + "s | %" + yMax + "s |\n";
        String line = String.join("", Collections.nCopies(8 + size + xMax + yMax, "-"));

        StringBuilder tabular = new StringBuilder(
                String.format("+%s+\n" + pattern + "+%s+\n", line, "№i", "Axis X", "Axis Y", line)
        );
        for (int i = 0; i < nodes.size(); i++) {
            tabular.append(String.format(pattern, i + 1, xData.get(i), yData.get(i)));
        }
        System.out.println(tabular.append("+").append(line).append("+"));
    }

    @Override
    public void start(Stage stage) throws Exception {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);

        ObservableList data1 = FXCollections.observableArrayList();
        ObservableList data2 = FXCollections.observableArrayList();

        XYChart.Series series1 = new XYChart.Series<>(data1);
        series1.setName("Original function");
        XYChart.Series series2 = new XYChart.Series<>(data2);
        series2.setName("Interpolational function");

        chart.getData().addAll(series1, series2);
        chart.getStylesheets().add("ru/ifmo/cmath/chart-styles.css");
        chart.setCursor(Cursor.HAND);

        Scene scene = new Scene(chart, 900, 600);
        stage.setTitle("Interpolation with Lagrangian Polynomial using Chebyshev Nodes");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        drawOriginalFunction(data1);
        drawInterpolationFunction(data2);
    }

    private void drawOriginalFunction(ObservableList data) {
        drawGraphic(initialFunction, data);
    }

    private void drawInterpolationFunction(ObservableList data) {
        drawGraphic(lagrangianPolynomial, data);
        /* Interpolation points */
        for (Double node : nodes) {
            data.add(new XYChart.Data<>(node, lagrangianPolynomial.apply(node)));
        }
    }

    private void drawGraphic(Function function, ObservableList data) {
        if (a > b) a = a + b - (b = a);

        int i = 0, count = 1024;
        for (double step = (b - a) / (count - 1); i < count; i++) {
            XYChart.Data tmp;
            data.add(tmp = new XYChart.Data<>(a + i * step, function.apply(a + i * step)));
            /* Delete a point symbol */
            tmp.getNode().setVisible(false);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
