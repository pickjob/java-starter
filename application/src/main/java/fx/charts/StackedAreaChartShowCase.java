package fx.charts;

import app.common.IShowCase;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class StackedAreaChartShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(StackedAreaChartShowCase.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        NumberAxis xAxis = new NumberAxis("X Values", 1.0d, 9.0d, 2.0d);
        NumberAxis yAxis = new NumberAxis("Y Values", 0.0d, 30.0d, 2.0d);

        ObservableList<StackedAreaChart.Series<Number, Number>> areaChartData =
                FXCollections.observableArrayList(
                        List.of(
                        new StackedAreaChart.Series<Number, Number>("Series 1",
                                FXCollections.observableArrayList(
                                        List.of(
                                        new StackedAreaChart.Data<Number, Number>(0,4),
                                        new StackedAreaChart.Data<Number, Number>(2,5),
                                        new StackedAreaChart.Data<Number, Number>(4,4),
                                        new StackedAreaChart.Data<Number, Number>(6,2),
                                        new StackedAreaChart.Data<Number, Number>(8,6),
                                        new StackedAreaChart.Data<Number, Number>(10,8))
                                )),
                        new StackedAreaChart.Series<Number, Number>("Series 2",
                                FXCollections.observableArrayList(
                                        List.of(
                                        new StackedAreaChart.Data<Number, Number>(0,8),
                                        new StackedAreaChart.Data<Number, Number>(2,2),
                                        new StackedAreaChart.Data<Number, Number>(4,9),
                                        new StackedAreaChart.Data<Number, Number>(6,7),
                                        new StackedAreaChart.Data<Number, Number>(8,5),
                                        new StackedAreaChart.Data<Number, Number>(10,7))
                                )),
                        new StackedAreaChart.Series<Number, Number>("Series 3",
                                FXCollections.observableArrayList(
                                        List.of(
                                        new StackedAreaChart.Data<Number, Number>(0,2),
                                        new StackedAreaChart.Data<Number, Number>(2,5),
                                        new StackedAreaChart.Data<Number, Number>(4,8),
                                        new StackedAreaChart.Data<Number, Number>(6,6),
                                        new StackedAreaChart.Data<Number, Number>(8,9),
                                        new StackedAreaChart.Data<Number, Number>(10,7))
                                )))
                );
        StackedAreaChart<Number, Number> chart = new StackedAreaChart(xAxis, yAxis, areaChartData);

        primaryStage.setScene(new Scene(chart));
        primaryStage.show();
    }

    @Override
    public void showSomething() {
        logger.info("JavaFx StackedAreaChart 示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
