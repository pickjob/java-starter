package j4ee.expressionlanguage;

import jakarta.el.ELProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

/**
 * @author: pickjob@126.com
 * @date: 2022-12-12
 */
public class ExpressLanguageShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        ELProcessor elProcessor = new ELProcessor();
        elProcessor.defineBean("now", new Date());
        String result = (String) elProcessor.eval("${15 * 10}");
        logger.info("15 * 10 = {} ", result);
    }
}
