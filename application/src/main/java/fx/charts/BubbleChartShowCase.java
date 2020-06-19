package fx.charts;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class BubbleChartShowCase extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        BubbleChart<Number, Number> bubbleChart = new BubbleChart<>(xAxis, yAxis);
        String bubbleChartCss = getClass().getResource("BubbleChart.css").toExternalForm();
        bubbleChart.getStylesheets().add(bubbleChartCss);
        bubbleChart.setTitle("Advanced BubbleChart");
        xAxis.setLabel("X Axis");
        yAxis.setLabel("Y Axis");
        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("Data Series 1");
        for (int i = 0; i < 20; i++) {
            series1.getData().add(
                    new XYChart.Data<Number, Number>(Math.random() * 100,
                            Math.random() * 100,
                            Math.random() * 10));
        }
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.setName("Data Series 2");
        for (int i = 0; i < 20; i++) {
            series2.getData().add(
                    new XYChart.Data<Number, Number>(Math.random() * 100,
                            Math.random() * 100,
                            Math.random() * 10));
        }
        bubbleChart.getData().addAll(series1, series2);

        primaryStage.setScene(new Scene(bubbleChart));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
