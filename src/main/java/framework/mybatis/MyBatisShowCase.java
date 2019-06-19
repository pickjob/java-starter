package framework.mybatis;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

/**
 * @author pickjob@126.com
 * @time 2019-06-18
 */
public class MyBatisShowCase {
    private static final Logger logger = LogManager.getLogger(MyBatisShowCase.class);

    public static void main(String[] args) throws Exception {
        DataSource dataSource = createDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.getTypeAliasRegistry().registerAlias(Order.class);
        configuration.addMapper(OrderMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List orders = sqlSession.selectList("framework.mybatis.OrderMapper.selectOrders");
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        logger.info("orders: {}", orderMapper.selectOrders());
    }

    private static DataSource createDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername("china");
        dataSource.setPassword("chinese");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost/sample?autoReconnect=true&useUnicode=true&characterEncoding=utf8&useSSL=false");
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
