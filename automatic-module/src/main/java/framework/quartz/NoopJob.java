package framework.quartz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author pickjob@126.com
 * @time 2019-08-01
 */
public class NoopJob implements Job {
    private static final Logger logger = LogManager.getLogger(NoopJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("noop");
    }
}