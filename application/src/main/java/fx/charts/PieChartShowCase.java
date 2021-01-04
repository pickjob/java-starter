package fx.charts;

import app.common.IShowCase;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @time 2019-06-12
 **/
public class PieChartShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(PieChartShowCase.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                    new PieChart.Data("Sun", 20),
                    new PieChart.Data("IBM", 12),
                    new PieChart.Data("HP", 25),
                    new PieChart.Data("Dell", 22),
                    new PieChart.Data("Apple", 30));

        PieChart pieChart = new PieChart(data);
        pieChart.setClockwise(false);
        pieChart.getData()
                .stream()
                .forEach(d -> {
            Tooltip tooltip = new Tooltip();
            tooltip.setText(d.getPieValue() + "%");
            Tooltip.install(d.getNode(), tooltip);
            d.pieValueProperty().addListener((observable, oldValue, newValue) ->
                    tooltip.setText(newValue + "%"));
        });

        primaryStage.setScene(new Scene(pieChart));
        primaryStage.show();
    }

    @Override
    public void showSomething() {
        logger.info("JavaFx PieChart 饼状图示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
