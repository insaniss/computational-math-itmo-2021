package ru.ifmo.cmath.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Graphic extends Application {
    private static int TYPE = 0, PROBLEM = 0;

    public static void setData(int type, int problem) {
        Graphic.TYPE = type;
        Graphic.PROBLEM = problem;
    }

    public void run() {
        launch();
    }

    @FXML
    public LineChart lineChart;

    @Override
    public void start(Stage stage) throws Exception {

        lineChart = new LineChart(
                new NumberAxis(-10, 10, 2),
                new NumberAxis(-10, 10, 2)
        );

        Scene scene = new Scene(lineChart, 900, 600);

        if (Graphic.TYPE == 0) drawFirstType();
        if (Graphic.TYPE == 1) drawSecondType();

        stage.setScene(scene);
        stage.show();
    }

    private void drawFirstType() {
        for (int i = 0; i < Math.EQUATIONS[Graphic.PROBLEM].length; i++) {
            XYChart.Series<Double, Double> series = new XYChart.Series<>();

            series.setName(Math.GRAPHS[Graphic.PROBLEM][i]);

            lineChart.setCreateSymbols(false);
            lineChart.getData().add(series);

            for (double point = -10; point <= 10; point += 0.01) {
                series.getData().add(new XYChart.Data<>(point, Math.EQUATIONS[Graphic.PROBLEM][i].apply(point)));
            }
        }
    }

    private void drawSecondType() {
        for (int i = 2; i < Math.SYSTEMS[Graphic.PROBLEM].length; i++) {
            XYChart.Series<Double, Double> series = new XYChart.Series<>();

            series.setName(Math.GRAPH[Graphic.PROBLEM][i-2]);

            lineChart.setCreateSymbols(false);
            lineChart.getData().add(series);

            for (double point = -10; point <= 10; point += 0.01) {
                series.getData().add(new XYChart.Data<>(point, Math.SYSTEMS[Graphic.PROBLEM][i].apply(point)));
            }
        }
    }
}
