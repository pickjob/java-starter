package fx.animation;

import app.common.IShowCase;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @time 2019-06-11
 **/
public class TranslateTransitionShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(TranslateTransitionShowCase.class);
    private TranslateTransition translate;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        root.setPrefSize(245, 80);
        root.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        root.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        Circle circle = new Circle(35, Color.CRIMSON);
        circle.setTranslateX(35);
        circle.setTranslateY(35);
        root.getChildren().add(circle);
        translate = new TranslateTransition(Duration.seconds(4), circle);
        translate.setFromX(35);
        translate.setToX(220);
        translate.setCycleCount(Timeline.INDEFINITE);
        translate.setAutoReverse(true);

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        translate.play();
    }

    @Override
    public void stop() {
        translate.stop();
    }

    @Override
    public void showSomething() {
        logger.info("TranslateTransition 动画示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
