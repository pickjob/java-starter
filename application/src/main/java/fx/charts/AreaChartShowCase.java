package fx.charts;


import app.common.IShowCase;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class AreaChartShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(AreaChartShowCase.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("X Values");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Y Values");
        ObservableList<AreaChart.Series<Number, Number>> areaChartData =
                FXCollections.observableList(
                       List.of(
                               new AreaChart.Series<Number, Number>("Series 1",
                                       FXCollections.observableArrayList(
                                               List.of(new AreaChart.Data<Number, Number>(0,4),
                                                       new AreaChart.Data<Number, Number>(2,5),
                                                       new AreaChart.Data<Number, Number>(4,4),
                                                       new AreaChart.Data<Number, Number>(6,2),
                                                       new AreaChart.Data<Number, Number>(8,6),
                                                       new AreaChart.Data<Number, Number>(10,8))
                                       )),
                               new AreaChart.Series<Number, Number>("Series 2",
                                       FXCollections.observableArrayList(
                                               List.of(new AreaChart.Data<Number, Number>(0,8),
                                                       new AreaChart.Data<Number, Number>(2,2),
                                                       new AreaChart.Data<Number, Number>(4,9),
                                                       new AreaChart.Data<Number, Number>(6,7),
                                                       new AreaChart.Data<Number, Number>(8,5),
                                                       new AreaChart.Data<Number, Number>(10,7))
                                       )),
                               new AreaChart.Series<Number, Number>("Series 3",
                                       FXCollections.observableArrayList(
                                               List.of(new AreaChart.Data<Number, Number>(0,2),
                                                       new AreaChart.Data<Number, Number>(2,5),
                                                       new AreaChart.Data<Number, Number>(4,8),
                                                       new AreaChart.Data<Number, Number>(6,6),
                                                       new AreaChart.Data<Number, Number>(8,9),
                                                       new AreaChart.Data<Number, Number>(10,7))
                                       )))
                );

        AreaChart<Number, Number> chart = new AreaChart<>(xAxis, yAxis, areaChartData);

        primaryStage.setScene(new Scene(chart));
        primaryStage.show();
    }

    @Override
    public void showSomething() {
        logger.info("JavaFx AreaChart 区域线状图示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
