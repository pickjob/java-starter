package fx.ui;


import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TitledPaneApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Label label = new Label("N/A");
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("To: "), 0, 0);
        grid.add(new TextField(), 1, 0);
        grid.add(new Label("Cc: "), 0, 1);
        grid.add(new TextField(), 1, 1);
        grid.add(new Label("Subject: "), 0, 2);
        grid.add(new TextField(), 1, 2);
        grid.add(new Label("Attachment: "), 0, 3);
        grid.add(label,1, 3);
        TitledPane gridTitlePane = new TitledPane();
        gridTitlePane.setText("Grid");
        gridTitlePane.setContent(grid);

        String[] imageNames = new String[]{"folder_close", "folder_open"};
        Image[] images = new Image[imageNames.length];
        ImageView[] pics = new ImageView[imageNames.length];
        TitledPane[] tps = new TitledPane[imageNames.length];
        for (int i = 0; i < imageNames.length; i++) {
            images[i] = new
                    Image(getClass().getResourceAsStream(imageNames[i] + ".png"));
            pics[i] = new ImageView(images[i]);
            tps[i] = new TitledPane(imageNames[i],pics[i]);
        }
        Accordion accordion = new Accordion();
        accordion.expandedPaneProperty().addListener(
                (ObservableValue<? extends TitledPane> ov, TitledPane old_val,
                 TitledPane new_val) -> {
                    if (new_val != null) {
                        label.setText(accordion.getExpandedPane().getText() +
                                ".png");
                    }
                });
        accordion.getPanes().addAll(tps);


        HBox hbox = new HBox(10);
        hbox.setPadding(new Insets(20, 0, 0, 20));
        hbox.getChildren().setAll(gridTitlePane, accordion);

        Scene scene = new Scene(new Group(), 800, 250);
        Group root = (Group)scene.getRoot();
        root.getChildren().add(hbox);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TitledPane");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
