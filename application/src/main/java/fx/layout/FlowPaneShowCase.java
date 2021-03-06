package fx.layout;

import app.common.IShowCase;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @time 2019-06-11
 **/
public class FlowPaneShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(FlowPaneShowCase.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL, 16, 20);

        for (int i = 0; i < 24; i++) {
            flowPane.getChildren().add(new Label("Label " + i));
        }

        primaryStage.setScene(new Scene(flowPane, 200, 120));
        primaryStage.show();
    }

    @Override
    public void showSomething() {
        logger.info("Layout FlowPane(流式布局) 示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
