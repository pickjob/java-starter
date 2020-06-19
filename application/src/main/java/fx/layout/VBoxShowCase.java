package fx.layout;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author pickjob@126.com
 * @time 2019-06-11
 **/
public class VBoxShowCase extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox();

        Label label1 = new Label("label1");
        Label label2 = new Label("label2");
        Label label3 = new Label("label3");

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label1, label2, label3);

        primaryStage.setScene(new Scene(vBox, 120, 100));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
