package app.framework.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Statement;

/**
 * HikariCP
 *      dataSourceClassName:
 *          PostgreSQL: org.postgresql.ds.PGSimpleDataSource
 *          SQLite: org.sqlite.SQLiteDataSource
 *          SQL Server: com.microsoft.sqlserver.jdbc.SQLServerDataSource
 *          Oracle: oracle.jdbc.pool.OracleDataSource
 *      jdbcUrl: MySQL Using JdbcURL
 *          jdbc:framework.mysql://mysql/sample?autoReconnect=true&useUnicode=true&characterEncoding=utf8&useSSL=false
 *      dataSource.username=china
 *      dataSource.password=chinese
 *      dataSource.databaseName=database
 *      dataSource.portNumber=5432
 *      dataSource.serverName=localhost
 *      dataSource.connectionTimeout=30000
 *      dataSource.keepaliveTime=30000
 *      dataSource.idleTimeout=600000
 *      dataSource.maxLifetime=1800000
 *      dataSource.connectionTestQuery=SELECT 1
 *      dataSource.maximumPoolSize=10
 *      dataSource.autoCommit=true
 *      dataSource.poolName=hikari-datasource-pool
 *
 * @author: pickjob@126.com
 * @date: 2024-09-05
 */
public class HikariCPShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        HikariConfig config = new HikariConfig();
        config.setPoolName("SQLitePool");
        config.setDriverClassName("org.sqlite.JDBC");
        config.setJdbcUrl("jdbc:sqlite:sample.db");
        try (HikariDataSource dataSource = new HikariDataSource(config);
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
        ) {
            statement.execute("SELECT 1");
        }
    }
}