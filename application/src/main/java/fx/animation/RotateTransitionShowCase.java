package fx.animation;

import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author pickjob@126.com
 * @time 2019-06-11
 **/
public class RotateTransitionShowCase extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        root.setPrefSize(140, 140);
        root.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        root.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        Rectangle rect = new Rectangle(20, 20, 100, 100);
        rect.setArcHeight(20);
        rect.setArcWidth(20);
        rect.setFill(Color.ORANGE);
        root.getChildren().add(rect);

        rotate = new RotateTransition(Duration.seconds(4), rect);
        rotate.setFromAngle(0);
        rotate.setToAngle(720);
        rotate.setCycleCount(Timeline.INDEFINITE);
        rotate.setAutoReverse(true);

        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
        rotate.play();
    }

    @Override
    public void stop() {
        rotate.stop();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private RotateTransition rotate;
}
