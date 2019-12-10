package app;

import basic.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import basic.utils.ScanUtil;

import java.util.Set;

public class Application {
    private static Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        traverseShowCase();
    }

    private static void traverseShowCase() {
        boolean showedFlag = false;
        Set<String> clsNameList = ScanUtil.scanClassWithPackageAndClass("basic", IShowCase.class);
        for (String clsName : clsNameList) {
            try {
                Class cls = Class.forName(clsName);
                Object obj = cls.getDeclaredConstructor().newInstance();
                if (obj instanceof IShowCase) {
                    IShowCase showCase = (IShowCase) obj;
                    if (showCase.isShow()) {
                        showCase.saySomething();
                        showCase.showSomething();
                        showedFlag = true;
                    }
                }
            } catch (Throwable throwable) {
                logger.error(throwable.getMessage(), throwable);
            }
        }
        if (!showedFlag) {
            logger.info("Hello world");
        }
    }
}
