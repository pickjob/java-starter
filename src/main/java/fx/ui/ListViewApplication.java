package fx.ui;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ListViewApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        ObservableList<String> data = FXCollections.observableArrayList("chocolate", "salmon", "gold", "coral", "darkorchid",
                "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
                "blueviolet", "brown");
        ListView<String> list = new ListView<>();
        list.setItems(data);
        list.setCellFactory((ListView<String> l) -> new ColorRectCell());
        list.setEditable(true);
        Label label = new Label();
        list.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov, String old_val,
                 String new_val) -> {
                    label.setText(new_val);
                    label.setTextFill(Color.web(new_val));
                });
        VBox box = new VBox();
        box.getChildren().addAll(list, label);
        VBox.setVgrow(list, Priority.ALWAYS);

        Scene scene = new Scene(box, 200, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ListViewSample");
        primaryStage.show();
    }

    static class ColorRectCell extends ListCell<String> {
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            Rectangle rect = new Rectangle(100, 20);
            if (item != null) {
                rect.setFill(Color.web(item));
                setGraphic(rect);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
