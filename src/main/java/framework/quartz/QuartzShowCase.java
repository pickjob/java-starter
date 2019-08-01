package framework.quartz;

import common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author pickjob@126.com
 * @time 2019-08-01
 */
public class QuartzShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(QuartzShowCase.class);

    @Override
    public void saySomething() {
        logger.info("展示Quartz示例");
    }

    @Override
    public void showSomething() {
        try {
            JobDetail jobDetail = JobBuilder.newJob(NoopJob.class)
                                    .withIdentity("jobKey")
                                    .build();
            ScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                                                .withIntervalInSeconds(1)
                                                .repeatForever();
            Trigger trigger = TriggerBuilder.newTrigger()
                                .withIdentity("trggerKey")
                                .startNow()
                                .withSchedule(scheduleBuilder)
                                .build();
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
            Thread.sleep(60 * 1000);
            scheduler.shutdown();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}


