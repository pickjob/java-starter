package framework.reactive;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * RxJava: Reactive Stream、ReactiveX 实现
 *      io.reactivex.rxjava3.core.Observable<T>: 无背压流
 *          onSubscribe onNext* (onError | onComplete)?
 *          io.reactivex.rxjava3.observables.ConnectableObservable<T>
 *          io.reactivex.rxjava3.subjects.Subject<T>: 兼具 Observable Observer, 适配 Observable
 *              io.reactivex.rxjava3.subjects.AsyncSubject<T>: 只 emit 原 Observable 最后一个元素, 如果异常回调, 也异常回调
 *              io.reactivex.rxjava3.subjects.BehaviorSubject<T>： 只 emit 原 Observable 最近一个元素, 没有Emit 默认值, 如果异常回调, 也异常回调
 *              io.reactivex.rxjava3.subjects.PublishSubject<T>: emit 原 Observable emit 元素, 无 原 Observable 历史emit 元素
 *              io.reactivex.rxjava3.subjects.ReplaySubject<T>
 *      io.reactivex.rxjava3.core.Flowable<T>: Reactive Stream Publisher实现, 有背压
 *          onSubscribe onNext* (onError | onComplete)?
 *      io.reactivex.rxjava3.core.Maybe<T>: 懒计算, 只发送至多一个元素, 或者 没有, 异常
 *          onSubscribe (onSuccess | onError | onComplete)?
 *      io.reactivex.rxjava3.core.Single<T>: 只产生一个元素
 *          onSubscribe (onSuccess | onError)?
 *          io.reactivex.rxjava3.subjects.SingleSubject<T>
 *      io.reactivex.rxjava3.core.FlowableSubscriber<T>: 订阅者
 *          io.reactivex.rxjava3.subscribers.DefaultSubscriber<T>: request Long.MAX_VALUE
 *          io.reactivex.rxjava3.subscribers.DisposableSubscriber<T>: request 不设置, 主要用于外部停止
 *          io.reactivex.rxjava3.subscribers.ResourceSubscriber<T>: request 不设置, 主要用于内部资源销毁
 *      Operators: 操作函数
 *          Subscribe:
 *              onNext: 可观察元素 发布消息 回调
 *              onError: 失败回调
 *              onCompleted: 成功回调
 *          Transforming Observables: 一个 Observable 转为 另一个 Observable
 *              Map: 映射后 emit 元素
 *              FlatMap: 映射后 emit 多个元素
 *              GroupBy: 分组 emit 元素集合
 *              Scan: (x, y) -> x 立即Emit x
 *              Reduce: (x, y) -> x, Emit 最终 x
 *          Filtering Observables: 过滤
 *              Filter: 过滤
 *              Sample: 依据另一个 Observable emit 最近元素
 *          Limit Emit Item: 限制 Emit 元素
 *              First / Last / ElementAt: emit 指定次序元素
 *              Take / TakeLast: emit 指定个数元素
 *              Skip / SkipLast: 跳过指定个数元素
 *              TakeWhile: emit 所有元素, 直到符合某个条件
 *              SkipWhile: 丢弃所有 emit 元素, 直到符合某个条件
 *              TakeUntil: emit 所有元素, 直到另一个Observable emit 一个元素
 *              SkipUntil: 丢弃所有 emit 元素, 直到另一个Observable emit 一个元素
 *          Conditional and Boolean Operators:
 *              All: emit 所有符合条件元素
 *              Contains: 是否包含某个条件元素
 *              SequenceEqual: 两个序列是否一致
 *              Amb: emit 最早的Observable元素
 *          Mathematical and Aggregate Operators:
 *              Max / Min / Average: 最大 / 最小 / 算数平均数
 *              Count / Sum: 条数 / 求和
 *              Distinct: 去重元素
 *          Combining Observables: 组合操作
 *              Merge: 合并 多个 Observable, 按 Emit 顺序 合并
 *              Concat: 合并 多个 Observable, 一个 追加到 另一个后面
 *              Zip: 两个 Observable Emit 元素合并成一个元素 emit
 *              And/Then/When: 根据逻辑条件 合并
 *              Join: 根据另一个 Observable 元素 条件  合并
 *              CombineLatest: 根据最新元素 合并
 *              Serialize: 多个Observable 序列化
 *          Connectable Observable Operators:
 *              Publish: 普通 Observable 转为 Connectable Observable
 *              Connect: Connectable Observable 不 emit 元素, 除非 主动 connect
 *              RefCount: Connectable Observable 转为 普通 Observable
 *              Replay: 重放, emit 另一个 Observable emit 元素
 *              Switch
 *          Convert From / To Observables
 *              From: 其他类型对象、结构 转成 Observable
 *              To: Observable 转成 其他类型对象、结构
 *              Materialize/Dematerialize: 元素 和 回调通知 转换
 *          Backpressure Operators:
 *              Buffer: 缓冲, 合并多个成一个 一次 emit
 *              Window: 缓冲, 合并多个成Observable
 *          Observable Utility Operators:
 *              Debounce: 消除抖动
 *              IgnoreElements: 忽略所有元素
 *              DefaultIfEmpty: 为空则 emit 默认元素
 *              Delay: 延迟 emit 元素
 *              TimeInterval: Emit  原 Observable Emit元素耗时
 *              Timeout: 超时抛异常结束
 *              Timestamp: emit 元素 附着Timestamp
 *              Do: 包装 Observable
 *              Using: 资源自动释放
 *          Creating Observables: 创建 Observable
 *              Create: 创建 Observable
 *              Defer: 创建 Observable , 直到观察者订阅 emit 元素
 *              Just: 创建 Observable , 从其他类型数据、元素, 只 emit 一个元素
 *              Interval: 创建 Observable , 定时 emit 数字序列
 *              Timer: 创建 Observable , 延迟 emit 元素 然后结束
 *              Range: 创建 Observable , emit 指定区间数字序列
 *              Repeat: 创建 Observable , emit 重复元素
 *              Start: 创建 Observable , 使用函数 emit 元素
 *              Empty / Never / Throw: 特定流, 空, 能结束 / 空, 不会结束 / 空, 抛异常结束
 *          多线程切换:
 *              SubscribeOn: 切换调用之前的线程
 *                  只有第一subscribeOn 起作用
 *              ObserveOn: 调用调用之后的线程
 *                  ObserveOn之后, 不可再调用 SubscribeOn
 *          异常处理:
 *              Catch: 捕获 Observable 抛出异常
 *              Retry: 重新 subscribe Observable
 * @author: pickjob@126.com
 * @date: 2023-02-28
 */
public class RxJavaShowCase {
    private static final Logger logger = LogManager.getLogger();
}
