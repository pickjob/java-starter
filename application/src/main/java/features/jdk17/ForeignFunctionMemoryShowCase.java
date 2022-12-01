package features.jdk17;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

/**
 * C ABI交互:
 *      // Requirement: Load library
 *      System.loadLibrary();
 *      // 1. Find foreign function on the C library path
 *      MethodHandle radixSort = CLinker.getInstance().downcallHandle(
 *          CLinker.systemLookup().lookup("radixSort"), ...);
 *      // 2. Allocate on-heap memory to store four strings
 *      String[] javaStrings   = { "mouse", "cat", "dog", "car" };
 *      // 3. Allocate off-heap memory to store four pointers
 *      MemorySegment offHeap  = MemorySegment.allocateNative(
 *          MemoryLayout.ofSequence(javaStrings.length, CLinker.C_POINTER), ...);
 *      // 4. Copy the strings from on-heap to off-heap
 *      for (int i = 0; i < javaStrings.length; i++) {
 *          // Allocate a string off-heap, then store a pointer to it
 *          MemorySegment cString = CLinker.toCString(javaStrings[i], newImplicitScope());
 *          MemoryAccess.setAddressAtIndex(offHeap, i, cString.address());
 *      }
 *      // 5. Sort the off-heap data by calling the foreign function
 *      radixSort.invoke(offHeap.address(), javaStrings.length, MemoryAddress.NULL, '\0');
 *      // 6. Copy the (reordered) strings from off-heap to on-heap
 *      for (int i = 0; i < javaStrings.length; i++) {
 *          MemoryAddress cStringPtr = MemoryAccess.getAddressAtIndex(offHeap, i);
 *          javaStrings[i] = CLinker.toJavaStringRestricted(cStringPtr);
 *      }
 *      assert Arrays.equals(javaStrings, new String[] {"car", "cat", "dog", "mouse"});  // true
 *
 *
 * CLinker: 链接函数符号到 MethodHandle
 *      SymbolLookup: 查找函数符号
 *          SymbolLookup.loaderLookup(): 查找System.load / System.loadLibrary 加载的 C ABI
 *          CLinker.systemLookup(): 查找标准C函数
 *      MethodType: 提供Java映射函数签名
 *      FunctionDescriptor: 提供函数签名
 *      upcall(): C ABI 调用 Java方法
 *      downcaall(): Java方法 调用 C ABI
 * MemorySegment: 连续内存
 *      ResourceScope: 堆外内存管理
 *      MemoryAddress: 内存地址
 *      MemoryAccess: 对外内存操作
 *
 * @author: pickjob@126.com
 * @date: 2022-11-30
 */
public class ForeignFunctionMemoryShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Throwable {
        MethodHandle strlen = CLinker.getInstance().downcallHandle(
                CLinker.systemLookup().lookup("strlen").get(),
                MethodType.methodType(int.class, MemoryAddress.class),
                FunctionDescriptor.of(CLinker.C_INT, CLinker.C_POINTER)
        );
        try (ResourceScope scope = ResourceScope.newConfinedScope()) {
            MemorySegment nativeStringAddress = CLinker.toCString("Hello World", scope);
            logger.info("strlen of Hello World is : {}", (int)strlen.invoke(nativeStringAddress.address()));
        }
    }
}
