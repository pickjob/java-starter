package leetcode;

import app.utils.StringSupplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * @Author pickjob@126.com
 * @Date 2020-10-19
 */
class StringFindShowCaseTest {
    private static final Logger logger = LogManager.getLogger(StringFindShowCaseTest.class);
    private static final StringSupplier targetSupplier = new StringSupplier(200);
    private static final StringSupplier patterSupplier = new StringSupplier(3);

    private StringFindShowCase stringFindShowCase = new StringFindShowCase();

    @Disabled
    @ParameterizedTest
    @MethodSource("stringProvider")
    public void BFFind(String targetString, String pattern) {
        int bfResult = stringFindShowCase.BFFind(targetString, pattern);
        int result = targetString.indexOf(pattern);
        logger.info("target: {}, pattern: {}, bfResult: {}, result: {}", targetString, pattern, bfResult, result);
        Assertions.assertEquals(bfResult, result);
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
    public void KMPFind(String targetString, String pattern) {
        int kmpResult = stringFindShowCase.KMPFind(targetString, pattern);
        int result = targetString.indexOf(pattern);
        logger.info("target: {}, pattern: {}, kmpResult: {}, result: {}", targetString, pattern, kmpResult, result);
        Assertions.assertEquals(kmpResult, result);
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
    void SundayFind(String targetString, String pattern) {
        int sundayResult = stringFindShowCase.SundayFind(targetString, pattern);
        int result = targetString.indexOf(pattern);
        logger.info("target: {}, pattern: {}, sundayResult: {}, result: {}", targetString, pattern, sundayResult, result);
        Assertions.assertEquals(sundayResult, result);
    }

    static Stream<String[]> stringProvider() {
        Stream.Builder<String[]> builder = Stream.<String[]>builder();
        for (int i = 0; i < 100000; i++) {
            builder.add(new String[]{targetSupplier.get().toUpperCase(), patterSupplier.get().toUpperCase()});
        }
        return builder.build();
    }
}