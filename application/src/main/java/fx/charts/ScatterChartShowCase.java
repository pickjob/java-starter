package fx.charts;

import app.common.IShowCase;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class ScatterChartShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(ScatterChartShowCase.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        NumberAxis xAxis = new NumberAxis("X-Axis", 0d, 8.0d, 1.0d);
        NumberAxis yAxis = new NumberAxis("Y-Axis", 0.0d, 5.0d, 1.0d);
        Series<Number, Number> series = new Series<>();
        series.setName("Series 1");
        series.getData().addAll(
                new Data<Number, Number>(0.2, 3.5),
                new Data<Number, Number>(0.7, 4.6),
                new Data<Number, Number>(1.8, 1.7),
                new Data<Number, Number>(2.1, 2.8),
                new Data<Number, Number>(4.0, 2.2),
                new Data<Number, Number>(4.1, 2.6),
                new Data<Number, Number>(4.5, 2.0),
                new Data<Number, Number>(6.0, 3.0),
                new Data<Number, Number>(7.0, 2.0),
                new Data<Number, Number>(7.8, 4.0));
        ScatterChart<Number, Number> chart = new ScatterChart<>(xAxis, yAxis);
        chart.getData().add(series);
        primaryStage.setScene(new Scene(chart));
        primaryStage.show();
    }

    @Override
    public void showSomething() {
        logger.info("JavaFx ScatterChart 散点示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
