package fx.control;

import app.common.IShowCase;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @time 2019-06-03
 **/
public class FxControlShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(FxControlShowCase.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefWidth(400);
        ObservableList<Node> vBoxContent = vBox.getChildren();

        HBox labelHBox = new HBox();
        labelHBox.setAlignment(Pos.CENTER);
        labelHBox.getChildren().addAll(new Label("Label"),
                new Hyperlink("Hyperlink"));

        TextField textField = new TextField("TextField");
        PasswordField passwordField = new PasswordField();
        passwordField.setTooltip(new Tooltip("abc"));

        HBox buttonHBox = new HBox();
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.getChildren().addAll(new Button("Button"), new CheckBox("CheckBox"));

        MenuButton menuButton = new MenuButton("MenuButton");
        menuButton.getItems().addAll(new MenuItem("MenuItem-1"), new MenuItem("MenuItem-2"));

        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll("ChoiceBox-1", "ChoiceBox-2", "ChoiceBox-3");

        HBox toggleButtonHBox = new HBox();
        ToggleButton toggleButton1 = new ToggleButton("ToggleButton-1");
        ToggleButton toggleButton2 = new ToggleButton("ToggleButton-2");
        ToggleButton toggleButton3 = new ToggleButton("ToggleButton-3");
        ToggleGroup toggleGroup1 = new ToggleGroup();
        toggleButton1.setToggleGroup(toggleGroup1);
        toggleButton2.setToggleGroup(toggleGroup1);
        toggleButton3.setToggleGroup(toggleGroup1);
        toggleGroup1.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle old, Toggle now) -> {
            logger.info("old: {}, now: {}", old, now);
        });
        toggleButtonHBox.getChildren().addAll(toggleButton1, toggleButton2, toggleButton3);

        HBox radioButtonHBox = new HBox();
        ToggleGroup toggleGroup2 = new ToggleGroup();
        RadioButton radioButton1 = new RadioButton("RadioButton-1");
        radioButton1.setToggleGroup(toggleGroup2);
        RadioButton radioButton2 = new RadioButton("RadioButton-2");
        radioButton2.setToggleGroup(toggleGroup2);
        RadioButton radioButton3 = new RadioButton("RadioButton-3");
        radioButton3.setToggleGroup(toggleGroup2);
        radioButtonHBox.getChildren().addAll(radioButton1, radioButton2, radioButton3);

        ComboBox comboBox = new ComboBox();
        comboBox.getItems().addAll("ComboBox-1", "ComboBox-2", "ComboBox-3");

        Slider slider = new Slider(0, 100, 50);
        Label sliderLabel = new Label("50");
        slider.valueProperty().addListener(
                (ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
                    sliderLabel.setText(String.format("%.2f", newVal));
                });
        HBox sliderHBox = new HBox();
        sliderHBox.getChildren().addAll(sliderLabel, slider);

        ProgressBar progressBar = new ProgressBar(0.6);
        ProgressIndicator progressIndicator = new ProgressIndicator(0.6);
        Pagination pagination = new Pagination();

        ListView listView = new ListView();
        listView.setPrefHeight(40);
        listView.getItems().addAll("Item-1", "Item-2", "Item-3");

        TableView tableView = new TableView();
        TableColumn<User, String> firstNameCol = new TableColumn("First Name");
        TableColumn<User, String> lastNameCol = new TableColumn("Last Name");
        TableColumn<User, String> emailCol = new TableColumn("Email");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        firstNameCol.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        firstNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<User, String> t) -> {
                    ((User) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setFirstName(t.getNewValue());
                });

        lastNameCol.setCellFactory(TextFieldTableCell.<User>forTableColumn());
        lastNameCol.setOnEditCommit(
                (TableColumn.CellEditEvent<User, String> t) -> {
                    ((User) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setLastName(t.getNewValue());
                });
        tableView.setEditable(true);
        tableView.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
        ObservableList<User> data = FXCollections.observableArrayList(
                new User("Jacob", "Smith", "Jacob.smith@example.com")
        );
        tableView.setItems(data);
        tableView.setFixedCellSize(30);
        tableView.prefHeightProperty().bind(Bindings.size(tableView.getItems()).multiply(tableView.getFixedCellSize()).multiply(1).add(30));

        vBoxContent.addAll(labelHBox, textField, passwordField,
                buttonHBox, menuButton, choiceBox, toggleButtonHBox, radioButtonHBox,
                comboBox, sliderHBox, progressBar, progressIndicator, pagination,
                listView, tableView);

        Scene scene = new Scene(vBox, 400, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Fx-Controller-ShowCase");
        primaryStage.show();
    }

    @Override
    public void showSomething() {
        logger.info("JavaFx基础控件示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }

    public static class User {
        private SimpleStringProperty firstName;
        private SimpleStringProperty lastName;
        private SimpleStringProperty email;

        public User(String firstName, String lastName, String email) {
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
            this.email = new SimpleStringProperty(email);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public SimpleStringProperty firstNameProperty() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName.set(firstName);
        }

        public String getLastName() {
            return lastName.get();
        }

        public SimpleStringProperty lastNameProperty() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName.set(lastName);
        }

        public String getEmail() {
            return email.get();
        }

        public SimpleStringProperty emailProperty() {
            return email;
        }

        public void setEmail(String email) {
            this.email.set(email);
        }
    }
}

