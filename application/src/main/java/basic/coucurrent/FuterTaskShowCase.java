package basic.coucurrent;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

/**
 * @author pickjob@126.com
 * @time 2019-12-13
 */
public class FuterTaskShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(FuterTaskShowCase.class);

    @Override
    public void saySomething() {
        logger.info("FutureTask延迟获得结果任务");
    }

    @Override
    public void showSomething() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask task = new FutureTask(() -> {
            logger.info("---任务进行中---");
            try {
                Thread.sleep(1000 * 15);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }, Void.class);
        executor.execute(task);
        try {
            task.get();
            logger.info("---任务结束---");
            executor.shutdown();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
