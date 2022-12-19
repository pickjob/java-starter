package stdlib.invoke.target;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @date 2022-12-16
 */
public class TargetClass {
    private static final Logger logger = LogManager.getLogger(TargetClass.class);
    private int state;

    public static void publicStaticMethod() {
        logger.info("TargetClass.publicStaticMethod() is invoked");
    }

    public void publicMethod() {
        logger.info("TargetClass.publicMethod() is invoked");
    }

    public void logState() {
        logger.info("state: {}", state);
    }

    private void privateMethod() {
        logger.info("TargetClass.privateMethod() is invoked");
    }


}
