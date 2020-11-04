package leetcode;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author pickjob@126.com
 * @Date 2020-10-26
 */
public class DecodeStringShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(DecodeStringShowCase.class);

    @Override
    public void showSomething() {
        for (String s : Arrays.asList("3[a]2[bc]", "3[a2[c]]", "2[abc]3[cd]ef", "abc3[cd]xyz", "100[leetcode]", "3[a]2[b4[F]c]")) {
            logger.info("{} ==> {}", s, decodeString(s));
        }
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }

    private String decodeString(String s) {
        String result = "";
        String piece = "";
        Deque<String> deque = new LinkedList<>();
        for (char c : s.toCharArray()) {
            if (c == '[' ) {
                deque.offerLast(piece);
                piece = "";
            } else if (c != ']') {
                piece += c;
            } else {
                String pre = deque.pollLast();
                char[] preCharArray = pre.toCharArray();
                String numStr = null;
                String preStr = "";
                for (int i = preCharArray.length - 1; i > -1; i--) {
                    if (preCharArray[i] >='0' && preCharArray[i] <= '9') {
                        if (i == 0) {
                            numStr = pre;
                        }
                    } else {
                        numStr = pre.substring(i + 1);
                        preStr = pre.substring(0, i + 1);
                        break;
                    }
                }
                String tmp = "";
                for (int i = 0; i < Integer.valueOf(numStr); i++) {
                    tmp += piece;
                }
                tmp = preStr + tmp;
                if (deque.size() == 0) {
                    result += tmp;
                    piece = "";
                } else {
                    piece = tmp;
                }
            }
        }
        if (!"".equals(piece)) {
            result += piece;
        }
        return result;
    }

    public int[][] updateMatrix(int[][] matrix) {
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        Deque<List<Integer>> deque = new LinkedList<>();
        for (int row = 0; row < rowLen; row++) {
            for (int col = 0; col < colLen; col++) {
                if (matrix[row][col] == 1) {
                    int level = -1;
                    deque.offer(Arrays.asList(row, col));
                    while (deque.size() > 0) {
                        int curSize = deque.size();
                        level++;
                        for (int i = 0; i < curSize; i++) {
                            List<Integer> item = deque.poll();
                            int r = item.get(0);
                            int c = item.get(0);
                            if (matrix[r][c] == 0) {
                                deque.clear();
                                break;
                            }
                            if (r > 0) {
                                deque.offer(Arrays.asList(r - 1, c));
                            }
                            if (r < rowLen - 2) {
                                deque.offer(Arrays.asList(r + 1, c));
                            }
                            if (c > 0) {
                                deque.offer(Arrays.asList(r, c - 1));
                            }
                            if (c < colLen - 2) {
                                deque.offer(Arrays.asList(r, c + 1));
                            }
                        }
                    }
                    matrix[row][col] = level;
                }
            }
        }
        return matrix;
    }
}
