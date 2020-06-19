package spring.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author: pickjob@126.com
 * @time: 2020-03-24
 **/
@Component
public class SimpleStaticMethodMatcherPointcutAdvisor extends StaticMethodMatcherPointcutAdvisor {
    private static Logger logger = LogManager.getLogger(SimpleStaticMethodMatcherPointcutAdvisor.class);

    public SimpleStaticMethodMatcherPointcutAdvisor() {
        MethodInterceptor methodInterceptor = new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                logger.info("method: {}, {}", invocation.getMethod(), invocation.getArguments());
                Object retVal = invocation.proceed();
                logger.info("return: {}", retVal);
                return retVal;
            }
        };
        setAdvice(methodInterceptor);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if ("spring.bean.SimpleShowCaseBean".equalsIgnoreCase(targetClass.getCanonicalName())
            && "saySomething".equalsIgnoreCase(method.getName())
        ) {
            return true;
        }
        return false;
    }
}
