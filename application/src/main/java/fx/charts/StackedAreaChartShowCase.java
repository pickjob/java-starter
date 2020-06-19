package fx.charts;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.stage.Stage;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class StackedAreaChartShowCase extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        NumberAxis xAxis = new NumberAxis("X Values", 1.0d, 9.0d, 2.0d);
        NumberAxis yAxis = new NumberAxis("Y Values", 0.0d, 30.0d, 2.0d);

        ObservableList<StackedAreaChart.Series> areaChartData =
                FXCollections.observableArrayList(
                        new StackedAreaChart.Series("Series 1",
                                FXCollections.observableArrayList(
                                        new StackedAreaChart.Data(0,4),
                                        new StackedAreaChart.Data(2,5),
                                        new StackedAreaChart.Data(4,4),
                                        new StackedAreaChart.Data(6,2),
                                        new StackedAreaChart.Data(8,6),
                                        new StackedAreaChart.Data(10,8)
                                )),
                        new StackedAreaChart.Series("Series 2",
                                FXCollections.observableArrayList(
                                        new StackedAreaChart.Data(0,8),
                                        new StackedAreaChart.Data(2,2),
                                        new StackedAreaChart.Data(4,9),
                                        new StackedAreaChart.Data(6,7),
                                        new StackedAreaChart.Data(8,5),
                                        new StackedAreaChart.Data(10,7)
                                )),
                        new StackedAreaChart.Series("Series 3",
                                FXCollections.observableArrayList(
                                        new StackedAreaChart.Data(0,2),
                                        new StackedAreaChart.Data(2,5),
                                        new StackedAreaChart.Data(4,8),
                                        new StackedAreaChart.Data(6,6),
                                        new StackedAreaChart.Data(8,9),
                                        new StackedAreaChart.Data(10,7)
                                ))
                );
        StackedAreaChart chart = new StackedAreaChart(xAxis, yAxis, areaChartData);

        primaryStage.setScene(new Scene(chart));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
