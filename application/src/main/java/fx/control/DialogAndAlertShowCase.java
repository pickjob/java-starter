package fx.control;

import app.common.IShowCase;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author: pickjob@126.com
 * @date: 2020-08-13
 **/
public class DialogAndAlertShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(DialogAndAlertShowCase.class);

    @Override
    public void start(Stage stage) throws Exception {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setContentText("Would you like to log in?");
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(loginButtonType);
        dialog.showAndWait()
                .ifPresent(response -> {
                    logger.info(response);
                });

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("Look, an Exception Dialog");
        alert.setContentText("Could not find file blabla.txt!");
        Exception ex = new FileNotFoundException("Could not find file blabla.txt");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();
        Label label = new Label("The exception stacktrace was:");
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    @Override
    public void showSomething() {
        logger.info("JavaFx Dialog & Alert 示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
