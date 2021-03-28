package ru.ifmo.cmath;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
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

    @Override
    public void init() throws Exception {
        try (InputStream data = new FileInputStream(Props.PATH)) {
            this.props = new Properties();
            this.props.load(data);
            /* Create an experimental function */
            this.experimentalFunction = new Function(props.getProperty("experimental.function"));
            /* Parse a string to double array */
            this.experimentalPoints = Array.parseDoubleArray(props.getProperty("experimental.points"));
            this.interpolationPoints = Array.parseDoubleArray(props.getProperty("interpolation.points"));
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
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        Scene scene = new Scene(chart, 900, 600);

        stage.setScene(scene);
        stage.show();
    }

}
