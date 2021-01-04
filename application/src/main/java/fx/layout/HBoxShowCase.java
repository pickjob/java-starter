package fx.layout;

import app.common.IShowCase;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @time 2019-06-11
 **/
public class HBoxShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(HBoxShowCase.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox hBox = new HBox();

        Label label1 = new Label("label1");
        Label label2 = new Label("label2");
        Label label3 = new Label("label3");

        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(label1, label2, label3);

        primaryStage.setScene(new Scene(hBox, 200, 120));
        primaryStage.show();
    }

    @Override
    public void showSomething() {
        logger.info("Layout HBox(水平布局) 示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
