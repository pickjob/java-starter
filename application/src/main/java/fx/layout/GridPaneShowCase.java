package fx.layout;

import app.common.IShowCase;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @time 2019-06-11
 **/
public class GridPaneShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gridPane = new GridPane();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Label label = new Label(i + ":" + j);
                GridPane.setConstraints(label, i, j);
                gridPane.getChildren().add(label);
            }
        }

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(8, 8, 8, 8));

        primaryStage.setScene(new Scene(gridPane));
        primaryStage.show();
    }

    @Override
    public void showSomething() {
        logger.info("Layout GridPane(表格布局) 示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
