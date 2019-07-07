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
        logger.info("{}", System.getProperties());
        traverseShowCase();
    }

    private static void traverseShowCase() {
        Set<String> clsNameList = ScanUtil.scanClassWithPackageAndClass("basic", IShowCase.class);
        for (String clsName : clsNameList) {
            try {
                Class cls = Class.forName(clsName);
                Object obj = cls.getDeclaredConstructor().newInstance();
                if (obj instanceof IShowCase) {
                    IShowCase showCase = (IShowCase) obj;
                    showCase.saySomething();
                    showCase.showSomething();
                }
            } catch (Throwable throwable) {
                logger.error(throwable.getMessage(), throwable);
            }
        }
    }
}
