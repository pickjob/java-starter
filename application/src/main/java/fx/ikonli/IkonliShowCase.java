package fx.ikonli;

import app.common.IShowCase;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.dashicons.Dashicons;
import org.kordamp.ikonli.devicons.Devicons;
import org.kordamp.ikonli.elusive.Elusive;
import org.kordamp.ikonli.entypo.Entypo;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.fontawesome5.FontAwesomeBrands;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.fontelico.Fontelico;
import org.kordamp.ikonli.foundation.Foundation;
import org.kordamp.ikonli.hawconsfilled.HawconsFilled;
import org.kordamp.ikonli.hawconsstroke.HawconsStroke;
import org.kordamp.ikonli.icomoon.Icomoon;
import org.kordamp.ikonli.ionicons.Ionicons;
import org.kordamp.ikonli.ionicons4.Ionicons4IOS;
import org.kordamp.ikonli.ionicons4.Ionicons4Logo;
import org.kordamp.ikonli.ionicons4.Ionicons4Material;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.ligaturesymbols.LigatureSymbols;
import org.kordamp.ikonli.linecons.Linecons;
import org.kordamp.ikonli.maki.Maki;
import org.kordamp.ikonli.maki2.Maki2;
import org.kordamp.ikonli.mapicons.Mapicons;
import org.kordamp.ikonli.material.Material;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.metrizeicons.MetrizeIcons;
import org.kordamp.ikonli.ociicons.Ociicons;
import org.kordamp.ikonli.octicons.Octicons;
import org.kordamp.ikonli.openiconic.Openiconic;
import org.kordamp.ikonli.paymentfont.PaymentFont;
import org.kordamp.ikonli.runestroicons.Runestroicons;
import org.kordamp.ikonli.themify.Themify;
import org.kordamp.ikonli.typicons.Typicons;
import org.kordamp.ikonli.weathericons.WeatherIcons;
import org.kordamp.ikonli.websymbols.Websymbols;
import org.kordamp.ikonli.zondicons.Zondicons;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * @author: pickjob@126.com
 * @time: 2020-06-23
 **/
public class IkonliShowCase extends Application implements IShowCase {
    private static final Logger logger = LogManager.getLogger(IkonliShowCase.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        List<Class<? extends Enum>> list = Arrays.asList(
                Dashicons.class
                , Devicons.class
                , Elusive.class
                , Entypo.class
                , Feather.class
                , FontAwesome.class
                , FontAwesomeBrands.class
                , FontAwesomeSolid.class
                , FontAwesomeRegular.class
                , Fontelico.class
                , Foundation.class
                , HawconsFilled.class
                , HawconsStroke.class
                , Icomoon.class
                , Ionicons4Material.class
                , Ionicons4IOS.class
                , Ionicons4Logo.class
                , Ionicons.class
                , LigatureSymbols.class
                , Linecons.class
                , Maki.class
                , Maki2.class
                , Mapicons.class
                , Material.class
                , MaterialDesign.class
                , MetrizeIcons.class
                , Ociicons.class
                , Octicons.class
                , Openiconic.class
                , PaymentFont.class
                , Runestroicons.class
                , Themify.class
                , Typicons.class
                , WeatherIcons.class
                , Websymbols.class
                , Zondicons.class
        );
        TabPane tabPane = new TabPane();
        for (Class<? extends Enum> cls : list) {
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(10));
            int iconIdx = 0;
            for (Object val : EnumSet.allOf(cls)) {
                Ikon ikon = (Ikon) val;
                VBox vBox = new VBox();
                FontIcon icon = FontIcon.of(ikon, 40);
                Label label = new Label(ikon.getDescription());
                vBox.getChildren().addAll(icon, label);
                gridPane.add(vBox, iconIdx % 20, iconIdx / 20);
                iconIdx++;
            }
            ScrollPane scrollPane = new ScrollPane(gridPane);
            Tab tab = new Tab();
            tab.setText(cls.getSimpleName());
            tab.setContent(scrollPane);
            tab.setClosable(false);
            tabPane.getTabs().add(tab);
        }
        Scene scene = new Scene(tabPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ikonli JavaFx icon show case");

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getWidth() / 4);
        primaryStage.setY(bounds.getHeight() / 4);
        primaryStage.setWidth(bounds.getWidth() / 2);
        primaryStage.setHeight(bounds.getHeight() / 2);
        primaryStage.show();
    }

    @Override
    public void showSomething() {
        logger.info("JavaFx 图标库 ikonli示例");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
