
package nl.ampere.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.StringConverter;

/**
 * Controller voor dashboard.fxml
 * (Dashboard, eigenaar: Billy).
 */
public class DashboardController {

    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    public void initialize() {
        xAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                double val = object.doubleValue();
                int hours = (int) val;
                int minutes = (int) Math.round((val - hours) * 60);
                return String.format("%02d:%02d", hours, minutes);
            }

            @Override
            public Number fromString(String string) {
                String[] parts = string.split(":");
                int hours = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);
                return hours + minutes / 60.0;
            }
        });

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Energieverbruik");
        series.getData().add(new XYChart.Data<>(16.0, 2));
        series.getData().add(new XYChart.Data<>(16.3, 2));
        series.getData().add(new XYChart.Data<>(16.5, 2.7));
        series.getData().add(new XYChart.Data<>(17.0, 3.8));
        series.getData().add(new XYChart.Data<>(17.3, 3.8));
        series.getData().add(new XYChart.Data<>(17.5, 4.3));
        series.getData().add(new XYChart.Data<>(18.0, 3.4));
        series.getData().add(new XYChart.Data<>(18.2, 1.8));
        series.getData().add(new XYChart.Data<>(18.3, 10.2));
        series.getData().add(new XYChart.Data<>(18.5, 3.5));

        XYChart.Series<Number, Number> limiet = new XYChart.Series<>();
        limiet.setName("Limiet");
        limiet.getData().add(new XYChart.Data<>(16.0, 8));
        limiet.getData().add(new XYChart.Data<>(18.5, 8));

        lineChart.getData().addAll(series, limiet);
        lineChart.setLegendVisible(false);
    }
}
