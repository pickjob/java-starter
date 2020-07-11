package basic.time;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author pickjob@126.com
 * @time 2020-07-10
 */
public class TimeDateShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(TimeDateShowCase.class);
    @Override
    public void saySomething() {
        logger.info("展示jdk中时间日期用法");
    }

    @Override
    public void showSomething() {
        Instant start = Instant.now();
        logger.info("Instant代表时间点: {}", Instant.now());
        Duration duration = Duration.between(start, Instant.now());
        logger.info("Duration代表时间间隔: {}", duration);
        logger.info("LocalDate代表本地日期: {}", LocalDate.now());
        logger.info("LocalTime代表本地时间: {}", LocalTime.now());
    }

    @Override
    public boolean isShow() {
        return true;
    }
}
