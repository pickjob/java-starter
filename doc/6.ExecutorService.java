/**
 * java.util.concurrent.Executor(最简单的任务调度)
 *      java.util.concurrent.ExecutorService(添加任务管理: 终止 / 追踪 )
 *          java.util.concurrent.ThreadPoolExecutor(线程池)
 *          java.util.concurrent.ScheduledThreadPoolExecutor(定时器线程池)
 *          java.util.concurrent.ForkJoinPool(ForkJoinTask线程池)
 *      java.util.concurrent.ScheduledExecutorService(添加定时任务执行)
 *          java.util.concurrent.ScheduledThreadPoolExecutor(定时器线程池)
 * Executor: 核心接口
 *      // Throws: RejectedExecutionException
 *      void execute(Runnable command);
 * ExecutorService: 任务生命周期管理
 *      // 已提交执行完成, 拒绝再提交的任务
 *      void                shutdown();
 *      // 等待所有任务结束, shutdown()一起使用
 *      boolean             awaitTermination(long timeout, TimeUnit unit) throws InterruptedException;
 *      // 立刻结束
 *      List<Runnable>      shutdownNow();
 *      // 任务提交
 *      <T> Future<T>       submit(Callable<T> task);
 *      <T> Future<T>       submit(Runnable task, T result);
 *      Future<?>           submit(Runnable task);
 *      // 返回所有任务Future, 均已完成(无论执行成功还是抛异常)
 *      <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException;
 *      <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException;
 *      // 返回一个任务Future, 已完成
 *      <T> T               invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException;
 *      <T> T               invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException,ExecutionException, TimeoutException;
 *      // 状态检查
 *      boolean             isShutdown();
 *      boolean             isTerminated();
 * ScheduledExecutorService: 定时任务
 *      public ScheduledFuture<?>     schedule(Runnable command, long delay, TimeUnit unit);
 *      public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit);
 *      public ScheduledFuture<?>     scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit);
 *      public ScheduledFuture<?>     scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit);
 * 线程池设计逻辑:
 *      Core and maximum pool sizes: 核心、最大线程数
 *      On-demand construction: 按需创建线程, 预先创建线程( prestartCoreThread / prestartAllCoreThreads)
 *      Creating new threads: 创建线程
 *      Keep-alive times: 存活时间(setKeepAliveTime), 默认当超过核心线程数起作用(allowCoreThreadTimeOut
 *      Queuing: (BlockingQueue)
 *          正在运行线程数小于核心线程数, 优先新增线程而不是入队
 *          正在运行线程数大于核心线程数, 优先入队而不是新增线程
 *          任务不能入队, 新增线程(直到超过最大线程数, 抛异常)
 *          入队策略:
 *              Direct handoffs(SynchronousQueue): 队列容量只有一
 *                  Bounded queue(ArrayBlockingQueue): 队列容量固定
 *                  Unbounded queue(LinkedBlockingQueue): 队列容量不限制(Integer.MAX_VALUE)
 *          Rejected tasks(已关闭或者超限): 调用RejectedExecutionHandler.rejectedExecution
 *              ThreadPoolExecutor.AbortPolicy: 直接抛出异常(RejectedExecutionException)
 *              ThreadPoolExecutor.CallerRunsPolicy: 调用者线程执行
 *              ThreadPoolExecutor.DiscardPolicy: 丢弃任务
 *              ThreadPoolExecutor.DiscardOldestPolicy: 丢弃醉倒的任务
 *          Hook methods: Hook方法
 *              beforeExecute(Thread, Runnable)
 *              afterExecute(Runnable, Throwable)
 *          Queue maintenance
 */