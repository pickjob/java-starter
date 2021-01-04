package fx.animation;

import app.common.IShowCase;
import javafx.animation.ScaleTransition;
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
public class ScaleTransitionShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(ScaleTransitionShowCase.class);
    private ScaleTransition scale;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        root.setPrefSize(180, 180);
        root.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        root.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        Rectangle rect = new Rectangle(50, 50, 50, 50);
        rect.setArcHeight(15);
        rect.setArcWidth(15);
        rect.setFill(Color.ORANGE);
        root.getChildren().add(rect);
        scale = new ScaleTransition(Duration.seconds(4), rect);
        scale.setToX(3);
        scale.setToY(3);
        scale.setCycleCount(Timeline.INDEFINITE);
        scale.setAutoReverse(true);

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        scale.play();
    }

    @Override
    public void stop() {
        scale.stop();
    }

    @Override
    public void showSomething() {
        logger.info("ScaleTransition 动画示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
