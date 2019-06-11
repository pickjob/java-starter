package fx.animation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author pickjob@126.com
 * @time 2019-06-11
 **/
public class InterpolatorShowCase extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        root.setPrefSize(450, 230);
        root.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        root.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

        circle1 = createMovingCircle(Interpolator.LINEAR, 1, 0.7, Color.RED);
        circle2 = createMovingCircle(Interpolator.EASE_BOTH, 2, 0.45, Color.VIOLET);
        circle3 = createMovingCircle(Interpolator.EASE_IN, 3, 0.2, Color.BLUE);
        circle4 = createMovingCircle(Interpolator.EASE_OUT, 4, 0.35, Color.YELLOW);
        circle5 = createMovingCircle(Interpolator.SPLINE(0.5, 0.1, 0.1, 0.5), 5, 0.7, Color.GREEN);

        root.getChildren().addAll(circle1, circle2, circle3, circle4, circle5);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        timeline.play();
    }

    @Override
    public void stop() {
        timeline.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Circle createMovingCircle(Interpolator interpolator, int which, double opacity, Color color) {
        Circle circle = new Circle(45, 45, 40, color);
        circle.setOpacity(opacity);
        circle.setCenterY((which * 35) + 5);
        circle.setEffect(new Lighting());

        KeyValue keyValue = new KeyValue(circle.translateXProperty(), 400, interpolator);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(4), keyValue);

        timeline.getKeyFrames().add(keyFrame);
        return circle;
    }

    private Circle circle1;
    private Circle circle2;
    private Circle circle3;
    private Circle circle4;
    private Circle circle5;
    private Timeline timeline;
}
