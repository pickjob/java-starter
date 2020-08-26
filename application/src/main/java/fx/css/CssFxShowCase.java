package fx.css;

import fr.brouillard.oss.cssfx.CSSFX;
import fr.brouillard.oss.cssfx.api.URIToPathConverter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: pickjob@126.com
 * @date: 2020-08-26
 **/
public class CssFxShowCase extends Application {
    @Override
    public void start(Stage mainStage) throws Exception {
        VBox vBox = new VBox();
        vBox.getChildren().add(new Button("Click Me"));
        Scene scene = new Scene(vBox, 800, 600);
        String userHome = System.getProperty("user.home");
        if (StringUtils.isNoneBlank(userHome)) {
            Path path = Paths.get(userHome + File.separator +  "cssfx.css");
            if (path.toFile().exists()) {
                scene.getStylesheets().add("file:/" + path.toAbsolutePath());
            }
        }
        mainStage.setScene(scene);
        mainStage.setTitle("CssFxShowCase");
        mainStage.show();
    }

    public static void main(String[] args) {
        Properties properties = System.getProperties();
        properties.put("cssfx.log", true);
        properties.put("cssfx.log.level", "DEBUG");
        URIToPathConverter userHomeConvert = new URIToPathConverter() {
            @Override
            public Path convert(String uri) {
                System.out.println(uri);
                Matcher m = Pattern.compile("file:/(.*\\.css)").matcher(uri);
                if (m.matches()) {
                    String path = m.group(1);
                    return Paths.get(path);
                }
                return null;
            }
        };

        CSSFX.addConverter(userHomeConvert).start();
        launch(args);
    }
}
