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

    @RepeatedTest(100)
    @DisplayName("测试Id生成器同一秒生成情况")
    void testGenerateSeqId() {
        long id1 = idService.generateSeqId();
        long id2 = idService.generateSeqId();
        long id3 = idService.generateSeqId();
        assertNotEquals(id1, id2);
        assertNotEquals(id2, id3);
    }
}