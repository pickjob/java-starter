package app.features.jdk19;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_LONG;

/**
 * Downcall Method Handles: Java 调用 C ABI
 * Upcall Stubs: C ABI 调用 Java方法
 *
 * Linker: 链接函数符号到 MethodHandle
 * SymbolLookup: 查找函数符号
 *      SymbolLookup.loaderLookup(): 查找 System.load / System.loadLibrary 加载的 C ABI
 *      SymbolLookup.libraryLookup(): 查找 .ddl / .so 函数
 *      Linker.systemLookup(): 查找标准C函数
 * FunctionDescriptor: 提供函数签名
 * ValueLayout.*: 类型映射
 * Arena: 堆外内存申请、管理
 *      ofConfined: 需要手动关闭, 不可共享
 *      ofShared: 可共享
 *      ofAuto: 自动管理, 无需关闭
 *      global: 全局
 *
 *      allocate: 申请堆外内存
 * MemorySegment: 堆外内存地址
 *      ofArray
 *
 * @author: pickjob@126.com
 * @date: 2024-09-02
 */
public class ForeignFunctionMemoryShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Throwable {
        Linker linker = Linker.nativeLinker();
        MethodHandle strlen = linker.downcallHandle(
                linker.defaultLookup().find("strlen").get(),
                FunctionDescriptor.of(JAVA_LONG, ADDRESS)
        );
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment nativeStringAddress = arena.allocateUtf8String("Hello World");
            logger.info("strlen of Hello World is : {}", strlen.invoke(nativeStringAddress));
        }
    }
}