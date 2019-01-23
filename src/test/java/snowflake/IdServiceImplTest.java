package snowflake;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author pickjob@126.com
 * @time 2019-01-16
 */
class IdServiceImplTest {
    private static IdService idService ;

    @BeforeAll
    static void init() {
        idService = new IdServiceImpl();
    }

    @RepeatedTest(10000)
    @DisplayName("测试Id生成器同一秒生成情况")
    void testGenerateSeqId() {
        idService.generateSeqId();
    }
}