/**
 * ConcurrentMarkSweepGC: 老年代多线程垃圾回收器(Deprecated@JDK9)
 *      开启参数
 *          -XX:+UseConcMarkSweepGC
 *      垃圾回收算法
 *          标记-清理算法
 *      回收流程
 *          初始标记(STW initial mark)
 *              STW
 *          并发标记(Concurrent marking)
 *              并发预清理(Concurrent precleaning)
 *              可中止的并发预清理(Concurrent Abortable Preclean)
 *          重新标记(remark)
 *              STW(耗时更久)
 *          并发清理(Concurrent sweeping)
 *              并发重置(Concurrent reset)
 *      特点
 *          低延迟优先于吞吐
 *          内存容易碎片, 更容易触发FULL GC
 *          **CPU资源需求更高**
 *          堆空间需求更高
 *      其他相关参数
 *          -XX:ConcGCThreads=threads: 指定并发线程数
 *          -XX:+UseCMSCompactAtFullCollection: 合并碎片, CPU占用会更高
 *          -XX:+CMSParallelRemarkEnabled: 减少第二次暂停时间
 *          -XX:+CMSPermGenSweepingEnabled -XX:+CMSClassUnloadingEnabled: 回收Perm区
 *          -XX:CMSinitiatingOccupancyFraction=n: 老年代占比, 默认68%
 *          -XX:ParallelCMSThreads=N: 回收线程数
 */