/**
 * ParNewGC: 新生代多线程垃圾回收器(Serial多线程版本, Deprecated@JDK9)
 *      开启参数
 *          -XX:+UseParNewGC
 *      垃圾回收算法
 *          复制算法
 *      特点
 *          并发回收, 回收期间工作线程仍处于暂停状态
 */