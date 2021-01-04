package fx.animation;

import app.common.IShowCase;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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
public class SequentialTransitionShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger();
    private SequentialTransition sequence;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        root.setPrefSize(245, 100);
        root.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        root.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        Rectangle rect = new Rectangle(-25,-25,50, 50);
        rect.setArcHeight(15);
        rect.setArcWidth(15);
        rect.setFill(Color.CRIMSON);
        rect.setTranslateX(50);
        rect.setTranslateY(50);
        root.getChildren().add(rect);

        FadeTransition fade = new FadeTransition(Duration.seconds(1));
        fade.setFromValue(1);
        fade.setToValue(0.3);
        fade.setCycleCount(2);
        fade.setAutoReverse(true);

        TranslateTransition translate = new TranslateTransition(Duration.seconds(2));
        translate.setFromX(50);
        translate.setToX(220);
        translate.setCycleCount(2);
        translate.setAutoReverse(true);

        RotateTransition rotate = new RotateTransition(Duration.seconds(2));
        rotate.setByAngle(180);
        rotate.setCycleCount(4);
        rotate.setAutoReverse(true);

        ScaleTransition scale = new ScaleTransition(Duration.seconds(2));
        scale.setToX(2);
        scale.setToY(2);
        scale.setCycleCount(2);
        scale.setAutoReverse(true);

        sequence = new SequentialTransition(rect, fade, translate, rotate, scale);
        sequence.setCycleCount(Timeline.INDEFINITE);
        sequence.setAutoReverse(true);

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        sequence.play();
    }

    @Override
    public void stop() {
        sequence.stop();
    }



    @Override
    public void showSomething() {
        logger.info("SequentialTransition 动画示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
