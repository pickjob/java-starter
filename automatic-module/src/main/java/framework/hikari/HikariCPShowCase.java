package framework.hikari;

import app.common.IShowCase;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author pickjob@126.com
 * @time 2019-06-18
 */
public class HikariCPShowCase implements IShowCase {
    private static final Logger logger = LogManager.getLogger(HikariCPShowCase.class);

    @Override
    public void showSomething() {
        logger.info("展示Hikari数据源配置示例");
        createDataSource();
    }

//    @Override
//    public boolean isShow() {
//        return true;
//    }

    private DataSource createDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername("china");
        dataSource.setPassword("chinese");
        dataSource.setDriverClassName("com.framework.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:framework.mysql://mysql/sample?autoReconnect=true&useUnicode=true&characterEncoding=utf8&useSSL=false");
        Properties properties = new Properties();
        properties.put("cachePrepStmts", true);
        properties.put("prepStmtCacheSize", 250);
        properties.put("prepStmtCacheSqlLimit", 2048);
        properties.put("useServerPrepStmts", true);
        properties.put("useLocalSessionState", true);
        properties.put("rewriteBatchedStatements", true);
        properties.put("cacheResultSetMetadata", true);
        properties.put("cacheServerConfiguration", true);
        properties.put("elideSetAutoCommits", true);
        properties.put("maintainTimeStats", false);
        dataSource.setDataSourceProperties(properties);
        return dataSource;
    }
}
