package fx.animation;

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

/**
 * @author pickjob@126.com
 * @time 2019-06-11
 **/
public class TimelineEventsShowCase extends Application {

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

        // create a timeline for moving the circle
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        // Add a specific action when each frame is started.
        // There are one or more frames during executing one
        // KeyFrame depending on the set Interpolator.
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                text.setText(String.format("%d", frameCount++));
            }
        };
        // create a keyValue with factory: scaling the circle 2times
        KeyValue keyValueX = new KeyValue(stack.scaleXProperty(), 2);
        KeyValue keyValueY = new KeyValue(stack.scaleYProperty(), 2);
        // create a keyFrame, the keyValue is reached at time 2s
        Duration duration = Duration.seconds(2);
        // add a specific action when the keyframe is reached
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

    public static void main(String[] args) {
        Application.launch(args);
    }

    // main timeline
    private Timeline timeline;
    private AnimationTimer timer;
    // variable for storing animation relative frame count
    private int frameCount = 0;
}
