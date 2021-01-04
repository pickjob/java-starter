package fx.charts;

import app.common.IShowCase;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class StackedBarChartShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(StackedBarChartShowCase.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        String[] years = {"2007", "2008", "2009"};
        CategoryAxis xAxis = new CategoryAxis(FXCollections.observableArrayList(years));
        NumberAxis yAxis = new NumberAxis("Units Sold", 0.0d, 10000.0d, 1000.0d);
        final ObservableList<StackedBarChart.Series<String, Number>> barChartData =
                FXCollections.observableArrayList(
                        List.of(
                                new StackedBarChart.Series<String, Number>("Region 1",
                                FXCollections.observableArrayList(
                                        List.of(
                                                new StackedBarChart.Data<String, Number>(years[0], 567d),
                                                new StackedBarChart.Data<String, Number>(years[1], 1292d),
                                                new StackedBarChart.Data<String, Number>(years[2], 1292d))
                                )
                        ),
                        new StackedBarChart.Series<String, Number>("Region 2",
                                FXCollections.observableArrayList(
                                        List.of(
                                        new StackedBarChart.Data<String, Number>(years[0], 956),
                                        new StackedBarChart.Data<String, Number>(years[1], 1665),
                                        new StackedBarChart.Data<String, Number>(years[2], 2559))
                                )
                        ),
                        new StackedBarChart.Series<String, Number>("Region 3",
                                FXCollections.observableArrayList(
                                        List.of(
                                        new StackedBarChart.Data<String, Number>(years[0], 1154),
                                        new StackedBarChart.Data<String, Number>(years[1], 1927),
                                        new StackedBarChart.Data<String, Number>(years[2], 2774))
                                )
                        ))
                );
        StackedBarChart<String, Number> chart = new StackedBarChart<>(xAxis, yAxis, barChartData, 25.0d);

        primaryStage.setScene(new Scene(chart));
        primaryStage.show();
    }

    @Override
    public void showSomething() {
        logger.info("JavaFx StackedBarChart 多块柱状图示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
