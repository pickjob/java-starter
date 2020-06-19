package fx.animation;

import javafx.animation.FadeTransition;
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
public class FadeTransitionShowCase extends Application {

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

        fade = new FadeTransition(Duration.seconds(4), rect);
        fade.setFromValue(1);
        fade.setToValue(0.2);
        fade.setCycleCount(Timeline.INDEFINITE);
        fade.setAutoReverse(true);

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
        fade.play();
    }

    @Override
    public void stop() {
        fade.stop();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private FadeTransition fade;
}
