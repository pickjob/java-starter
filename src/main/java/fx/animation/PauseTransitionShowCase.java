package fx.animation;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
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

/**
 * @author pickjob@126.com
 * @time 2019-06-11
 **/
public class PauseTransitionShowCase extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        root.setPrefSize(245, 100);
        root.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        root.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        // create rectangle
        Rectangle rect = new Rectangle(-25, -25, 50, 50);
        rect.setArcHeight(15);
        rect.setArcWidth(15);
        rect.setFill(Color.CRIMSON);
        rect.setTranslateX(50);
        rect.setTranslateY(75);
        root.getChildren().add(rect);

        Duration time = Duration.seconds(2);

        TranslateTransition translate1 = new TranslateTransition(time);
        translate1.setFromX(50);
        translate1.setToX(150);

        PauseTransition pause = new PauseTransition(time);

        TranslateTransition translate2 = new TranslateTransition(time);
        translate2.setFromX(150);
        translate2.setToX(200);

        animation = new SequentialTransition(rect, translate1, pause, translate2);
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.setAutoReverse(true);

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        animation.play();
    }

    @Override
    public void stop() {
        animation.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Animation animation;
}
