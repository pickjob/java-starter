package app;

import common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ScanUtil;

import java.util.Set;

public class Application {
    private static Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Hello world");
        traverseShowCase();
    }

    private static void traverseShowCase() {
        Set<Class<?>> clsList = ScanUtil.scanClasses(IShowCase.class);
        for (Class<?> cls : clsList) {
            try {
                IShowCase showCase = (IShowCase) cls.getDeclaredConstructor().newInstance();
                showCase.saySomething();
                showCase.showSomething();
            } catch (Throwable throwable) {
                logger.error(throwable.getMessage(), throwable);
            }
        }
    }
}
