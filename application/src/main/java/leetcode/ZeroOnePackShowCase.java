package leetcode;

import app.common.IShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * @Author ws@yuan-mai.com
 * @Date 2020-10-14
 */
public class ZeroOnePackShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(ZeroOnePackShowCase.class);

    @Override
    public void showSomething() {
        logger.info("0-1背包");
        int N = 5;
        int V = 12;
        int[] C = {5, 4, 7 ,2 ,6};
        int[] W = {3, 12, 10, 3, 6};
        System.out.println(solutionA(N, V, C, W));
        System.out.println(solutionB(N, V, C, W));
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }

    // O(NV) 空间O(NV)
    private int solutionA(int N, int V, int[] C, int[] W) {
        int[][] F = new int[N + 1][V + 1];
        for(int n = 1; n <= N; n++) {
            for(int v = 0; v <= V; v++) {
                F[n][v] = F[n - 1][v];
                if(v >= C[n - 1]) {
                    F[n][v] = Math.max(F[n - 1][v], F[n - 1][v - C[n - 1]] + W[n - 1]);
                }
            }
        }
        return F[N][V];
    }
    // 时间O(NV) 空间O(V)
    private int solutionB(int N, int V, int[] C, int[] W) {
        int[] F = new int[V + 1];
        for(int n = 1; n <= N; n++) {
            for(int v = V; v >= C[n -1]; v--) {
                F[v] = Math.max(F[v], F[v - C[n - 1]] + W[n - 1]);
                logger.info("n: {}, v: {}, F[]: {}", n, v, Arrays.toString(F));
            }
        }
        return F[V];
    }
}
