package test.leetcode;

import app.utils.StringSupplier;
import leetcode.StringFindShowCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * @Author ws@yuan-mai.com
 * @Date 2020-10-16
 */
public class KmpShowCaseTest {
    private static final Logger logger = LogManager.getLogger(KmpShowCaseTest.class);
    private static final StringSupplier targetSupplier = new StringSupplier(200);
    private static final StringSupplier patterSupplier = new StringSupplier(3);

    private StringFindShowCase kmpShowCase = new StringFindShowCase();

    @Disabled
    @ParameterizedTest
    @MethodSource("stringProvider")
    public void testBFFind (String targetString, String pattern) {
        int bfResult = kmpShowCase.BFFind(targetString, pattern);
        int result = targetString.indexOf(pattern);
        logger.info("target: {}, pattern: {}, bfResult: {}, result: {}", targetString, pattern, bfResult, result);
        Assertions.assertEquals(bfResult, result);
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
    public void testKMPFind (String targetString, String pattern) {
        int kmpResult = kmpShowCase.KMPFind(targetString, pattern);
        int result = targetString.indexOf(pattern);
        logger.info("target: {}, pattern: {}, kmpResult: {}, result: {}", targetString, pattern, kmpResult, result);
        Assertions.assertEquals(kmpResult, result);
    }

    static Stream<String[]> stringProvider() {
        Stream.Builder<String[]> builder = Stream.<String[]>builder();
        for (int i = 0; i < 100000; i++) {
            builder.add(new String[]{targetSupplier.get().toUpperCase(), patterSupplier.get().toUpperCase()});
        }
        return builder.build();
    }
}
