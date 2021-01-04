package fx.charts;

import app.common.IShowCase;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class HorizontalBarChartShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(HorizontalBarChartShowCase.class);

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
                List.of(new XYChart.Data<Number, String>(567, years[0]),
                        new XYChart.Data<Number, String>(1292, years[1]),
                        new XYChart.Data<Number, String>(2180, years[2])));
        XYChart.Series<Number, String> series2 = new XYChart.Series<>();
        series2.setName("Data Series 2");
        series2.getData().addAll(
                List.of(new XYChart.Data<Number, String>(956, years[0]),
                        new XYChart.Data<Number, String>(1665, years[1]),
                        new XYChart.Data<Number, String>(2450, years[2])));
        XYChart.Series<Number, String> series3 = new XYChart.Series<>();
        series3.setName("Data Series 3");
        series3.getData().addAll(
                List.of(new XYChart.Data<Number, String>(800, years[0]),
                        new XYChart.Data<Number, String>(1000, years[1]),
                        new XYChart.Data<Number, String>(2800, years[2])));

        chart.getData().addAll(List.of(series1, series2, series3));

        primaryStage.setScene(new Scene(chart));
        primaryStage.show();
    }

    @Override
    public void showSomething() {
        logger.info("JavaFx 水平柱状图示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
