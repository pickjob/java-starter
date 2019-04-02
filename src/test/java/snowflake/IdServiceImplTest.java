package snowflake;

import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author pickjob@126.com
 * @time 2019-01-16
 */
class IdServiceImplTest {
    private static final int TOTAL = 1000000;
    private IdService idService ;
    private Set<Long> holder;

    @BeforeAll
    void init() {
        idService = new IdServiceImpl();
        holder = new HashSet<>();
    }

    @DisplayName("测试Id生成器同一秒生成情况")
    void testGenerateSeqId() {
        for (int i = 0; i < TOTAL; i++) {
            holder.add(idService.generateSeqId());
        }
        assert holder.size() == TOTAL;
    }
}