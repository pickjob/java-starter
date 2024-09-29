package app.standard.invoke;

import app.standard.invoke.target.TargetClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;

/**
 * MethodHandle
 *
 * @author pickjob@126.com
 * @date 2024-09-10
 */
public class MethodHandleShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        TargetClass targetClass = new TargetClass();
        invokePublicStaticMethod(lookup, targetClass);
        invokePublicMethod(lookup, targetClass);
        MethodHandles.Lookup privateLookup = MethodHandles.privateLookupIn(TargetClass.class, lookup);
        invokePrivateMethod(privateLookup, targetClass);
        invokePrivateFieldCAP(privateLookup, targetClass);
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
        Integer state = (Integer) varHandle.get(targetClass);
        varHandle.compareAndSet(targetClass, state, state + 10);
        MethodHandle methodHandle = lookup.findVirtual(TargetClass.class, "logState", MethodType.methodType(void.class));
        methodHandle.invoke(targetClass);
    }
}