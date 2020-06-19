package fx.layout;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * @author pickjob@126.com
 * @time 2019-06-11
 **/
public class FlowPaneShowCase extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL, 16, 20);

        for (int i = 0; i < 24; i++) {
            flowPane.getChildren().add(new Label("Label " + i));
        }

        primaryStage.setScene(new Scene(flowPane, 200, 120));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
