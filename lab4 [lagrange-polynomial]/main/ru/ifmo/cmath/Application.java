package ru.ifmo.cmath;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import ru.ifmo.cmath.interpolation.Function;
import ru.ifmo.cmath.interpolation.LagrangePolynomialBuilder;
import ru.ifmo.cmath.utils.Array;
import ru.ifmo.cmath.utils.Props;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Application extends javafx.application.Application {
    private Properties props;
    private Function experimentalFunction;
    private Function lagrangePolynomial;
    private Double[] experimentalPoints;
    private Double[] interpolationPoints;
    private Double lowerBound;
    private Double upperBound;

    @Override
    public void init() throws Exception {
        try (InputStream data = new FileInputStream(Props.FILE)) {
            this.props = new Properties();
            this.props.load(data);
            /* Create an experimental function */
            this.experimentalFunction = new Function(props.getProperty("experimental.function"));
            /* Parse a string to double array */
            this.experimentalPoints = Array.parseDoubleArray(props.getProperty("experimental.points"));
            this.interpolationPoints = Array.parseDoubleArray(props.getProperty("interpolation.points"));
            /* Calculate graph borders */
            this.lowerBound = Array.minOf(experimentalPoints, interpolationPoints);
            this.upperBound = Array.maxOf(experimentalPoints, interpolationPoints);
            /* Build a Lagrange Polynomial */
            this.lagrangePolynomial = new LagrangePolynomialBuilder(experimentalFunction)
                    .experimentalData(experimentalPoints)
                    .build();
        } catch (Exception exception) {
            this.exit(exception.getMessage(), 0);
        }
    }

    public void exit(String message, int status) {
        System.err.println(message);
        /* Terminate the program with status */
        System.exit(status);
    }

    @Override
    public void start(Stage stage) throws Exception {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        /* Build a line chart */
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        /* Add a stylesheet */
        chart.getStylesheets().add(Props.CSS);
        /* Set a chart as root to scene */
        Scene scene = new Scene(chart, 900, 600);
        /* Calculate a value of data */
        XYChart.Series series1 = createChart(experimentalFunction);
        series1.setName("Experimental function");
        XYChart.Series series2 = createChart(lagrangePolynomial);
        series2.setName("Lagrange polynomial");
        XYChart.Series series3 = createChart(lagrangePolynomial, experimentalPoints);
        series3.setName("Interpolation points");
        XYChart.Series series4 = createChart(lagrangePolynomial, interpolationPoints);
        series4.setName("Interpolation points");
        /* Set a data to line chart */
        if (interpolationPoints.length > 0) {
            chart.getData().addAll(series1, series2, series3, series4);
        } else {
            chart.getData().addAll(series1, series2, series3);
        }
        /* Remove all symbols from first series & second series */
        this.removeChartLineSymbol(series1, series2);

        stage.setTitle("Interpolation with Lagrange Polynomial");
        stage.setScene(scene);
        stage.show();
    }

    private XYChart.Series createChart(Function function) {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        /* Graph step value */
        Double step = (upperBound - lowerBound) / 4096;
        /* Resize lower and upper bound */
        lowerBound -= 128 * step;
        upperBound += 128 * step;
        /* Added data to series */
        for (Double xPoint = lowerBound; xPoint <= upperBound; xPoint += step) {
            Double yPoint = function.apply(xPoint);
            /* If function is defined */
            if (!yPoint.isNaN() && !yPoint.isInfinite()) {
                series.getData().add(new XYChart.Data<>(xPoint, yPoint));
            }
        }
        return series;
    }

    private XYChart.Series createChart(Function function, Double... data) {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        /* Added data to series */
        for (Double xPoint : data) {
            series.getData().add(new XYChart.Data<>(xPoint, function.apply(xPoint)));
        }
        return series;
    }

    @SafeVarargs
    private final void removeChartLineSymbol(XYChart.Series<Double, Double>... series) {
        for (XYChart.Series<Double, Double> s : series) {
            removeChartLineSymbol(s);
        }
    }

    private void removeChartLineSymbol(XYChart.Series<Double, Double> series) {
        for (XYChart.Data<Double, Double> data : series.getData()) {
            /* This node is StackPane */
            data.getNode().setVisible(false);
        }
    }
}
