package fx.hello;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CodingApplication extends Application {
    private static final Logger logger = LogManager.getLogger(CodingApplication.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text welcomeText = new Text("Welcome");
        welcomeText.setId("welcome-text");
        grid.add(welcomeText, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField nameField = new TextField();
        grid.add(nameField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwdField = new PasswordField();
        grid.add(pwdField, 1, 2);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 3);

        Text actiontarget = new Text();
        actiontarget.setId("actiontarget");
        grid.setHalignment(actiontarget, HPos.RIGHT);
        grid.add(actiontarget, 0, 6, 2, 1);

        btn.setOnAction(e -> {
            logger.info("event type: {}, source: {}, target: {}", e.getEventType(), e.getSource(), e.getTarget());
            logger.info("userName: {}, pwd: {}", nameField.getText(), pwdField.getText());
            actiontarget.setText("Sign in button pressed");
        });

        Scene scene = new Scene(grid, 300, 275);
        scene.getStylesheets()
             .add(getClass().getResource("Login.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFx Welcome");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
