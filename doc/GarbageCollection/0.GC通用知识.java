/**
 * Java程序内存分类
 *      JVM运行时数据区(Runtime Data Area)
 *          程序计数器(Program Counter Register): 线程私有, 代表线程执行字节码行号指示器, 无OOM
 *          Java虚拟机栈(Java Virtual Machine Stack): 线程私有, 函数栈帧(局部变量、动态链接、方法...), StackOverflowError, OutOfMemoryError
 *          本地方法栈(Native Method Stack): Native方法函数栈(局部变量、动态链接、方法...), StackOverflowError, OutOfMemoryError
 *          方法区(Method Area): 线程共享, No-Heap(非堆) OutOfMemoryError
 *              类信息
 *              常量
 *              静态变量
 *              即时编译器编译后代码
 *              运行时常量池(Runtime Constant Pool)
 *          堆(Heap): 线程共享, OutOfMemoryError
 *      直接内存(Direct Memory), OutOfMemoryError
 *      STW(Stop The World): 所有Java线程停止运行
 *      安全点(SafePoint): OopMap生成位置, 到达才能GC
 *      抢先式中断(Preemptive Suspension)
 *          GC控制, 未达到安全点就让线程继续执行(无)
 *      主动式中断(Voluntary Suspension)
 *          Java线程主动轮询(分配内存)
 *      对象存活性判断
 *          引用计数(Reference Counting)
 *              强引用
 *              软引用(SoftReference): 内存溢出异常前会回收
 *              弱引用(WeakReference): 下一次垃圾回收发生前
 *              虚引用(PhantomReference): 垃圾回收对象收到通知
 *          可达性分析
 *              GC ROOT
 *                  虚拟机栈中引用的对象(本地变量表)
 *                  方法区中静态属性引用的对象
 *                  方法区中常亮引用的对象
 *                  本地方法栈中引用的对象(Native对象)
 *                  OopMap
 *  垃圾收集算法
 *      标记-清除(Mark-Sweep)
 *          阶段
 *              标记
 *              清除
 *         特性
 *             劣势:标记、清除效率低
 *             劣势:空间碎片多
 *      复制(Copying)
 *          原理
 *              内存分为两个区, 复制存活的对象到空闲的另一个区域
 *          特性
 *              优势: 长期存活对象少时高效
 *              劣势: 牺牲一半内存
 *              适用场景: 对象存活率非常低、内存允许50%浪费
 *              优化: 分区域, 大量对象存活率低, 只需预留少数空间
 *                  Eden 80%
 *                  Survivor 20%
 *                      From Survivor 10%
 *                      To Survivor 10% (预留空间)
 *      标记-整理(Mark-Compact)
 *          标记
 *          整理
 *      分代(Generational Collection)
 *          新生代(Young generation) 1 / 3
 *              Eden 80%
 *              Survivor 20%
 *              From Survivor 10%
 *              To Survivor 10%
 *          老年代(Old generation) 2 / 3
 *      增量(Incremental Collection)
 * 垃圾回收器(GarbageCollection)
 *      新生代GC
 *          SerialGC: 新生代串行垃圾回收器
 *          ParNewGC: 新生代多线程垃圾回收器(Serial多线程版本, Deprecated@JDK9)
 *          ParallelScavengeGC: 新生代多线程垃圾回收器(吞吐量优先)
 *          G1New(G1专用)
 *      老年代GC
 *          SerialOldGC: 老年代串行垃圾回收器
 *          ParallelOldGC: 老年代多线程垃圾回收器(吞吐量优先)
 *          ConcurrentMarkSweepGC: 老年代多线程垃圾回收器(Deprecated@JDK9)
 *          G1Old(G1专用): 高吞吐垃圾回收器
 *      不分代GC
 *          EpsilonGC: No-op GC, 只分配内存, 不回收内存
 *          ShenandoahGC: 高吞吐、低延迟垃圾回收器(G1优化)
 *          ZGC: 低延迟、高吞吐垃圾回收器(延迟更低)
 *      常见组合
 *          SerialGC + SerialOldGC
 *          ParallelScavengeGC + ParallelOldGC
 *          ParNewGC + ConcurrentMarkSweepGC
 *          G1New + G1Old
 *          ShenandoahGC
 *          ZGC
 *      GC分类
 *          Partial GC: 不完整搜集整个Java对垃圾收集
 *              Yong GC / Minor GC: 只收集Yong Gen
 *              Old Gc / Major GC: 只收集Old Gen
 *              MixedGC: 整个Young Gen & 部分 Old Gen(G1特有)
 *          Full GC:
 *              整个Java对和方法垃圾收集
 *          触发机制
 *              Eden区空间不够, 触发Minor GC
 *              老年代空间不够, 触发Full GC
 *      指标
 *          内存占用(Footprint)
 *          吞吐量(Throughput)
 *          延迟(Latency)
 */