package fx.animation;

import app.common.IShowCase;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
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
public class FadeTransitionShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(FadeTransitionShowCase.class);
    private FadeTransition fade;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle rect = new Rectangle(0, 0, 200, 100);
        rect.setArcHeight(20);
        rect.setArcWidth(20);
        rect.setFill(Color.DODGERBLUE);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(rect);

        fade = new FadeTransition(Duration.seconds(10), rect);
        fade.setFromValue(0.2);
        fade.setToValue(1);
        fade.setCycleCount(Timeline.INDEFINITE);
        fade.setAutoReverse(true);


        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        fade.play();
    }

    @Override
    public void stop() {
        fade.stop();
    }

    @Override
    public void showSomething() {
        logger.info("FadeTransition 动画示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
