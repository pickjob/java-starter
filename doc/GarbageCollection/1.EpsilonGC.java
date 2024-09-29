/**
 * EpsilonGC: No-op GC, 只分配内存, 不回收内存
 *      开启参数
 *          -XX:+UnlockExperimentalVMOptions
 *          -XX:+UseEpsilonGC
 *      特点
 *          只分配内存, 不回收内存, JVM最小实现
 */