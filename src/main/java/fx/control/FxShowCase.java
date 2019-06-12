package fx.control;

import fx.entity.EntityFactory;
import fx.entity.MyTextFieldListCell;
import fx.entity.Person;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pickjob@126.com
 * @time 2019-06-03
 **/
public class FxShowCase extends Application {
    private static final Logger logger = LogManager.getLogger(FxShowCase.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox hBox = new VBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefWidth(450);
        hBox.getChildren().addAll(createContent());

        ScrollPane scrollPane = new ScrollPane(hBox);
        Scene scene = new Scene(scrollPane, 450, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Fx-ShowCase");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public List<Node> createContent() {
        List<Node> result = new ArrayList<>();
        result.add(new Label("Label"));
        result.add(new Hyperlink("Hyperlink"));
        result.add(new Button("Button"));

        TextFlow textFlow = new TextFlow();
        Button button = new Button("button");
        ToggleButton toggleButton = new ToggleButton("ToggleButton");
        textFlow.getChildren().addAll(button, toggleButton);
        result.add(textFlow);

        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton radioButton1 = new RadioButton("A");
        radioButton1.setToggleGroup(toggleGroup);
        RadioButton radioButton2 = new RadioButton("B(selected)");
        radioButton2.setToggleGroup(toggleGroup);
        radioButton2.setSelected(true);
        RadioButton radioButton3 = new RadioButton("C");
        radioButton3.setToggleGroup(toggleGroup);
        toggleGroup.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> observable, Toggle old, Toggle now) -> {
                    logger.info("old: {}, now: {}", old, now);
                }
        );

        HBox hBox1 = new HBox();
        hBox1.setSpacing(5);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.getChildren().addAll(radioButton1, radioButton2, radioButton3);
        result.add(hBox1);

        HBox hBox2 = new HBox();
        hBox2.setSpacing(5);
        hBox2.setAlignment(Pos.CENTER);
        CheckBox checkBox1 = new CheckBox("Simple");
        CheckBox checkBox2 = new CheckBox("Three-state");
        checkBox2.setAllowIndeterminate(true);
        checkBox2.setIndeterminate(false);
        CheckBox checkBox3 = new CheckBox("Disabled");
        checkBox3.setSelected(true);
        hBox2.getChildren().addAll(checkBox1, checkBox2, checkBox3);
        result.add(hBox2);

        HBox hBox3 = new HBox();
        hBox3.setSpacing(5);
        hBox3.setAlignment(Pos.CENTER);
        hBox3.getChildren().addAll(new TextField("TextField"), new PasswordField());
        result.add(hBox3);

        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll("Dog", "Cat", "Horse");
        choiceBox.getSelectionModel().selectFirst();
        result.add(choiceBox);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(
                "Highest",
                "High",
                "Normal",
                "Low",
                "Lowest"
        );
        comboBox.setValue("Normal");
        comboBox.setCellFactory(view -> {
            ListCell<String> cell = new ListCell<String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item);
                        if (item.contains("High")) setTextFill(Color.RED);
                        else if (item.contains("Low")) setTextFill(Color.GREEN);
                        else setTextFill(Color.BLACK);
                    }
                    else setText(null);
                }
            };
            return cell;
        });
        result.add(comboBox);

        ToolBar toolBar = new ToolBar(new Slider());
        result.add(toolBar);

        Float[] values = new Float[] {-1.0f, 0f, 0.6f, 1.0f};
        for (int i = 0; i < values.length; i++) {
            Label label = new Label();
            label.setText("progress:" + values[i]);
            ProgressBar pb = new ProgressBar();
            pb.setProgress(values[i]);
            ProgressIndicator pin = new ProgressIndicator();
            pin.setProgress(values[i]);
            HBox box = new HBox();
            box.setSpacing(5);
            box.setAlignment(Pos.CENTER);
            box.getChildren().addAll(label, pb, pin);
            result.add(box);
        }

        Pagination pagination = new Pagination(5);
        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        pagination.setPageFactory(i -> new Label("" + i));
        result.add(pagination);

        MenuItem menuItem = new MenuItem("menuItem");
        RadioMenuItem radioMenuItem = new RadioMenuItem("radioMenuItem");
        CheckMenuItem checkMenuItem = new CheckMenuItem("checkMenuItem");
        Menu menu = new Menu("Menu");
        menu.getItems().addAll(menuItem, radioMenuItem, checkMenuItem);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        result.add(menuBar);

        ColorPicker colorPicker = new ColorPicker();
        result.add(colorPicker);

        DatePicker datePicker = new DatePicker();
        result.add(datePicker);

        ObservableList<Person> personList = FXCollections.<Person>observableArrayList(EntityFactory.generatePersons());

        Spinner spinner = new Spinner();
        SpinnerValueFactory<Person> svf = new SpinnerValueFactory.ListSpinnerValueFactory<>(personList);
        svf.setConverter(new StringConverter<Person>() {
            @Override
            public String toString(Person object) {
                return object.getName() + "-" + object.getAge();
            }

            @Override
            public Person fromString(String string) {
                return new Person(string, 11);
            }
        });
        spinner.setValueFactory(svf);
        spinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_LEFT_HORIZONTAL);

        HTMLEditor htmlEditor = new HTMLEditor();
        htmlEditor.setHtmlText("<h1>Header 1</h1>");

        ListView<Person> listView = new ListView<Person>(personList);
        listView.setEditable(true);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.setCellFactory(view -> new MyTextFieldListCell());
        listView.setOnEditCommit(e -> {
            ObservableList<Person> list = e.getSource().getItems();
            Person p = list.get(e.getIndex());
            p.setName(e.getNewValue().getName());
            logger.info("{}", p);
        });

        TableColumn<Person, String> nameCol = new TableColumn<>();
        nameCol.setText("name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        TableColumn<Person, Integer> ageCol = new TableColumn<>();
        ageCol.setText("age");
        ageCol.setCellValueFactory(new PropertyValueFactory<Person, Integer>("age"));
        TableColumn<Person, Boolean> auditCol = new TableColumn<>();
        auditCol.setText("audit");
        auditCol.setCellValueFactory(new PropertyValueFactory<Person, Boolean>("audit"));
        auditCol.setCellFactory(CheckBoxTableCell.forTableColumn(auditCol));
        TableView<Person> tableView = new TableView<>();
        tableView.setItems(personList);
        tableView.setEditable(true);
        tableView.getColumns().addAll(nameCol, ageCol, auditCol);

        TreeItem<Person> root = EntityFactory.generateTreePerson();

        TreeView<Person> treeView = new TreeView<>(root);
        treeView.setShowRoot(true);

        TreeTableColumn<Person, String> nameColumn = new TreeTableColumn<>("Name");
        nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Person, String>("name"));
        TreeTableColumn<Person, String> ageColumn = new TreeTableColumn<>("Age");
        ageColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Person, String>("age"));
        TreeTableColumn<Person, Boolean> auditColumn = new TreeTableColumn<>("audit");
        auditColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Person, Boolean>("audit"));
        auditColumn.setCellFactory(CheckBoxTreeTableCell.forTreeTableColumn(auditColumn));
        TreeTableView<Person> treeTableView = new TreeTableView<>(root);
        treeTableView.setEditable(true);
        treeTableView.getColumns().addAll(nameColumn, ageColumn, auditColumn);

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load("http://www.baidu.com");

        Accordion accordion = new Accordion();
        accordion.getPanes().addAll(new TitledPane("Spinner", spinner),
                                    new TitledPane("HTMLEditor", htmlEditor),
                                    new TitledPane("ListView", listView),
                                    new TitledPane("TableView", tableView),
                                    new TitledPane("TreeView", treeView),
                                    new TitledPane("TreeTableView", treeTableView),
                                    new TitledPane("WebView", webView)
        );

        result.add(accordion);

        return result;
    }
}
