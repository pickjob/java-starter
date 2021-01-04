package fx.animation;

import app.common.IShowCase;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @time 2019-06-11
 **/
public class AnimationTimerShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(AnimationTimerShowCase.class);
    private Timeline timeline;
    private AnimationTimer timer;
    private int frameCount = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Circle circle = new Circle(35,  Color.rgb(156,216,255));
        circle.setEffect(new Lighting());
        Text text = new Text(Integer.toString(frameCount));
        text.setStroke(Color.BLACK);
        StackPane stack = new StackPane();
        stack.setLayoutX(35);
        stack.setLayoutY(35);
        stack.getChildren().addAll(circle, text);

        Pane pane = new Pane(stack);
        pane.setPrefSize(300, 100);
        pane.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                text.setText(String.format("%d", frameCount++));
            }
        };
        KeyValue keyValueX = new KeyValue(stack.scaleXProperty(), 2);
        KeyValue keyValueY = new KeyValue(stack.scaleYProperty(), 2);
        Duration duration = Duration.seconds(2);
        EventHandler<ActionEvent> onFinished = (ActionEvent t) -> {
            stack.setTranslateX(Math.random() * 200);
            // reset counter
            frameCount = 0;
        };
        KeyFrame keyFrame = new KeyFrame(duration, onFinished, keyValueX, keyValueY);
        timeline.getKeyFrames().add(keyFrame);

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(pane));
        primaryStage.show();
        timeline.play();
        timer.start();
    }

    @Override public void stop() {
        timeline.stop();
        timer.stop();
    }

    @Override
    public void showSomething() {
        logger.info("AnimationTimer 动画示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
