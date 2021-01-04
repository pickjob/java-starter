package fx.animation;

import app.common.IShowCase;
import javafx.animation.FillTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @time 2019-06-11
 **/
public class FillTransitionShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(FillTransitionShowCase.class);
    private FillTransition fill;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        root.setPrefSize(105, 105);
        root.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        root.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        Rectangle rect = new Rectangle(0, 0, 400, 300);
        rect.setArcHeight(20);
        rect.setArcWidth(20);
        rect.setFill(Color.DODGERBLUE);
        root.getChildren().add(rect);

        fill = new FillTransition(Duration.seconds(5), rect, Color.RED, Color.DODGERBLUE);
        fill.setCycleCount(Timeline.INDEFINITE);
        fill.setAutoReverse(true);

        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setResizable(false);
        primaryStage.show();

        fill.play();
    }

    @Override
    public void stop() {
        fill.stop();
    }

    @Override
    public void showSomething() {
        logger.info("FillTransition 动画示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
