package basic.jcf;

import basic.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author pickjob@126.com
 * @time 2019-04-23
 */
public class ArrayListSpliteratorShowCase implements IShowCase, Consumer<String> {
    private static final Logger logger = LogManager.getLogger(ArrayListSpliteratorShowCase.class);
    private AtomicInteger idx = new AtomicInteger();

    @Override
    public void saySomething() {
        logger.info("ArrayListSpliterator分割策略(一半一半), trySpliterator分割的永远是现有的一半或太小不能分割");
    }

    @Override
    public void showSomething() {
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
        Spliterator arrayListSpliterator = arrayList.spliterator();
        Spliterator lowHalfPiece = arrayListSpliterator.trySplit();
        Spliterator hiLowHalfPiece = arrayListSpliterator.trySplit();
        logger.info("lowHalfPiece:");
        lowHalfPiece.tryAdvance(this);
        lowHalfPiece.forEachRemaining(this);
        logger.info("hiLowHalfPiece");
        hiLowHalfPiece.forEachRemaining(this);
        logger.info("arrayListSpliterator");
        arrayListSpliterator.forEachRemaining(this);

    }

    @Override
    public void accept(String s) {
        logger.info("idx: {}, word: {}", idx.addAndGet(1), s);
    }
}
