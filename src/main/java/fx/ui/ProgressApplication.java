package fx.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProgressApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Float[] values = new Float[] {-1.0f, 0f, 0.6f, 1.0f};
        Label[] labels = new Label[values.length];
        ProgressBar[] pbs = new ProgressBar[values.length];
        ProgressIndicator[] pins = new ProgressIndicator[values.length];
        HBox hbs [] = new HBox[values.length];
        Group root = new Group();
        for (int i = 0; i < values.length; i++) {
            Label label = labels[i] = new Label();
            label.setText("progress:" + values[i]);
            ProgressBar pb = pbs[i] = new ProgressBar();
            pb.setProgress(values[i]);
            ProgressIndicator pin = pins[i] = new ProgressIndicator();
            pin.setProgress(values[i]);
            HBox hb = hbs[i] = new HBox();
            hb.setSpacing(5);
            hb.setAlignment(Pos.CENTER);
            hb.getChildren().addAll(label, pb, pin);
        }

        VBox vb = new VBox();
        vb.setSpacing(5);
        vb.getChildren().addAll(hbs);
        Scene scene = new Scene(root, 300, 250);
        scene.setRoot(vb);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Progress Controls");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
