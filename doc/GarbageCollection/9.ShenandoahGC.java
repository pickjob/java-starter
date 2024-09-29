/**
 * ShenandoahGC: 高吞吐、低延迟垃圾回收器(G1优化)
 *      开启参数
 *          -XX:+UseShenandoahGC
 *      清理过程
 *          初始标记(Initial Mark)
 *              STW, 短
 *          并发标记(Concurrent Marking)
 *          最终标记(Final Mark)
 *              STW, 短
 *          并发清理(Concurrent Cleanup)
 *          并发移除(Concurrent Evacuation)
 *          初始化更新引用(Init Update Refs)
 *              STW, 短
 *          并发更新引用(Concurrent Update References)
 *          最终更新引用(Final Update Refs)
 *          并发清理(Concurrent Cleanup)
 *      特点
 *          BrooksPointers: 转发指针
 *              访问对象时, 速度会更慢
 *              需要更多的空间存储多出来的这根指针
 *      相关参数
 *          -XX:ShenandoahGCMode=<name>
 *              normal
 *              iu
 *              passive
 *          -XX:ShenandoahGCHeuristics=<name>
 *              adaptive: 自适应
 *              static
 *              compact
 *              aggressive
 */