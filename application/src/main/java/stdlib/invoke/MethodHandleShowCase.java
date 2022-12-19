package stdlib.invoke;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stdlib.invoke.target.TargetClass;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;

/**
 * Invokedynamic:
 *      MethodHandle: 对对象方法操作
 *          bindTo
 *          invoke(Object... args)
 *          invokeExact(Object... args)
 *          invokeWithArguments(Object... arguments)
 *          invokeWithArguments(List<?> arguments)
 *      VarHandle: 对对象变量操作
 *          compareAndXX
 *          getAndXXX
 *      MethodHandlers.Lookup: 查找器
 *          findXXX
 *
 * @author pickjob@126.com
 * @date 2022-12-16
 */
public class MethodHandleShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("Dynamic Invoke(VarHandle, MethodHanle)使用示例");
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


    private static void invokePublicStaticMethod(MethodHandles.Lookup lookup, TargetClass targetClass) throws Throwable {
        MethodHandle methodHandle = lookup.findStatic(TargetClass.class, "publicStaticMethod", MethodType.methodType(void.class));
        methodHandle.invoke();
    }

    private static void invokePublicMethod(MethodHandles.Lookup lookup, TargetClass targetClass) throws Throwable {
        MethodHandle methodHandle = lookup.findVirtual(TargetClass.class, "publicMethod", MethodType.methodType(void.class));
        methodHandle.invoke(targetClass);
    }

    private static void invokePrivateMethod(MethodHandles.Lookup lookup, TargetClass targetClass) throws Throwable {
        MethodHandle methodHandle = lookup.findVirtual(TargetClass.class, "privateMethod", MethodType.methodType(void.class));
        methodHandle.invoke(targetClass);
    }

    private static void invokePrivateFieldCAP(MethodHandles.Lookup lookup, TargetClass targetClass) throws Throwable {
        VarHandle varHandle = lookup.findVarHandle(TargetClass.class, "state", int.class);
        Integer state = (Integer)varHandle.get(targetClass);
        varHandle.compareAndSet(targetClass, state, state + 10);
        MethodHandle methodHandle = lookup.findVirtual(TargetClass.class, "logState", MethodType.methodType(void.class));
        methodHandle.invoke(targetClass);
    }
}
