package fx.charts;

import app.common.IShowCase;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class DrillDownPieChartShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(DrillDownPieChartShowCase.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        Data A, B, C, D;
        ObservableList<Data> data = FXCollections.observableArrayList(A = new Data("A", 20),
                B = new Data("B", 30),
                C = new Data("C", 10),
                D = new Data("D", 40));

        PieChart pieChart = new PieChart(data);

        setDrillDownData(pieChart, A, "a");
        setDrillDownData(pieChart, B, "b");
        setDrillDownData(pieChart, C, "c");
        setDrillDownData(pieChart, D, "d");

        primaryStage.setScene(new Scene(pieChart));
        primaryStage.show();
    }

    private void setDrillDownData(final PieChart pie, final Data data, final String labelPrefix) {
        data.getNode().setOnMouseClicked((MouseEvent t) -> {
            pie.setData(FXCollections.observableArrayList(
                    new Data(labelPrefix + "-1", 7),
                    new Data(labelPrefix + "-2", 2),
                    new Data(labelPrefix + "-3", 5),
                    new Data(labelPrefix + "-4", 3),
                    new Data(labelPrefix + "-5", 2)));
        });
    }

    @Override
    public void showSomething() {
        logger.info("JavaFx PieChart 下层示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
