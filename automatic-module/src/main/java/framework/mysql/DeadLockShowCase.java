package framework.mysql;

import com.google.common.collect.ImmutableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author pickjob@126.com
 * @time 2019-06-20
 */
public class DeadLockShowCase extends MySqlShowCaseBase {
    private static final Logger logger = LogManager.getLogger(DeadLockShowCase.class);

    public static void main(String[] args) throws Throwable {
        deadLockCase1();
    }

    // 一种极其特殊的死锁情况。。。
    private static void deadLockCase1() throws Throwable {
        List<String> prepareTableSqls = ImmutableList.of(
                "DROP TABLE IF EXISTS test"
                ,"CREATE TABLE test(no  VARCHAR(20) PRIMARY KEY, idx int)"
        );
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        for (String sql : prepareTableSqls) {
            statement.execute(sql);
        }
        CountDownLatch stage = new CountDownLatch(2);
        List<String> lockSqlList = new ArrayList<>();
        lockSqlList.add("insert into test(no, idx) values ('5', 14)");
        lockSqlList.add("select sleep(2)");
        lockSqlList.add("insert into test(no, idx) values ('4', 17)");
        executeSqlInOneTransactionOneThread(lockSqlList, stage);
        Thread.sleep(800);
        List<String> blockSqlList = new ArrayList<>();
        blockSqlList.add("update test set idx = 31 where no = 5 ");
        executeSqlInOneTransactionOneThread(blockSqlList, stage);
        stage.await();
    }
}
