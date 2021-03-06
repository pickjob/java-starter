package fx.layout;

import app.common.IShowCase;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @time 2019-06-11
 **/
public class BorderPaneShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(BorderPaneShowCase.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();

        Label label1 = new Label("TOP");
        Label label2 = new Label("LEFT");
        Label label3 = new Label("RIGHT");
        Label label4 = new Label("BOTTOM");
        Label label5 = new Label("CENTER");

        borderPane.setTop(label1);
        borderPane.setLeft(label2);
        borderPane.setRight(label3);
        borderPane.setBottom(label4);
        borderPane.setCenter(label5);

        primaryStage.setScene(new Scene(borderPane, 120, 100));
        primaryStage.show();
    }

    @Override
    public void showSomething() {
        logger.info("Layout BorderPane(上、下、左、右、居中布局) 示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
