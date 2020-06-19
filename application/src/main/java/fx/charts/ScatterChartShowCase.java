package fx.charts;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class ScatterChartShowCase extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        NumberAxis xAxis = new NumberAxis("X-Axis", 0d, 8.0d, 1.0d);
        NumberAxis yAxis = new NumberAxis("Y-Axis", 0.0d, 5.0d, 1.0d);
        Series<Number, Number> series = new Series<>();
        series.setName("Series 1");
        series.getData().addAll(new Data(0.2, 3.5),
                new Data(0.7, 4.6),
                new Data(1.8, 1.7),
                new Data(2.1, 2.8),
                new Data(4.0, 2.2),
                new Data(4.1, 2.6),
                new Data(4.5, 2.0),
                new Data(6.0, 3.0),
                new Data(7.0, 2.0),
                new Data(7.8, 4.0));
        ScatterChart chart = new ScatterChart(xAxis, yAxis);
        chart.getData().add(series);
        primaryStage.setScene(new Scene(chart));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
