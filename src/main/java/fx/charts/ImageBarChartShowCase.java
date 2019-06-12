package fx.charts;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class ImageBarChartShowCase extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String imageBarChartCss = getClass().getResource("ImageBarChart.css").toExternalForm();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart chart = new BarChart(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.getStylesheets().add(imageBarChartCss);

        chart.getData().add(
                new XYChart.Series<>("Sales Per Product",
                        FXCollections.observableArrayList(
                                new XYChart.Data<>("SUV", 120),
                                new XYChart.Data<>("Sedan", 50),
                                new XYChart.Data<>("Truck", 180),
                                new XYChart.Data<>("Van", 20)
                        )
                )
        );
        primaryStage.setScene(new Scene(chart));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
