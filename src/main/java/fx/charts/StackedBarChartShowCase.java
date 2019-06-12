package fx.charts;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.stage.Stage;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class StackedBarChartShowCase extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String[] years = {"2007", "2008", "2009"};
        CategoryAxis xAxis = new CategoryAxis(FXCollections.observableArrayList(years));
        NumberAxis yAxis = new NumberAxis("Units Sold", 0.0d, 10000.0d, 1000.0d);
        final ObservableList<StackedBarChart.Series> barChartData =
                FXCollections.observableArrayList(
                        new StackedBarChart.Series("Region 1",
                                FXCollections.observableArrayList(
                                        new StackedBarChart.Data(years[0], 567d),
                                        new StackedBarChart.Data(years[1], 1292d),
                                        new StackedBarChart.Data(years[2], 1292d)
                                )
                        ),
                        new StackedBarChart.Series("Region 2",
                                FXCollections.observableArrayList(
                                        new StackedBarChart.Data(years[0], 956),
                                        new StackedBarChart.Data(years[1], 1665),
                                        new StackedBarChart.Data(years[2], 2559)
                                )
                        ),
                        new StackedBarChart.Series("Region 3",
                                FXCollections.observableArrayList(
                                        new StackedBarChart.Data(years[0], 1154),
                                        new StackedBarChart.Data(years[1], 1927),
                                        new StackedBarChart.Data(years[2], 2774)
                                )
                        )
                );
        StackedBarChart chart = new StackedBarChart(xAxis, yAxis, barChartData, 25.0d);

        primaryStage.setScene(new Scene(chart));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
