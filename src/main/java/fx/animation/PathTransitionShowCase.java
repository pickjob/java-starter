package fx.animation;

import javafx.animation.PathTransition;
import static javafx.animation.PathTransition.OrientationType;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author pickjob@126.com
 * @time 2019-06-11
 **/
public class PathTransitionShowCase extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        root.setPrefSize(280, 190);
        root.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
        root.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

        Rectangle rect = new Rectangle(0, 0, 40, 40);
        rect.setArcHeight(10);
        rect.setArcWidth(10);
        rect.setFill(Color.ORANGE);
        root.getChildren().add(rect);
        Path path = new Path(new MoveTo(20, 20),
                new CubicCurveTo(380, 0, 220, 120, 120, 80),
                new CubicCurveTo(0, 40, 0, 240, 220, 120));
        path.setStroke(Color.DODGERBLUE);
        path.getStrokeDashArray().setAll(5d, 5d);
        root.getChildren().add(path);

        transition = new PathTransition(Duration.seconds(4), path, rect);
        transition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
        transition.setCycleCount(Timeline.INDEFINITE);
        transition.setAutoReverse(true);

        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
        transition.play();
    }

    @Override
    public void stop() {
        transition.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private PathTransition transition;
}
