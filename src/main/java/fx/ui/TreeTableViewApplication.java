package fx.ui;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class TreeTableViewApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        List<Employee> employees = Arrays.<Employee>asList(
                new Employee("Ethan Williams", "ethan.williams@example.com"),
                new Employee("Emma Jones", "emma.jones@example.com"),
                new Employee("Michael Brown", "michael.brown@example.com"),
                new Employee("Anna Black", "anna.black@example.com"),
                new Employee("Rodger York", "roger.york@example.com"),
                new Employee("Susan Collins", "susan.collins@example.com"));
        ImageView depIcon = new ImageView(
                new Image(getClass().getResourceAsStream("folder_close.png"), 25, 25, true, true)
        );

        TreeItem<Employee> root =
                new TreeItem<>(new Employee("Sales Department", ""), depIcon);
        root.setExpanded(true);
        employees.forEach((employee) -> {
            root.getChildren().add(new TreeItem<>(employee));
        });
        Scene scene = new Scene(new Group(), 400, 400);
        scene.setFill(Color.LIGHTGRAY);
        Group sceneRoot = (Group) scene.getRoot();

        TreeTableColumn<Employee, String> empColumn =
                new TreeTableColumn<>("Employee");
        empColumn.setPrefWidth(190);
        empColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Employee, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getName())
        );

        TreeTableColumn<Employee, String> emailColumn =
                new TreeTableColumn<>("Email");
        emailColumn.setPrefWidth(190);
        emailColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Employee, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getEmail())
        );

        TreeTableView<Employee> treeTableView = new TreeTableView<>(root);
        treeTableView.getColumns().setAll(empColumn, emailColumn);
        sceneRoot.getChildren().add(treeTableView);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tree Table View Sample");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public class Employee {
        private SimpleStringProperty name;
        private SimpleStringProperty email;

        public SimpleStringProperty nameProperty() {
            if (name == null) {
                name = new SimpleStringProperty(this, "name");
            }
            return name;
        }
        public SimpleStringProperty emailProperty() {
            if (email == null) {
                email = new SimpleStringProperty(this, "email");
            }
            return email;
        }
        private Employee(String name, String email) {
            this.name = new SimpleStringProperty(name);
            this.email = new SimpleStringProperty(email);
        }
        public String getName() {
            return name.get();
        }
        public void setName(String fName) {
            name.set(fName);
        }
        public String getEmail() {
            return email.get();
        }
        public void setEmail(String fName) {
            email.set(fName);
        }
    }
}
