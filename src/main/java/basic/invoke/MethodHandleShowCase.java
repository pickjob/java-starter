package basic.invoke;

import basic.invoke.target.TargetClass;
import basic.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;

/**
 * @author pickjob@126.com
 * @time 2019-04-30
 */
public class MethodHandleShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(MethodHandleShowCase.class);

    @Override
    public void saySomething() {
        logger.info("Dynamic Invoke(VarHandle, MethodHanle)使用示例");
    }

    @Override
    public void showSomething() {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            TargetClass targetClass = new TargetClass();
            invokePublicMethod(lookup, targetClass);
            invokePublicStaticMethod(lookup, targetClass);
            invokePublicMethod(lookup, targetClass);
            MethodHandles.Lookup privateLookup = MethodHandles.privateLookupIn(TargetClass.class, lookup);
            invokePrivateMethod(privateLookup, targetClass);
            invokePrivateFieldCAP(privateLookup, targetClass);
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
        }
    }

    private void invokePublicStaticMethod(MethodHandles.Lookup lookup, TargetClass targetClass) throws Throwable {
        MethodHandle methodHandle = lookup.findStatic(TargetClass.class, "publicStaticMethod", MethodType.methodType(void.class));
        methodHandle.invoke();
    }

    private void invokePublicMethod(MethodHandles.Lookup lookup, TargetClass targetClass) throws Throwable {
        MethodHandle methodHandle = lookup.findVirtual(TargetClass.class, "publicMethod", MethodType.methodType(void.class));
        methodHandle.invoke(targetClass);
    }

    private void invokePrivateMethod(MethodHandles.Lookup lookup, TargetClass targetClass) throws Throwable {
        MethodHandle methodHandle = lookup.findVirtual(TargetClass.class, "privateMethod", MethodType.methodType(void.class));
        methodHandle.invoke(targetClass);
    }

    private void invokePrivateFieldCAP(MethodHandles.Lookup lookup, TargetClass targetClass) throws Throwable {
        VarHandle varHandle = lookup.findVarHandle(TargetClass.class, "state", int.class);
        Integer state = (Integer)varHandle.get(targetClass);
        varHandle.compareAndSet(targetClass, state, state + 10);
        MethodHandle methodHandle = lookup.findVirtual(TargetClass.class, "logState", MethodType.methodType(void.class));
        methodHandle.invoke(targetClass);
    }
}
