package app.standard.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * JDBC:
 *      sqlite:
 *          org.sqlite.JDBC
 *          jdbc:sqlite::memory:
 *          jdbc:sqlite:/path/to/file/db
 *      postgresql:
 *          org.postgresql.Driver
 *          jdbc:postgresql://<Server.host>:5432/<SID>?ssl=false
 *      mysql:
 *          jdbc:mysql://<Server.host>:3306/<SID>?autoReconnect=true&useUnicode=true&characterEncoding=utf8useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
 *      oracle:
 *          jdbc:oracle:thin:@<Server.host>:1521:<SID>
 *      sql server:
 *          jdbc:sqlserver://<Server.host>:1433;databaseName=<dbName>
 *
 * Establishing Connections
 *      DriverManager.getConnection
 *      DataSource.getConnection
 * Creating Statements
 *      Statement
 *          PreparedStatement
 *              PreparedStatement.addBatch
 *              PreparedStatement.executeBatch
 *          CallableStatement
 * Executing Queries
 *      execute:ResultSet
 *      executeQuery:ResultSet
 *      executeUpdate:integer
 * Processing ResultSet Objects
 *      ResultSet.next
 *      ResultSet.getString
 *      ResultSet.getInt
 * Closing Connections
 *      Using Transactions
 *          con.setAutoCommit(false);
 *          con.commit();
 *          con.setAutoCommit(true);
 * RowSet
 *      JdbcRowSet
 *      CachedRowSet
 *      WebRowSet
 *      JoinRowSet
 *      FilteredRowSet
 *
 * @author: pickjob@126.com
 * @date: 2024-09-08
 */
public class JDBCShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement statement = connection.createStatement();) {
            statement.setQueryTimeout(30);
            statement.executeUpdate("DROP TABLE IF EXISTS person");
            statement.executeUpdate("CREATE TABLE person(id INTEGER, name TEXT)");
            statement.executeUpdate("INSERT INTO person VALUES(1, 'leo')");
            statement.executeUpdate("INSERT INTO person VALUES(2, 'yui')");
            ResultSet rs = statement.executeQuery("select * from person");
            showResultSetInfo(rs);
            while(rs.next()) {
                logger.info("id: {} ", rs.getInt("id"));
                logger.info("name: {} ", rs.getString("name"));
            }
        }
    }

    private static void showResultSetInfo(ResultSet rs) throws SQLException {
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        for (int i=1; i <= resultSetMetaData.getColumnCount(); i++) {
            logger.info("Column name: {}({}), type: {}, type_name: {}({}), class_name: {}",
                    resultSetMetaData.getColumnName(i),
                    resultSetMetaData.getColumnLabel(i),
                    resultSetMetaData.getColumnType(i),
                    resultSetMetaData.getColumnTypeName(i),
                    resultSetMetaData.getPrecision(i),
                    resultSetMetaData.getColumnClassName(i)
            );
        }
    }
}