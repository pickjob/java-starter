package app.snowflake;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author pickjob@126.com
 * @time 2019-05-10
 */
public class IdServiceShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(IdServiceShowCase.class);

    @Override
    public void saySomething() {
        logger.info("展示IdService生成");
    }

    @Override
    public void showSomething() {
        IdService idService = new IdServiceImpl();
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                long time = System.currentTimeMillis();
                while (true) {
                    logger.info("SeqId: {}", idService.generateSeqId());
                    if (System.currentTimeMillis() - time > 1000 * 5) break;
                }
            }).start();
        }
    }
}
