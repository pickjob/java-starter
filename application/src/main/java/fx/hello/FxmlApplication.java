package fx.hello;

import app.common.IShowCase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FxmlApplication extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(FxmlApplication.class);

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml.fxml"));
        stage.setTitle("FXML Welcome");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }

    @Override
    public void showSomething() {
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
