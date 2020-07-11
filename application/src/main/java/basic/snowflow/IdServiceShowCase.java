package basic.snowflow;

import app.snowflake.Id;
import app.snowflake.supplier.SynchronizedIdSupplier;
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
        SynchronizedIdSupplier idSupplier = new SynchronizedIdSupplier(11L, 2L, 2L);
        long id = idSupplier.get();
        logger.info("id: {}, ID: {}", id, Id.from(id));
    }

    @Override
    public boolean isShow() {
        return true;
    }
}
