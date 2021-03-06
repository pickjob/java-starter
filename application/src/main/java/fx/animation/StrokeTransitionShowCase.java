package fx.animation;

import app.common.IShowCase;
import javafx.animation.StrokeTransition;
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
public class StrokeTransitionShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger();
    private StrokeTransition stroke;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        root.setPrefSize(200, 200);
        root.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        root.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        Rectangle rect = new Rectangle(0, 0, 150, 150);
        rect.setArcHeight(20);
        rect.setArcWidth(20);
        rect.setFill(null);
        rect.setStroke(Color.DODGERBLUE);
        rect.setStrokeWidth(10);
        root.getChildren().add(rect);

        stroke = new StrokeTransition(Duration.seconds(3), rect, Color.RED, Color.DODGERBLUE);
        stroke.setCycleCount(Timeline.INDEFINITE);
        stroke.setAutoReverse(true);

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        stroke.play();
    }

    @Override
    public void stop() {
        stroke.stop();
    }

    @Override
    public void showSomething() {
        logger.info("StrokeTransition 动画示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
