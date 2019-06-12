package fx.charts;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class DrilldownPieChartShowCase extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Data A, B, C, D;
        ObservableList<Data> data = FXCollections.observableArrayList(A = new Data("A", 20),
                B = new Data("B", 30),
                C = new Data("C", 10),
                D = new Data("D", 40));
        PieChart pie = new PieChart(data);
        String drillDownChartCss = getClass().getResource("DrilldownChart.css").toExternalForm();
        pie.getStylesheets().add(drillDownChartCss);

        setDrilldownData(pie, A, "a");
        setDrilldownData(pie, B, "b");
        setDrilldownData(pie, C, "c");
        setDrilldownData(pie, D, "d");

        primaryStage.setScene(new Scene(pie));
        primaryStage.show();
    }

    private void setDrilldownData(final PieChart pie, final Data data, final String labelPrefix) {
        data.getNode().setOnMouseClicked((MouseEvent t) -> {
            pie.setData(FXCollections.observableArrayList(
                    new Data(labelPrefix + "-1", 7),
                    new Data(labelPrefix + "-2", 2),
                    new Data(labelPrefix + "-3", 5),
                    new Data(labelPrefix + "-4", 3),
                    new Data(labelPrefix + "-5", 2)));
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
