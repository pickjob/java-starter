package fx.charts;

import java.util.Arrays;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class HorizontalBarChartShowCase extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Price");
        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel("Year");
        String[] years = {"2007", "2008", "2009"};
        ObservableList<String> categories = FXCollections.<String>observableArrayList(Arrays.asList(years));
        yAxis.setCategories(categories);

        BarChart<Number, String> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Horizontal Bar Chart Example");
        XYChart.Series<Number, String> series1 = new XYChart.Series<>();
        series1.setName("Data Series 1");
        series1.getData().addAll(
                new XYChart.Data<Number, String>(567, years[0]),
                new XYChart.Data<Number, String>(1292, years[1]),
                new XYChart.Data<Number, String>(2180, years[2]));

        XYChart.Series<Number, String> series2 = new XYChart.Series<>();
        series2.setName("Data Series 2");
        series2.getData().addAll(
                new XYChart.Data<Number, String>(956, years[0]),
                new XYChart.Data<Number, String>(1665, years[1]),
                new XYChart.Data<Number, String>(2450, years[2]));
        XYChart.Series<Number, String> series3 = new XYChart.Series<>();
        series3.setName("Data Series 3");
        series3.getData().addAll(
                new XYChart.Data<Number, String>(800, years[0]),
                new XYChart.Data<Number, String>(1000, years[1]),
                new XYChart.Data<Number, String>(2800, years[2]));

        chart.getData().add(series1);
        chart.getData().add(series2);
        chart.getData().add(series3);

        primaryStage.setScene(new Scene(chart));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
