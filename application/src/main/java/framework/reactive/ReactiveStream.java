package framework.reactive;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Reactive Stream: 异步非阻塞背压流处理标准
 *      Publisher: 发布者, 可能无边界
 *          public interface Publisher<T> {
 *              public void subscribe(Subscriber<? super T> s);
 *          }
 *          订阅后周期收到回调顺序: onSubscribe onNext* (onError | onComplete)?
 *      Subscriber: 订阅者
 *          public interface Subscriber<T> {
 *              public void onSubscribe(Subscription s);
 *              public void onNext(T t);
 *              public void onError(Throwable t);
 *              public void onComplete();
 *          }
 *          调用Subscription.request、Subscription.cancel 告知处理上限 backpressure
 *          Subscription:
 *              public interface Subscription {
 *                  public void request(long n);
 *                  public void cancel();
 *              }
 *          Processor
 *              public interface Processor<T, R> extends Subscriber<T>, Publisher<R> {
 *              }
 *
 * @author: pickjob@126.com
 * @date: 2023-03-02
 */
public class ReactiveStream {
    private static final Logger logger = LogManager.getLogger();
}
