package fx.animation;

import app.common.IShowCase;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A sample that demonstrates the basics of timeline creation.
 */
public class TimelineShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(TimelineShowCase.class);
    private Timeline timeline;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();

        final Circle circle = new Circle(25, 25, 20, Color.web("1c89f4"));
        circle.setEffect(new Lighting());

        Button buttonStart = new Button("Start");
        buttonStart.setOnAction((ActionEvent t) -> {
            // start timeline
            timeline.play();
        });
        Button buttonStop = new Button("Stop");
        buttonStop.setOnAction((ActionEvent t) -> {
            // stop timeline
            timeline.stop();
        });
        Button buttonPlayFromStart = new Button("Restart");
        buttonPlayFromStart.setOnAction((ActionEvent t) -> {
            // play from start
            timeline.playFromStart();
        });
        Button buttonPause = new Button("Pause");
        buttonPause.setOnAction((ActionEvent t) -> {
            // pause from start
            timeline.pause();
        });
        // text showing current time
        final TextFlow flow = new TextFlow();
        final Text current = new Text("Current time: ");
        final Text rate = new Text();
        final Text ms = new Text(" ms");
        current.setBoundsType(TextBoundsType.VISUAL);
        ms.setBoundsType(TextBoundsType.VISUAL);
        rate.setFont(Font.font("Courier", FontWeight.BOLD, 14));
        rate.setText(String.format("%4d", 0));
        timeline.currentTimeProperty().addListener((Observable ov) -> {
            rate.setText(String.format("%4.0f",
                    timeline.getCurrentTime().toMillis()));
            flow.requestLayout();
        });
        flow.getChildren().addAll(current, rate, ms);
        // Autoreverse checkbox
        final CheckBox checkBoxAutoReverse = new CheckBox("Auto Reverse");
        checkBoxAutoReverse.setSelected(true);
        checkBoxAutoReverse.selectedProperty().addListener((Observable ov) -> {
            timeline.setAutoReverse(checkBoxAutoReverse.isSelected());
        });
        // add all navigation to layout
        HBox hBox1 = new HBox(10);
        hBox1.setPadding(new Insets(5, 10, 0, 5));
        hBox1.getChildren().addAll(buttonStart, buttonPause,
                buttonStop, buttonPlayFromStart);
        hBox1.setAlignment(Pos.CENTER_LEFT);
        VBox controls = new VBox(10);
        controls.setPadding(new Insets(0, 0, 0, 5));
        controls.getChildren().addAll(checkBoxAutoReverse, flow);
        controls.setAlignment(Pos.CENTER_LEFT);
        VBox vBox = new VBox(20);
        vBox.setLayoutY(60);
        vBox.getChildren().addAll(hBox1, controls);

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

        timeline.getKeyFrames()
                .addAll(
                        new KeyFrame(Duration.ZERO, new KeyValue(circle.translateXProperty(), 0)),
                        new KeyFrame(new Duration(4000), new KeyValue(circle.translateXProperty(), 205)));
        root.getChildren().addAll(vBox, circle);

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void showSomething() {
        logger.info("Timeline 动画示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
