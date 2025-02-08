package app.framework.mybatis;

import app.framework.mybatis.domain.Person;
import app.framework.mybatis.mapper.PersonMapper;
import com.zaxxer.hikari.HikariConfig;
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

import java.sql.Connection;
import java.sql.Statement;

/**
 * MyBatis: a first class persistence framework
 *      SqlSessionFactory
 *          Once created, the SqlSessionFactory should exist for the duration of your application execution.
 *          It's a best practice to not rebuild the SqlSessionFactory multiple times in an application run.
 *      SqlSession
 *          Each thread should have its own instance of SqlSession. Instances of SqlSession are not to be shared and are not thread safe.
 *          Therefore the best scope is request or method scope.
 *      Mapper Instances
 *          Mappers are interfaces that you create to bind to your mapped statements. Instances of the mapper interfaces are acquired from the SqlSession.
 *          That is, they should be requested within the method that they are used, and then be discarded.
 *
 * @author: pickjob@126.com
 * @date: 2024-09-13
 */
public class MyBatisShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        Configuration configuration = getConfiguration();
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
            Person person = new Person();
            person.setName("First");
            logger.info("Person after insert: {}", person);
            personMapper.insertOne(person);
            logger.info("Person after insert: {}", person);
            personMapper.selectAll().forEach(p -> logger.info("Person: {}", p));
        }
    }

    private static Configuration getConfiguration() throws Exception {
        HikariConfig config = new HikariConfig();
        config.setPoolName("SQLitePool");
        config.setDriverClassName("org.sqlite.JDBC");
        config.setJdbcUrl("jdbc:sqlite:sample.db");
        config.setConnectionTestQuery("SELECT 1");
        HikariDataSource dataSource = new HikariDataSource(config);
        try (Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();) {
            statement.execute("DROP TABLE IF EXISTS PERSON;");
            statement.execute("CREATE TABLE person(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);");
        }
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setUseGeneratedKeys(true);
        configuration.getTypeAliasRegistry().registerAliases("app.framework.mybatis.domain");
        configuration.addMappers("app.framework.mybatis.mapper");
        return configuration;
    }
}