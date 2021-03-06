package basic.invoke;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.*;

/**
 * @author pickjob@126.com
 * @time 2019-04-30
 */
public class CallSiteShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(CallSiteShowCase.class);

    @Override
    public void showSomething() {
        logger.info("关于CallSite的使用示例");
        try {
            constantCallSite();
            mutableCallSite();
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
        }
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }

    private static void constantCallSite() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle toUpperCase = lookup.findVirtual(String.class, "toUpperCase", MethodType.methodType(String.class));
        ConstantCallSite constantCallSite = new ConstantCallSite(toUpperCase);
        String result = (String) constantCallSite.dynamicInvoker().invoke("aaa");
        logger.info("constantCallSite original: {},result: {}", "aaa", result);
    }

    private static void mutableCallSite() throws Throwable {
        // 可以直接先指定方法签名, 再指定方法名
        MutableCallSite mutableCallSite = new MutableCallSite(MethodType.methodType(String.class));
        MethodHandle handle = mutableCallSite.dynamicInvoker();
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle toUpperCase = lookup.findVirtual(String.class, "toUpperCase", MethodType.methodType(String.class));
        MethodHandle targetHandle = MethodHandles.filterReturnValue(MethodHandles.constant(String.class, "aaa"), toUpperCase);
        // 指定实际调用链
        mutableCallSite.setTarget(targetHandle);
        // 仍用原来句柄调用
        String result = (String)handle.invoke();
        logger.info("mutableCallSite original: {}, result: {}", "aaa", result);
    }
}
