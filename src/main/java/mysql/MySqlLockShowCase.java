package mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author pickjob@126.com
 * @time 2019-05-11
 **/
public class MySqlLockShowCase {
    private static final Logger logger = LogManager.getLogger(MySqlLockShowCase.class);

    public static void main(String[] args) throws Throwable {
        parepareTestTable();
        // Gap Lock
//        gapLock();
        // Next-Key Lock Show Case
//        nextKeyLock();
    }

    private static void gapLock() throws Throwable {
        String showTimeOutSql = "show variables like 'innodb_lock_wait_timeout'";
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(showTimeOutSql);
        while (rs.next()) {
            logger.info("{}: {}", rs.getString(1), rs.getInt(2));
        }
        CountDownLatch stage = new CountDownLatch(2);
        List<String> lockSqlList = new ArrayList<>();
        lockSqlList.add("update test set en = 'haha' where id > 5 and id < 7");
        lockSqlList.add("select sleep(60)");
        executeSqlInOneTransactionOneThread(lockSqlList, stage);
        Thread.sleep(1000);
        List<String> blockSqlList = new ArrayList<>();
        blockSqlList.add("insert into test(id, number, en) values (6, 7, 'senven')");
        executeSqlInOneTransactionOneThread(blockSqlList, stage);
        stage.await();
    }

    private static void nextKeyLock() throws Throwable {
        String showTimeOutSql = "show variables like 'innodb_lock_wait_timeout'";
        Connection connection = createConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(showTimeOutSql);
        while (rs.next()) {
            logger.info("{}: {}", rs.getString(1), rs.getInt(2));
        }
        CountDownLatch stage = new CountDownLatch(2);
        List<String> lockSqlList = new ArrayList<>();
        lockSqlList.add("update test set en = 'haha' where number = 3 ");
        lockSqlList.add("select sleep(60)");
        executeSqlInOneTransactionOneThread(lockSqlList, stage);
        Thread.sleep(1000);
        List<String> blockSqlList = new ArrayList<>();
        blockSqlList.add("insert into test(number, en) values (7, 'senven')");
        executeSqlInOneTransactionOneThread(blockSqlList, stage);
        stage.await();
    }

    private static void executeSqlInOneTransactionOneThread(List<String> sqlList, CountDownLatch stage) throws Throwable {
        new Thread(() -> {
            logger.info("{} start", Thread.currentThread().getName());
            Connection conn = null;
            try {
                conn = createConnection();
                conn.setAutoCommit(false);
                Statement stm = conn.createStatement();
                for (String sql : sqlList) {
                    stm.execute(sql);
                }
                conn.commit();
                conn.setAutoCommit(true);
            } catch (Throwable e) {
                logger.error(e.getMessage(), e);
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                stage.countDown();
            }
            logger.info("{} end", Thread.currentThread().getName());
        }).start();
    }

    private static void parepareTestTable() throws Throwable {
        Connection connection = createConnection();
        if (connection == null) return;
        String dropTableSql = "DROP TABLE IF EXISTS test";
        String createTableSql = "CREATE TABLE test( " +
                                    "id int NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                                    "number int NOT NULL, " +
                                    "en varchar(20) NOT NULL, " +
                                    "KEY idx_number(number) USING BTREE" +
                                    ")";
        String insertSql = "INSERT INTO test VALUES " +
                                "(1, 1, 'one'), " +
                                "(5, 3, 'three'), " +
                                "(7, 8, 'eight'), " +
                                "(11, 12, 'twelve')";
        connection.setAutoCommit(true);
        Statement statement = connection.createStatement();
        statement.execute(dropTableSql);
        statement.execute(createTableSql);
        statement.execute(insertSql);
        connection.close();
    }

    private static Connection createConnection() throws Throwable {
        return DriverManager.getConnection("jdbc:mysql://localhost/sample?useSSL=false", "china", "chinese");
    }
}
