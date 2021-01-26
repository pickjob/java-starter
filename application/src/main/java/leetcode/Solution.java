package leetcode;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @Author pickjob@126.com
 * @Date 2020-11-26
 */
public class Solution implements IShowCase {
    private static final Logger logger = LogManager.getLogger(Solution.class);

    @Override
    public void showSomething() {
         logger.info("一些简单的代码编写");
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }

}
