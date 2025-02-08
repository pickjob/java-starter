package app.framework.jackson;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Jackson: Json处理
 *      注解:
 *          属性注解:
 *              @JsonProperty
 *              @JsonFormat:
 *                  pattern: 日期格式
 *                  timezone: 时区
 *                  shape: JsonFormat.Shape
 *                  with: JsonFormat.Feature[]
 *                  without: JsonFormat.Feature[]
 *              @JsonAnyGetter: 支持Map
 *              @JsonPropertyOrder
 *              @JsonCreator
 *              @JsonEnumDefaultValue
 *              @JsonIgnore
 *          类注解:
 *              @JsonSerialize
 *              @JsonDeserialize
 *              @JsonIgnoreProperties
 *      ObjectMapper:
 *          配置:
 *              // 格式化输出
 *              mapper.enable(SerializationFeature.INDENT_OUTPUT);
 *              // 序列化空 POJO
 *              mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
 *              // 序列化 java.util.Date, Calendar 成 TIMESTAMP
 *              mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
 *
 *              // 反序列化未知属性抛异常
 *              mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
 *              // 反序列话 空字符成 null
 *              mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
 *
 *              // 允许 C类 注释
 *              mapper.enable(JsonParser.Feature.ALLOW_COMMENTS);
 *          方法:
 *              readValue / writeValue
 *
 * @author: pickjob@126.com
 * @date: 2024-09-04
 **/
public class JacksonShowCase {
    private static final Logger logger = LogManager.getLogger();
}