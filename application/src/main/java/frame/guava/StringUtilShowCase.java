package frame.guava;

import app.common.IShowCase;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author: pickjob@126.com
 * @time: 2020-06-18
 **/
public class StringUtilShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(StringUtilShowCase.class);
    @Override
    public void saySomething() {
        logger.info("展示Guava中关于String工具");
    }

    @Override
    public void showSomething() {
        logger.info("Joiner: {}", Joiner.on("; ").skipNulls().join("Harry", null, "Ron", "Hermione"));
        logger.info("Spliter: {}", Splitter.on(',').trimResults().omitEmptyStrings().split("foo,bar,,   qux"));
    }
}
