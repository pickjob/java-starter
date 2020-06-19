package app;

import app.common.IShowCase;
import app.utils.ScanUtil;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class Application {
    private static Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        traverseShowCase();
    }

    private static void traverseShowCase() throws Exception {
        boolean showedFlag = false;
        Set<String> clsNameList = ScanUtil.scanClassWithPackageAndClass("frame", IShowCase.class);
        for (String clsName : clsNameList) {
            try {
//                logger.info("clsName: {}", clsName);
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
