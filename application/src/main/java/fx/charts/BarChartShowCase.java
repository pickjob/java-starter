package fx.charts;

import app.common.IShowCase;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class BarChartShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(BarChartShowCase.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        String[] years = {"2007", "2008", "2009"};
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.observableArrayList(years));
        NumberAxis yAxis = new NumberAxis("Units Sold", 0.0d, 3000.0d, 1000.0d);
        ObservableList<BarChart.Series<String, Number>> barChartData =
                FXCollections.observableList(
                        List.of(new BarChart.Series<String, Number>("Apples",
                                        FXCollections.observableArrayList(
                                                List.of(new BarChart.Data<String, Number>(years[0], 567d),
                                                        new BarChart.Data<String, Number>(years[1], 1292d),
                                                        new BarChart.Data<String, Number>(years[2], 1292d)))),
                                new BarChart.Series<String, Number>("Lemons",
                                        FXCollections.observableArrayList(
                                                List.of(new BarChart.Data<String, Number>(years[0], 956),
                                                        new BarChart.Data<String, Number>(years[1], 1665),
                                                        new BarChart.Data<String, Number>(years[2], 2559)))),
                                new BarChart.Series<String, Number>("Oranges",
                                        FXCollections.observableArrayList(
                                                List.of(new BarChart.Data<String, Number>(years[0], 1154),
                                                        new BarChart.Data<String, Number>(years[1], 1927),
                                                        new BarChart.Data<String, Number>(years[2], 2774)))))
                );

        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis, barChartData, 25.0d);

        primaryStage.setScene(new Scene(chart));
        primaryStage.show();
    }

    @Override
    public void showSomething() {
        logger.info("JavaFx BarChart 柱状图示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
