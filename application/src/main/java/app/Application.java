package app;

import app.common.IShowCase;
import app.utils.ScanUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Application {
    private static Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        traverseShowCase(List.of(args));
    }

    private static void traverseShowCase(List<String> args) throws Exception {
        boolean showedFlag = false;
        Set<String> clsNameSet = new HashSet<>();
        for (String arg : args) {
            clsNameSet.addAll(ScanUtil.scanClassWithPackageAndClass(arg, IShowCase.class));
        }
        List<IShowCase> showCaseList = new ArrayList<>();
        for (String clsName : clsNameSet) {
            try {
                Class cls = Class.forName(clsName);
                Object obj = cls.getDeclaredConstructor().newInstance();
                if (obj instanceof IShowCase showCase) {
                    if (showCase.isShow()) {
                        showCaseList.add(showCase);
                    }
                }
            } catch (Throwable throwable) {
                logger.error(throwable.getMessage(), throwable);
            }
        }
        showCaseList.sort((case1, case2) -> case1.order() - case2.order());
        for (IShowCase showCase : showCaseList) {
            showCase.showSomething();
            showedFlag = true;
        }
        if (!showedFlag) {
            logger.info("Hello world");
        }
    }
}
