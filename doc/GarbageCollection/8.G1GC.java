/**
 * G1GC: 低延迟、高吞吐新生代、老年代垃圾回收器
 *      开启参数
 *          -XX:+UseG1GC
 *      清理过程
 *          全局并发标记(Concurrent Marking)
 *              初始标记(Initial Marking)
 *              STW, 速度快
 *              根区域扫描(Root Region Scanning)
 *          并发标记(Concurrent Marking)
 *              慢、耗时
 *          最终标记(Final Marking, Remark)
 *              STW, 速度快
 *          清理(Cleanup) / 混合回收
 *              STW
 *              失败会进入 Serial回收
 *      特点
 *          Region: 内存布局改为一块一块
 *              Eden Region
 *              Survivor Region
 *              Old Region
 *              Free Region
 *              Humongous Region: 超大内存块
 *          STAB: 维持并发GC正确性
 *      GC
 *          Young GC: 新生代
 *          Mix GC: 新生代 + 老年代
 *     其他相关参数
 *          -XX:InitiatingHeapOccupancyPercent: 老年代站用百分比触发混合回收, 默认45%
 *          -XX:G1HeapRegionSize: 设置每个Region的大小, 值是2的幂, 范围是1MB到32MB之间, 目标是根据最小的Java堆大小划分出约2048个区域,
 *           默认是堆内存的1/2048
 *          -XX:G1ReservePercent=percent: 保留堆占比, 达到会触发标记周期, 默认10%
 *          -XX:ParallelGCThreads=N: GCRoot线程数
 *          -XX:ConcGCThreads=N: 并发标记线程数
 *          -XX:MaxGCPauseMillis=time: 设置期望达到的最大GC停顿时间, 默认值是200ms
 *          -XX:MaxTenuringThreshold=N: 晋升的阈值, 默认是15
 *          -XX:G1OldCSetRegionThresholdPercent=percent: G1老年代
 *          -XX:G1HeapWastePercent=percent: 混合回收释放Region占比空间 , 默认5%
 *          -XX:G1MixedGCCountTarget=number: 混合回收后运行再次STW回收次数, 默认为8
 *          -XX:G1MixedGCLiveThresholdPercent=percent: 存活对象低于85%开始MixedGC
 *     不推荐设置
 *          -Xmn size: 不要设置Young Generation大小
 */