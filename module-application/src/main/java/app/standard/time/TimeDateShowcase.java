package app.standard.time;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *  Instant 代表时间点
 *  Duration 代表时间间隔
 *  LocalDate 代表本地日期
 *  LocalTime 代表本地时间
 *
 * @author: pickjob@126.com
 * @date: 2024-09-08
 */
public class TimeDateShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Instant start = Instant.now();
        logger.info("Instant 代表时间点: {}", Instant.now());
        Duration duration = Duration.between(start, Instant.now());
        logger.info("Duration 代表时间间隔: {}", duration);
        logger.info("LocalDate 代表本地日期: {}", LocalDate.now());
        logger.info("LocalTime 代表本地时间: {}", LocalTime.now());
    }
}