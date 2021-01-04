package fx.layout;

import app.common.IShowCase;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @time 2019-06-11
 **/
public class AnchorPaneShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(AnchorPaneShowCase.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        Label label1 = new Label("TopLeft");
        Label label2 = new Label("TopRight");
        Label label3 = new Label("ButtonLeft");
        Label label4 = new Label("ButtonRight");
        anchorPane.getChildren().addAll(label1, label2, label3, label4);

        AnchorPane.setTopAnchor(label1, Double.valueOf(20));
        AnchorPane.setLeftAnchor(label1, Double.valueOf(20));
        AnchorPane.setTopAnchor(label2, Double.valueOf(20));
        AnchorPane.setRightAnchor(label2, Double.valueOf(20));
        AnchorPane.setBottomAnchor(label3, Double.valueOf(20));
        AnchorPane.setLeftAnchor(label3, Double.valueOf(20));
        AnchorPane.setBottomAnchor(label4, Double.valueOf(20));
        AnchorPane.setRightAnchor(label4, Double.valueOf(20));

        primaryStage.setScene(new Scene(anchorPane, 400, 400));
        primaryStage.show();
    }

    @Override
    public void showSomething() {
        logger.info("Layout AnchorPane(锚布局)");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
