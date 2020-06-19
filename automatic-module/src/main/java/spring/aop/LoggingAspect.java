package spring.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author pickjob@126.com
 * @time 2019-04-27
 **/
@Component
@Aspect
public class LoggingAspect implements Ordered {
    private static Logger logger = LogManager.getLogger(LoggingAspect.class);

    @Around("execution(public * spring..*(..))")
    public Object accessLogging(ProceedingJoinPoint pjp) throws Throwable {
        String id = UUID.randomUUID().toString();
        logger.info("{} entering {}", id, pjp.toLongString());
        Object retVal = pjp.proceed();
        logger.info("{} leaving", id);
        return retVal;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}