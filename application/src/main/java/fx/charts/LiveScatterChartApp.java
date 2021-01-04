package fx.charts;

import app.common.IShowCase;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart.Data;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class LiveScatterChartApp extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(LiveScatterChartApp.class);

    private ScatterChart.Series<Number,Number> series;
    private double nextX = 0;
    private SequentialTransition animation;

    public LiveScatterChartApp() {
        final KeyFrame initialFrames =
            new KeyFrame(Duration.millis(60),
                         (ActionEvent actionEvent) -> {
                             final Number nextY =
                                Math.sin(Math.toRadians(nextX)) * 100;
                             final Data<Number,Number> data =
                                new Data<Number, Number>(nextX, nextY);
                             series.getData().add(data);
                             nextX += 10;
                         });
        Timeline initial = new Timeline(initialFrames);
        initial.setCycleCount(200);
        final KeyFrame followingFrames =
            new KeyFrame(Duration.millis(60),
                         (ActionEvent actionEvent) -> {
                             final Number nextY =
                                 Math.sin(Math.toRadians(nextX)) * 100;
                             final Data<Number,Number> data =
                             new Data<Number, Number>(nextX, nextY);
                             series.getData().add(data);
                             if (series.getData().size() > 54) {
                                 series.getData().remove(0);
                             }
                             nextX += 10;
                         });
        Timeline following = new Timeline(followingFrames);
        following.setCycleCount(Animation.INDEFINITE);
        animation = new SequentialTransition(initial, following);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis(-100, 100, 10);
        xAxis.setForceZeroInRange(false);
        xAxis.setLabel("X Axis");
        xAxis.setAnimated(false);
        yAxis.setLabel("Y Axis");
        yAxis.setAutoRanging(false);
        // add starting data
        series = new ScatterChart.Series<>();
        series.setName("Sine Wave");
        series.getData().add(new Data<Number, Number>(5d, 5d));
        ScatterChart<Number, Number> sc = new ScatterChart<>(xAxis, yAxis);
        sc.getData().add(series);
        sc.setTitle("Animated Sine Wave ScatterChart");

        primaryStage.setScene(new Scene(sc));
        primaryStage.show();
        animation.play();
    }

    @Override
    public void showSomething() {
        logger.info("示例散点动画示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
