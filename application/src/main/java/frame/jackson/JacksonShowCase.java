package frame.jackson;

import app.common.IShowCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsFactory;
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsSchema;
import com.fasterxml.jackson.dataformat.javaprop.util.Markers;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * @Author ws@yuan-mai.com
 * @Date 2020-11-16
 */
public class JacksonShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(JacksonShowCase.class);
    private static final String YAML_CONFIG = "nodeA: \n" +
                                              "    nodeB: AAA \n" +
                                              "    nodeC: \n" +
                                              "    - A \n" +
                                              "    - B \n" +
                                              "    - C \n";

    @Override
    public void showSomething() {
        logger.info("Jacson Yaml <==> Properties 文件互相转换");
        ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
        JavaPropsSchema schema = JavaPropsSchema.emptySchema()
                .withFirstArrayOffset(0)
                .withWriteIndexUsingMarkers(true)
                .withIndexMarker(Markers.create("[", "]"));
        ObjectMapper propsMapper = new ObjectMapper(new JavaPropsFactory());
        try {
            Map map = yamlMapper.readValue(YAML_CONFIG, Map.class);
            propsMapper
                    .writer(schema)
                    .writeValue(System.out, map);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }
}
