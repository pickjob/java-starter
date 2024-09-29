/**
 * Java Collection Framework
 *      java.util.Collection: 集合
 *          java.util.List: 有序集合
 *              java.util.ArrayList
 *              java.util.LinkedList
 *              java.util.Vector: synchronized线程安全
 *              java.util.Stack: synchronized线程安全
 *              java.util.concurrent.CopyOnWriteArrayList: 写保护读不保护
 *          java.util.Set: 去重集合
 *              java.util.HashSet: HashMap实现
 *              java.util.LinkedHashSet
 *              java.util.EnumSet: 抽象类, 基本依赖静态方法自实现
 *              java.util.concurrent.CopyOnWriteArraySet: CopyOnwriteArrayList实现
 *              java.util.SortedSet
 *                  java.util.NavigableSet
 *                      java.util.TreeSet: TreeMap实现
 *                      java.util.concurrent.ConcurrentSkipListSet
 *         java.util.Queue: 队列,异常或特殊值
 *              java.util.PriorityQueue: 无边界队列, 不支持null
 *              java.util.concurrent.ConcurrentLinkedQueue: 无边界队列, 线程安全, CAS实现
 *              java.util.concurrent.BlockingQueue: 队列,永久等待或超时
 *                  java.util.concurrent.ArrayBlockingQueue: 有边界, Lock、Condition实现
 *                  java.util.concurrent.LinkedBlockingQueue: 可选边界, Lock、Condition实现
 *                  java.util.concurrent.DelayQueue: 无边界队列, 过期的元素才能被移除
 *                  java.util.concurrent.SynchronousQueue: 存取一致队列
 *                  java.util.concurrent.PriorityBlockingQueue: 无边界队列, 加锁排序
 *
 *              java.util.concurrent.BlockingDeque
 *                  java.util.concurrent.LinkedBlockingDeque: 可选边界, Lock、Condition实现
 *
 *              java.util.concurrent.TransferQueue
 *                  java.util.concurrent.LinkedTransferQueue: 无边界队列, SynchronousQueue类似
 *      java.util.Deque: 双端队列
 *          java.util.ArrayDeque: 无边界, 不支持null元素, tail从头开始, head从尾开始, 中间为预留空间
 *          java.util.LinkedList: 无边界, 支持null元素, Node链表实现
 *          java.util.concurrent.ConcurrentLinkedDeque: 无边界
 *
 *          java.util.concurrent.BlockingDeque
 *              java.util.concurrent.LinkedBlockingDeque: 可选边界, Lock、Condition实现
 *      java.util.Map(键值对)
 *          java.util.HashMap(支持K、V为null)
 *          java.util.LinkedHashmap(LRU jdk实现)
 *          java.util.EnumMap(Key为enum, 不支持K为null, 支持V为null)
 *          java.util.WeakHashMap
 *          java.util.Hashtable(synchronized保证并发性, 不支持K、V为null)
 *
 *          java.util.SortedMap
 *              java.util.NavigableMap
 *              java.util.TreeMap(红黑树)
 *
 *          java.util.concurrent.ConcurrentMap
 *              java.util.concurrent.ConcurrentHashMap(与hashtable类似, 不支持K、V为null)
 *
 *          java.util.concurrent.ConcurrentNavigableMap
 *              java.util.concurrent.ConcurrentSkipListMap
 */