package framework.guava;

import com.google.common.collect.Range;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Guava Range:
 *      (a..b)    open(C, C)
 *      [a..b]    closed(C, C)
 *      [a..b)    closedOpen(C, C)
 *      (a..b]    openClosed(C, C)
 *      (a..+∞)    greaterThan(C)
 *      [a..+∞)    atLeast(C)
 *      (-∞..b)    lessThan(C)
 *      (-∞..b]    atMost(C)
 *      (-∞..+∞)    all()
 *      支持操作:
 *          contains(C): bool 包含
 *          encloses(Range): bool 包含Range
 *          span(Range<C> other): Range<C> 并集
 *          intersection(Range<C> other): Range<C>交集
 *
 * @author: pickjob@126.com
 * @date: 2022-12-05
 */
public class RangeShowCase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        Range<Integer> range1 = Range.open(10, 15);
        Range<Integer> range2 = Range.closed(13, 17);
        logger.info("(10, 15) ∪  [13, 17] : {}", range1.span(range2));
        logger.info("(10, 15) ∩  [13, 17] : {}", range1.intersection(range2));
    }
}
