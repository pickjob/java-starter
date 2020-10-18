package leetcode;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * @Author ws@yuan-mai.com
 * @Date 2020-10-16
 */
public class StringFindShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(StringFindShowCase.class);
    @Override
    public void showSomething() {
        logger.info(Arrays.toString(new StringFindShowCase().getNext("ABCDABD".toCharArray())));
    }

    @Override
    public boolean isShow() {
        return true;
    }

    public int BFFind(String targetStr, String patternStr){
        if (patternStr.length() == 0) return 0;
        char[] targetArray = targetStr.toCharArray();
        char[] patternArray = patternStr.toCharArray();
        for (int targetIdx = 0; targetIdx < targetArray.length - patternArray.length + 1; targetIdx++ ) {
            for (int patternIdx = 0; patternIdx < patternArray.length; patternIdx++) {
                if (targetArray[targetIdx + patternIdx] != patternArray[patternIdx]) {
                    break;
                } else if (patternIdx == patternArray.length -1) {
                    return targetIdx;
                }
            }
        }
        return -1;
    }


    public int KMPFind(String targetStr, String patternStr) {
        if (patternStr.length() == 0) return 0;
        char[] targetArray = targetStr.toCharArray();
        char[] patternArray = patternStr.toCharArray();
        int[] next = getNext(patternArray);
        for (int targetIdx = 0, patternIdx = 0; targetIdx < targetArray.length; targetIdx++) {
            while (patternIdx > 0 && targetArray[targetIdx] != patternArray[patternIdx]) {
                patternIdx = next[patternIdx - 1];
            }
            if (targetArray[targetIdx] == patternArray[patternIdx]) {
                patternIdx++;
            }
            if(patternIdx == patternArray.length){
                return targetIdx - patternIdx + 1;
            }
        }
        return -1;
    }

    public int[] getNext(char[] patternArray) {
        int[] next = new int[patternArray.length];
        for (int slow = 0, fast = 1; fast < patternArray.length; fast++) {
            while (slow > 0 && patternArray[slow] != patternArray[fast]) {
                slow = next[slow - 1];
            }
            if (patternArray[slow] == patternArray[fast]) {
                slow++;
            }
            next[fast] = slow;
        }
        return next;
    }
}
