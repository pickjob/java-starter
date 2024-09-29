/**
 * ZGC: 低延迟、高吞吐垃圾回收器(延迟更低)
 *      开启参数
 *          -XX:+UseZGC
 *      特点
 *          Region, 不分代
 *              Small Region: 2M, Object < 256KB
 *              Medium Region: 32M Object [256KB, 4MB)
 *              Large Region: 容量不固定 >= 4M
 *          读屏障、染色指针、内存多重映射
 *          低延迟优先
 *      清理过程
 *          并发标记(Concurrent Mark)
 *              STW, 可达性分析
 *              标记染色指针上
 *          并发预备重分配(Concurrent Prepare for Relocate)
 *          并发重分配(Concurrent Relocate)
 *          并发重映射(Concurrent Remap)
 *      触发机制
 *          定时触发: -XX:ZCollectionInterval
 *          预热触发, 最多三次, 在堆内存达到10%、20%、30%时触发
 *          分配速率, 基于正态分布统计, 计算内存99.9%可能的最大分配速率, 以及此速率下内存将要耗尽的时间点,在耗尽之前触发GC
 *          主动触发, -XX:ZProactive 默认开启, 堆内存增长10% 5分钟
 *      相关参数
 *          -XX:ZAllocationSpikeTolerance=factor: 触发自适应算法修正系数, 越大越早触发GC, 默认2.0
 *          -XX:ZCollectionInterval=seconds: 两次GC最大间隔, 默认0(关闭)
 *          -XX:ZFragmentationLimit=percent: 设置最大回收碎片占比, 默认25%
 *          -XX:ZProactive
 *          -XX:ZUncommit: 未使用内存会还给系统
 *          -XX:ZUncommitDelay=seconds: 未使用内存会延迟还给系统(分步), 默认300s
 */