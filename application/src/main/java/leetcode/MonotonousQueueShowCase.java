package leetcode;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @Author pickjob@126.com
 * @Date 2020-11-02
 */
public class MonotonousQueueShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(MonotonousQueueShowCase.class);

    @Override
    public void showSomething() {
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7 };
        int k = 3;
        logger.info(Arrays.toString(maxSlidingWindow(nums, k)));
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }

    private int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> deque = new LinkedList<>();
        int[] result = new int[nums.length - k + 1];
        for (int i = 0; i < k; i++) {
            if (deque.peekLast() != null) {
                while (deque.size() > 0 && nums[deque.peekLast()] < nums[i]) {
                    deque.pollLast();
                }
            }
            deque.addLast(i);
        }
        result[0] = nums[deque.peekFirst()];
        for (int i = 1; i < nums.length - k + 1; i++) {
            if (deque.size() > 0 && deque.peekFirst() < i ) {
                deque.pollFirst();
            }
            if (deque.peekLast() != null) {
                while (deque.size() > 0 && nums[deque.peekLast()] < nums[(i + k - 1)]) {
                    deque.pollLast();
                }
            }
            deque.addLast((i + k - 1));
            result[i] = nums[deque.peekFirst()];
        }
        return result;
    }
}
