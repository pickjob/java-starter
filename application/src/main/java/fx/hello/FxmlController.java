package fx.hello;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author: pickjob@126.com
 * @time: 2020-03-17
 **/
public class FxmlController {
    private static final Logger logger = LogManager.getLogger(FxmlApplication.class);
    @FXML private TextField nameField;
    @FXML private PasswordField pwdField;
    @FXML private Text actiontarget;

    @FXML
    private void handle(ActionEvent e) {
        logger.info("event type: {}, source: {}, target: {}", e.getEventType(), e.getSource(), e.getTarget());
        logger.info("userName: {}, pwd: {}", nameField.getText(), pwdField.getText());
        actiontarget.setText("Sign in button pressed");
    }
}
