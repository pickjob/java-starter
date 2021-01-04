package fx.charts;

import app.common.IShowCase;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class LineChartShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(LineChartShowCase.class);

    @Override public void start(Stage primaryStage) throws Exception {
        NumberAxis xAxis = new NumberAxis("Values for X-Axis", 0, 3, 1);
        NumberAxis yAxis = new NumberAxis("Values for Y-Axis", 0, 3, 1);
        ObservableList<XYChart.Series<Number, Number>> lineChartData =
                FXCollections.observableList(
                        List.of(new LineChart.Series<Number, Number>("Series 1",
                                        FXCollections.observableArrayList(
                                                List.of(new XYChart.Data<Number, Number>(0.0, 1.0),
                                                        new XYChart.Data<Number, Number>(1.2, 1.4),
                                                        new XYChart.Data<Number, Number>(2.2, 1.9),
                                                        new XYChart.Data<Number, Number>(2.7, 2.3),
                                                        new XYChart.Data<Number, Number>(2.9, 0.5)))),
                                new LineChart.Series<Number, Number>("Series 2",
                                        FXCollections.observableArrayList(
                                                List.of(new XYChart.Data<Number, Number>(0.0, 1.6),
                                                        new XYChart.Data<Number, Number>(0.8, 0.4),
                                                        new XYChart.Data<Number, Number>(1.4, 2.9),
                                                        new XYChart.Data<Number, Number>(2.1, 1.3),
                                                        new XYChart.Data<Number, Number>(2.6, 0.9)))))
                );

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis, lineChartData);

        primaryStage.setScene(new Scene(lineChart));
        primaryStage.show();
    }

    @Override
    public void showSomething() {
        logger.info("JavaFx LineChart 线状图示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
