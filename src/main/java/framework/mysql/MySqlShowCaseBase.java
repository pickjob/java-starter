package framework.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author pickjob@126.com
 * @time 2019-06-20
 */
public class MySqlShowCaseBase {
    private static final Logger logger = LogManager.getLogger(MySqlShowCaseBase.class);

    protected static void executeSqlInOneTransactionOneThread(List<String> sqlList, CountDownLatch stage) throws Throwable {
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

    protected static Connection createConnection() throws Throwable {
        return DriverManager.getConnection("jdbc:framework.mysql://localhost/sample?autoReconnect=true&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true", "china", "chinese");
    }
}
