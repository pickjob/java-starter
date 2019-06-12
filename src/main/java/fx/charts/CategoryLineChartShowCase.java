package fx.charts;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class CategoryLineChartShowCase extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        // setup charts
        chart.setTitle("LineChart with Category Axis");
        xAxis.setLabel("X Axis");
        yAxis.setLabel("Y Axis");
        // add starting data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Data Series 1");
        series.getData().add(new XYChart.Data<String, Number>(CATEGORIES[0], 50d));
        series.getData().add(new XYChart.Data<String, Number>(CATEGORIES[1], 80d));
        series.getData().add(new XYChart.Data<String, Number>(CATEGORIES[2], 90d));
        series.getData().add(new XYChart.Data<String, Number>(CATEGORIES[3], 30d));
        series.getData().add(new XYChart.Data<String, Number>(CATEGORIES[4], 122d));
        series.getData().add(new XYChart.Data<String, Number>(CATEGORIES[5], 10d));
        chart.getData().add(series);

        primaryStage.setScene(new Scene(chart));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static final String[] CATEGORIES = { "Alpha", "Beta", "RC1", "RC2", "1.0", "1.1" };
}
