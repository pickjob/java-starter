package fx.charts;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class PieChartShowCase extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                    new PieChart.Data("Sun", 20),
                    new PieChart.Data("IBM", 12),
                    new PieChart.Data("HP", 25),
                    new PieChart.Data("Dell", 22),
                    new PieChart.Data("Apple", 30));
        PieChart chart = new PieChart(data);
        chart.setClockwise(false);

        primaryStage.setScene(new Scene(chart));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
