package app.standard.jcf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Spliterator：分割器
 *      tryAdvance(Consumer<? super T> action): boolean 仍有剩余元素, 用Consumer消费, 返回 true, 否则返回false, 一次消费一个元素
 *      forEachRemaining(Consumer<? super T> action): void 消费所有剩余元素
 *      trySplit(): Spliterator<T> 分割剩余元素
 *
 * @author: pickjob@126.com
 * @date: 2024-09-10
 */
public class ArrayListSpliteratorShowcase {
    private static final Logger logger = LogManager.getLogger();
    private AtomicInteger idx = new AtomicInteger();

    public static void main(String[] args) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("hello");
        arrayList.add("world");
        arrayList.add("work");
        arrayList.add("hard");
        arrayList.add("996");
        arrayList.add("fuck");
        arrayList.add("xxxx");
        arrayList.add("dark");
        arrayList.add("kk");
        Consumer<String> consumer = null;
        Spliterator allSpliterator = arrayList.spliterator();
        consumer = s -> logger.info("All splitor consume word: {}", s);
        allSpliterator.tryAdvance(consumer);
        Spliterator lowHalfPiece = allSpliterator.trySplit();
        consumer = s -> logger.info("Low splitor consume word: {}", s);
        lowHalfPiece.forEachRemaining(consumer);
        Spliterator hiLowHalfPiece = allSpliterator.trySplit();
        consumer = s -> logger.info("High splitor consume word: {}", s);
        hiLowHalfPiece.forEachRemaining(consumer);
        allSpliterator.forEachRemaining(consumer);
    }
}