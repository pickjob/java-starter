package app.features.jdk17;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.random.RandomGenerator;

/**
 * 随机数生成:
 *      接口:
 *          public interface RandomGenerator {
 *              long nextLong();
 *              // 一些 default 实现方法
 *              default nextInt();
 *              default nextFloat();
 *              default nextDouble();
 *              ...
 *              // 默认算法构造
 *              static RandomGenerator of(String name);
 *          }
 *      算法：
 *          L32X64MixRandom: 默认
 *          L32X64StarStarRandom
 *          L64X128MixRandom
 *          L64X128StarStarRandom
 *          L64X256MixRandom
 *          L64X1024MixRandom
 *          L128X128MixRandom
 *          L128X256MixRandom
 *          L128X1024MixRandom
 *
 * @author: pickjob@126.com
 * @date: 2024-09-02
 */
public class LXMRandomGeneratorsShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        RandomGenerator randomGenerator = RandomGenerator.getDefault();
        logger.info("Random long: {}", randomGenerator.nextLong());
    }
}