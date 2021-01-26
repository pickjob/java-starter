package fx.charts;

import app.common.IShowCase;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
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
public class BubbleChartShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(BubbleChartShowCase.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        BubbleChart<Number, Number> bubbleChart = new BubbleChart<>(xAxis, yAxis);
        bubbleChart.setTitle("Advanced BubbleChart");
        xAxis.setLabel("X Axis");
        yAxis.setLabel("Y Axis");
        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("Data Series 1");
        for (int i = 0; i < 20; i++) {
            series1.getData().add(
                    new XYChart.Data<Number, Number>(Math.random() * 100,
                            Math.random() * 100,
                            Math.random() * 10));
        }
        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.setName("Data Series 2");
        for (int i = 0; i < 20; i++) {
            series2.getData().add(
                    new XYChart.Data<Number, Number>(Math.random() * 100,
                            Math.random() * 100,
                            Math.random() * 10));
        }
        bubbleChart.getData().addAll(List.of(series1, series2));

        primaryStage.setScene(new Scene(bubbleChart));
        primaryStage.show();
    }

    @Override
    public void showSomething() {
        logger.info("JavaFx BubbleChart 气泡图");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
